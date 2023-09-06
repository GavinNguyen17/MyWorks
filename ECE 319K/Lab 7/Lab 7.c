; LCD.s
; Student names: change this to your names or look very silly
; Last modification date: change this to the last modification date or look very silly

; Runs on TM4C123
; Use SSI0 to send an 8-bit code to the ST7735 160x128 pixel LCD.

; As part of Lab 7, students need to implement these writecommand and writedata
; This driver assumes two low-level LCD functions

; Backlight (pin 10) connected to +3.3 V
; MISO (pin 9) unconnected
; SCK (pin 8) connected to PA2 (SSI0Clk)
; MOSI (pin 7) connected to PA5 (SSI0Tx)
; TFT_CS (pin 6) connected to PA3 (SSI0Fss)
; CARD_CS (pin 5) unconnected
; Data/Command (pin 4) connected to PA6 (GPIO)
; RESET (pin 3) connected to PA7 (GPIO)
; VCC (pin 2) connected to +3.3 V
; Gnd (pin 1) connected to ground

GPIO_PORTA_DATA_R       EQU   0x400043FC
SSI0_DR_R               EQU   0x40008008
SSI0_SR_R               EQU   0x4000800C

      EXPORT   writecommand
      EXPORT   writedata

      AREA    |.text|, CODE, READONLY, ALIGN=2
      THUMB
      ALIGN

; The Data/Command pin must be valid when the eighth bit is
; sent.  The SSI module has hardware input and output FIFOs
; that are 8 locations deep.  Based on the observation that
; the LCD interface tends to send a few commands and then a
; lot of data, the FIFOs are not used when writing
; commands, and they are used when writing data.  This
; ensures that the Data/Command pin status matches the byte
; that is actually being transmitted.
; The write command operation waits until all data has been
; sent, configures the Data/Command pin for commands, sends
; the command, and then waits for the transmission to
; finish.
; The write data operation waits until there is room in the
; transmit FIFO, configures the Data/Command pin for data,
; and then adds the data to the transmit FIFO.
; NOTE: These functions will crash or stall indefinitely if
; the SSI0 module is not initialized and enabled.

; This is a helper function that sends an 8-bit command to the LCD.
; Input: R0  8-bit command to transmit
; Output: none
; Assumes: SSI0 and port A have already been initialized and enabled
writecommand
;; --UUU-- Code to write a command to the LCD
;1) Read SSI0_SR_R and check bit 4, 
;2) If bit 4 is high, loop back to step 1 (wait for BUSY bit to be low)
;3) Clear D/C=PA6 to zero
;4) Write the command to SSI0_DR_R
;5) Read SSI0_SR_R and check bit 4, 
;6) If bit 4 is high, loop back to step 5 (wait for BUSY bit to be low)
		 PUSH {R4-R8, LR}
Cloop1 	 LDR R4, =SSI0_SR_R; 0001.0000
		 LDR R8, [R4]
		 AND R8, #0x10
		 MOV R5, R8
		 LSR R5, #4
		 CMP R5, #0x01
		 BEQ Cloop1
		 LDR R6, =GPIO_PORTA_DATA_R
		 LDR R7, [R6]; 0100.0000
		 BIC R7, #0x40
		 STR R7, [R6]
		 LDR R6, =SSI0_DR_R
		 STR R0, [R6]
Cloop2 	LDR R4, =SSI0_SR_R
		 LDR R8, [R4]
		 AND R8, #0x10
		 MOV R5, R8
		 LSR R5, #4
		 CMP R5, #0x01
		 BEQ Cloop2
		POP{R4-R8, LR}

    
    
    BX  LR                          ;   return

; This is a helper function that sends an 8-bit data to the LCD.
; Input: R0  8-bit data to transmit
; Output: none
; Assumes: SSI0 and port A have already been initialized and enabled
writedata
;; --UUU-- Code to write data to the LCD
;1) Read SSI0_SR_R and check bit 1, 
;2) If bit 1 is low loop back to step 1 (wait for TNF bit to be high)
;3) Set D/C=PA6 to one
;4) Write the 8-bit data to SSI0_DR_R
		PUSH {R4-R8, LR}
