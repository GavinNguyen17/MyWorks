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
// PortB pins5-0 is the DAC
  SYSCTL_RCGCGPIO_R |= 0x02;           // activate port B
  while((SYSCTL_PRGPIO_R&0x02)==0){}; // allow time for clock to start
  GPIO_PORTB_AMSEL_R &= ~0x3F;// disable analog on PB3-0
  GPIO_PORTB_PCTL_R &= ~0x00FFFFFF; // configure PB3-0 as GPIO
  GPIO_PORTB_DIR_R |= 0x3F;   // make PB3-0 out
  GPIO_PORTB_AFSEL_R &= 0x3F; // disable alt funct on PB3-0
  GPIO_PORTB_DEN_R |= 0x3F;   // enable digital I/O on PB3-0
  GPIO_PORTB_DR8R_R |= 0x3F;  // enable 8 mA drive on PB3-0
}

// **************DAC_Out*********************
// output to DAC
// Input: 6-bit data, 0 to 15 
// Output: none
void DAC_Out(uint32_t data){
	    GPIO_PORTB_DATA_R = data;
}	 