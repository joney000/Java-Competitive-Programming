/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

 class Segment_Tree{
	
	
  public static void main(String[] args)throws Exception

    {  
	
	/*  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in),2000);
    BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out),2000);
	String[] s = br.readLine().split(" ");	
	int n = Integer.parseInt(s[0]);int q = Integer.parseInt(s[1]);
	int num[] = new int[n+1];
	int[] m = new int[3*n+1];//size = 2*n+1
	Arrays.fill(num,-1);
 	s = br.readLine().split(" ");	
	for(int i=1;i<=n;i++)num[i]=Integer.parseInt(s[i-1]);
	///build tree
	maketree(1,1,n,m,num);
	
	for(int qq=1;qq<=q;qq++){
	s = br.readLine().split(" ");	
	int i = Integer.parseInt(s[0]); int j=Integer.parseInt(s[1]);
	int ans = query(1,1,n,m,num,i,j);
	out.write(""+num[ans]+"\n");out.flush();
	}
	
	
    }   
	public static void maketree(int node,int i,int j,int[] m,int []num)throws Exception{
		if(i==j){m[node]=i;return;}
	
		maketree(2*node,i,(i+j)/2,m,num);
		maketree(2*node+1,((i+j)/2)+1,j,m,num);
	
		if(num[m[2*node]]<=num[m[2*node+1]])m[node]=m[2*node];
		else m[node]=m[2*node+1];
	}
	public static int query(int node,int l,int r,int[] m,int []num,int i,int j)throws Exception{
		if(l>j||r<i||l>r)return -1;  //invalid condition
		if(l>=i&&r<=j) return m[node];
		
		int p1 = query(2*node,l,(l+r)/2,m,num,i,j);
		int p2 = query(2*node+1,((l+r)/2)+1,r,m,num,i,j);
		
		if(p1==-1)return p2;
		if(p2==-1)return p1;
		if(num[p1]<=num[p2])return p1;
		else return p2;
	}
	public static boolean isPrime(long n)throws Exception{
		if(n==1)return false;
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
 } //end of class        
        
