
// DollarStoreZelda.c
// Runs on TM4C123
// Rodrigo Romero & Gavin Nguyen
// This is a starter project for the EE319K Lab 10

// Last Modified: 1/12/2022 
// http://www.spaceinvaders.de/
// sounds at http://www.classicgaming.cc/classics/spaceinvaders/sounds.php
// http://www.classicgaming.cc/classics/spaceinvaders/playguide.php

// ******* Possible Hardware I/O connections*******************
// Slide pot pin 1 connected to ground
// Slide pot pin 2 connected to PD2/AIN5
// Slide pot pin 3 connected to +3.3V 
// buttons connected to PE0-PE3
// 32*R resistor DAC bit 0 on PB0 (least significant bit)
// 16*R resistor DAC bit 1 on PB1
// 8*R resistor DAC bit 2 on PB2 
// 4*R resistor DAC bit 3 on PB3
// 2*R resistor DAC bit 4 on PB4
// 1*R resistor DAC bit 5 on PB5 (most significant bit)
// LED on PD1
// LED on PD0


#include <stdint.h>
#include "../inc/tm4c123gh6pm.h"
#include "ST7735.h"
#include "Print.h"
#include "Random.h"
#include "TExaS.h"
#include "ADC.h"
#include "Images.h"
#include "Sound.h"
#include "Timer1.h"
#include "Timer2.h"
#include "Timer3.h"

void DisableInterrupts(void); // Disable interrupts
void EnableInterrupts(void);  // Enable interrupts
void Delay100ms(uint32_t count); // time delay in 0.1 seconds

//uint32_t Data;        // 12-bit ADC
//uint32_t Position;    // 32-bit fixed-point 0.001 cm
uint32_t ADCvalue[2];
uint32_t DataX;
uint32_t DataY;
uint8_t exploflag =0;
uint8_t BFDflag =0;
uint8_t BRDflag =0;
const uint16_t *OldImage;
uint8_t OldX;
uint8_t OldY;
uint8_t OldH;
uint8_t OldW;
uint8_t buttonflag=0;
uint8_t health=3;
uint8_t collflag=0;
uint8_t roomflag=0; 
uint8_t crossflag=0;
uint8_t language=0;
uint8_t selectarrow=0;
uint8_t menuprint=0;
uint8_t bossFlag=0;
uint8_t BossDefeat =0;
uint8_t TScreen=0;
uint8_t Tprint=0;
uint8_t enemydef=0;
uint16_t sum=0;
uint8_t pickup=1;
uint8_t pickupk=1;
uint8_t attacks=0;
uint8_t checkflag=0;
uint8_t check2=0;
uint8_t instructprint=0;
uint8_t instructprint2=0;
uint8_t instructflag=0;
uint8_t page=0;
uint8_t i =0,d; //i is to cycle the animation of direction being walked in 
								//d is direction which will show which direction the sword points
								//d=0 looking down
								//d=1 looking up
								//d=2 looking right
								//d=3 looking left
			
struct user{
	uint8_t x;
	uint8_t y;
	int8_t dx;
	int8_t dy;
	uint8_t w;
	uint8_t h;
	const uint16_t *image;
};

typedef struct user u_type;
u_type player = {72,88,1,1,16,18,CharFor};

struct skel{
	uint8_t x;
	uint8_t y;
	int8_t dx;
	int8_t dy;
	uint8_t w;
	uint8_t h;
	const uint16_t *image;
	int8_t health;
	uint8_t repeat;
	uint8_t mov;
};
struct project{
	uint8_t x;
	uint8_t y;
	int8_t dx;
	uint8_t dy;
	uint8_t w;
	uint8_t h;
	const uint16_t *image;

};
typedef struct skel s_type;
s_type enemy = {30,90,1,1,17,18,skeleton1,2,0,1};
s_type enemy2 = {104,56,1,1,17,18,skeleton1,2,0,1};
s_type enemy3 = {25,80,1,1,17,18,skeleton1,2,0,1};
s_type enemy4 = {77,56,1,1,17,18,skeleton1,2,0,1};
s_type ganon = {64,93,0,0,32,32,BossStanding,5,0,1};

struct boss{
	const uint16_t *image;
	uint8_t health;
};

//typedef struct boss b_type;
//b_type ganon = {BossStanding,1};

typedef struct project p_type;
p_type fire = {1,1,0,0,10,12,BossFire};
p_type fire2 = {1,1,0,0,10,12,BossFire};

void ScoreScreen(void){
 ST7735_FillScreen(ST7735_BLACK);
	NVIC_DIS0_R = 1<<21;           // 9) enable IRQ 21 in NVIC
	 TIMER1_IMR_R &= ~0x00000001;
	NVIC_DIS1_R = 1<<(35-32);
	TIMER3_IMR_R &= ~0x00000001;
	NVIC_DIS0_R = 1<<23;
	TIMER2_IMR_R &= ~0x00000001;
	if(language==1){
 ST7735_SetCursor(0,0);
	ST7735_OutString("Thanks For Playing");
  ST7735_SetCursor(9,5);
	ST7735_OutString("Score");
 }
 if(language==2){
 ST7735_SetCursor(0,0);
	ST7735_OutString("Gracias Por Jugar");
  ST7735_SetCursor(9,5);
	ST7735_OutString("Puntaje");
 }
 sum=(enemydef*100)+500;
 ST7735_SetCursor(9,6);
 LCD_OutDec(sum);
 playsound(Song);
 //playsound(Song2);
 while(1){
 }
 }
