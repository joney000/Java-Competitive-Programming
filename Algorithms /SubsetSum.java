/* package joney_000 
*/

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;


class SubsetSum  

 {
		public static long ans=0;

  public static void main(String[] args)throws Exception

    {                 

       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	   BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
     
        ans=0;     
        int tests=Integer.parseInt(br.readLine());
     
    	for(int t=0;t<tests;t++){
					String[] s=br.readLine().split(" ");			
					int n=Integer.parseInt(s[0]);int S = Integer.parseInt(s[1]);
					int num[]=new int[n];			
					//s=br.readLine().split(" ");    
				
  					for(int i=0;i<n;i++){
        			    		num[i]=Integer.parseInt(br.readLine());
					}


					boolean[][] dp=new boolean[n+1][S+1];
					for(int i=0;i<=n;i++)dp[i][0]=false;
  					for(int i=0;i<=S;i++)dp[0][1]=false;
					for(int i=1;i<=n;i++){
						for(int j=1;j<=S;j++){
							if(num[i-1]<=j){
								dp[i][j]=(num[i-1]==j||(dp[i-1][j-num[i-1]])||dp[i-1][j]);
							}else{ 
								dp[i][j]=dp[i-1][j];
							     }
						}
					}
					if(dp[n][S]){
        			out.write("Yes"+"\n");out.flush();}else{
										out.write("No"+"\n");out.flush();
									}
  		}	      
  }
}
