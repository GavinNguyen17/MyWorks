//
// Created by Gavin on 11/9/2022.
//
#include <string>
#ifndef PA8_ABSTRACTSYNTAXTREE_H
#define PA8_ABSTRACTSYNTAXTREE_H
using namespace std;
class Node {
public:
    bool isOperator;
    string operan;
    int value=0;
    Node* leftChild=NULL;
    Node* rightChild=NULL;




};


#endif //PA8_ABSTRACTSYNTAXTREE_H