void GameOver(void){
	ST7735_FillScreen(ST7735_BLACK);
	if(language==1){
		ST7735_SetCursor(9,5);
		ST7735_OutString("Score");
		sum=(enemydef*100);
 ST7735_SetCursor(9,6);
 LCD_OutDec(sum);
	
	ST7735_SetCursor(8,0);
	ST7735_OutString("GAME OVER");
	ST7735_SetCursor(0,3);
	ST7735_OutString("Press");
	ST7735_DrawCircle(33,29,ST7735_YELLOW);
	ST7735_SetCursor(8,3);
	ST7735_OutString("To Restart");
	DisableInterrupts();
	}
	if(language==2){
		 ST7735_SetCursor(9,6);
	ST7735_OutString("Puntaje");
		sum=(enemydef*100);
 ST7735_SetCursor(9,7);
 LCD_OutDec(sum);
	ST7735_SetCursor(4,0);
	ST7735_OutString("JUEGO TERMINADO");
	ST7735_SetCursor(0,3);
	ST7735_OutString("Prensa");
	ST7735_DrawCircle(38,29,ST7735_YELLOW);
	ST7735_SetCursor(0,4);
	ST7735_OutString("Para Reiniciar");
	DisableInterrupts();
	}
	while(1){}
}
  void Title(void){
	if (TScreen==0){
		if(Tprint==0){
		ST7735_DrawBitmap(0,128,TitleScreen,160,128);
		Tprint=1;
		}
		
	}	
	}
	void MainMenu(void){
		if(menuprint==0){
	ST7735_SetCursor(9,3);
	ST7735_OutString("English");
	ST7735_SetCursor(9,5);
	ST7735_OutString("Espanol");
	//ST7735_SetCursor(9,7);
	//ST7735_OutString("Binary");
	ST7735_SetCursor(0,9);
	ST7735_OutString("Change/Cambiar");
		ST7735_DrawCircle(87,90,ST7735_GREEN);
		ST7735_SetCursor(0,10);
	ST7735_OutString("Select/Seleccionar");
		ST7735_DrawCircle(110,99,ST7735_RED);
			menuprint=1;
		}			
		if(selectarrow==0&&language==0){
		ST7735_FillRect (100,70,8,7,ST7735_BLACK);
		ST7735_FillRect (100,49,8,7,ST7735_BLACK);
	ST7735_DrawBitmap(100,36,Arrow,8,7);
			}
		if(selectarrow==1&&language==0){
	ST7735_FillRect (100,30,8,7,ST7735_BLACK);
			ST7735_FillRect (100,70,8,7,ST7735_BLACK);
	ST7735_DrawBitmap(100,55,Arrow,8,7);
			}
	}
	
	void Instructions(void){
	if(instructprint==0){
		ST7735_FillScreen(ST7735_BLACK);
		if(language==1){
	ST7735_SetCursor(0,0);
	ST7735_OutString("Use Joystick to Move");
	ST7735_SetCursor(0,1);
	ST7735_OutString("Press  to Attack");	
	ST7735_SetCursor(0,2);
	ST7735_DrawCircle (30,10, ST7735_GREEN);
	ST7735_OutString("Pick Up   and by");
	ST7735_SetCursor(0,3);
	ST7735_DrawBitmap(23, 25, UIBomb, 5, 7);
	ST7735_DrawBitmap(45, 32, key, 8, 15);		
	ST7735_OutString("Stepping Over Them");	
	ST7735_SetCursor(0,4);
	ST7735_OutString("If Player Has");
	ST7735_DrawBitmap(75, 50, UIBomb, 5, 7);			
	ST7735_SetCursor(0,5);
	ST7735_OutString("Press  on    ");
	ST7735_DrawCircle (30,49, ST7735_RED);
	ST7735_DrawBitmap ( 55, 58, CrackedFrontDoor, 18,11);
	ST7735_SetCursor(0,6);
	ST7735_OutString("to unlock door");
	ST7735_SetCursor(0,7);
	ST7735_OutString("Walk into  ");
	ST7735_DrawBitmap ( 55, 85, KeyRightdoor, 7,18);
	ST7735_SetCursor(0,9);
	ST7735_OutString("If Player Has  ");
		ST7735_DrawBitmap(80, 100, key, 8, 15);
ST7735_SetCursor(0,10);		
	ST7735_OutString("Press  to Continue");	
	ST7735_DrawCircle (30,100, ST7735_GREEN);	
	instructprint=1;
	instructprint2=1;
		}
		if(language==2&&page==0){
	ST7735_SetCursor(0,0);
	ST7735_OutString("Usa El Joystick");
	ST7735_SetCursor(0,1);
			ST7735_OutString("Para Moverte");
	ST7735_SetCursor(0,2);
	ST7735_OutString("Presiona   Para");
	ST7735_SetCursor(0,3);
	ST7735_OutString("Atacar");
	ST7735_SetCursor(0,4);
	ST7735_DrawCircle (50,20, ST7735_GREEN);
	ST7735_OutString("Recogida  y Por");
	ST7735_SetCursor(0,5);
	ST7735_DrawBitmap(50, 45, UIBomb, 5, 7);
	ST7735_DrawBitmap(95, 52, key, 8, 15);		
	ST7735_OutString("Pasando por encima ");	
	ST7735_SetCursor(0,6);
	ST7735_OutString("de ellos");		
	ST7735_SetCursor(0,7);
	ST7735_OutString("Si El Jugador Tiene ");
	ST7735_DrawBitmap(110, 55, UIBomb, 5, 7);
	ST7735_SetCursor(0,8);
	ST7735_OutString("Pulse  en    ");			
	ST7735_DrawCircle (30,78, ST7735_RED);
	ST7735_DrawBitmap ( 58, 88, CrackedFrontDoor, 18,11);
	ST7735_SetCursor(0,9);
	ST7735_OutString("abrir la puerta");
		ST7735_SetCursor(0,10);
	ST7735_OutString("  Continuar");	
	ST7735_DrawCircle (0,100, ST7735_GREEN);
	instructprint=1;
	}
		}

if(language==2&&page==1){
	if(instructprint2==0){
ST7735_FillScreen(ST7735_BLACK);
ST7735_SetCursor(0,0);
	ST7735_OutString("Entrar en  ");
	ST7735_DrawBitmap ( 60, 18, KeyRightdoor, 7,18);
	ST7735_SetCursor(0,2);
	ST7735_OutString("Si el jugador tiene");
		ST7735_DrawBitmap(115, 28, key, 8, 15);
ST7735_SetCursor(0,10);		
	ST7735_OutString("  Continuar");	
	ST7735_DrawCircle (0,100, ST7735_GREEN);	
	instructprint2=1;
		}
	}

	
}		
		
		
		

void Timer1A_Handler(void){ // can be used to perform tasks in background
	if(BFDflag == 1)BFDflag = 2;
	if(exploflag==1){
		ST7735_DrawBitmap(74,38,smoke,11,9);
		exploflag=0;
		BFDflag = 1;
		playsound(Blow);
	}

	if(roomflag==5&&BossDefeat==0){		
		if(bossFlag==1)ganon.image=BossArmDown;
		if(bossFlag==2)ganon.image=BossLeftArm;
		if(bossFlag==3)ganon.image=BossArmDown;
		if(bossFlag==4)ganon.image=BossRgtArm;
		ST7735_DrawBitmap(64,93,ganon.image,32,32);
		bossFlag=((bossFlag+1)%5);
		if(bossFlag==0)bossFlag++;
		if(ganon.health<=0)BossDefeat=1;
	}
  TIMER1_ICR_R = TIMER_ICR_TATOCINT;// acknowledge TIMER1A timeout
   // execute user task
}

void projmov(){
	if(attacks==0){
		ST7735_DrawBitmap(fire.x,fire.y,fire.image,fire.w,fire.h);
		ST7735_DrawBitmap(fire2.x,fire2.y,fire2.image,fire2.w,fire2.h);
	}
}
	

