// Set <Project5.cpp>
// ECE 312 Project 5 submission by
// <Your Name Here>
// <Your EID>
// Slip days used: <0>
// Fall 2022

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include "Set.h"

/*
 * Several of the required functions have been written already
 * You may use these functions as-is or you may modify them in any way.
 * Please NOTE, you are responsible for ensuring that the functions work correctly in
 * your project solution. Just because the function works correctly in my solution does
 * NOT mean that the function will work correctly in yours.
 */

/*
 * Design NOTES:
 *
 * The design provided in this starter kit assumes
 * (1) empty sets will be represented with length == 0 and elements == nullptr (i.e., address 0)
 * (2) amortized doubling is not used, and capacity is ignored/unused. Functions should assume that
 * the amount of storage available in the elements[] array is equal to length
 */



/* done for you already */
void destroySet(Set* self) {
    free(self->elements);
}

/* done for you already */
void createEmptySet(Set* self) {
    self->len = 0;
    self->elements = nullptr;
}

/* done for you already */
void createSingletonSet(Set* self, int x) {
    self->elements = (int*) malloc(sizeof(int));
    self->elements[0] = x;
    self->len = 1;
}

/* done for you already */
void createCopySet(Set* self, const Set* other) {
    self->elements = (int*) malloc(other->len * sizeof(int));
    for (int k = 0; k < other->len; k += 1) {
        self->elements[k] = other->elements[k];
    }
    self->len = other->len;
}

/* done for you already */
void assignSet(Set* self, const Set* other) {
    if (self == other) { return; }

    destroySet(self);
    createCopySet(self, other);
}

/* return true if x is an element of self */
bool isMemberSet(const Set* self, int x) {
    int start=0;
    int end= self->len-1;
    while(start<=end){
        int mid = (start+end)/2;
        if (x==self->elements[mid]){
            return true;
        }
        if(x<self->elements[mid]){
            end=mid-1;
        }
        else{
            start = mid +1;
        }
    }
    return false;

}

/*
 * add x as a new member to this set.
 * If x is already a member, then self should not be changed
 * Be sure to restore the design invariant property that elemnts[] remains sorted
 * (yes, you can assume it is sorted when the function is called, that's what an invariant is all about)
 */
void insertSet(Set* self, int x) {
int i=0, j=0, temp=0, flag=0;
    if(isMemberSet(self, x) == true){
        return;
    }
    if (self->len == 0) {
        createSingletonSet(self, x);
        return;
    } else {

        self->elements=(int*) realloc(self->elements, sizeof(int)*(self->len+1));
        self->len++;
    }
    i=self->len-2;
    while(1) {
    if(x>self->elements[i]){
        self->elements[i+1]=x;
        break;
    }

    if(x<self->elements[i]){
        self->elements[i+1]=self->elements[i];
        if(i==0){
            self->elements[i]=x;
            break;
        }
        if(x>self->elements[i-1]){
            self->elements[i]=x;
            break;
        }

        i--;
    }
    }
}


/*
 * don't forget: it is OK to try to remove an element
 * that is NOT in the set.
 * If 'x' is not in the set 'self', then
 * removeSet should do nothing (it's not an error)
 * Otherwise, ('x' IS in the set), remove x. Be sure to update self->length
 * It is not necessary (nor recommended) to call malloc -- if removing an element means the
 * array on the heap is "too big", that's almost certainly OK, and reallocating a smaller array
 * is almost definitely NOT worth the trouble
 */
void removeSet(Set* self, int x) {
    int i=0;
    if(isMemberSet(self, x) == false){
        return;
    }
    for(int flag=0; flag==0;i++){
        if(self->elements[i]==x){
            break;
        }

    }
    for(;i<self->len-1;i++){
        self->elements[i]=self->elements[i+1];

    }
    self->len-=1;
}

/* done for you already */
void displaySet(const Set* self) {
    int k;

    printf("{");

    if (self->len == 0) {
        printf("}");
    }
    else {
        for (k = 0; k < self->len; k += 1) {
            if (k < self->len - 1) {
                printf("%d,", self->elements[k]);
            } else {
                printf("%d}", self->elements[k]);
            }
        }
    }
}

