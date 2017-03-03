/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;


class Knapsack

 {
		public static long ans=0;

  public static void main(String[] args)throws Exception

    {                 

       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	   BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
     
        ans=0;     
        int tests=Integer.parseInt(br.readLine());
     
    	for(int t=0;t<tests;t++){
								
					int n=Integer.parseInt(br.readLine());	
					int W=Integer.parseInt(br.readLine());
					int val[]=new int[n];					
					String[] s=br.readLine().split(" ");    
					
  					for(int i=0;i<n;i++){
        			    		val[i]=Integer.parseInt(s[i]);
					}
					
					int wt[]=new int[n];					
					s=br.readLine().split(" ");    
					
  					for(int i=0;i<n;i++){
        			    		wt[i]=Integer.parseInt(s[i]);
					}
					int[][] dp=new int[n+1][W+1];
					for(int i=0;i<=n;i++)dp[i][0]=0;
					for(int i=0;i<=W;i++)dp[0][i]=0;
					for(int i=1;i<=n;i++){
						for(int j=1;j<=W;j++){
							
							if(wt[i-1]<=j){
								
								dp[i][j]=Math.max(dp[i-1][j],val[i-1]+dp[i-1][j-wt[i-1]]);
							}else{
								dp[i][j]=dp[i-1][j];	
							}
						}
					}												
					ans=dp[n][W];	
        			out.write(""+ans+"\n");out.flush();
  		}	      
  }
}
