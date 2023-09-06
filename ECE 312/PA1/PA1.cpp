/**
 * NAME:Gavin Nguyen
 * EID:Gpn235
 * Fall 2022
 * Santacruz
 */

#include <stdio.h> // declarations of standard input/output functions
#include <stdint.h> // declarations of standard integer types (e.g., int32_t)
#include <stdlib.h> // declarations of general functions in the standard library (e.g., abort, exit)

void printSuperStrings(char strings [], char superstrings []) {
    int i = 0, n = 0, j = 0, m = 0, stringnum = 0, supstringnum = 0, flag = 0, pi = 0, pj = 0, output = 0, done=0;
    //TODO
    //your code here.
    while (done==0) {
        /*Count the number of letters of string and superstring,
        if superstring<string go to the next superstring word*/

            for (i = i; flag != 1; i++) {
                if (strings[i] == NULL) {
                    break;
                }
                if (strings[i] == ' ') {
                    flag = 1;
                }
                if (strings[i] == 10) {
                    flag = 1;
                }
                if (strings[i] == 9) {
                    flag = 1;
                }
                if (flag == 0) {
                    stringnum++;
                }

            }
            flag = 0;
            i = pi;
        pj=j;
        for (j = j; flag != 1; j++) {
            if (superstrings[j] == NULL) {
                break;
            }
            if (superstrings[j] == ' ') {
                flag = 1;
            }
            if (superstrings[j] == 10) {
                flag = 1;
            }
            if (superstrings[j] == 9) {
                flag = 1;
            }
            if (flag == 0) {
                supstringnum++;
            }

        }
        flag = 0;
        j=pj;
        if (stringnum > supstringnum) {
            flag = 1;
            supstringnum = 0;
        }
        //Compare String and Superstring

        for (j = j; flag != 1; j++) {
            if (superstrings[j] == NULL) {
                if (n == stringnum) {
                    output = 1;
                }
                break;
            }
            if (superstrings[j] == strings[i]) {
                n++;
                i++;

            }
            if (superstrings[j] + 0x20 == strings[i]) {
                n++;
                i++;
            }
            if (superstrings[j] - 0x20 == strings[i]) {
                n++;
                i++;
            }

            if (superstrings[j+1] == ' ') {
                flag = 1;
            }
            if (superstrings[j+1] == 10) {
                flag = 1;
            }
            if (superstrings[j+1] == 9) {
                flag = 1;
            }
            if (flag == 1) {
                if (n == stringnum) {
                    output = 1;
                }

            }
        }



    //Output Superstring Word \n if String is in Superstring
    if(stringnum==0 && n==0){output=0;}
    if (output == 1) {
    for (pj=pj; pj < j; pj++) {
        printf("%c", superstrings[pj]);
    }
    printf("\n");
    output=0;
    flag=0;


}
    flag=0;
    if(superstrings[j]==NULL){
        flag=2;
    }
        for(j=j;flag!=2;j++){
            if (superstrings[j] == NULL) {
                break;
            }
            if (superstrings[j] == ' ') {
                flag = 2;
            }
            if (superstrings[j] == 10) {
                flag = 2;
            }
            if (superstrings[j] == 9) {
                flag = 2;
            }
        }
                //Repeat if String and Superstring are not null

                if(strings[i]==NULL && superstrings[j]!=NULL){
                    i=pi;
                }
                if(superstrings[j]==NULL) {
                    if (strings[i+stringnum] == NULL) {
                        strings[i]=NULL;
                        if (strings[i] == NULL && superstrings[j] == NULL) {
                            break;
                        }
                    }
                        i = pi;

                }

                    if (superstrings[j] == NULL) {
                        i = stringnum+pi+1;
                        pi = i;
                        j = 0;
                        pj = 0;
                        n = 0;
                        supstringnum = 0;
                        stringnum = 0;
                        flag=0;

                    } else {
                        supstringnum = 0;
                        stringnum = 0;
                        i = pi;
                        n = 0;
                        pj = j;
                        flag = 0;
                    }


    }
        }

