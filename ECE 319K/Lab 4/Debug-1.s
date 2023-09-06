;****************** Debug.s ***************
; Program written by: Gavin Nguyen & Rodrigo Romero
; Date Created: 2/14/2017
; Last Modified: 2/20/2022

; You may assume your debug functions have exclusive access to SysTick
; However, please make your PortF initialization/access friendly,
; because you have exclusive access to only one of the PortF pins.

; Your Debug_Init should initialize all your debug functionality
; Everyone writes the same version of Debug_ElapsedTime
; Everyone writes Debug_Beat, but the pin to toggle is revealed in the UART window
; There are four possible versions of Debug_Dump. 
; Which version you implement is revealed in the UART window

; ****************Option 0******************
; This is the first of four possible options
; Input: R0 is the data be 8-bit strategic information 
; Output: none
; Dump R0 into an array if this value is different from the previous value
; Please Dump R0 on the first call

; ****************Option 1******************
; This is the second of four possible options
; Input: R0 7-bit strategic information 
; Output: none
; If R0 bit 6 is low, 
; - observe the value in bits 5-0 of R0 (value from 0 to 63): 
;     maintain a histogram recording the number of times each value as occurred
;     since N will be less than 200, no histogram count can exceed the 8-bit 255 maximum,  
; If R0 bit 6 is high,
; - Do nothing

; ****************Option 2******************
; This is the third of four possible options
; Input: R0 is the data be 8-bit strategic information 
; Output: none 
; Dump R0 into an array if R0 bit 6 is low and bit 0 is high

; ****************Option 3******************
; This is the fourth of four possible options
; Input: R0 7-bit strategic information 
; Output: none
; - calculate the absolute value difference between this value and the value at the previous call to Debug_Dump
;     for the first call to Dump_Dump, assume the previous value was 0
;     the differences will range from 0 to 63
;     maintain a histogram recording the number of times each difference as occurred
;     since N will be less than 200, no histogram count can exceed the 8-bit 255 maximum,  


SYSCTL_RCGCGPIO_R  EQU 0x400FE608
GPIO_PORTF_DATA_R  EQU 0x400253FC
GPIO_PORTF_DIR_R   EQU 0x40025400
GPIO_PORTF_DEN_R   EQU 0x4002551C
SYSCTL_RCGCTIMER_R EQU 0x400FE604
TIMER2_CFG_R       EQU 0x40032000
TIMER2_TAMR_R      EQU 0x40032004
TIMER2_CTL_R       EQU 0x4003200C
TIMER2_IMR_R       EQU 0x40032018
TIMER2_TAILR_R     EQU 0x40032028
TIMER2_TAPR_R      EQU 0x40032038
TIMER2_TAR_R       EQU 0x40032048

; RAM Area
            AREA    DATA, ALIGN=2
;place your debug variables in RAM here
            EXPORT DumpBuf
            EXPORT Histogram
            EXPORT MinimumTime
            EXPORT MaximumTime         
DumpBuf     SPACE 166 ; 200 8-bit I/O values, your N will be less than 200
Histogram   SPACE 64  ; count of the number of times each value has occured
MinimumTime SPACE 4   ; smallest elapsed time between called to Debug_ElapsedTime
MaximumTime SPACE 4   ; largest elapsed time between called to Debug_ElapsedTime
; you will need additional globals, but do not change the above definitions
N 		EQU 200
CINDX 	SPACE 1
VARL 	SPACE 1
TIMEL	SPACE 4 
COUNTT 	SPACE 1
compare SPACE 4


; ROM Area
        EXPORT Debug_Init
        EXPORT Debug_Dump 
        EXPORT Debug_ElapsedTime
        EXPORT Debug_Beat
;-UUU-Import routine(s) from other assembly files (like SysTick.s) here
        AREA    |.text|, CODE, READONLY, ALIGN=2
        THUMB
        EXPORT EID1
EID1    DCB "gpn235",0  ;replace ABC123 with your EID
        EXPORT EID2
