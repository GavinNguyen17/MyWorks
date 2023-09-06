/* 
 * EE312 Simple CRM Project
 *
 * YOUR INFO HERE!
 */

#include <stdio.h>
#include <assert.h>
#include "MyString.h"
#include "Invent.h"

#define MAX_CUSTOMERS 1000
Customer customers[MAX_CUSTOMERS];
int num_customers = 0;
int currbooks=0, currdice=0, currfigures=0, currtowers=0, newcust=0;
int bigbooks=0, bigdice=0, bigfigures=0,bigtowers=0, mostbooks=0, mostdice=0, mostfigures=0, mosttowers=0, flag=0;

/* you'll probably need several more global variables */
/* and of course, you have a few functions to write */


/* clear the inventory and reset the customer database to empty */
void reset(void) {
    String c;
    currbooks=0, currdice=0, currfigures=0, currtowers=0;
    bigbooks=0, bigdice=0, bigfigures=0,bigtowers=0, mostbooks=0, mostdice=0, mostfigures=0, mosttowers=0;

    for(int i=0;i<num_customers;i++) {
        StringDestroy(&customers[i].name);
        customers[i].books=0;
        customers[i].dice=0;
        customers[i].figures=0;
        customers[i].towers=0;
    }
    num_customers=0;
}

void processSummarize() {
    int n=0;
    if(num_customers!=0) {
        for (int i = num_customers-1; i >= 0; i--) {
            if (num_customers != 1 && (customers[i].books != 0 || customers[i].dice != 0 || customers[i].figures != 0 ||
                                       customers[i].towers != 0)) {
                n++;
            }
            if (num_customers != 1 && customers[i].books == 0 && customers[i].dice == 0 && customers[i].figures == 0 &&
                customers[i].towers == 0) {
                StringDestroy(&customers[i].name);
            }


        }
        if (num_customers != 1) {
            num_customers = n;
        }
    }
    for(int i=0; i<num_customers; i++){
        if(num_customers==1){
            mostbooks=i;
            bigbooks=customers[i].books;
        }
        for (int j=0; j<num_customers;j++){
            if(i!=j) {
                if (customers[i].books>customers[j].books && customers[i].books>bigbooks) {
                    mostbooks=i;
                    bigbooks=customers[i].books;
                }
                if (customers[i].books<customers[j].books && customers[j].books>bigbooks) {
                    mostbooks=j;
                    bigbooks=customers[j].books;
                }
            }
        }
    }

    for(int i=0; i<num_customers; i++){
        for (int j=0; j<num_customers;j++){
            if(num_customers==1){
                mostdice=i;
                bigdice=customers[i].dice;
            }
            if(i!=j) {
                if (customers[i].dice>customers[j].dice && customers[i].dice>bigdice) {
                    mostdice=i;
                    bigdice=customers[i].dice;
                }
                if (customers[i].dice<customers[j].dice && customers[j].dice>bigdice) {
                    mostdice=j;
                    bigdice=customers[j].dice;
                }
            }
        }
    }

    for(int i=0; i<num_customers; i++){
        for (int j=0; j<num_customers;j++){
            if(num_customers==1){
                mostfigures=i;
                bigfigures=customers[i].figures;
            }
            if(i!=j) {
                if (customers[i].figures>customers[j].figures && customers[i].figures>bigfigures) {
                    mostfigures=i;
                    bigfigures=customers[i].figures;
                }
                if (customers[i].figures<customers[j].figures && customers[j].figures>bigfigures) {
                    mostfigures=j;
                    bigfigures=customers[j].figures;
                }
            }
        }
    }

    for(int i=0; i<num_customers; i++){
        for (int j=0; j<num_customers;j++){
            if(num_customers==1){
                mosttowers=i;
                bigtowers=customers[i].towers;
            }
            if(i!=j) {
                if (customers[i].towers>customers[j].towers && customers[i].towers>bigtowers) {
                    mosttowers=i;
                    bigtowers=customers[i].towers;
                }
                if (customers[i].towers<customers[j].towers && customers[j].towers>bigtowers) {
                    mosttowers=j;
                    bigtowers=customers[j].towers;
                }
            }
        }
    }
    printf("There are %d Books %d Dice %d Figures and %d Towers in inventory \n", currbooks, currdice, currfigures, currtowers);
    printf("we have had a total of %d different customers \n", num_customers);
    if(bigbooks!=0) {
        StringPrint(&customers[mostbooks].name);
        printf(" has purchased the most Books (%d) \n", bigbooks);
    }
    else {
        printf("no one has purchased any Books \n");
    }

    if(bigdice!=0) {
        StringPrint(&customers[mostdice].name);
        printf(" has purchased the most Dice (%d) \n", bigdice);
    }
    else {
        printf("no one has purchased any Dice \n");
    }

    if(bigfigures!=0) {
        StringPrint(&customers[mostfigures].name);
        printf(" has purchased the most Figures (%d) \n", bigfigures);    }
    else {
        printf("no one has purchased any Figures \n");
    }

    if(bigtowers!=0) {
        StringPrint(&customers[mosttowers].name);
        printf(" has purchased the most Towers (%d) \n", bigtowers);    }
    else {
        printf("no one has purchased any Towers \n");
    }

}


