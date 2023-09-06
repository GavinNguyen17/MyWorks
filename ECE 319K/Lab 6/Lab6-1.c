// Sound.c
// Sound.c
// This module contains the SysTick ISR that plays sound
// Runs on TM4C123
// Program written by: Rodrigo Romero, Gavin Nguyen
// Date Created: 3/6/17 
// Last Modified: 1/11/22 
// Lab number: 6
// Hardware connections
// TO STUDENTS "REMOVE THIS LINE AND SPECIFY YOUR HARDWARE********

// Code files contain the actual implemenation for public functions
// this file also contains an private functions and private data
#include <stdint.h>
#include "dac.h"
#include "../inc/tm4c123gh6pm.h"
uint8_t idx = 0;
const uint8_t wave[64] = {32, 35, 38, 41, 44, 47, 49, 52, 
													54, 56, 58, 59, 61, 62, 62, 63, 
													63, 63, 62, 62, 61, 59, 58, 56, 
													54, 52, 49, 47, 44, 41, 38, 35, 
													32, 29, 26, 23, 20, 17, 15, 12, 
													10, 8,  6,  5,  3,  2,  2,  1, 
													1,  1,  2,  2,  3,  5,  6,  8, 
													10, 12, 15, 17, 20, 23, 26, 29};

// **************Sound_Init*********************
// Initialize digital outputs and SysTick timer
// Called once, with sound/interrupts initially off
// Input: none
// Output: none
void Sound_Init(void){
	DAC_Init();
	NVIC_ST_CTRL_R = 0;     	// disable SysTick during setup
	NVIC_ST_RELOAD_R = 0;// reload value
	NVIC_ST_CURRENT_R = 0;  	// any write to current clears it
	NVIC_SYS_PRI3_R = (NVIC_SYS_PRI3_R&0x00FFFFFF)|0x40000000; // priority 2          
	NVIC_ST_CTRL_R = 0x07; 
	
}

// **************Sound_Start*********************
// Start sound output, and set Systick interrupt period 
// Sound continues until Sound_Start called again, or Sound_Off is called
// This function returns right away and sound is produced using a periodic interrupt
// Input: interrupt period
//           Units of period to be determined by YOU
//           Maximum period to be determined by YOU
//           Minimum period to be determined by YOU
//         if period equals zero, disable sound output
// Output: none
void Sound_Start(uint32_t period){
		NVIC_ST_CTRL_R = 0x07;
		NVIC_ST_RELOAD_R=period-1;
		//NVIC_ST_CURRENT_R = 0;
}

// **************Sound_Voice*********************
// Change voice
// EE319K optional
// Input: voice specifies which waveform to play
//           Pointer to wave table
// Output: none
void Sound_Voice(const uint8_t *voice){
  
}
// **************Sound_Off*********************
// stop outputing to DAC
// Output: none
void Sound_Off(void){
	NVIC_ST_CTRL_R=0x05;
	NVIC_ST_RELOAD_R=8000;

}
// **************Sound_GetVoice*********************
// Read the current voice
// EE319K optional
// Input: 
// Output: voice specifies which waveform to play
//           Pointer to current wavetable
const uint8_t *Sound_GetVoice(void){
  return 0; // replace
}
#define PF4 (*((volatile uint32_t *)0x40025040))
#define PF3 (*((volatile uint32_t *)0x40025020))
#define PF2 (*((volatile uint32_t *)0x40025010))
#define PF1 (*((volatile uint32_t *)0x40025008))
#define PF0 (*((volatile uint32_t *)0x40025004))

// Interrupt service routine
// Executed every 12.5ns*(period)
void SysTick_Handler(void){
	DAC_Out(wave[idx]);
	idx=(idx+1)%64;

}