EID2    DCB "rr47552",0  ;replace ABC123 with your EID
;---------------Your code for Lab 4----------------
;Debug initialization for all your debug routines
;This is called once by the Lab4 grader to assign points (if you pass #2 into TExaS_Init
;It is later called at the beginning of the main.s
;for options 0 and 2, place 0xFF into each element of DumpBuf
;for options 1 and 3, place 0 into each element of Histogram
; save all registers (not just R4-R11)
; you will need to initialize global variables, Timer2 and Port F here
Debug_Init 
		PUSH {R0,R1,R2,LR}
		BL   Timer2_Init ;TIMER2_TAR_R is 32-bit down counter
;you write this
		LDR R0, =SYSCTL_RCGCGPIO_R
		LDR R1, [R0]
		ORR R1, #0x20
		STR R1, [R0]
		NOP
		NOP
		
		LDR R0, =GPIO_PORTF_DIR_R
		LDR R1, [R0]
		ORR R1, #0x04
		STR R1, [R0]
		
		LDR R0, =GPIO_PORTF_DEN_R
		LDR R1, [R0]
		ORR R1, #0x04
		STR R1, [R0]
		
		MOV R0, #0
		LDR R1, =CINDX
		LDR R2, =VARL
		STRB R0, [R1]
		STRB R0, [R2]
		
		LDR R1, =TIMEL
		STR R0, [R1]
		LDR R1, =COUNTT
		STRB R0, [R1]
		LDR R1, =compare
		STR R0, [R1]
		
		MOV R4, #-1 
		MOV R5, #0
dloop	LDR R6, =DumpBuf
		STRB R4, [R6, R5]
		;LDR R6, =TDIFF
		;STR R4, [R6, R5, LSL #2]
		ADD R5, #1
		CMP R5, #N
		BNE dloop
		POP {R0,R1,R2,PC}
		BX LR
            
    
; There are four possible options: 0,1,2 or 3
; Debug_Dump is called after every output.
; Stop recording and measuring after N observations
; Don't stop after N calls to Debug_Dump, but stop after N changes to your data structures
; N will be revealed to you in the UART window)
; Save all registers (not just R4-R11)

Debug_Dump
      PUSH {R0-R6,LR}
;you write this
;assume capture is called about every 2.5ms (real board)
;Let M = number of instructions in your Debug_Dump
;Calculate T = M instructions * 2cycles/instruction * 12.5ns/cycle 
;Calculate intrusiveness is T/2.5ms = ???
			LDR R1, =CINDX
			LDRB R2, [R1]
			CMP R2, #166
			BEQ LEAVE
			CMP R2, #0
			BEQ FIRST; store the first value in DumpBuf
			LDR R3, =DumpBuf
			ADD R3, R2
			LDR R4, [R3]
			LDR R5, =VARL
			LDRB R6, [R5]
			CMP R0, R6
			BEQ LEAVE
			STRB R0, [R5]
			STRB R0, [R3]
			ADD R2, #1
			STRB R2, [R1]
			B LEAVE


FIRST 
			LDR R4, =DumpBuf
			STRB R0, [R4]
			ADD R2, #1
			LDR R3, =VARL
			STRB R0, [R3]
			STRB R2, [R1]
LEAVE  		POP {R0-R6,PC}
			BX LR


; Your Debug_ElapsedTime is called after every output.
; Input: none 
; Output: none
; - observe the current time as a 32-bit unsigned integer: 
;     NowTime = TIMER2_TAR
; - Starting with the second call you will be able to measure elapsed time:
;     calcalate ElapsedTime = LastTime-NowTime (down counter)
;     determine the Minimum and Maximum ElapsedTime
; - Set LastTime = NowTime (value needed for next call)
; - Stop recording after N calls (N revealed to you in the UART window)
; save all registers (not just R4-R11)
Debug_ElapsedTime 
			PUSH {R0-R8,LR}
			LDR R0, =TIMER2_TAR_R
			LDR R1, [R0]
			LDR R2, =COUNTT
			LDRB R3, [R2]
			CMP R3, #0
			BEQ TIME1
			CMP R3, #1
			BEQ FIRSTT
			CMP R3, #166
			BEQ EXIT
			LDR R4, =TIMEL
			LDR R5, [R4]
			STR R1, [R4]
			MOV R6, R1
			SUB R5, R6
			CMP R5, #0
			BEQ ADDC
			LDR R7, = MinimumTime
			LDR R8, [R7]
			SUBS R8, R5
			CMP R8, #0
			BLT MAXIMUM
			STR R5, [R7]
			B ADDC
MAXIMUM		LDR R7, = MaximumTime
			LDR R8, [R7]
			SUBS R8, R5
			CMP R8, #0
			BGT EXIT
			STR R5, [R7]
			B ADDC


FIRSTT		LDR R4, = MinimumTime
			LDR R5, = MaximumTime
			LDR R6, = TIMEL
			LDR R7, [R6]
			SUBS R7, R1
			STR R7, [R4]
			STR R7, [R5]
			STR R1, [R6]
			B ADDC
TIME1
			LDR R4, = MinimumTime
			LDR R5, = MaximumTime
			LDR R6, = TIMEL
			STR R1, [R4]
			STR R1, [R5]
			STR R1, [R6]
			
	
	  
ADDC 		ADD R3, #1
			STRB R3, [R2] 
EXIT   		POP {R0-R8,PC}
			BX LR
    
; Your Debug_Beat function is called every time through the main loop to
; indicate to the operator if the main program is running (not stuck or dead).
; Inputs: none
; Outputs: none
; However, slow down the flashing so the LED flashes at about 1 Hz. 
; 1Hz means repeating: high for 500ms, low for 500ms
; Basically, toggle an LED every Mth call to your Debug_Beat 
; Find the constant M, so the flashing rate is between 0.5 and 2 Hz.
; The Port F pin you need to use will be revealed to you in the UART window.
; Save all registers (not AAPCS) 
Debug_Beat
      PUSH {R0-R4,LR}
;you write this 
			LDR R0, =GPIO_PORTF_DATA_R
			LDR R1, [R0]
			LDR R2, =compare
			LDR R3, [R2]
			CMP R3, #0
			BEQ toggle
			CMP R3, #153
			BEQ toggle1
			B gone
		
toggle	
			EOR R1, #0x04
			STR R1, [R0]
			B gone
		
toggle1 
			EOR R1, #0x04
			STR R1, [R0]
			MOV R3, #1
			STR R3, [R2]
			B gone1
			
gone    	ADD R3, #1
			STR R3, [R2]
gone1		POP  {R0-R4,PC}
			BX LR


;------------Timer2_Init------------
; This subroutine is functional and does not need editing
; Initialize Timer2 running at bus clock.
; Make it so TIMER2_TAR can be used as a 32-bit time
; TIMER2_TAR counts down continuously
; Input: none
; Output: none
; Modifies: R0,R1
Timer2_Init
    LDR R1,=SYSCTL_RCGCTIMER_R
    LDR R0,[R1]
    ORR R0,R0,#0x04
    STR R0,[R1]    ; activate TIMER2
    NOP
    NOP
    LDR R1,=TIMER2_CTL_R
    MOV R0,#0x00
    STR R0,[R1]    ; disable TIMER2A during setup
    LDR R1,=TIMER2_CFG_R
    STR R0,[R1]    ; configure for 32-bit mode
    LDR R1,=TIMER2_TAMR_R
    MOV R0,#0x02
    STR R0,[R1]    ; configure for periodic mode, default down-count settings
    LDR R1,=TIMER2_TAILR_R
    LDR R0,=0xFFFFFFFE
    STR R0,[R1]    ; reload value
    LDR R1,=TIMER2_TAPR_R
    MOV R0,#0x00
    STR R0,[R1]    ; no prescale, bus clock resolution
    LDR R1,=TIMER2_IMR_R
    MOV R0,#0x00
    STR R0,[R1]    ; no interrupts
    LDR R1,=TIMER2_CTL_R
    MOV R0,#0x01
    STR R0,[R1]    ; enable TIMER2A
    BX  LR          
  
    ALIGN      ; make sure the end of this section is aligned
    END        ; end of file