void processPurchase() {
    String p,c;
    int n;
    int i=0;
    readString(&c);
    readString(&p);
    readNum(&n);
    if(num_customers==0){
        customers[i].name = StringDup(&c);
        customers[i].books=0;
        customers[i].dice=0;
        customers[i].figures=0;
        customers[i].towers=0;
        num_customers++;
    }
    else{
        for(i=0; i<num_customers;i++) {
            if(StringIsEqualTo(&c, &customers[i].name)){
                newcust=1;
            }
        }
        if(newcust==0){
            customers[i].name = StringDup(&c);
            customers[i].books=0;
            customers[i].dice=0;
            customers[i].figures=0;
            customers[i].towers=0;
            num_customers++;
        }
        if(newcust==1){
            for(i=0; flag==0;i++){
                if(StringIsEqualTo(&c, &customers[i].name)){
                    flag=1;
                }

            }
            flag=0;
            i--;
            }

        newcust=0;
    }
    if(p.len==5){
        currbooks=currbooks-n;
        if(currbooks<0 || n<=0){
            currbooks=currbooks+n;
            printf("Sorry ");
            StringPrint(&customers[i].name);
            printf(", we only have %d Books \n", currbooks);
        }
        else{
         customers[i].books=customers[i].books+n;
        }
    }
    if(p.len==4){
        currdice=currdice-n;
        if(currdice<0 || n<=0){
            currdice=currdice+n;
            printf("Sorry ");
            StringPrint(&customers[i].name);
            printf(", we only have %d Dice \n", currdice);
        }
        else{
            customers[i].dice=customers[i].dice+n;
        }
    }
    if(p.len==7){
        currfigures=currfigures-n;
        if(currfigures<0 || n<=0){
            currfigures=currfigures+n;
            printf("Sorry ");
            StringPrint(&customers[i].name);
            printf(", we only have %d Figures \n", currfigures);
        }
        else{
            customers[i].figures=customers[i].figures+n;
        }
    }
    if(p.len==6){
        currtowers=currtowers-n;
        if(currtowers<0 || n<=0){
            currtowers=currtowers+n;
            printf("Sorry ");
            StringPrint(&customers[i].name);
            printf(", we only have %d Towers \n", currtowers);
        }
        else{
            customers[i].towers=customers[i].towers+n;
        }
    }
    StringDestroy(&c);
    StringDestroy(&p);
}


void processInventory() {
    String p;
    int n;
    readString(&p);
    readNum(&n);
    if(p.len==5){
        currbooks=currbooks+n;
    }
    if(p.len==4){
        currdice=currdice+n;
    }
    if(p.len==7){
        currfigures=currfigures+n;
    }
    if(p.len==6){
        currtowers=currtowers+n;
    }
    StringDestroy(&p);
}