// DAC.c
// This software configures DAC output
// Lab 6 requires 6 bits for the DAC
// Runs on LM4F120 or TM4C123
// Program written by: put your names here
// Date Created: 3/6/17 
// Last Modified: 1/11/22 
// Lab number: 6
// Hardware connections
// TO STUDENTS "REMOVE THIS LINE AND SPECIFY YOUR HARDWARE********

#include <stdint.h>
#include "../inc/tm4c123gh6pm.h"
// Code files contain the actual implemenation for public functions
// this file also contains an private functions and private data



// **************DAC_Init*********************
// Initialize 6-bit DAC, called once 
// Input: none
// Output: none
void DAC_Init(void){
  SYSCTL_RCGCGPIO_R |=0x02;
	__nop();
	__nop();
	__nop();
	__nop();

	GPIO_PORTB_DIR_R |= 0x3F;
	GPIO_PORTB_DEN_R |= 0x3F;

}

// **************DAC_Out*********************
// output to DAC
// Input: 6-bit data, 0 to 63 
// Input=n is converted to n*3.3V/63
// Output: none
void DAC_Out(uint32_t data){
	GPIO_PORTB_DATA_R = data;
}
// Lab6.c
// Runs on TM4C123
// Use SysTick interrupts to implement a 4-key digital piano
// EE319K lab6 starter
// Program written by: put your names here
// Date Created: 3/6/17 
// Last Modified: 1/11/22  
// Lab number: 6
// Hardware connections
// TO STUDENTS "REMOVE THIS LINE AND SPECIFY YOUR HARDWARE********


#include <stdint.h>
#include "../inc/tm4c123gh6pm.h"
#include "Sound.h"
#include "Key.h"
#include "Music.h"
#include "Lab6Grader.h"
// put both EIDs in the next two lines
char EID1[] = "gpn235"; //  ;replace abc123 with your EID
char EID2[] = "rr47552"; //  ;replace abc123 with your EID

void DisableInterrupts(void); // Disable interrupts
void EnableInterrupts(void);  // Enable interrupts
void DAC_Init(void);          // your lab 6 solution
void DAC_Out(uint8_t data);   // your lab 6 solution
uint8_t Testdata;

// lab video Lab6_voltmeter, Program 6.1
// A simple program that outputs sixteen DAC values. Use this main if you have a voltmeter.
const uint32_t Inputs[16]={0,1,7,8,15,16,17,18,31,32,33,47,48,49,62,63};
int voltmetermain(void){ uint32_t i;  
  DisableInterrupts();
  TExaS_Init(SCOPE);    
  LaunchPad_Init();
  DAC_Init(); // your lab 6 solution
  i = 0;
  EnableInterrupts();
  while(1){                
    Testdata = Inputs[i];
    DAC_Out(Testdata); // your lab 6 solution
    i=(i+1)&0x0F;  // <---put a breakpoint here
  }
}

// DelayMs
//  - busy wait n milliseconds
// Input: time to wait in msec
// Outputs: none
void static DelayMs(uint32_t n){
  volatile uint32_t time;
  while(n){
    time = 6665;  // 1msec, tuned at 80 MHz
    while(time){
      time--;
    }
    n--;
  }
}
// lab video Lab6_static. Program 6.2
// A simple program that outputs sixteen DAC values. 
// Use this main if you do not have a voltmeter. 
// Connect PD3 to your DACOUT and observe the voltage using TExaSdisplay in scope mode.
int staticmain(void){  
  uint32_t last,now,i;  
  DisableInterrupts();
  TExaS_Init(SCOPE);    // bus clock at 80 MHz
  LaunchPad_Init();
  DAC_Init(); // your lab 6 solution
  i = 0;
  EnableInterrupts();
  last = LaunchPad_Input();
  while(1){                
    now = LaunchPad_Input();
    if((last != now)&&now){
      Testdata = Inputs[i];
      DAC_Out(Testdata); // your lab 6 solution
      i=(i+1)&0x0F;
    }
    last = now;
    DelayMs(25);   // debounces switch
  }
}


     
int main(void){       
  DisableInterrupts();
  TExaS_Init(LOGICANALYZERB);    // bus clock at 80 MHz
  Key_Init();
  LaunchPad_Init();
  Sound_Init();
  Music_Init();
  EnableInterrupts();
  while(1){
	//voltmetermain();
		if(NVIC_ST_CURRENT_R==0){
			GPIO_PORTF_DATA_R ^=0x02;
			}

	uint8_t KeyVal;
	KeyVal = Key_In();
	uint16_t period;
	if(KeyVal == 0x00){
		Music_StopSong();
		Sound_Off();
	}
	if(KeyVal == 0x01){
	period=EF0;
		Sound_Start(period);
}
if(KeyVal == 0x02){
period=G0;
	Sound_Start(period);
}
if(KeyVal == 0x04){
period=BF0;
	Sound_Start(period);
}
if(KeyVal == 0x08){
period=C0;
	Sound_Start(period);
}
if(KeyVal == 0x10){
	Music_PlaySong();
}


  }             
}


