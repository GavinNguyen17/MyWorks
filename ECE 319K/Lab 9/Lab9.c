// Lab9.c
// Runs on TM4C123
// Student names: Rodrigo Romero, Gavin Nguyen
// Last modification date: change this to the last modification date or look very silly
// Last Modified: 1/12/2022 

// Analog Input connected to PD2=ADC5
// displays on Sitronox ST7735
// PF3, PF2, PF1 are heartbeats
// UART1 on PC4-5
// * Start with where you left off in Lab8. 
// * Get Lab8 code working in this project.
// * Understand what parts of your main have to move into the UART1_Handler ISR
// * Rewrite the SysTickHandler
// * Implement the s/w Fifo on the receiver end 
//    (we suggest implementing and testing this first)

//PC5 of this computer is connected to PC4 of the other computer & then 
//PC4 of this computer is connected to PC5 0f the other computer
// grounds conected
//for main loop get the info from fifo and send to lcd
//for systick handler send info to fifo



#include <stdint.h>

#include "ST7735.h"
#include "TExaS.h"
#include "ADC.h"
#include "print.h"
#include "../inc/tm4c123gh6pm.h"
#include "UART.h"
#include "Fifo.h"
#define PC54                  (*((volatile uint32_t *)0x400060C0)) // bits 5-4
#define PF321                 (*((volatile uint32_t *)0x40025038)) // bits 3-1
// TExaSdisplay logic analyzer shows 7 bits 0,PC5,PC4,PF3,PF2,PF1,0 
void LogicAnalyzerTask(void){
  UART0_DR_R = 0x80|PF321|PC54; // sends at 10kHz
}

//*****the first three main programs are for debugging *****
// main1 tests just the ADC and slide pot, use debugger to see data
// main2 adds the LCD to the ADC and slide pot, ADC data is on Nokia
// main3 adds your convert function, position data is no Nokia

void DisableInterrupts(void); // Disable interrupts
void EnableInterrupts(void);  // Enable interrupts
#define STX 0x02
#define ETX 0x03
#define LF  0x0A
#define CR  0x0D
#define PF1       (*((volatile uint32_t *)0x40025008))
#define PF2       (*((volatile uint32_t *)0x40025010))
#define PF3       (*((volatile uint32_t *)0x40025020))
uint32_t Data;      // 12-bit ADC
uint32_t Position;  // 32-bit fixed-point 0.001 cm
int32_t TxCounter = 0;
extern char *p;

// Initialize Port F so PF1, PF2 and PF3 are heartbeats
void PortF_Init(void){
  SYSCTL_RCGCGPIO_R |= 0x20;      // activate port F
  while((SYSCTL_PRGPIO_R&0x20) != 0x20){};
  GPIO_PORTF_DIR_R |=  0x0E;   // output on PF3,2,1 (built-in LED)
  GPIO_PORTF_PUR_R |= 0x10;
  GPIO_PORTF_DEN_R |=  0x1E;   // enable digital I/O on PF
}
// **************SysTick_Init*********************
// Initialize Systick periodic interrupts
// Input: interrupt period
//        Units of period are 12.5ns
//        Maximum is 2^24-1
//        Minimum is determined by length of ISR
// Output: none
uint32_t flag = 0;
void SysTick_Init(uint32_t period){
 // write this (copy from Lab 8)
	DisableInterrupts();
	NVIC_ST_CTRL_R = 0;
	NVIC_ST_RELOAD_R = period-1; //should be 10 Hz
	NVIC_ST_CURRENT_R = 0;
	NVIC_SYS_PRI3_R = (NVIC_SYS_PRI3_R&0x00FFFFFF)|0x40000000;
	NVIC_ST_CTRL_R = 0x07;
	EnableInterrupts();
}

// Get fit from excel and code the convert routine with the constants
// from the curve-fit
uint32_t Convert(uint32_t x){
 // write this (copy from Lab 8)
    x=((488)*x)+(7382);
	x=x/1000;
  return x;
}


