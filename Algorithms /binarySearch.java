/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;


 public class Solution

 {

  public static void main(String[] args)throws Exception

    {  
	
	/*  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */               

       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
       BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
    
        
			int t = Integer.parseInt(br.readLine());
			long arr[] = new long[1000001];
			for(long i=1;i<=1000000;i++){
			  arr[(int)i]=(i*(i+1)*(2*i+1))/6;
			}
			for(int test=0;test<t;test++){
			 
			 long x = Long.parseLong(br.readLine());
			  int idx = Arrays.binarySearch(arr,1,1000000+1,x); // [L..R] = Arrays.binarySearch(arr,l,r+1,key);
			  if(idx<0){
			    idx=-idx;idx-=2;
			  }
	   		  long ans = idx ;
			  out.write(""+ans+"\n");
			  out.flush();          
			}     
        	
        
    }//end of public static void main();
	public static boolean isPrime(long n)throws Exception{
		if(n==2||n==3)return true;
		for(int i=2;i<=Math.sqrt(n);i++){
			if(n%i==0)return false;
		}
	return true;
	}
	public static long gcd(long a, long b)throws Exception{
		if(b==0)return a;
		return gcd(b,a%b);
	}
	public static long lcm(long a, long b)throws Exception{
		if(b==0||a==0)return 0;
		return (a*b)/gcd(a,b);
	}
	public static long pow(long a,long b,long mod)throws Exception{
		if(b==1)return a%mod;
		if(b==0)return 1;
		long ans=pow(a,b/2,mod);
		ans=(ans*ans)%mod;
		if(b%2!=0)ans=(ans*a)%mod;
	
		return ans;
	}
 } /