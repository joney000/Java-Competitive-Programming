/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;


class Main

 {
		
  public static void main(String[] args)throws Exception{                 

       BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	   BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
       int n = Integer.parseInt(br.readLine());
	   int arr [] = new int[100005];
	   String[] s = br.readLine().split(" ");
	   for(int i=1;i<=n;i++)arr[i]=Integer.parseInt(s[i-1]);
	   mergeSort(arr,1,n);
	   for(int i=1;i<=n;i++)out.write(""+arr[i]+" ");
	   out.flush();
	   
  }
  public static void mergeSort(int arr[],int st ,int end)throws Exception{
    
	if(st>=end)return;
    	int mid = (st+end)/2; ////Same as mid=l+(r-l)/2;, but this avoids integer overflow for large l and h
	mergeSort(arr,st,mid);
	mergeSort(arr,mid+1,end);
	merge(arr,st,mid,end);
	
  }
  
  public static void merge(int arr[],int st ,int mid, int end)throws Exception{

	LinkedList<Integer> L = new LinkedList<Integer>();
	LinkedList<Integer> R = new LinkedList<Integer>();
	
	for(int i=st;i<=mid;i++)L.add(arr[i]);
	for(int i=mid+1;i<=end;i++)R.add(arr[i]);
	while((!L.isEmpty())&&(!R.isEmpty())){
	 int a = L.getFirst();
	 int b = R.getFirst();
	 if(a<=b){
	  arr[st]=L.removeFirst();
	  st++;
	 }else{
	  arr[st]=R.removeFirst();
	  st++;
	 }
	}
	while(!L.isEmpty()){arr[st]=L.removeFirst();st++;}
	while(!R.isEmpty()){arr[st]=R.removeFirst();st++;}
  }
 
 }	
