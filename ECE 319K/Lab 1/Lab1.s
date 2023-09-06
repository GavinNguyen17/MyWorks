;****************** Lab1.s ***************
; Program initially written by: Yerraballi and Valvano
; Author: Gavin Nguyen
; Date Created: 1/15/2018 
; Last Modified: 1/30/2022
; Brief description of the program: Solution to Lab1
; The objective of this system is to implement a parity system
; Hardware connections: 
;  One output is positive logic, 1 turns on the LED, 0 turns off the LED
;  Three inputs are positive logic, meaning switch not pressed is 0, pressed is 1
;			PE0 is an input
;			PE1 is an input
;			PE2 is an input
;			PE5 is an output
GPIO_PORTD_DATA_R  EQU 0x400073FC
GPIO_PORTD_DIR_R   EQU 0x40007400
GPIO_PORTD_DEN_R   EQU 0x4000751C
GPIO_PORTE_DATA_R  EQU 0x400243FC
GPIO_PORTE_DIR_R   EQU 0x40024400
GPIO_PORTE_DEN_R   EQU 0x4002451C
SYSCTL_RCGCGPIO_R  EQU 0x400FE608
       PRESERVE8 
       AREA   Data, ALIGN=4
; No global variables needed

       ALIGN 4
       AREA    |.text|, CODE, READONLY, ALIGN=2
       THUMB
       EXPORT EID
EID    DCB "gpn235",0  ;replace abc123 with your EID
       EXPORT RunGrader
	   ALIGN 4
RunGrader DCD 1 ; change to nonzero when ready for grading
           
      EXPORT  Lab1
Lab1 
;Initialize
;Start Port E Clock
	LDR R0, =SYSCTL_RCGCGPIO_R		
	LDR R1, [R0]             
	ORR R1, #0x10			
	STR R1, [R0]				
;Stabalize Clock
	NOP
	NOP
;Set Directions for Output as pin PE5
	LDR R0, =GPIO_PORTE_DIR_R
	LDR R1, [R0]             
	ORR R1, #0x20            
	STR R1, [R0]				
;Enable Digital
	LDR R0, =GPIO_PORTE_DEN_R
	LDR R1, [R0]             
	ORR R1, #0x27			
	STR R1, [R0]				
loop
;input, calculate, output
;Check To see if switches 0-2 are on
	LDR R0, =GPIO_PORTE_DATA_R
	LDR R1, [R0]
	AND R1, #0x01
	LDR R2, [R0]
	AND R2, #0x02
	LDR R3, [R0]
	AND R3, #0x04
;Right shift Key 0-2 to the 5th Bit
	LSL R1, #5
	LSL R2, #4
	LSL R3, #3
;EOR K1 and K0
	EOR R2, R2, R1
;EOR K2 and (K1 EOR K0)
	EOR R3, R3, R2
;EOR Output with 0x20 to flip the Output
	EOR R3, R3, #0x20
	STR R3, [R0]

    B    loop


    
    ALIGN        ; make sure the end of this section is aligned
    END          ; end of file
               