void Timer2A_Handler(void){
  static uint8_t f=0;
	//static uint8_t mov =0;
	if(roomflag==5&&BossDefeat==0){
		if(attacks==0){
			fire.dx=1;
			fire.dy=1;
			fire2.dx=1;
			fire2.dy=1;
		if(fire.x<96&&fire.y==61){
			fire.x+=fire.dx;	
		}
		if(fire.x==96&&fire.y<106){
			fire.y+=fire.dy;
		}
		if(fire.x>64-fire.w&&fire.y==106){
			fire.x-=fire.dx;	
		}
		if(fire.x==64-fire.w&&fire.y>61){
			fire.y-=fire.dy;
		}
		if(fire2.x<96&&fire2.y==61){
			fire2.x+=fire2.dx;	
		}
		if(fire2.x==96&&fire2.y<106){
			fire2.y+=fire2.dy;
		}
		if(fire2.x>64-fire2.w&&fire2.y==106){
			fire2.x-=fire2.dx;	
		}
		if(fire2.x==64-fire2.w&&fire2.y>61){
			fire2.y-=fire2.dy;
		}
	}
	projmov();	
	}
	
	else{
	if(f==0){
		enemy.image =skeleton2;
		enemy2.image =skeleton2;
		enemy3.image =skeleton1;
		enemy4.image =skeleton1;
	}else {
		enemy.image =skeleton1;
		enemy2.image =skeleton1;
		enemy3.image =skeleton2;
		enemy4.image =skeleton2;
	}
	if(roomflag==1){
		if((enemy.mov ==0 &&enemy.x<135)){
			enemy.dx =1;
		}else{enemy.mov=1;}
		if((enemy.mov ==1&&enemy.x>8)){
				enemy.dx =-1;
		}else{enemy.mov=0;}
		
		if(enemy2.mov ==0 &&enemy2.y<120){
			enemy2.dy =1;
		}else{enemy2.mov=1;}
		if(enemy2.mov ==1&&enemy2.y-enemy2.h>39){
			enemy2.dy=-1;
		}else{enemy2.mov=0;}
		
	enemy.x +=enemy.dx;
	enemy2.y +=enemy2.dy;
	if(enemy.health>0)ST7735_DrawBitmap(enemy.x, enemy.y, enemy.image, enemy.w, enemy.h);
		if(enemy.health==0) {
		enemydef++;
		enemy.health--;
		}
	if(enemy2.health>0)ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		if(enemy2.health==0){
		enemydef++;
		enemy2.health--;
		}
	}
	if(roomflag==2){
		if(enemy.mov ==0 &&enemy.y<120){
			enemy.dy =1;
		}else{enemy.mov=1;}
		if(enemy.mov ==1&&enemy.y-enemy.h>39){
			enemy.dy=-1;
		}else{enemy.mov=0;}
		
		if(enemy2.mov ==0 &&enemy2.y<120){
			enemy2.dy =1;
		}else{enemy2.mov=1;}
		if(enemy2.mov ==1&&enemy2.y-enemy2.h>39){
			enemy2.dy=-1;
		}else{enemy2.mov=0;}
		
		if(enemy3.mov ==0 &&enemy3.y<120){
			enemy3.dy =1;
		}else{enemy3.mov=1;}
		if(enemy3.mov ==1&&enemy3.y-enemy3.h>39){
			enemy3.dy=-1;
		}else{enemy3.mov=0;}
		
		if(enemy4.mov ==0 &&enemy4.y<120){
			enemy4.dy =1;
		}else{enemy4.mov=1;}
		if(enemy4.mov ==1&&enemy4.y-enemy4.h>39){
			enemy4.dy=-1;
		}else{enemy4.mov=0;}
		
		enemy.y +=enemy.dy;
		enemy2.y +=enemy2.dy;
		enemy3.y +=enemy3.dy;
		enemy4.y +=enemy4.dy;
		if(enemy.health>0)ST7735_DrawBitmap(enemy.x, enemy.y, enemy.image, enemy.w, enemy.h);
		if(enemy.health==0){
		enemydef++;
		enemy.health--;
		}
		if(enemy2.health>0)ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		if(enemy2.health==0) {
		enemydef++;
		enemy2.health--;
		}
		if(enemy3.health>0)ST7735_DrawBitmap(enemy3.x, enemy3.y, enemy3.image, enemy3.w, enemy3.h);
		if(enemy3.health==0) {
		enemydef++;
		enemy3.health--;
		}
		if(enemy4.health>0)ST7735_DrawBitmap(enemy4.x, enemy4.y, enemy4.image, enemy4.w, enemy4.h);
		if(enemy4.health==0) {
		enemydef++;
		enemy4.health--;
		}
	}
	
	if(roomflag==3){
		if((enemy.mov ==0 &&enemy.x<135)){
			enemy.dx =1;
		}else{enemy.mov=1;}
		if((enemy.mov ==1&&enemy.x>8)){
				enemy.dx =-1;
		}else{enemy.mov=0;}
		
		if(enemy2.mov ==0 &&enemy2.y<120){
			enemy2.dy =1;
		}else{enemy2.mov=1;}
		if(enemy2.mov ==1&&enemy2.y-enemy2.h>39){
			enemy2.dy=-1;
		}else{enemy2.mov=0;}
		
		if((enemy3.mov ==0 &&enemy3.x<135)){
			enemy3.dx =1;
		}else{enemy3.mov=1;}
		if((enemy3.mov ==1&&enemy3.x>8)){
				enemy3.dx =-1;
		}else{enemy3.mov=0;}
		enemy.x +=enemy.dx;
		enemy2.y +=enemy2.dy;
		enemy3.x +=enemy3.dx;
		if(enemy.health>0)ST7735_DrawBitmap(enemy.x, enemy.y, enemy.image, enemy.w, enemy.h);
		if(enemy.health==0) {
		enemydef++;
		enemy.health--;
		}
		if(enemy2.health>0)ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		if(enemy2.health==0) {
		enemydef++;
		enemy2.health--;
		}
		if(enemy3.health>0)ST7735_DrawBitmap(enemy3.x, enemy3.y, enemy3.image, enemy3.w, enemy3.h);
		if(enemy3.health==0) {
		enemydef++;
		enemy3.health--;
		}
	}
	}
	
	
	if(roomflag==4){
		if((enemy.mov ==0 &&enemy.x<130)){
			enemy.dx =1;
		}else{enemy.mov=1;}
		if((enemy.mov ==1&&enemy.x>8)){
				enemy.dx =-1;
		}else{enemy.mov=0;}
		
		if(enemy2.mov ==0 &&enemy2.y<120){
			enemy2.dy =1;
		}else{enemy2.mov=1;}
		if(enemy2.mov ==1&&enemy2.y-enemy2.h>39){
			enemy2.dy=-1;
		}else{enemy2.mov=0;}
		if((enemy3.mov ==0 &&enemy3.x<0x81)){
			enemy3.dx =1;
		}else{enemy3.mov=1;}
		if((enemy3.mov ==1&&enemy3.x>8)){
				enemy3.dx =-1;
		}else{enemy3.mov=0;}
		
		enemy.x +=enemy.dx;
		enemy2.y +=enemy2.dy;
		enemy3.x +=enemy3.dx;
		if(enemy.health>0)ST7735_DrawBitmap(enemy.x, enemy.y, enemy.image, enemy.w, enemy.h);
		{
		if(enemy.health==0) {	
		enemydef++;
		enemy.health--;
		}
		}
		if(enemy2.health>0)ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		if(enemy2.health==0) {
		enemydef++;
		enemy2.health--;
		}
		if(enemy3.health>0)ST7735_DrawBitmap(enemy3.x, enemy3.y, enemy3.image, enemy3.w, enemy3.h);
		if(enemy3.health==0) {
		enemydef++;
		enemy3.health--;
		}
	}
		
	f^=1;
	TIMER2_ICR_R = TIMER_ICR_TATOCINT;// acknowledge TIMER2A timeout
	
}

