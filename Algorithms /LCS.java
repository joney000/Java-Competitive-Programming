import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

class A {

 static int CeilIndex(int A[], int l, int r, int key) 
   {
    int m;
 
    while( r - l > 1 ) {
        m = l + (r - l)/2;
        if(A[m] <= key ) 
            r = m;
        else
            l = m;
    }
 
    return r;
}
 
static int LongestIncreasingSubsequenceLength(int A[], int size) {
     
    int[] tailTable   = new int[size];
    int len;
 
    for(int i=0;i<size;++i)
        tailTable[i] = 0;
    
    tailTable[0] = A[0];
    len = 1;
    for( int i = 1; i < size; i++ ) {
        if( A[i] > tailTable[0] )
            tailTable[0] = A[i];
        else if( A[i] < tailTable[len-1] )
            
            tailTable[len++] = A[i];
        else          
            tailTable[CeilIndex(tailTable, -1, len-1, A[i])] = A[i];
    }
 
    return len;
}
 
public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] arr = new int[N];
        
        for(int i=0;i<N;++i)
            arr[i] = sc.nextInt();
  long  n = LongestIncreasingSubsequenceLength(arr, N);
    n= n*(n-1)*(n-2);
	n/=6;
	System.out.println(n);
 
}
}