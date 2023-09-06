//
// Created by Gavin on 11/10/2022.
//
#include "Parse.h"
#include "PA8.h"
map<string,int> symbol;

int operationtable(int v1, int v2, string operatr){
    if (operatr =="+") {
        return v1+v2;
    }
    else if (operatr =="-"){
       return v1-v2;
    }
    else if (operatr =="*"){
        return v1*v2;
    }
    else if (operatr =="/"){
        return  v1/v2;
    }
    else if (operatr =="%"){
        return v1%v2;
    }
    else if (operatr =="&&"){
        return v1&&v2;
    }
    else if (operatr =="||"){
        return v1||v2;
    }
    else if (operatr =="<"){
        if(v1<v2){
            return 1;
        }
        return 0;
    }
    else if (operatr ==">"){
        if(v1>v2){
            return 1;
        }
        return 0;
    }
    else if (operatr =="=="){
        if(v1==v2){
            return 1;
        }
        return 0;
    }
    else if (operatr =="!="){
        if(v1!=v2){
            return 1;
        }
        return 0;
    }
    else if (operatr =="<="){
        if(v1<=v2){
            return 1;
        }
        return 0;
    }
    else if (operatr ==">="){
        if(v1>=v2){
            return 1;
        }
        return 0;
    }
    else if(operatr  == "!"){
        return !v1;
    }
    else if(operatr  == "~"){
        return v1*(-1);
    }
}

void print(){
    read_next_token();
    string text=string(next_token());
    cout<<text;
}

void output(){
    read_next_token();
    if((next_token_type==NUMBER)){
        cout<<token_number_value;
    }
    else if(next_token_type==NAME){
        cout<<symbol[next_token()];
    }
    else {
        Node *n = new Node;
        cout << polish(n);
        delete n->leftChild;
        delete n->rightChild;
        delete n;
    }
}

void var(){
    int num;

    string token = string(next_token());
    if(symbol.count(token)==0){
        read_next_token();
        if(next_token_type==NUMBER) {
            num = token_number_value;
        }
        else if(next_token_type==SYMBOL){
            Node *n = new Node;
            num= polish(n);
        }
        else if(next_token_type==NAME){
            num=grabvar();
        }
    symbol[token]=num;
    }
    else{
       cout<<"variable "<<token<<" incorrectly re-initialized"<<endl;
       set();
    }

}

void set(){
    int num;

    string token = string(next_token());
    if(symbol.count(token)==1){
        read_next_token();
        if(next_token_type==NUMBER) {
            num = token_number_value;
        }
        else if(next_token_type==SYMBOL){
            Node *n = new Node;
            num= polish(n);
        }
        else if(next_token_type==NAME){
            num=grabvar();
        }
        symbol[token]=num;
    }
    else{
        cout<<"variable "<<token<<" not declared"<<endl;
        var();
    }
}
int grabvar(){
    return symbol[next_token()];
}

void destroy(){
    symbol.clear();
}

int polish(Node* n){
    string token = string(next_token());
    n->operan=token;
    Node* nL= new Node;
    Node* nR= new Node;
    n->leftChild=nL;
    n->rightChild=nR;
    read_next_token();

    if(next_token_type==NUMBER || next_token_type==NAME){
        if(next_token_type==NAME){
            int numL=grabvar();
            nL->value=numL;
        }
        else {
            nL->value = token_number_value;
        }
        delete nL->rightChild;
        delete nL->leftChild;
    }
    else if(next_token_type==SYMBOL){
        nL->value=polish(nL);
        delete nL->leftChild;
        delete nL->rightChild;
    }

    token = string(peek_next_token());
    if((token!="text")&&(token!="output")&&(token!="var")&&(token!="set")) {
        read_next_token();
        if (next_token_type == SYMBOL) {
            nR->value = polish(nR);
            delete nR->leftChild;
            delete nR->rightChild;
        } else if (next_token_type == NUMBER || next_token_type == NAME) {
            if (next_token_type == NAME) {
                int numR = grabvar();
                nR->value = numR;
            } else {
                nR->value = token_number_value;
            }
            delete nR->rightChild;
            delete nR->leftChild;
        }
    }
    return operationtable(n->leftChild->value,n->rightChild->value,n->operan);

}