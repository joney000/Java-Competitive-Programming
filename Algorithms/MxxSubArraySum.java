/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;


class MaxSubArraySum

 {
		public static long ans=0;

  public static void main(String[] args)throws Exception

    {                 

       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	   BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
     
            
        int tests=Integer.parseInt(br.readLine());
     
    	for(int t=0;t<tests;t++){
								
					int n=Integer.parseInt(br.readLine());	
					int num[]=new int[n];					
					String[] s=br.readLine().split(" ");    
					
  					for(int i=0;i<n;i++){
        			    num[i]=Integer.parseInt(s[i]);
					}
					ans=0;long tempSum=0;
					/*  //does not handle the case all array element are negative.
					for(int i=0;i<n;i++){
						tempSum=+num[i];
						if(tempSum<0){
							tempSum=0;
						}
						ans=Math.max(ans,temp);
					}
					 */
					 //handle the all negative case
					ans=-100000000000L;
					for(int i=0;i<n;i++){
						tempSum=Math.max(num[i],tempSum+num[i]);
						ans=Math.max(ans,tempSum);
					} 
        			out.write(""+ans+"\n");out.flush();
  		}	      
  }
}
