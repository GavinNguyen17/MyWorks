#include "Parse.h"
#include "PA8.h"
using namespace std;



void run() {
    read_next_token();
    for(;next_token_type!=END;read_next_token()){
        string token = string(next_token());
        if(token=="//"){
            skip_line();
        }
        if(token=="text"){
        print();
        }
        else if(token=="output"){
            output();
        }
        else if(token=="set"){
            read_next_token();
            set();
        }
        else if(token=="var"){
            read_next_token();
            var();
        }
    }
    destroy();
}

int main() {
    set_input("test_grader.blip");
        run();
}
