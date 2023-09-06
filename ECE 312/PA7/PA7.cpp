/*
 * Name: 
 * EID: 
 * PA7
 * Santacruz, Fall 2022
 */

#include <stdio.h>
#include "UTString.h"
#include "Customer.h"
#include "CustomerDB.h"
#include <iostream>
using namespace std;


void readString(UTString&);
void readNum(int&);

CustomerDB database;

int num_books = 0;
int num_dice = 0;
int num_figures = 0;
int num_towers = 0;

/* clear the inventory and reset the customer database to empty */
void reset(void) {
    database.clear();
    num_books = 0;
    num_dice = 0;
    num_figures = 0;
    num_towers = 0;
}

/*
 * selectInventItem is a convenience function that allows you
 * to obtain a pointer to the inventory record using the item type name
 * word must be "Books", "Dice", "Figures" or "Towers"
 * for example the expression *selectInventItem("Books") returns the
 * current global variable for the number of books in the inventory
 */
int* selectInventItem(UTString word) {
    if (word == "Books") {
        return &num_books;
    } else if (word == "Dice") {
        return &num_dice;
    } else if (word == "Figures") {
        return &num_figures;
    } else if (word == "Towers"){
        return &num_towers;
    }

    /* NOT REACHED */
    return 0;
}

/*
 * this overload of selectInventItem is similar to selectInventItem above, however
 * this overload takes a Customer as a second argument and selects the data member
 * of that Customer that matches "word". The function returns a pointer to one of the three data
 * members from the specified customer.
 */
int* selectInventItem(UTString word, Customer& cust) {
    if (word == "Books") {
        return &cust.books;
    } else if (word == "Dice") {
        return &cust.dice;
    } else if (word == "Figures") {
        return &cust.figures;
    } else if (word == "Towers"){
        return &cust.towers;
    }

    /* NOT REACHED */
    return 0;
}

/*
 * findMax searches through the CustomerDB "database" and returns the Customer
 * who has purchased the most items of the specified type.
 * type must be one of "Books", "Dice", "Figures" or "Towers".
 *
 * Note: if two or more Customers are tied for having purchased the most of that item type
 * then findMax returns the first Customer in the CustomerDB who has purchased that maximal
 * quantity.
 *
 * Note: in the special case (invalid case) where there are zero Customers in the
 * CustomerDB, fundMax returns a null pointer (0)
 */
Customer* findMax(UTString type) {
    Customer* result = 0;
    int max = 0;
    for (int k = 0; k < database.size(); k += 1) {
        Customer& cust = database[k];
        int *p = selectInventItem(type, cust);
        if (*p > max) {
            max = *p;
            result = &cust;
        }
    }

    return result;
}

void processPurchase() {
    int n;
    UTString item;
    CustomerDB MyDB;
    UTString person;
    readString(person);
    readString(item);
    readNum(n);

    if(n>0) {
        if (item == "Books") {
            if (num_books - n >= 0) {
                Customer p = database[person];
                database[person].books += n;
                num_books= num_books-n;
            } else {
                cout << "Sorry "<<person.c_str()<<", we only have "<<num_books<<" Books"<<endl;
            }
        }
        if (item == "Dice") {
            if (num_dice - n >= 0) {
                Customer p = database[person];
                database[person].dice += n;
                num_dice = num_dice-n;
            } else {
                cout << "Sorry "<<person.c_str()<<", we only have "<<num_dice<<" Dice"<<endl;
            }

        }
        if (item == "Figures") {
            if (num_figures - n >= 0) {
                Customer p = database[person];
                database[person].figures += n;
                num_figures = num_figures-n;
            } else {
                cout << "Sorry "<<person.c_str()<<", we only have "<<num_figures<<" Figures"<<endl;
            }

        }
        if (item == "Towers") {
            if (num_towers - n >= 0) {
                Customer p = database[person];
                database[person].towers += n;
                num_towers = num_towers-n;
            } else {
                cout << "Sorry "<<person.c_str()<<", we only have "<<num_towers<<" Towers"<<endl;
            }

        }
        }
    }


void processSummarize() {
    cout<<"There are "<<num_books<<" Books "<<num_dice<<" Dice "<<num_figures<<" Figures and "<<num_towers<<" Towers in inventory"<<endl;
    cout<<"we have had a total of "<<database.size()<< " different customers"<<endl;
    Customer* bookmax=findMax(UTString("Books"));
    Customer* dicemax=findMax(UTString("Dice"));
    Customer* figuremax=findMax(UTString("Figures"));
    Customer* towermax=findMax(UTString("Towers"));
    if(bookmax!=0){
        cout<<bookmax->name.c_str()<<" has purchased the most Books ("<<bookmax->books<<")"<<endl;
    }
    else{
        cout<<"no one has purchased any Books"<<endl;
    }

    if(dicemax!=0){
        cout<<dicemax->name.c_str()<<" has purchased the most Dice ("<<dicemax->dice<<")"<<endl;
    }
    else{
        cout<<"no one has purchased any Dice"<<endl;
    }

    if(figuremax!=0){
        cout<<figuremax->name.c_str()<<" has purchased the most Figures ("<<figuremax->figures<<")"<<endl;
    }
    else{
        cout<<"no one has purchased any Figures"<<endl;
    }

    if(towermax!=0){
        cout<<towermax->name.c_str()<<" has purchased the most Towers ("<<towermax->towers<<")"<<endl;
    }
    else{
        cout<<"no one has purchased any Towers"<<endl;
    }

}




void processInventory() {
    int n;
    UTString item;
    readString(item);
    readNum(n);
    if(item=="Books"){
        num_books+=n;
    }
    if(item=="Dice"){
        num_dice+=n;
    }
    if(item=="Figures"){
        num_figures+=n;
    }
    if(item=="Towers"){
        num_towers+=n;
    }
}
