/* package whatever; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/* Name of the class has to be "Main" only if the class is public. */
class Ideone
 {    public static int n=0;
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
		int tests=Integer.parseInt(br.readLine());
		int num[]=new int[20];
		//int arr[]=new int[10000000];
		//int ch[]=new int[1001];
		for(int t=0;t<tests;t++){
			String s[]=br.readLine().split(" ");
			 n=Integer.parseInt(s[0]);
			int m=Integer.parseInt(s[1]);
			for(int i=0;i<n;i++){num[i]=Integer.parseInt(br.readLine());}
			if(check(0,m,num)){out.write("\nYes");out.flush();}else{out.write("\nNo");out.flush();}
			}
	}
			public static boolean check(int i,int m,int[] arr)throws Exception{
				if(m==0)return true;
				if(i>=n || m<0)return false;
				return check(i+1,m,arr)||check(i+1,m-arr[i],arr);
			}

}
