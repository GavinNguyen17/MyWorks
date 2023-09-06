// FiFo.c
// Runs on LM4F120/TM4C123
// Provide functions that implement the Software FiFo Buffer
// Last Modified: 11/11/2021 
// Student names: Rodrigo Romero, Gavin Nguyen
#include <stdint.h>

// Declare state variables for FiFo
//        size, buffer, put and get indexes
#define SIZE 10
	uint32_t  PutI; //should be 0 to SIZE-1
	uint32_t  GetI; //should be 0 to SIZE-1
	int32_t  FIFO[SIZE];
	int32_t  *p;

// *********** FiFo_Init**********
// Initializes a software FIFO of a
// fixed size and sets up indexes for
// put and get operations
void Fifo_Init() {
//Complete this
	PutI=GetI=0; //empty
	p=&FIFO[0];
}

// *********** FiFo_Put**********
// Adds an element to the FIFO
// Input: Character to be inserted
// Output: 1 for success and 0 for failure
//         failure is when the buffer is full
uint32_t Fifo_Put(char data){
  //Complete this routine
	if(((PutI+1)%SIZE) == GetI) return 0; //fail if full
	FIFO[PutI] = data;
	PutI =(PutI+1)%SIZE;
   return(1);
}

// *********** Fifo_Get**********
// Gets an element from the FIFO
// Input: Pointer to a character that will get the character read from the buffer
// Output: 1 for success and 0 for failure
//         failure is when the buffer is empty
uint32_t Fifo_Get(char *datapt){
  //Complete this routine
	if(PutI==GetI) return 0; //fail if empty
	*datapt =FIFO[GetI];
	GetI = (GetI+1)%SIZE;
   return(1);
}