/* return true if self and other have exactly the same elements */
bool isEqualToSet(const Set* self, const Set* other) {
    if(self->len!=other->len){
        return false;
    }
    for(int i=0; i<self->len;i++){
        if(self->elements[i]!=other->elements[i]){
            return false;
        }
    }
    return true;
}

/* return true if every element of self is also an element of other */
bool isSubsetOf(const Set* self, const Set* other) {
    int count = 0, i=0, j=0;
    if (self->len > other->len) {
        return false;
    }
    if (self->len == 0) {
        return true;
    }
    while(self->len>i){
        if(self->elements[i]==other->elements[j]){
            i++;
            j++;
            count++;
        }
        else{
            i++;
        }
    }
    if (count==self->len){
        return true;
    }

}

/* done for you */
bool isEmptySet(const Set* self) {
    return self->len == 0;
}

/* remove all elements from self that are not also elements of other */

void intersectFromSet(Set* self, const Set* other){
    int l=0,i=0,j=0, flag=0;
    if(self->len==0){
        free(self->elements);
        createEmptySet(self);
        return;
    }
    if(other->len==0){
        free(self->elements);
        createEmptySet(self);
        return;
    }
    int* newset=(int*)malloc(sizeof(int)*self->len);

    while( i<self->len && j<other->len){
        if(self->elements[i]>other->elements[j]){
            j++;
        }
        else if(self->elements[i]<other->elements[j]){
            i++;
        }
        else if(self->elements[i]==other->elements[j]){
            newset[l]=self->elements[i];
            l++;
            j++;
            i++;

        }
    }

    free(self->elements);
    self->elements=newset;
    self->len=l;
}

/* remove all elements from self that are also elements of other */

void subtractFromSet(Set* self, const Set* other) {
    int l=0, j=0, i=0;
    int flag=0;
    if(self->len==0){
        free(self->elements);
        createEmptySet(self);
        return;
    }
    if(other->len==0){
        return;
    }

    int* newset=(int*)malloc(sizeof(int)*self->len);

    while(self->len>i) {
        if(j>=other->len&& self->len>i){
            newset[l]=self->elements[i];
            l++;
            i++;
            flag=1;
        }
        else if (other->elements[j] > self->elements[i]) {
                newset[l] = self->elements[i];
                l++;
                i++;
                flag=1;
        }
        else if (other->elements[j] < self->elements[i]) {
            j++;
            flag = 1;
        }
            if(flag==0) {
                i++;
                j++;
            }
            flag=0;

    }

    if(l==0){
        free(self->elements);
        free(newset);
        createEmptySet(self);
        return;
    }
    free(self->elements);
    self->elements=newset;
    self->len=l;
}

/* add all elements of other to self (obviously, without creating duplicate elements) */
void unionInSet(Set* self, const Set* other) {
    int l=0;
    int i =0, j=0;
    if(self->len==0){
        free(self->elements);
        createCopySet(self,other);
        self->len=other->len;
        return;
    }
    if(other->len==0){
        return;
    }
    int* newset=(int*)malloc(sizeof(int)*(self->len+other->len));

    while(self->len>i||other->len>j){
        if(i>=self->len&& other->len>j){
            newset[l]=other->elements[j];
            l++;
            j++;
        }
        else if(j>=other->len){
            newset[l]=self->elements[i];
            l++;
            i++;
        }
        else if(self->elements[i]<other->elements[j]){
            newset[l]=self->elements[i];
            l++;
            i++;
        }
        else if(self->elements[i]>other->elements[j]){
            newset[l]=other->elements[j];
            l++;
            j++;

        }
        else if(self->elements[i]==other->elements[j]){
            newset[l]=self->elements[i];
            i++;
            j++;
            l++;
        }
    }
    free(self->elements);
    self->elements=newset;
    self->len=l;
}