// Key.c
// This software configures the off-board piano keys
// Lab 6 requires a minimum of 4 keys, but you could have more
// Runs on LM4F120 or TM4C123
// Program written by: put your names here
// Date Created: 3/6/17 
// Last Modified: 1/11/22  
// Lab number: 6
// Hardware connections
// TO STUDENTS "REMOVE THIS LINE AND SPECIFY YOUR HARDWARE********

// Code files contain the actual implemenation for public functions
// this file also contains an private functions and private data
#include <stdint.h>
#include "../inc/tm4c123gh6pm.h"

// **************Key_Init*********************
// Initialize piano key inputs on PA5-2 or PE3-0
// Input: none 
// Output: none
void Key_Init(void){ volatile uint32_t delay;
	SYSCTL_RCGCGPIO_R |= 0x30;
	__nop();
	__nop();
	__nop();
	__nop();
	GPIO_PORTF_DIR_R |= 0x0E;
	GPIO_PORTF_DEN_R |= 0x0E;
	GPIO_PORTE_DIR_R |= 0x00;
	GPIO_PORTE_DEN_R |= 0x1F;

}
// **************Key_In*********************
// Input from piano key inputs on PA5-2 or PE3-0
// Input: none 
// Output: 0 to 15 depending on keys
//   0x01 is just Key0, 0x02 is just Key1, 0x04 is just Key2, 0x08 is just Key3
uint32_t Key_In(void){ 
uint8_t  key0,key1,key2,key3,key4,sum;
		key0 = (GPIO_PORTE_DATA_R & 0x01); //0000.0001
		key1 = (GPIO_PORTE_DATA_R & 0x02); //0000.0010
		key2 = (GPIO_PORTE_DATA_R & 0x04); //0000.0100
		key3 = (GPIO_PORTE_DATA_R & 0x08); //0000.1000
		key4 = (GPIO_PORTE_DATA_R & 0x10);
		sum  = key0+key1+key2+key3+key4;
	return (sum);

}


//------------LaunchPad_Init------------
// Initialize Switch input and LED output
// Input: none
// Output: none
void LaunchPad_Init(void){
// implement if needed
}


//------------LaunchPad_Input------------
// Input from Switches, 
// Convert hardware negative logic to software positive logic 
// Input: none
// Output: 0x00 none
//         0x01 SW2 pressed (from PF4)
//         0x02 SW1 pressed (from PF1)
//         0x03 both SW1 and SW2 pressed
uint8_t LaunchPad_Input(void){
// implement if needed
  return 0; // replace   
}
//------------LaunchPad__Output------------
// Output to LaunchPad LEDs 
// Positive logic hardware and positive logic software
// Input: 0 off, bit0=red,bit1=blue,bit2=green
// Output: none
void LaunchPad_Output(uint8_t data){  // write three outputs bits of PORTF
// implement if needed

}