void Timer3A_Handler(void){
  TIMER3_ICR_R = TIMER_ICR_TATOCINT;// acknowledge TIMER3A timeout
	if(collflag==1){
		health--;
		if(health == 2){
			ST7735_DrawBitmap(43,25, blackheart, 17,16);
		}
		if(health==1){
			ST7735_DrawBitmap(24,25, blackheart, 17,16);
		}
		if(health == 0){
			ST7735_DrawBitmap(5,25, blackheart, 17,16);
			GameOver();
		}
		collflag=0;
	}
	if(checkflag==0&&roomflag==2&&(player.x>=0&&player.x<=15&&player.y<=80 && player.y>=30)){
		pickup=1;
		checkflag=1;
		ST7735_DrawBitmap(128,20,UIBomb,5,7);
		ST7735_FillRect(10,70-7,5,8,ST7735_BLACK);
	}
	if(check2==0&&roomflag==4&&(player.x>=130&&player.x<=160&&player.y<=80 && player.y>=30)){
		pickupk=1;
		check2=1;
    ST7735_DrawBitmap(115,20,key,8,11);
		ST7735_FillRect(145,65-15,8,16,ST7735_BLACK);
	}
}

//**************************************************************************************************************************************************
void PolledButtons_Init(void){
	SYSCTL_RCGCGPIO_R |= 0x10; //0001.0000 PortE
	while((SYSCTL_PRGPIO_R & 0x00000010) == 0){};
	GPIO_PORTE_AMSEL_R &= ~0x03; //0000.0011
  GPIO_PORTE_DIR_R &= ~0x03;    // (c) make PE0-2 in 0000.0111
  GPIO_PORTE_AFSEL_R &= ~0x03;  //     disable alt funct on PE0-2
  GPIO_PORTE_DEN_R |= 0x03;     //     enable digital I/O on PE0-2   
  GPIO_PORTE_PCTL_R &= ~0x00000FFF; // configure PE0-2 as GPIO
  GPIO_PORTE_PUR_R |= 0x03;     //     enable weak pull-up on PE0-2
  GPIO_PORTE_IS_R &= ~0x03;     // (d) PE0-2 is edge-sensitive
  GPIO_PORTE_IBE_R &= ~0x03;    //     PE0-2 is not both edges
  GPIO_PORTE_IEV_R &= ~0x03;    //     PE0-2 falling edge event
  GPIO_PORTE_ICR_R = 0x03;      // (e) clear flag 0,1,2
  GPIO_PORTE_IM_R |= 0x03;      // (f) arm interrupt on PE0-2 *** No IME bit as mentioned in Book ***
  NVIC_PRI7_R = (NVIC_PRI7_R&0xFF00FFFF)|0x00A00000; // (g) priority 5
  NVIC_EN0_R = 0x00000010;      // (h) enable interrupt 4 in NVIC
}




// **************GPIOPortE_Handler*********************
// Used for the function of the three switches
// Input: none
// Output: none
void GPIOPortE_Handler(void){
	
	if(TScreen==0){
		if(GPIO_PORTE_RIS_R&0x01){ 			
		GPIO_PORTE_ICR_R = 0x01;
		TScreen=1;
		playsound(Slash);
		ST7735_FillScreen(ST7735_BLACK);
	}
}
	if(language==0){
	for(uint32_t j=0; j<0x100000; j++){}
	if(GPIO_PORTE_RIS_R&0x01&&selectarrow==0){ 			
		GPIO_PORTE_ICR_R = 0x01;
	selectarrow=1;
	}
	if(GPIO_PORTE_RIS_R&0x01&&selectarrow==1){
		GPIO_PORTE_ICR_R = 0x01;
	selectarrow=0;
	}
	}
	if(instructflag==0){
	if(GPIO_PORTE_RIS_R&0x01){ 			
		GPIO_PORTE_ICR_R = 0x01;
		page=1;
		if(instructprint==1&&instructprint2==1){
		instructflag=1;
		}
	}
}
	else{
	for(uint32_t j=0; j<0x100000; j++){}
	if(GPIO_PORTE_RIS_R&0x01){ 				//this is for the attack button
		GPIO_PORTE_ICR_R = 0x01;
		buttonflag=1;
		playsound(Slash);
	}
}
	if(language==0){
	for(uint32_t j=0; j<0x100000; j++){}
	if(GPIO_PORTE_RIS_R&0x02){
		GPIO_PORTE_ICR_R = 0x02;
	if(selectarrow==0){
	language=1;
	}
	if(selectarrow==1){
	language=2;
	}
	}
	}
	else{
	if(GPIO_PORTE_RIS_R&0x02){
		GPIO_PORTE_ICR_R = 0x02;
		if(player.y<=58 && d==1 && player.x>=70 && player.x<=80&&roomflag==1&&pickup==1){
				if(language==1){
		ST7735_SetCursor(11,0);
		}
			if(language==2){
		ST7735_SetCursor(11,0);
		}
			exploflag =1;
			ST7735_DrawBitmap(77,38, Upbomb,6,7);
			playsound(Drop);
		}
		}	
	}
}




void SysTick_Init(uint32_t period){
  // write this
	DisableInterrupts();
	NVIC_ST_CTRL_R = 0;
	NVIC_ST_RELOAD_R = period-1; //should be 10 Hz
	NVIC_ST_CURRENT_R = 0;
	NVIC_SYS_PRI3_R = (NVIC_SYS_PRI3_R&0x00FFFFFF)|0x40000000;
	NVIC_ST_CTRL_R = 0x07;
	EnableInterrupts();
}



uint32_t convertX (uint32_t x){   //Data[1]
	return (((x<<8)/2047)*1000>>8); //range from 0 to 1000
}

uint32_t convertY (uint32_t y){   //Data[0]
	return (((y<<8)/2047)*1000>>8); //range from 0 to 1000
}

