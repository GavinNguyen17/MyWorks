;****************** Lab3.s ***************
; Program written by: Gavin Nguyen & Rodrigo Romero
; Date Created: 2/4/2017
; Last Modified: 2/13/2022
; Brief description of the program
;   The LED toggles at 2 Hz and a varying duty-cycle
; Hardware connections (External: Two buttons and one LED)
;  Change is Button input  (1 means pressed, 0 means not pressed)
;  Breathe is Button input  (1 means pressed, 0 means not pressed)
;  LED is an output (1 activates external LED)
; Overall functionality of this system is to operate like this
;   1) Make LED an output and make Change and Breathe inputs.
;   2) The system starts with the the LED toggling at 2Hz,
;      which is 2 times per second with a duty-cycle of 30%.
;      Therefore, the LED is ON for 150ms and off for 350 ms.
;   3) When the Change button is pressed-and-released increase
;      the duty cycle by 20% (modulo 100%). Therefore for each
;      press-and-release the duty cycle changes from 30% to 70% to 70%
;      to 90% to 10% to 30% so on
;   4) Implement a "breathing LED" when Breathe Switch is pressed:
; PortE device registers
GPIO_PORTE_DATA_R  EQU 0x400243FC
GPIO_PORTE_DIR_R   EQU 0x40024400
GPIO_PORTE_DEN_R   EQU 0x4002451C
SYSCTL_RCGCGPIO_R  EQU 0x400FE608

        IMPORT  TExaS_Init
        THUMB
        AREA    DATA, ALIGN=2
;global variables go here
OnIndex   SPACE 4
offIndex  SPACE 4
ONlooper  SPACE 1
OFFlooper SPACE 1
ONDC	  SPACE	4
OFFDC	  SPACE	4
COUNT EQU 5000

       AREA    |.text|, CODE, READONLY, ALIGN=2
       THUMB
       EXPORT EID1
EID1   DCB "gpn235",0  ;replace ABC123 with your EID
       EXPORT EID2
EID2   DCB "rr47552",0  ;replace ABC123 with your EID
       ALIGN 4
count  	 DCB 3,5,7,9,1
Ocount   DCB 7,5,3,1,9

     EXPORT  Start

Start
; TExaS_Init sets bus clock at 80 MHz, interrupts, ADC1, TIMER3, TIMER5, and UART0
     MOV R0,#2  ;0 for TExaS oscilloscope, 1 for PORTE logic analyzer, 2 for Lab3 grader, 3 for none
     BL  TExaS_Init ;enables interrupts, prints the pin selections based on EID1 EID2
 ; Your Initialization goes here
	LDR R0, =SYSCTL_RCGCGPIO_R
	LDR R1, [R0]
	ORR R1, R1, #0x10
	STR R1, [R0]
	NOP 
	NOP
	
	LDR R0, =GPIO_PORTE_DIR_R
	LDR R1, [R0]
	ORR R1, R1, #0x20
	BIC R1, R1, #0x05
	STR R1, [R0]
	
	LDR R0, =GPIO_PORTE_DEN_R
	LDR R1, [R0]
	ORR R1, R1, #0x25
	STR R1, [R0]

;Puts the initial values in the index
	LDR R0, =OnIndex
	LDR R1, =count
	STR R1, [R0]
	
	LDR R0, =offIndex
	LDR R1, =Ocount
	STR R1, [R0]

;sets up loop count that will cause the array values to loop
	LDR R0, =ONlooper
	MOV R1, #5
	STRB R1, [R0]
	LDR R0, =OFFlooper
	STRB R1, [R0]
	

loop  
; main engine goes here
	LDR R0, =GPIO_PORTE_DATA_R
	
;check here if "change" is set	
			LDR R1,[R0]; CHANGE PE0
			AND R1, R1, #0x01
			LSL R1, #31
			ADDS R1, #0x00
			BEQ breath
			BL NOF; branch New On/Off 
	

;This should just set up the initizal on/off of the LED
toggle
	LDR R0, =GPIO_PORTE_DATA_R
	MOV R1, #0x20; turns on the LED
	STR R1, [R0]

onDelay
	  LDR R2, =OnIndex
	  LDR R2, [R2]
	  LDRB R3, [R2]
	  BL Multiply
lloop SUBS R3, #1
	  BNE lloop
	  
	  MOV R1, #0x00
	  STR R1, [R0]
	  
offDelay
	  LDR R2, =offIndex
	  LDR R2, [R2]
	  LDRB R3, [R2]
	  BL Multiply
dloop SUBS R3, #1
	  BNE dloop

	  B    loop
	 
