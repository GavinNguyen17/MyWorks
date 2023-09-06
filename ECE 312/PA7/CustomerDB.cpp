/*
 * Name: 
 * EID: 
 * PA7
 * Santacruz, Fall 2022
 */
#include <iostream>
#include <cassert>
#include "UTString.h"
#include "CustomerDB.h"

using namespace std;

const int default_capacity = 1;

Customer::Customer(UTString name) { // done, please do not edit
    this->books = 0;
    this->dice = 0;
    this->figures = 0;
    this->towers = 0;
    this->name = name;
}

CustomerDB::CustomerDB(void) { // done, please do not edit
    this->capacity = default_capacity;
    this->data = new Customer[this->capacity];
    this->length = 0;
}

int CustomerDB::size(void) { // done, please do not edit
    return this->length;
}

CustomerDB::~CustomerDB(void) { // done, please do not edit
    delete[] this->data;
}



void CustomerDB::clear(void) { // done, please do not edit
    delete[] this->data;
    this->capacity = default_capacity;
    this->data = new Customer[this->capacity];
    this->length = 0;
}

Customer& CustomerDB::operator[](int k) { // done, please do not edit
    assert(k >= 0 && k < this->length);
    return this->data[k];
}

Customer& CustomerDB::operator[](UTString name) {
    if(isMember(name)==1){
        for(int i=0; i<this->length;i++){
            if(this->data[i].name==name){
                return this->data[i];
            }
        }
    }
    else {
        if (this->capacity == this->length) {
            Customer *new_data = new Customer[capacity*2];
            for (int i = 0; i < this->length; i++) {
                new_data[i] = this->data[i];
            }
            delete[]this->data;
            new_data[this->length] = name;
            this->data=new_data;
            this->capacity=this->capacity*2;
            this->length++;
        } else {
            this->data[this->length] = name;
            this->length++;
        }

    }
    return this->data[length-1];
}

bool CustomerDB::isMember(UTString name) { 
    for(int i=0; i<this->length;i++){
        if(this->data[i].name==name){
            return true;
        }
    }
    return false;
}
