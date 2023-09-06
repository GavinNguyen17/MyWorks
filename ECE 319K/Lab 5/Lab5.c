// Lab5.c starter program EE319K Lab 5, Spring 2022
// Runs on TM4C123
// Gavin Nguyen and Rodrigo Romero
// Last Modified: 1/11/2021

/* Option A1, connect LEDs to PB5-PB0, switches to PA5-3, walk LED PF321
   Option A2, connect LEDs to PB5-PB0, switches to PA4-2, walk LED PF321
   Option A6, connect LEDs to PB5-PB0, switches to PE3-1, walk LED PF321
   Option A5, connect LEDs to PB5-PB0, switches to PE2-0, walk LED PF321
   Option B4, connect LEDs to PE5-PE0, switches to PC7-5, walk LED PF321
   Option B3, connect LEDs to PE5-PE0, switches to PC6-4, walk LED PF321
   Option B1, connect LEDs to PE5-PE0, switches to PA5-3, walk LED PF321
   Option B2, connect LEDs to PE5-PE0, switches to PA4-2, walk LED PF321
  */
// east/west red light connected to bit 5
// east/west yellow light connected to bit 4
// east/west green light connected to bit 3
// north/south red light connected to bit 2
// north/south yellow light connected to bit 1
// north/south green light connected to bit 0
// pedestrian detector connected to most significant bit (1=pedestrian present)
// north/south car detector connected to middle bit (1=car present)
// east/west car detector connected to least significant bit (1=car present)
// "walk" light connected to PF3-1 (built-in white LED)
// "don't walk" light connected to PF3-1 (built-in red LED)
#include <stdint.h>
#include "SysTick.h"
#include "Lab5grader.h"
#include "../inc/tm4c123gh6pm.h"
typedef enum StateId {goS, waitS, ALLSS, goWlk, BLR1, BLO1, BLR2, BLO2, BLR3, BLO3, BLR4, ALLSWLK, goW, waitW, ALLSW} StateID_t;
#define T5sec 10
// put both EIDs in the next two lines
char EID1[] = "rr47552"; //  ;replace abc123 with your EID
char EID2[] = "gpn235"; //  ;replace abc123 with your EID

void DisableInterrupts(void);
void EnableInterrupts(void);

struct State {
	uint8_t OutE;
	uint8_t OutF;
uint32_t Delay;
uint32_t Next[8];
};

typedef const struct State State_t;

State_t FSM[15]={
{0x21, 0x02, T5sec,{goS,waitS,goS,waitS,waitS,waitS,waitS,waitS}},  //goS
{0x22, 0x02, T5sec,{ALLSS,ALLSS,ALLSS,ALLSS,ALLSS,ALLSS,ALLSS,ALLSS}}, //waitS
{0x24, 0x02, T5sec,{goS,goW,goS,goW,goWlk,goWlk,goWlk,goWlk}}, //ALLSS
{0x24, 0x0E, T5sec,{BLR1,BLR1,BLR1,BLR1,goWlk,BLR1,BLR1,BLR1}}, //goWlk
{0x24, 0x02, T5sec%20,{BLO1,BLO1,BLO1,BLO1,BLO1,BLO1,BLO1,BLO1}}, //BLR1
{0x24, 0x00, T5sec%20,{BLR2,BLR2,BLR2,BLR2,BLR2,BLR2,BLR2,BLR2}}, //BLO1
{0x24, 0x02, T5sec%20,{BLO2,BLO2,BLO2,BLO2,BLO2,BLO2,BLO2,BLO2}}, //BLR2
{0x24, 0x00, T5sec%20,{BLR3,BLR3,BLR3,BLR3,BLR3,BLR3,BLR3,BLR3}}, //BLO2
{0x24, 0x02, T5sec%20,{BLO3,BLO3,BLO3,BLO3,BLO3,BLO3,BLO3,BLO3}}, //BLR3
{0x24, 0x00, T5sec%20,{BLR4,BLR4,BLR4,BLR4,BLR4,BLR4,BLR4,BLR4}}, //BLO3
{0x24, 0x02, T5sec%20,{ALLSWLK,ALLSWLK,ALLSWLK,ALLSWLK,ALLSWLK,ALLSWLK,ALLSWLK,ALLSWLK}}, //BLR4
{0x24, 0x02, T5sec,{goW,goW,goS,goW,goWlk,goW,goS,goW}}, //ALLSWLK
{0x0C, 0x02, T5sec,{goW,goW,	waitW,waitW,waitW,waitW,waitW,waitW}}, //goW
{0x14, 0x02, T5sec,{ALLSW,ALLSW,ALLSW,ALLSW,ALLSW,ALLSW,	ALLSW,ALLSW}}, //waitW
{0x24, 0x02, T5sec,{goW,goW,goS,goS,goWlk,goWlk,goS,goS}} //ALLSW
};


