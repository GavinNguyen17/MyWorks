// matrix-multiplication <Project2.cpp>
// ECE 312 Project 2 submission by
// <Your Name Here>
// <Your EID>
// Slip days used: <0>
// Fall 2022

#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include "MatrixMultiply.h"

void multiplyMatrices(
        double a[],
        const uint32_t a_rows,
        const uint32_t a_cols,
        double b[],
        const uint32_t b_cols,
        double c[]) {
    int m=0,p=0,n=0, e=0;
    double sum=0;

    for(m=0;m<a_rows;m++){
        for(p=0;p<b_cols;p++){
            c[e]=0;
            for(n=0;n<a_cols;n++){
                sum=a[m*a_cols+n]*b[b_cols*n+p];
                c[e]=sum+c[e];
            }
            e++;
        }

    }

    // https://en.wikipedia.org/wiki/Row-_and_column-major_order
}

double** multiplyMatricesPtr(
        double** a,
        const uint32_t a_rows,
        const uint32_t a_cols,
        double** b,
        const uint32_t b_cols) {
    int m=0,p=0,n=0;
    double sum=0, prod=0;
    double **q = (double**) malloc((a_rows)*sizeof(double *));
    for(int i=0; i<a_rows; i++){
        q[i]= (double*) malloc( (b_cols) * sizeof(double));
    }
    for(m=0;m<a_rows;m++){
        for(p=0;p<b_cols;p++){
            for(n=0,sum=0;n<a_cols;n++){
                sum += a[m][n] * b[n][p];
            }
            q[m][p]=sum;
        }


    }
    return q;
}

// https://en.wikipedia.org/wiki/Matrix_(mathematics)#Submatrix
double** createSubMatrix(
         double** a,
         const uint32_t a_rows,
         const uint32_t a_cols,
		 const uint32_t row_x, 
		 const uint32_t col_y) {
    int m = 0, p = 0, n = 0, o = 0, r = 0, i = 0;
        double **s = (double **) malloc((a_rows) * sizeof(double *));
        for (int i = 0; i < (a_rows - 1); i++) {
            s[i] = (double *) malloc((a_cols) * sizeof(double));
        }
        for (m = 0, o = 0; m < a_rows - 1; m++, o++) {
            for (p = 0,r=0; p < a_cols - 1; p++) {
                if (o == row_x) {
                    o++; }
                if (p == col_y) {
                    s[m][p] = a[o][p + 1];
                    n = 1;
                    r=1;
                }
                if(r==1 && n==0){
                    s[m][p] = a[o][p + 1];
                    n = 1;
                }
                if (n == 0) {
                    s[m][p] = a[o][p];
                }
                n = 0;
            }

        }
        return s;
    }

