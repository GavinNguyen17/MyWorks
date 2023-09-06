/**
 * NAME:
 * EID:
 * Fall 2022
 * Santacruz
 */

#include <assert.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include "UTString.h"

/*
 * Helper macro to find the signature of a UTString
 * Treat this like a function that takes a UTString*
 * Accesses a uint32_t from where the signature should be located
 */
#define CHECK(s) (*(uint32_t*) ((s)->string + (s)->length + 1))

/*
 * Checks if a UTString is valid or not.
 */
bool isOurs(const UTString* s) {
    if (CHECK(s) == SIGNATURE) { return true; }
    else { return false; }
}

/*
 * Allocate a utstring on the heap.
 * Initialize the string correctly by copying src.
 * Return a pointer to the UTString.
 */

UTString* utstrdup(const char* src) {
    int flag=0;
    UTString* UT = (UTString*)malloc(sizeof(UTString));
    UT->length = strlen(src);
    UT->capacity= strlen(src);
    UT->string= (char*) malloc(sizeof(strlen(src)) * sizeof(SIGNATURE) + 1);
    for(int i=0, n=0;flag==0;i++,n++){
        UT->string[i]=src[n];
        if(n==UT->capacity){
            UT->string[i]='\0';
            flag=1;
        }
    }
    CHECK(UT)=SIGNATURE;
    return UT;
}

/*
 * Reverses the string in s.
 * Null and check should remain in the same location.
 * Only reverse everything before the \0.
 */
UTString* utstrrev(UTString* s) {
    int flag=0;
    int i=0;
    int n=0;
    char temparray[s->length];
    assert(isOurs(s));
    for(i=0, n=s->length; flag!=1 ; i++,n--){
        if(s->string[n]=='\0'){
            n--;
        }
        temparray[i]=s->string[n];
        if(i+1==s->length){
            flag=1;
        }
    }
    flag=0;
    for(n=0; flag!=1 ; n++){
        s->string[n]=temparray[n];
        if(n+1==s->length){
            flag=1;
        }
    }

    CHECK(s)=SIGNATURE;
    return s;
}

/*
 * Append the string suffix to the UTString s.
 *  s must be a valid UTString.
 * Do not allocate any additional storage: only append as many characters
 *  as will actually fit in s.
 * Update the length of s.
 * Return s with the above changes. */
UTString* utstrcat(UTString* s, const char* suffix) {
    int flag=0;
   int e= strlen(suffix);
    assert(isOurs(s));
   for (int i = s->length, n = 0; flag == 0; i++, n++) {
            s->string[i] = suffix[n];
            if (s->length + n == s->capacity) {
                s->string[i] = '\0';
                flag = 1;
            }
            if (i==s->length+ e) {
                s->string[i] = '\0';
              flag = 1;
            }


    }
    s->length=s->length+ e;
       if(s->length>s->capacity){
            s->length= strlen(s->string);
        }

    CHECK(s)=SIGNATURE;
    return s;
}

/*
 * Copy src into dst.
 *  dst must be a valid UTString.
 *  src is an ordinary string.
 * Do not allocate any additional storage: only copy as many characters
 *  as will actually fit in dst.
 * Update the length of dst.
 * Return dst with the above changes.
 */
UTString* utstrcpy(UTString* dst, const char* src) {
    int flag = 0;
    assert(isOurs(dst));
    for(int i=0, n=0;flag==0;i++,n++){
        dst->string[i]=src[n];
        if(n==dst->capacity){
            dst->string[i]='\0';
            flag=1;
        }
    }

        dst->length = strlen(dst->string);

    CHECK(dst)=SIGNATURE;
        return dst;
    }

/*
 * Free all memory associated with the given UTString.
 */
void utstrfree(UTString* self) {
    assert(isOurs(self));
    free(self->string);
    free(self);
}

/*
 * Make s have at least as much capacity as new_capacity.
 *  s must be a valid UTString.
 * If s has enough capacity already, do nothing.
 * If s does not have enough capacity, then allocate enough capacity,
 *  copy the old string and the null terminator, free the old string,
 *  and update all relevant metadata.
 * Return s with the above changes.
 */
UTString* utstrrealloc(UTString* s, uint32_t new_capacity) {
    assert(isOurs(s));
    if(s->capacity<new_capacity){
        char* UT= (char*)realloc(s->string,sizeof(UTString)+new_capacity+1);
        s->string=UT;
        s->length= strlen(s->string);
        s->capacity=new_capacity;

        return s;
    }
    else {

        return s;
    }
}
