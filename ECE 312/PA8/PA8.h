//
// Created by Gavin on 11/10/2022.
//
#include "AbstractSyntaxTree.h"
#ifndef PA8_PA8_H
#define PA8_PA8_H
#include <iostream>
#include <string>
#include <map>
#include <vector>
using namespace std;


void run();
void print();
void output();
void var();
void set();
void destroy();
int operationtable(int v1, int v2, string operatr);
int polish(Node* other);
int grabvar();


#endif //PA8_PA8_H