Rloop1 	LDR R4, =SSI0_SR_R; 0001.0000
		 LDR R8, [R4]
		 AND R8, #0x02
		 MOV R5, R8
		 LSR R5, #1
		 CMP R5, #0x00
		 BEQ Rloop1
		 LDR R6, =GPIO_PORTA_DATA_R
		 LDR R7, [R6]; 0100.0000
		 ORR R7, #0x40
		 STR R7, [R6]
		 LDR R6, =SSI0_DR_R
		 STR R0, [R6]
		 POP {R4-R8, LR}

    
    
    BX  LR                          ;   return




		
;***************************************************
; This is a library for the Adafruit 1.8" SPI display.
; This library works with the Adafruit 1.8" TFT Breakout w/SD card
; ----> http://www.adafruit.com/products/358
; as well as Adafruit raw 1.8" TFT display
; ----> http://www.adafruit.com/products/618
;
; Check out the links above for our tutorials and wiring diagrams
; These displays use SPI to communicate, 4 or 5 pins are required to
; interface (RST is optional)
; Adafruit invests time and resources providing this open source code,
; please support Adafruit and open-source hardware by purchasing
; products from Adafruit!
;
; Written by Limor Fried/Ladyada for Adafruit Industries.
; MIT license, all text above must be included in any redistribution
;****************************************************

    ALIGN                           ; make sure the end of this section is aligned
    END                             ; end of file

; Print.s
; Student names: change this to your names or look very silly
; Last modification date: change this to the last modification date or look very silly
; Runs on TM4C123
; EE319K lab 7 device driver for any LCD
;
; As part of Lab 7, students need to implement these LCD_OutDec and LCD_OutFix
; This driver assumes two low-level LCD functions
; ST7735_OutChar   outputs a single 8-bit ASCII character
; ST7735_OutString outputs a null-terminated string 

    IMPORT   ST7735_OutChar
    IMPORT   ST7735_OutString
    EXPORT   LCD_OutDec
    EXPORT   LCD_OutFix

    AREA    |.text|, CODE, READONLY, ALIGN=2
    THUMB

  

;-----------------------LCD_OutDec-----------------------
; Output a 32-bit number in unsigned decimal format
; Input: R0 (call by value) 32-bit unsigned number
; Output: none
; Invariables: This function must not permanently modify registers R4 to R11
; R0=0,    then output "0"
; R0=3,    then output "3"
; R0=89,   then output "89"
; R0=123,  then output "123"
; R0=9999, then output "9999"
; R0=4294967295, then output "4294967295"
LCD_OutDec
	PRESERVE8