// Music.c
// playing your favorite song.
//
// For use with the TM4C123
// EE319K lab6 extra credit
// Program written by: Rodrigo Romero, Gavin Nguyen
// 1/11/22

#include "Sound.h"
#include "DAC.h"
#include <stdint.h>
#include "../inc/tm4c123gh6pm.h"
#define bus 80000000
#define q (bus/2) //quarter
#define h (q*2) //half
#define j (q/20) //wait
#define i (q/2) //eighth
#define R 0 //0 Hz Rest
typedef enum  StateID{a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, 
											b1, b2, b3, b4, b5, b6, b7, b8, b9, b10,
											c1, c2, c3, c4, c5, c6, c7, c8, c9, c10,
											d1, d2, d3, d4, d5, d6, d7, d8, d9, d10} StateID_t;

struct State{
	uint32_t Note;
	uint32_t Length; 
};

typedef const struct State Barney_t;
uint8_t I = 0;

Barney_t FSM[40]={
{G, q}, {E, q},   {G, h-j}, {R, j},    {G, q}, {E, q},  {G, h}, {A,q},  {G,q},   {F, q}, 
{E, q}, {D, q},   {E, q},   {F, q},    {E, i}, {F,i},   {G, q}, {C0,q}, {R, j},  {C0,q-j}, 
{R, j}, {C0,i-j}, {R, j},   {C0, i-j}, {R, j}, {C0, i}, {D, i}, {E, i}, {F, i},  {G, h-j}, 
{R, j}, {G, q},   {D, q-j}, {R, j},    {D, q}, {F,q},   {E,q},  {D,q},  {C0, h}, {0, 0}
};



void DisableInterrupts(void); // Disable interrupts
void EnableInterrupts(void);  // Enable interrupts

void Music_Init(uint32_t period){
	volatile int delay; 
  void DisableInterrupts(void);
  SYSCTL_RCGCTIMER_R |= 0x01;   // 0) activate TIMER0
  delay = 0;        // user function
  TIMER0_CTL_R = 0x00000000;    // 1) disable TIMER0A during setup
  TIMER0_CFG_R = 0x00000000;    // 2) configure for 32-bit mode
  TIMER0_TAMR_R = 0x00000002;   // 3) configure for periodic mode, default down-count settings
  TIMER0_TAILR_R = 50;//period-1;    // 4) reload value
  TIMER0_TAPR_R = 0;            // 5) bus clock resolution
  TIMER0_ICR_R = 0x00000001;    // 6) clear TIMER0A timeout flag
  TIMER0_IMR_R = 0x00000001;    // 7) arm timeout interrupt
  NVIC_PRI4_R = (NVIC_PRI4_R&0x00FFFFFF)|0x80000000; // 8) priority 4
// interrupts enabled in the main program after all devices initialized
// vector number 35, interrupt number 19
  NVIC_EN0_R = 1<<19;           // 9) enable IRQ 19 in NVIC  ENABLE TAILR WITH THIS ********
  TIMER0_CTL_R = 0x00000001;    // 10) enable TIMER0A
  void EnableInterrupts(void);
}



	
// Play song, while button pushed or until end
void Music_PlaySong(Barney_t FSM[]){
	 TIMER0_CTL_R = 0x00000001;    // enable TIMER0A
}

// Stop song
void Music_StopSong(void){
	TIMER0_CTL_R = 0x00000000; //stops the timer
	I=0;
}

void Timer0A_Handler(void){
	TIMER0_ICR_R = TIMER_ICR_TATOCINT;
	//check flag to see if playing song
	Sound_Start(FSM[I].Note);
	TIMER0_TAILR_R = FSM[I].Length;
	I++;
	if(TIMER0_TAILR_R == 0){
		TIMER0_CTL_R = 0x00000000;
		//Music_StopSong();
		//I = 0; //need to do something to reset I but make it so that it doesn't repeat
	}
}