;New On/Off -should set the new delay and make the array loop
NOF
	     PUSH {LR, R7}
	     LDR R2, =ONlooper
		 LDRB R3, [R2]
	     LDR R0, =OnIndex
	     LDR R1, [R0]
	     SUBS R3, #0x01
		 STRB R3, [R2]
	     BEQ reset
         ADD R1, #0x01
	     STR R1, [R0]
		 B Next
reset    MOV R3,#5 
		 STRB R3, [R2]
		 SUB R1, #0x04
		 STR R1, [R0]

Next
		 LDR R2, =OFFlooper
		 LDRB R3, [R2]
	     LDR R0, =offIndex
	     LDR R1, [R0]
		 SUBS R3, #0x01
		 STRB R3, [R2]
		 BEQ reset1	 
	     ADD R1, #0x01
	     STR R1, [R0]
		 B leave
reset1   MOV R3,#5
		 STRB R3, [R2]
		 SUB R1, #0x04
		 STR R1, [R0]
	 
leave    ;This doesn't allow loop until after the button has been set back to 0 (low)
		 LDR R0, =GPIO_PORTE_DATA_R
		 LDR R1, [R0]
		 AND R1, R1, #0x01
		 ADDS R1, #0x00
		 BNE leave
		 POP {LR, R7}
	     BX LR
	
	
;multiply the value of the array by 1 millie
Multiply 
		PUSH {LR, R7}
		MOV R4, #0x0A
		MOV R5, #06
math	MUL R3, R3, R4
		SUBS R5, R5, #1
		BNE math
		POP {LR, R7}
		BX LR


;Then check if "breathing" is set
breath		 
			LDR R1, [R0] ;Is breath turned on
			AND R1,	#0x04
			ADDS R1, #0
			BNE CHECKER
			B toggle
			
;Check if duty cycle is at 90%		
CHECKER		CMP R6, #1
			BEQ R5OFF
			CMP R5, #1
			BEQ ADDEROFF
			ADD R6, #0
			CMP R6, #9
			BEQ R5ON
			B ADDERON
R5ON 		MOV R5, #1
			B CHECKER
R5OFF		MOV R5, #0
			
;Duty cycle increasing		
ADDERON		LDR R2, =OnIndex
			LDR R2, [R2]
			LDRB R2, [R2]
			MOV R6, R2
			LDR R3, =offIndex
			LDR R3, [R3]
			LDRB R3, [R3]
			BL NOF2
			BL MULT
			B CHANGE
;Duty cycle decreasing			
ADDEROFF	LDR R3, =OnIndex
			LDR R3, [R3]
			LDRB R3, [R3]
			LDR R2, =offIndex
			LDR R2, [R2]
			LDRB R2, [R2]
			MOV R6, R2
			BL NOF2
			BL MULT
			B CHANGE
;Multiply the Array with 20000		
MULT		PUSH{R1, LR}
			LDR R1, =COUNT
			MUL R2, R1
			MUL R3, R1
			MOV R8, R2
			MOV R9, R3
			MOV R10, #25
			POP {R1, LR}
			BX LR

;Turns the duty cycle on and off
CHANGE		MOV R1, #0X20
			STR R1, [R0]
DCON		SUBS R2, #1
			BNE DCON
			MOV R1, #0X00
			STR R1,[R0]
DCOFF		SUBS R3, #1
			BNE DCOFF
			MOV R2, R8
			MOV R3, R9
			SUBS R10, #1
			BNE CHANGE
			B loop	 			


NOF2
	     PUSH {LR, R0, R1, R2, R3, R7}
	     LDR R2, =ONlooper
		 LDRB R3, [R2]
	     LDR R0, =OnIndex
	     LDR R1, [R0]
	     SUBS R3, #0x01
		 STRB R3, [R2]
	     BEQ reset2
         ADD R1, #0x01
	     STR R1, [R0]
		 B Next2
reset2   MOV R3, #5
		 STRB R3, [R2]
		 SUB R1, #0x04
		 STR R1, [R0]

Next2
		 LDR R2, =OFFlooper
		 LDRB R3, [R2]
	     LDR R0, =offIndex
	     LDR R1, [R0]
		 SUBS R3, #0x01
		 STRB R3, [R2]
		 BEQ reset3	 
	     ADD R1, #0x01
	     STR R1, [R0]
		 POP {LR, R0, R1, R2, R3, R7}
		 BX LR
reset3   MOV R3, #5
		 STRB R3, [R2]
		 SUB R1, #0x04
		 STR R1, [R0]
		 POP {LR, R0, R1, R2, R3, R7}
		 BX LR
		 


	
	
	 B    loop
   
  
      
     ALIGN      ; make sure the end of this section is aligned
     END        ; end of file

	


