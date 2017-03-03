/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

//zee algorithm to find pattern P in the larger text T in O(|S|+|T|)
 public class Main{
	
	
  public static void main(String[] args)throws Exception{  
	
	/*  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in),2000);
    BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out),2000);
	//int tests = Integer.parseInt(br.readLine());
	
	int arr [] =new int[1000005];
	
	//for(int t=0;t<tests ; t++){
		String[] s = br.readLine().split(" ");
		int a = Integer.parseInt(s[0]);int b = Integer.parseInt(s[1]);int p = Integer.parseInt(s[2]);int q = Integer.parseInt(s[3]);
		//long g = gcd(p,q);
		long v= 1;
		long x = 0;
		long y= 0;
		long l = 1;
		long r = 100000000000000000L;

		out.write(""+v);
		out.flush();
  }
    
	long binarySearch(int A[], int l, int r, int key){
    	int m = 0;
        while( l <= r ){
                m = l + (r-l)/2;
			if( A[m] == key ){ // first comparison
   		         return m;
   			} 
			if( A[m] < key ){ // second comparison
				l = m + 1;
			}else{
				r = m - 1;
			}    
		}
 		return -1;
	}
	public static boolean isPrime(long n)throws Exception{
		if(n==1)return false;
		if(n<=3)return true;
		if(n%2==0)return false;
		for(int i=2 ;i <= Math.sqrt(n); i++){
			if(n%i==0)return false;
		}
		return true;
	}
	public static long gcd(long a, long b)throws Exception{
		if(b==0)return a;
		return gcd(b,a%b);//b must be non zero so base condition on b==0;
	}
	public static long lcm(long a, long b)throws Exception{
		if(a==0||b==0)return 0;
		return a*b/gcd(a,b);//b must be non zero so base condition on b==0;
	}
	
	public static long pow(long a,long b,long mod)throws Exception{
	if(b==1)return a%mod;
	if(b==0)return 1;
	long ans=pow(a,b/2,mod);
	ans=(ans*ans)%mod;
	if(b%2!=0)ans=(ans*a)%mod;
	
	return ans;
	}
 } //end of class        
        
