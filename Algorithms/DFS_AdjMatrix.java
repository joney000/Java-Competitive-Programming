package Algorithms;

import java.lang.*;
import java.util.*;
import java.math.*;
import java.io.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : DFS using AdjMatrix O(V^2)
 * Platform  : CodeForces
 */
class DfsAdjencyMatrix {
	
	private static BufferedReader  br = new BufferedReader(new InputStreamReader(System.in),2000);;
	private static BufferedWriter  out = new BufferedWriter(new OutputStreamWriter(System.out),2000);
	/*  private static BufferedReader = new BufferedReader(new FileReader("input.txt"));
        private static BufferedWriter  out= new BufferedWriter(new FileWriter("output.txt"));
    */
	public DfsAdjencyMatrix(){
			
	}
	
	public static void main(String[] args)throws Exception{
		
		int tests = Integer.parseInt(br.readLine());
		for(int t=1;t<=tests;t++){
			// End Of File  str = br.readLine();   if (str ==null || str.length()==0) break;
			String []s = br.readLine().split(" ");
			
			int n = Integer.parseInt(s[0]); //int m = Integer.parseInt(s[1]);
			int adj[][] = new int[n+1][n+1];
			for(int i=1;i<=n;i++){
				s = br.readLine().split(" ");
				for(int j=1;j<=n;j++){
					adj[i][j] = Integer.parseInt(s[j-1]);
				}
			}
			boolean vis[] = new boolean[n+1];  // indexing [1..n]
			int src = 1;
			dfs(adj,vis,src,n);
			out.flush();
		}
		
	}
	// DFS-BFS
	public static void dfs(int[][]adj ,boolean[] vis ,int src ,int n)throws Exception{
			
		LinkedList<Integer> stack = new LinkedList<Integer>();
	    	stack.addLast(src);
		vis[src] = true;
		int level = 0;
	//	print(src,level);
		while(!stack.isEmpty()){
			int node = stack.getLast(); // DFS-BFS Decesion
			int check = 0;
			for(int j=1;j<=n;j++){
				if(adj[node][j]==1 && (!vis[j])){
					check = 1;
					level++;
					stack.addLast(j);
	//				print(j,level); 
					vis[j]=true;
					break;
				}
				
			}
			
			// DFS-BFS Decesion remove IF Block
			if(check==0){
				//adj[node][1..n] is empty
			  stack.removeLast();
			  level--;
			}
		}
	}
	public static void print(int node , int level)throws Exception{
		
		out.write(" node="+node+" its depth="+level+"\n");
	}
}