int main(void){ 
  DisableInterrupts();
  TExaS_Init(GRADER);
  SysTick_Init();   // Initialize SysTick for software waits
  // initialize system
	SYSCTL_RCGCGPIO_R |=0x34; 
	GPIO_PORTC_DIR_R = 0x00; 
	GPIO_PORTC_DEN_R |=0x70;
	GPIO_PORTE_DIR_R |= 0x3F;
	GPIO_PORTE_DEN_R |= 0x3F;
	GPIO_PORTF_DIR_R |=0x0E;
	GPIO_PORTF_DEN_R |=0x0E;
	uint8_t CS, In;
  EnableInterrupts(); 
	
	//Initialize(void)();
	CS = goS;

  while(1){
    GPIO_PORTE_DATA_R =FSM[CS].OutE;
		GPIO_PORTF_DATA_R =FSM[CS].OutF;
		SysTick_Wait10ms(FSM[CS].Delay);
		In =(GPIO_PORTC_DATA_R & 0x70)>>4;
		CS=FSM[CS].Next[In];


  }
}

// SysTick.c
// Runs on TM4C123
// Put your names here
// Last Modified: 1/11/2022
#include <stdint.h>
#include "../inc/tm4c123gh6pm.h"

// Initialize SysTick with busy wait running at bus clock.
#define NVIC_ST_CTRL_COUNT      0x00010000  // Count flag
#define NVIC_ST_CTRL_CLK_SRC    0x00000004  // Clock Source
#define NVIC_ST_CTRL_INTEN      0x00000002  // Interrupt enable
#define NVIC_ST_CTRL_ENABLE     0x00000001  // Counter mode
#define NVIC_ST_RELOAD_M        0x00FFFFFF  // Counter load value

// Initialize SysTick with busy wait running at bus clock.
void SysTick_Init(void){
  NVIC_ST_CTRL_R = 0;                   // disable SysTick during setup
  NVIC_ST_RELOAD_R = NVIC_ST_RELOAD_M;  // maximum reload value
  NVIC_ST_CURRENT_R = 0;                // any write to current clears it
                                        // enable SysTick with core clock
  NVIC_ST_CTRL_R = NVIC_ST_CTRL_ENABLE+NVIC_ST_CTRL_CLK_SRC;
}
// Time delay using busy wait.
// The delay parameter is in units of the core clock. (units of 20 nsec for 50 MHz clock)
void SysTick_Wait(uint32_t delay){
  volatile uint32_t elapsedTime;
  uint32_t startTime = NVIC_ST_CURRENT_R;
  do{
    elapsedTime = (startTime-NVIC_ST_CURRENT_R)&0x00FFFFFF;
  }
  while(elapsedTime <= delay);
}
// Time delay using busy wait.
// This assumes 50 MHz system clock.
void SysTick_Wait10ms(uint32_t delay){
  uint32_t i;
  for(i=0; i<delay; i++){
    SysTick_Wait(800000);  // wait 10ms (assumes 80 MHz clock)
 }
}