uint8_t flag =0;
void SysTick_Handler(void){
  // write this
	ADC_In89(ADCvalue);
	DataX = convertX(ADCvalue[1]);
	DataY = convertY(ADCvalue[0]);
	flag = 1;
}

// **************move*********************
// set new x and y location of character sprite 
// makes sure charcter doesn't leave map
// checks if the character is going into a new room
// Input: none
// Output: none
void move(){
	if(player.dy==0)player.x +=player.dx;
	if(player.dx==0)player.y +=player.dy;
	if(player.x<7)player.x=7;
	if(player.x>135)player.x=135; //137
	if(player.y>120)player.y=120;
	if(player.y<56)player.y=56;
	if(player.x==0x87&&player.y<=90&&player.y-player.h>=65&&d==2&&(roomflag==3||roomflag==2)){//moving towards the right door should only work on specific rooms
		ST7735_FillRect(player.x,player.y-player.h,player.w,player.h,ST7735_BLACK);
		player.x=7;
		crossflag=1;
		if(roomflag==2)roomflag=1;
		if(roomflag==3)roomflag=5;
    }
	if(player.x==7&&player.y<=90&&player.y-player.h>=65&&d==3&&(roomflag==1||(roomflag==5&&BossDefeat==1))){//moving towards the left door should only work on specific rooms
		ST7735_FillRect(player.x,player.y-player.h,player.w,player.h,ST7735_BLACK);
		player.x=0x87;
		crossflag=1;
		if(roomflag==1){
			roomflag=2;
		}else{
			roomflag=3;
		}
    }
	if(player.y<=58 && d==1 && player.x>=70 && player.x<=80&&(roomflag==0||(roomflag==1&&BFDflag==3)||roomflag==3)){//0 1* 3  *only after bomb placed 
		ST7735_FillRect(player.x,player.y-player.h,player.w,player.h+1,ST7735_BLACK);
		player.y=120;
		crossflag=1;
		if(roomflag==0){roomflag=1;
		}else{
			if(roomflag==1){roomflag=3;
			}else{
				if(roomflag==3)roomflag=4;
			}
		}
	} 
	if(player.y==120 && d==0 && player.x>=70 && player.x<=80&&(roomflag==1||roomflag==3||roomflag==4)){ //1, 3, 4
		ST7735_FillRect(player.x,player.y-player.h,player.w,player.h,ST7735_BLACK);
		player.y=58;
		crossflag=1;
		if(roomflag==1){
			roomflag=0;
		}else{
			if(roomflag==3){
				roomflag=1;
			}else{
				if(roomflag==4)roomflag=3;
			}
		}
	}
}
// **************Room_Init*********************
// establishes the room layout and enemy layout
// Input: none
// Output: none
void Room_Init(){
	TIMER2_IMR_R &= ~0x00000001;
    NVIC_DIS0_R = 1<<23;
	ST7735_FillRect(7,39,145,81,ST7735_BLACK);
	if(roomflag==0){//first room, no enemies
		ST7735_DrawBitmap(0, 127, leftDoorWall,7,96); //left wall w/ door
		ST7735_DrawBitmap(0,88,leftNoDoor,7,18);
		ST7735_DrawBitmap(153, 127, rightDoorWall,7,96); //right wall w/ door
		ST7735_DrawBitmap(153, 88, RightNoDoor,7,18); //right wall w/ door
		ST7735_DrawBitmap(7, 38, frontWall,146,7); //front wall no door
		ST7735_DrawBitmap(70,38,OpenFrontDoor,18,9);
		ST7735_DrawBitmap(7, 127, backWall,146,7); //back wall no door
		
	}
	if(roomflag==1){//second room, 3 enemies
		ST7735_DrawBitmap(0, 127, leftDoorWall,7,96); //left wall w/ door
		ST7735_DrawBitmap(71, 127, BackDoor,18,7);
		ST7735_DrawBitmap(153, 88, RightNoDoor,7,18); //right wall w/ door
		if(BFDflag!=3){
			ST7735_DrawBitmap(70,38,CrackedFrontDoor,18,7);
		}else{ST7735_DrawBitmap(70,38,OpenFrontDoor,18,9);}
		enemy.y=90;
		enemy2.x=104;
		ST7735_DrawBitmap(enemy.x,enemy.y, enemy.image, enemy.w, enemy.h);
		ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
	}
	if(roomflag==2){//third room, 4 enemies, and bomb item
		ST7735_DrawBitmap(0,88,leftNoDoor,7,18);
		ST7735_DrawBitmap(153, 127, rightDoorWall,7,96); //right wall w/ door
		ST7735_DrawBitmap(7, 127, backWall,146,7); //back wall no door
		ST7735_DrawBitmap(7, 38, frontWall,146,7); //front wall no door
		if(pickup==0){ST7735_DrawBitmap(10,70,UIBomb,5,7);}

		enemy.x=45;
		enemy2.x=104;
		enemy3.x=25;
		enemy4.x=77;
		ST7735_DrawBitmap(enemy.x,enemy.y, enemy.image, enemy.w, enemy.h);
		ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		ST7735_DrawBitmap(enemy3.x,enemy3.y, enemy3.image, enemy3.w, enemy3.h);
		ST7735_DrawBitmap(enemy4.x,enemy4.y, enemy4.image, enemy4.w, enemy4.h);
		
	}
	if(roomflag==3){//fourth room, 4 enemies
		ST7735_DrawBitmap(0,88,leftNoDoor,7,18);
		ST7735_DrawBitmap(71, 127, BackDoor,18,7);
		ST7735_DrawBitmap(70,38,OpenFrontDoor,18,9);
		ST7735_DrawBitmap(153, 127, rightDoorWall,7,96); //right wall w/ door
		ST7735_DrawBitmap(153, 86, KeyRightdoor,7,18); //right wall w/ door
		enemy.x=7;
		enemy.y=56;
		enemy2.x=108;
		enemy3.y=90;
		ST7735_DrawBitmap(enemy.x,enemy.y, enemy.image, enemy.w, enemy.h);
		ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		ST7735_DrawBitmap(enemy3.x,enemy3.y, enemy3.image, enemy3.w, enemy3.h);
	}
	if(roomflag==4){//fifth room, enemies, and key item 
		ST7735_DrawBitmap(71, 127, BackDoor,18,7);
		ST7735_DrawBitmap(0,88,leftNoDoor,7,18);
		ST7735_DrawBitmap(7, 38, frontWall,146,7); //front wall no door
		ST7735_DrawBitmap(153, 88, RightNoDoor,7,18); //right wall w/ door
		enemy.x=7;
		enemy.y=57;
		enemy2.x=129;
		enemy3.y=90;
		ST7735_DrawBitmap(enemy.x,enemy.y, enemy.image, enemy.w, enemy.h);
		ST7735_DrawBitmap(enemy2.x, enemy2.y, enemy2.image, enemy2.w, enemy2.h);
		ST7735_DrawBitmap(enemy3.x,enemy3.y, enemy3.image, enemy3.w, enemy3.h);
		if(pickupk==0){
		ST7735_DrawBitmap(145,65,key,8,15);
	}
		}
	if(roomflag==5){//sixth room, final boss
		Timer2_Init(4000000);
		ST7735_DrawBitmap(7, 38, frontWall,146,7); //front wall no door
		ST7735_DrawBitmap(153, 88, RightNoDoor,7,18); //right wall w/ door
		ST7735_DrawBitmap(0, 127, leftDoorWall,7,96); //left wall w/ door
		ST7735_DrawBitmap(7, 127, backWall,146,7); //back wall no door
		ST7735_DrawBitmap(64,93,ganon.image,32,32);
		fire.y=61;	
		fire.x=64-fire.w;
		fire2.y=ganon.y+fire2.h;
		fire2.x=ganon.x+ganon.w;
		
		bossFlag=1;
				

	}
	enemy.health=2;
	enemy2.health=2;
	enemy3.health=2;
	enemy4.health=2;
	enemy.repeat=0;
	enemy2.repeat=0;
	enemy3.repeat=0;
	enemy4.repeat=0;
	crossflag=0;
	TIMER2_IMR_R = 0x00000001;
    NVIC_EN0_R = 1<<23;
}