FP RN 11
Count EQU 0
Num EQU 4
			
			PUSH{R4-R9,R11,LR};REMEMBER TO DO THIS
			SUB SP, #8; Allocation
			MOV FP, SP
			CMP R0, #0
			BEQ OPRINT
			MOV R4, #0
			STR R4, [FP, #Count]
			STR R0, [FP, #Num]
			MOV R5, #10
			
Loop 		LDR R4, [FP, #Num]
			CMP R4, #0
			BEQ Next
			UDIV R6, R4, R5
			MUL R7, R6, R5
			SUB R8, R4, R7
			PUSH {R8}
			STR R6, [FP, #Num]
			LDR R6, [FP, #Count]
			ADD R6, #1
			STR R6, [FP, #Count]
			B Loop
Next		LDR R4, [FP, #Count]
			CMP R4, #0
			BEQ Done
			POP {R0}
			ADD R0,  #0x30
			BL ST7735_OutChar
			SUB R4, #1
			STR R4, [FP, #Count]
			B Next
OPRINT		PUSH {R8}
			POP {R0}
			ADD R0,  #0x30
			BL ST7735_OutChar

Done 		ADD SP, #8
			POP {R4-R9, R11, LR}

			BX  LR
;* * * * * * * * End of LCD_OutDec * * * * * * * *

; -----------------------LCD _OutFix----------------------
; Output characters to LCD display in fixed-point format
; unsigned decimal, resolution 0.001, range 0.000 to 9.999
; Inputs:  R0 is an unsigned 32-bit number
; Outputs: none
; E.g., R0=0,    then output "0.000"
;       R0=3,    then output "0.003"
;       R0=89,   then output "0.089"
;       R0=123,  then output "0.123"
;       R0=9999, then output "9.999"
;       R0>9999, then output "*.***"
; Invariables: This function must not permanently modify registers R4 to R11
LCD_OutFix
OUT_FIX	
		PUSH{R4-R9, R11, LR}
		MOV R4, #9999
		CMP R0, R4
		BHI NONE
		MOV R4, #1000
		MOV R5, #100
		MOV R6, #10
		MOV R9, #1
		UDIV R7, R0, R4
		MOV R8, R0
		MOV R0, R7
		ADD R0, #0x30
		BL ST7735_OutChar
		MOV R0, #0x2E
		BL ST7735_OutChar
		MOV R0, R8
		MUL R7, R4
		SUBS R0, R7
		UDIV R7, R0, R5
		MOV R8, R0
		MOV R0, R7
		ADD R0, #0x30
		BL ST7735_OutChar
		MOV R0, R8
		MUL R7, R5
		SUBS R0, R7
		UDIV R7, R0, R6
		MOV R8, R0
		MOV R0, R7
		ADD R0, #0x30
		BL ST7735_OutChar
		MOV R0, R8
		MUL R7, R6
		SUBS R0, R7
		UDIV R7, R0, R9
		MOV R8, R0
		MOV R0, R7
		ADD R0, #0x30
		BL ST7735_OutChar
		B FDONE
			
	
NONE
		MOV R0, #0x2A
		BL ST7735_OutChar
		MOV R0, #0x2E
		BL ST7735_OutChar
		MOV R0, #0x2A
		BL ST7735_OutChar
		MOV R0, #0x2A
		BL ST7735_OutChar
		MOV R0, #0x2A
		BL ST7735_OutChar

FDONE	POP{R4-R9, R11, LR}
		BX LR

 
     ALIGN
;* * * * * * * * End of LCD_OutFix * * * * * * * *

     ALIGN                           ; make sure the end of this section is aligned
     END                             ; end of file
	 
// IO.c
// This software configures the switch and LED
// You are allowed to use any switch and any LED, 
// although the Lab suggests the SW1 switch PF4 and Red LED PF1
// Runs on TM4C123
// Program written by: put your names here
// Date Created: March 30, 2018
// Last Modified:  change this or look silly
// Lab number: 7


#include "../inc/tm4c123gh6pm.h"
#include <stdint.h>

//------------IO_Init------------
// Initialize GPIO Port for a switch and an LED
// Input: none
// Output: none
void IO_Init(void) {
 // --UUU-- Code to initialize PF4 and PF2
	SYSCTL_RCGCGPIO_R |= 0x20; //0010.0000
	__nop();
	__nop();
	//unlock PF0
	GPIO_PORTF_LOCK_R = 0x4C4F434B; //0x4C4F.434B
	GPIO_PORTF_CR_R = 0x1F;
	GPIO_PORTF_AMSEL_R = 0x00;
	GPIO_PORTF_PCTL_R = 0x00000000;
	GPIO_PORTF_DIR_R = 0x0E; //0000.0110
	GPIO_PORTF_AFSEL_R = 0x00;
	GPIO_PORTF_PUR_R = 0x11; //0001.0001
	GPIO_PORTF_DEN_R = 0x1F; //0001.0111
	
}
//------------IO_HeartBeat------------
// Toggle the output state of the  LED.
// Input: none
// Output: none
//------------IO_HeartBeat------------
// Toggle the output state of the  LED.
// Input: none
// Output: none
uint32_t counter = 0;
uint8_t j =0;
void IO_HeartBeat(void) {
 // --UUU-- PF2 is heartbeat
	uint32_t delay = 800000;
	if (j == 0){
		GPIO_PORTF_DATA_R ^= 0x02;
		j++;
	}
	if (counter < delay) {
		counter ++;
	} else {
		GPIO_PORTF_DATA_R ^= 0x02; //0000.0000 
		counter = 0;
	}
}


//------------IO_Touch------------
// wait for release and press of the switch
// Delay to debounce the switch
// Input: none
// Output: none
void IO_Touch(void) {
 // --UUU-- wait for release; 
	// If pressed x01 x10 x00
	// If released x11
	//delay for 20ms; 
	//and then wait for press
	uint32_t i=0;
	uint32_t delay=16000000;



	while((GPIO_PORTF_DATA_R & 0x11) != 0x11){}
	for(i=0; i<delay; i++){}
		while((GPIO_PORTF_DATA_R & 0x11) == 0x11){}	
		}