// final main program for bidirectional communication
// Sender sends using SysTick Interrupt, Tx uses busy-wait
// Receiver receives using RX interrrupts
int main(void){  
   DisableInterrupts();
  //PLL_Init();  // simulation
  TExaS_Init(&LogicAnalyzerTask); // real board
  ST7735_InitR(INITR_REDTAB);
  ADC_Init();    // initialize to sample ADC
  PortF_Init();
  UART_Init();   // your initialize UART
//Enable SysTick Interrupt by calling SysTick_Init()
  SysTick_Init(80000000/10); // Interrupt at 10Hz
  // other initializations as needed
  EnableInterrupts();
  ST7735_PlotClear(0,2000); // 0 to 200, 0.01cm
	ST7735_SetCursor(0,0);
	ST7735_OutString("Lab 9, d = ");
 while(1){ // one time through the loop every 100 ms
// get message from software FIFO
		uint32_t val, sum=0;
		Fifo_Get(p);
		val  = *p;
		while(val == 0x3C){
			//Fifo_Get(p);
			Fifo_Get(p);
			//com  = *p;
			val  = ((*p)-0x30)*1000;
			sum = sum+val;
			Fifo_Get(p);
			Fifo_Get(p);
			val  = *p;
			if(val>9999){
			break;
			}
			val  = ((*p)-0x30)*100;
			sum = sum+val;
			Fifo_Get(p);
			//val  = *p;
			val  = ((*p)-0x30)*10;
			sum = sum+val;
			Fifo_Get(p);
			//val  = *p;
			val  = ((*p)-0x30);
			sum = sum+val;
			ST7735_SetCursor(11,0);
			LCD_OutFix(sum);// output to LCD
			ST7735_SetCursor(16,0);
			ST7735_OutString("cm");
			ST7735_SetCursor(0,1);
			ST7735_OutString("                 ");
			
			PF3 ^= 0x08;// Heartbeat when message received 
		}   
  }
}







void SysTick_Handler(void){ // every 100 ms
 //Similar to Lab8 except rather than grab sample and put in mailbox //uart outchar
 //format message and transmit 
	Data = Convert(ADC_In());
	UART_OutChar('<');
	UART_OutChar((Data/1000)+0x30);
	Data = Data%1000;
	UART_OutChar('.');
	UART_OutChar((Data/100)+0x30);
	Data = Data%100;
	UART_OutChar((Data/10)+0x30);
	Data = Data%10;
	UART_OutChar((Data)+0x30);
	UART_OutChar('>');
	UART_OutChar(0x0A);
	//find digits 
  PF1 ^= 0x02;  // Heartbeat on transmit message
}


uint32_t Status[20];             // entries 0,7,12,19 should be false, others true
char GetData[10];  // entries 1 2 3 4 5 6 7 8 should be 1 2 3 4 5 6 7 8
int mainfifo(void){ // Make this main to test Fifo
  Fifo_Init(); // Assuming a buffer of size 6
  for(;;){
    Status[0]  = Fifo_Get(&GetData[0]);  // should fail,    empty
    Status[1]  = Fifo_Put(1);            // should succeed, 1 
    Status[2]  = Fifo_Put(2);            // should succeed, 1 2
    Status[3]  = Fifo_Put(3);            // should succeed, 1 2 3
    Status[4]  = Fifo_Put(4);            // should succeed, 1 2 3 4
    Status[5]  = Fifo_Put(5);            // should succeed, 1 2 3 4 5
    Status[6]  = Fifo_Put(6);            // should succeed, 1 2 3 4 5 6
    Status[7]  = Fifo_Put(7);            // should fail,    1 2 3 4 5 6 
    Status[8]  = Fifo_Get(&GetData[1]);  // should succeed, 2 3 4 5 6
    Status[9]  = Fifo_Get(&GetData[2]);  // should succeed, 3 4 5 6
    Status[10] = Fifo_Put(7);            // should succeed, 3 4 5 6 7
    Status[11] = Fifo_Put(8);            // should succeed, 3 4 5 6 7 8
    Status[12] = Fifo_Put(9);            // should fail,    3 4 5 6 7 8 
    Status[13] = Fifo_Get(&GetData[3]);  // should succeed, 4 5 6 7 8
    Status[14] = Fifo_Get(&GetData[4]);  // should succeed, 5 6 7 8
    Status[15] = Fifo_Get(&GetData[5]);  // should succeed, 6 7 8
    Status[16] = Fifo_Get(&GetData[6]);  // should succeed, 7 8
    Status[17] = Fifo_Get(&GetData[7]);  // should succeed, 8
    Status[18] = Fifo_Get(&GetData[8]);  // should succeed, empty
    Status[19] = Fifo_Get(&GetData[9]);  // should fail,    empty
  }
}