int main(void){
	DisableInterrupts();
	PolledButtons_Init();
	ST7735_InitR(INITR_REDTAB);
	ST7735_SetRotation(3);
	EnableInterrupts();
	Sound_Init();
	while(TScreen==0){
	Title();
	}
	while(language==0){
	MainMenu();
	}
  while(instructflag==0){
	Instructions();
	}
	DisableInterrupts();
  TExaS_Init(NONE);       // Bus clock is 80 MHz 
  Random_Init(1);
 // Output_Init(); don't use this 
	SysTick_Init(8000000/3);//8000000 5333333
	Timer2_Init(8000000);
	Timer3_Init(800000);
	ADC_Init();
	Timer1_Init(88000000,4);
	ST7735_FillScreen(ST7735_BLACK);
	//ST7735_DrawBitmap(7, 127, backWall,146,7);
	
	ST7735_DrawBitmap(5, 25, hearts,55,23); //this is the hearts User interface 
	if(language==2)ST7735_DrawBitmap(25,7,Vida,15,5);
	ST7735_DrawBitmap(123, 26, button,32,20); // this is the buttons user interface
	ST7735_DrawBitmap(player.x, player.y, player.image, player.w, player.h);		//this is the player
	ST7735_DrawBitmap(145, 23, Sword,4,11);
	crossflag=1;
	EnableInterrupts();
		while(1){
		if(crossflag==1)Room_Init();
		if(flag == 1){
			i^=1;
			if(DataX<666){
				player.dx = 1;
				d=2;
				if(i==0){
					player.w = 18;
					player.h = 18;
					player.image = CharRgt;
				}
				if(i==1){
					player.w = 17;
					player.h = 17;
					player.image = CharRgt2;
				}
			}
			if(DataX>1333){
				player.dx = -1;
				d=3;
				if(i==0){
					player.w = 18;
					player.h = 18;
					player.image = Charlft;
				}
				if(i==1){
					player.w = 18;
					player.h = 17;
					player.image = Charlft2;
				}
			}
			if(DataX>666&&DataX<1333){
					player.dx = 0;
			}
			if(DataY<666){
  
				player.dy = 1;
				d=0;
				if(i==0){
					player.w = 16;
					player.h = 18;
					player.image = CharFor;
					}
				if(i==1){
					player.w =17;
					player.image = CharFor2;
					}
			}
			if(DataY>1333){
				player.dy = -1;
				d=1;
				if(i==0){
					player.w = 18;
					player.h = 18;
					player.image = CharBack;
				}
				if(i==1){
					player.w = 18;
					player.h = 18;
					player.image = CharBack2;
				}
			}
			if(DataY>666&&DataY<1333){
					player.dy = 0;
			}
			move();
			ST7735_DrawBitmap(player.x, player.y, player.image, player.w, player.h);		//this is the player
			flag =0;
		}
		if(BFDflag==2){
			ST7735_DrawBitmap(70,38,OpenFrontDoor,18,9);
			BFDflag = 3;
		}
		//Collision//
		if(((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy.health>0&&player.x+player.w<=enemy.x+enemy.w && player.x+player.w >= enemy.x && player.y>=enemy.y-enemy.h && player.y<=enemy.y)||((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy2.health>0&&player.x+player.w<=enemy2.x+enemy2.w && player.x+player.w >= enemy2.x && player.y>=enemy2.y-enemy2.h && player.y<=enemy2.y)){
			collflag=1;
			ST7735_FillRect(player.x+player.w-5,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			
		}
		if(((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy.health>0&&player.x>=enemy.x && player.x<= enemy.x+enemy.w && player.y>=enemy.y-enemy.h && player.y<=enemy.y)||((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy2.health>0&&player.x>=enemy2.x && player.x<= enemy2.x+enemy2.w && player.y>=enemy2.y-enemy2.h && player.y<=enemy2.y)){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			collflag=1;
		}

		if(((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy.health>0&&player.x+player.w<=enemy.x+enemy.w && player.x+player.w >= enemy.x && player.y-player.h<=enemy.y && player.y-player.h>=enemy.y-enemy.h)||((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy2.health>0&&player.x+player.w<=enemy2.x+enemy2.w && player.x+player.w >= enemy2.x && player.y-player.h<=enemy2.y && player.y-player.h>=enemy2.y-enemy2.h)){
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y+5;}
			if(d==0){player.y=player.y+5;}	
			collflag=1;
		}
		if(((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy.health>0&&player.x>=enemy.x && player.x<= enemy.x+enemy.w && player.y-player.h<=enemy.y && player.y-player.h>=enemy.y-enemy.h)||((roomflag==1||roomflag==2||roomflag==3||roomflag==4)&&enemy2.health>0&&player.x>=enemy2.x && player.x<= enemy2.x+enemy2.w && player.y-player.h<=enemy2.y && player.y-player.h>=enemy2.y-enemy2.h)){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y+5;}
			if(d==0){player.y=player.y+5;}
			collflag=1;
		}
	if((roomflag==2||roomflag==3||roomflag==4)&&enemy3.health>0&&(player.x+player.w<=(enemy3.x+enemy3.w)&& player.x+player.w >= (enemy3.x) && player.y>=(enemy3.y-enemy3.h) && player.y<=(enemy3.y))){
			collflag=1;
			ST7735_FillRect(player.x+player.w-5,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			
		}
		if((roomflag==2||roomflag==3||roomflag==4)&&enemy3.health>0&&(player.x>=(enemy3.x) && player.x<= (enemy3.x+enemy3.w) && player.y>=(enemy3.y-enemy3.h) && player.y<=(enemy3.y))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
	
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			collflag=1;
		}
		if((roomflag==2||roomflag==3||roomflag==4)&&enemy3.health>0&&(player.x+player.w<=(enemy3.x+enemy3.w) && player.x+player.w >= (enemy3.x) && player.y-player.h<=(enemy3.y) && player.y-player.h>=(enemy3.y-enemy3.h))){
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
		
			if(d==1){player.y=player.y+5;}
		
			if(d==0){player.y=player.y+5;}	
			collflag=1;
		}
		if((roomflag==2||roomflag==3||roomflag==4)&&enemy3.health>0&&(player.x>=(enemy3.x) && player.x<= (enemy3.x+enemy3.w) && player.y-player.h<=(enemy3.y) && player.y-player.h>=(enemy3.y-enemy3.h))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y+5;}
	
			if(d==0){player.y=player.y+5;}
			collflag=1;
		}
		if(roomflag==2&&enemy4.health>0&&(player.x+player.w<=(enemy4.x+enemy4.w)&& player.x+player.w >= (enemy4.x) && player.y>=(enemy4.y-enemy4.h) && player.y<=(enemy4.y))){
			collflag=1;
			ST7735_FillRect(player.x+player.w-5,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			
		}
		if(roomflag==2&&enemy4.health>0&&(player.x>=(enemy4.x) && player.x<= (enemy4.x+enemy4.w) && player.y>=(enemy4.y-enemy4.h) && player.y<=(enemy4.y))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
	
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			collflag=1;
		}
		if(roomflag==2&&enemy4.health>0&&(player.x+player.w<=(enemy4.x+enemy4.w) && player.x+player.w >= (enemy4.x) && player.y-player.h<=(enemy4.y) && player.y-player.h>=(enemy4.y-enemy.h))){
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
		
			if(d==1){player.y=player.y+5;}
		
			if(d==0){player.y=player.y+5;}	
			collflag=1;
		}
		if(roomflag==2&&enemy4.health>0&&(player.x>=(enemy4.x) && player.x<= (enemy4.x+enemy4.w) && player.y-player.h<=(enemy4.y) && player.y-player.h>=(enemy4.y-enemy4.h))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y+5;}
	
			if(d==0){player.y=player.y+5;}
			collflag=1;
		}
		if(roomflag==2&&enemy4.health>0&&(player.x+player.w<=(enemy4.x+enemy4.w)&& player.x+player.w >= (enemy4.x) && player.y>=(enemy4.y-enemy4.h) && player.y<=(enemy4.y))){
			ST7735_FillRect(player.x+player.w-5,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			collflag=1;
		}
		if(roomflag==5&&(player.x+player.w<=(96)&& player.x+player.w >= (64) && player.y>=(61) && player.y<=(93))){
			ST7735_FillRect(player.x+player.w-5,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			
		}
		if(roomflag==5&&(player.x>=(64) && player.x<= (96) && player.y>=(61) && player.y<=(93))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
		}
		if(roomflag==5&&(player.x+player.w<=(96) && player.x+player.w >= (64) && player.y-player.h<=(93) && player.y-player.h>=(60))){
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
		
			if(d==1){player.y=player.y+5;}
		
			if(d==0){player.y=player.y+5;}	
		}
		if(roomflag==5&&(player.x>=(64) && player.x<= (96) && player.y-player.h<=(93) && player.y-player.h>=(61))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y+5;}
	
			if(d==0){player.y=player.y+5;}
		}
		
		if(roomflag==5&&(player.x+player.w<=(fire.x+fire.w)&& player.x+player.w >= (fire.x) && player.y>=(fire.y-fire.h) && player.y<=(fire.y))){
			ST7735_FillRect(player.x+player.w-5,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			collflag=1;
		}
		if(roomflag==5&&(player.x>=(fire.x) && player.x<= (fire.x+fire.w) && player.y>=(fire.y-fire.h) && player.y<=(fire.y))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-5,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y-5;}
			if(d==0){player.y=player.y-5;}
			collflag=1;
		}
		if(roomflag==5&&(player.x+player.w<=(fire.x+fire.w) && player.x+player.w >= (fire.x) && player.y-player.h<=(fire.y) && player.y-player.h>=(fire.y-fire.h))){
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x-5;}
			if(d==2){player.x=player.x-5;}
		
			if(d==1){player.y=player.y+5;}
		
			if(d==0){player.y=player.y+5;}	
			collflag=1;
		}
		if(roomflag==5&&(player.x>=(fire.x) && player.x<= (fire.x+fire.w) && player.y-player.h<=(fire.y) && player.y-player.h>=(fire.y-fire.h))){
			ST7735_FillRect(player.x,player.y-player.h,5,18,ST7735_BLACK);
			ST7735_FillRect(player.x,player.y-player.w,18,5,ST7735_BLACK);
			if(d==3){player.x=player.x+5;}
			
			if(d==2){player.x=player.x+5;}
			if(d==1){player.y=player.y+5;}
	
			if(d==0){player.y=player.y+5;}
			collflag=1;
		}
		if(buttonflag==1){
		if(language==1){
		ST7735_SetCursor(11,0);
		//ST7735_OutString("Swing");
		}	
		if(language==2){
		ST7735_SetCursor(11,0);
		//ST7735_OutString("Balancear");
		}	
		OldY = player.y;
		OldH = player.h;
		OldW = player.w;
		OldX = player.x;
		OldImage = player.image;
		if(d==0){ //character attacking forward
			player.w = 18;
			player.h = 29;
			if(player.y>109){
				OldY =109;
				player.y = 120;
			}else{player.y += 11;}
			player.image = CharForAtt;
			ST7735_DrawBitmap(player.x, player.y, player.image, player.w, player.h);
			for(uint32_t j=0; j<0x00400000; j++){}
			if(player.x+8>=enemy.x&&player.x+player.w-8<=enemy.x+enemy.w&&player.y>enemy.y-enemy.h&&player.y-11<=enemy.y-enemy.h){
				enemy.health--;
			}
			if(player.x+8>=enemy2.x&&player.x+player.w-8<=enemy2.x+enemy2.w&&player.y>enemy2.y-enemy2.h&&player.y-11<=enemy2.y-enemy2.h){
				enemy2.health--;
			}
			if(player.x+8>=enemy3.x&&player.x+player.w-8<=enemy3.x+enemy3.w&&player.y>enemy3.y-enemy3.h&&player.y-11<=enemy3.y-enemy3.h){
				enemy3.health--;
			}
			if(player.x+8>=enemy4.x&&player.x+player.w-8<=enemy4.x+enemy4.w&&player.y>enemy4.y-enemy4.h&&player.y-11<=enemy4.y-enemy4.h){
				enemy4.health--;
			}
			if(roomflag==5&&(player.x+8>=64&&player.x+player.w-8<=96&&player.y>61&&player.y-11<=61)){
				ganon.health--;
			}
			player.image =OldImage;
			player.y = OldY;
			player.h = OldH;
			player.w = OldW; 
			player.x = OldX;
			ST7735_FillRect(player.x,player.y,player.w,11, ST7735_BLACK);
		}
		if(d==1){ //character attacking backward
			player.w = 18;
			player.h = 30;
			if(player.y<68){
				OldY = 68;
				player.y =68;
			}
			player.image = CharBackAtt;
			ST7735_DrawBitmap(player.x, player.y, player.image, player.w, player.h);
			for(uint32_t j=0; j<0x00400000; j++){}
			if(player.x+6>=enemy.x&&player.x+player.w-9<=enemy.x+enemy.w&&player.y-player.h<=enemy.y&&player.y-18>=enemy.y){
				enemy.health--;
			}
			if(player.x+6>=enemy2.x&&player.x+player.w-9<=enemy2.x+enemy2.w&&player.y-player.h<=enemy2.y&&player.y-18>=enemy2.y){
				enemy2.health--;
			}
			if(player.x+6>=enemy3.x&&player.x+player.w-9<=enemy3.x+enemy3.w&&player.y-player.h<=enemy3.y&&player.y-18>=enemy3.y){
				enemy3.health--;
			}
			if(player.x+6>=enemy4.x&&player.x+player.w-9<=enemy4.x+enemy4.w&&player.y-player.h<=enemy4.y&&player.y-18>=enemy4.y){
				enemy4.health--;
			}
			if(roomflag==5&&(player.x+6>=64&&player.x+player.w-9<=96&&player.y-player.h<=93&&player.y-18>=93)){
				ganon.health--;
			}
			player.image =OldImage;
			player.y = OldY;
			player.h = OldH;
			player.w = OldW; 
			player.x = OldX;
			ST7735_FillRect((player.x+4),(player.y-28),7,12, ST7735_BLACK);
		}
		if(d==2){ //character attacking right
			player.w = 29;
			player.h = 17;
			if(player.x>123){
				OldX =123;
				player.x = 123;
			}
			player.image = CharRgtAtt;
			ST7735_DrawBitmap(player.x, player.y, player.image, player.w, player.h);
			for(uint32_t j=0; j<0x00400000; j++){}
			if(player.x+player.w>=enemy.x&&player.x+16<=enemy.x&&player.y-7<=enemy.y&&player.y-9>=enemy.y-enemy.h){
				enemy.health--;
			}
			if(player.x+player.w>=enemy2.x&&player.x+16<=enemy2.x&&player.y-7<=enemy2.y&&player.y-9>=enemy2.y-enemy2.h){
				enemy2.health--;
			}
			if(player.x+player.w>=enemy3.x&&player.x+16<=enemy3.x&&player.y-7<=enemy3.y&&player.y-9>=enemy3.y-enemy3.h){
				enemy3.health--;
			}
			if(player.x+player.w>=enemy4.x&&player.x+16<=enemy4.x&&player.y-7<=enemy4.y&&player.y-9>=enemy4.y-enemy4.h){
				enemy4.health--;
			}
			if(roomflag==5&&(player.x+player.w>=64&&player.x+16<=64&&player.y-7<=93&&player.y-9>=61)){
				ganon.health--;
			}
			player.image =OldImage;
			player.y = OldY;
			player.h = OldH;
			player.w = OldW; 
			player.x = OldX;
			ST7735_FillRect((player.x+16),(player.y-10),12,7, ST7735_BLACK);
		}
		if(d==3){ //character attacking left
			player.w = 29;
			player.h = 17;
			if(player.x <= 18){
				OldX = 18;
				player.x=7;
			}else{player.x -=11;}
			player.image = CharLftAtt;
			ST7735_DrawBitmap(player.x, player.y, player.image, player.w, player.h);
			for(uint32_t j=0; j<0x00400000; j++){}
			if(player.x<enemy.x+enemy.w&&player.x+13>=enemy.x+enemy.w&&player.y-7<=enemy.y&&player.y-9>=enemy.y-enemy.h){
				enemy.health--;
			}
			if(player.x<enemy2.x+enemy2.w&&player.x+13>=enemy2.x+enemy2.w&&player.y-7<=enemy2.y&&player.y-9>=enemy2.y-enemy2.h){
				enemy2.health--;
			}
			if(player.x<enemy3.x+enemy3.w&&player.x+13>=enemy3.x+enemy3.w&&player.y-7<=enemy3.y&&player.y-9>=enemy3.y-enemy3.h){
				enemy3.health--;
			}
			if(player.x<enemy4.x+enemy4.w&&player.x+13>=enemy4.x+enemy4.w&&player.y-7<=enemy4.y&&player.y-9>=enemy4.y-enemy4.h){
				enemy4.health--;
			}
			if(roomflag==5&&(player.x<96&&player.x+13>=96&&player.y-7<=93&&player.y-9>=61)){
				ganon.health--;
			}
			player.image =OldImage;
			player.y = OldY;
			player.h = OldH;
			player.w = OldW; 
			player.x = OldX;
			ST7735_FillRect((player.x-11),(player.y-10),12,7, ST7735_BLACK);
		}
	}
		buttonflag=0;
		if(enemy.health<=0&&enemy.repeat==0){
			ST7735_FillRect(enemy.x,enemy.y-enemy.h+1,enemy.w,enemy.h-1,ST7735_BLACK);
			enemy.repeat=1;
		}
		if(enemy2.health<=0&&enemy2.repeat==0){
			ST7735_FillRect(enemy2.x,enemy2.y-enemy2.h+1,enemy2.w,enemy2.h-1,ST7735_BLACK);
			enemy2.repeat=1;
		}
		if(enemy3.health<=0&&enemy3.repeat==0){
			ST7735_FillRect(enemy3.x,enemy3.y-enemy3.h+1,enemy3.w,enemy3.h-1,ST7735_BLACK);
			enemy3.repeat=1;
		}
		if(enemy4.health<=0&&enemy4.repeat==0){
			ST7735_FillRect(enemy4.x,enemy4.y-enemy4.h+1,enemy4.w,enemy4.h-1,ST7735_BLACK);
			enemy4.repeat=1;
		}

			
			if(BossDefeat==1){
			if(ganon.health==0&&ganon.repeat==0){
            sum=sum+5;
            ST7735_FillRect(ganon.x,ganon.y-ganon.h+1,ganon.w,ganon.h-1,ST7735_BLACK);
            ganon.repeat=1;
			}
			if(BossDefeat==1)ScoreScreen();
		
	}
		 
}


}






