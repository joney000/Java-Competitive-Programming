import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

class LCA {
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in),2000);
	public static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out),2000);

	public static void main(String[] args)throws Exception{
		
			
		
		
		String[] s = br.readLine().split(" ");
		int n = Integer.parseInt(s[0]); int m = Integer.parseInt(s[1]);
		int et[] = new int[2*n];  //euler tour [1..2n-1]
		LinkedList<Integer> adj[] = new LinkedList[n+1]; //Adjency List
		for(int i=1;i<=n ;i++)adj[i]=new LinkedList<Integer>();   //init List
		int l[] = new int[2*n];     //l[i] = level of node et[i]
		int f[] = new int[n+1];		  //f[i] = first occurence of node i in the et  
		
		int node[] = new int[4*(2*n)]; //seg tree
		int level[] = new int[n+1];		 // level[i]= level of node i in the tree
		int u=0,v=0;
		for(int i=1;i<=m;i++){
			
			s = br.readLine().split(" ");
			u=Integer.parseInt(s[0]);
			v=Integer.parseInt(s[1]);
			adj[u].add(v);
			
		}
		dfs(adj,1,level,et,n);		//Assuming that node 1 is the root node
		printEt(et,n);
		printNodeLevels(level,n);
		
		for(int i=1;i<=2*n-1;i++){
			l[i]=level[et[i]];
			if(f[et[i]]==0)f[et[i]]=i;
			
		}
		
		maketree(1,1,2*n-1,node,l);    //l[1..2*n-1]
		out.write("\nTree Built\n");out.flush();
		//for(int i=1;i<=n;i++)out.write(" "+f[i]);
		//out.flush();
		printtree(1,1,2*n-1,node,l);
		int q = Integer.parseInt(br.readLine());
		int temp=0,ans=0;
		for(int i=1;i<=q;i++){
		
			s = br.readLine().split(" ");
			u = Integer.parseInt(s[0]); v = Integer.parseInt(s[1]); 
			if(f[u]>=f[v]){  //swap u,v
				temp = u; u=v; v=temp;
			}
			out.write("indexes i="+f[u]+" j="+f[v]);out.flush();
			ans = et[query(1,1,2*n-1,node,l,f[u],f[v])];
			out.write(""+ans+"\n");out.flush();
		}
		out.flush();
		
		
	}
	 public static void printtree(int index,int i,int j,int[] node,int []L)throws Exception{
		if(i==j){out.write("\n min value btwn i="+i+" j="+j+" is"+node[index]);out.flush();
				return;}
	
		printtree(2*index,i,(i+j)/2,node,L);
		printtree(2*index+1,((i+j)/2)+1,j,node,L);
		out.write("\n min value btwn i="+i+" j="+j+" is"+node[index]);out.flush();
		
	}
	public static void maketree(int index,int i,int j,int[] node,int []L)throws Exception{
		if(i==j){node[index]=i;	return;	}
	
		maketree(2*index,i,(i+j)/2,node,L);
		maketree(2*index+1,((i+j)/2)+1,j,node,L);
	
		if(L[node[2*index]]<=L[node[2*index+1]])node[index]=node[2*index];
		else node[index]=node[2*index+1];
	}
	public static int query(int index,int l,int r,int[] node,int []L,int i,int j)throws Exception{
		if(l>j||r<i||l>r)return -1;  //invalid condition
		if(l>=i&&r<=j) return node[index];
		
		int p1 = query(2*index,l,(l+r)/2,node,L,i,j);
		int p2 = query(2*index+1,((l+r)/2)+1,r,node,L,i,j);
		
		if(p1==-1)return p2;
		if(p2==-1)return p1;
		if(L[p1]<=L[p2])return p1;
		else return p2;
	}
		
	public static void printEt(int[] et , int n)throws Exception{
		out.write("Euler Tour\n");out.flush();
		for(int i=1; i<=2*n-1 ;i++){
			out.write(" "+et[i]);
		
		}
		out.flush();
	}
	public static void printNodeLevels(int[] level , int n)throws Exception{
		out.write("levels\n");out.flush();
		for(int i=1; i<=n ;i++){
			out.write(" node "+i+" ="+level[i]);
		
		}
		out.flush();
	}
	public static void dfs(LinkedList<Integer> adj[] ,int root, int level[] ,int et[] , int n)throws Exception{
	
		boolean visited[] = new boolean[n+1];	
		LinkedList <Integer> q = new LinkedList<Integer>();
		int index = 1;
		int l = 0;//level
		
		q.add(root);
		visited[root]=true;
			
		while(!q.isEmpty()){
		
			int u = q.getLast(); //top
			if(et[index-1]!=u){et[index]=u;index++;}//if it's not leaf node
			level[u]=l;
			
			if(adj[u].size()>0){
				int v = adj[u].removeFirst();
				if(!visited[v]){
					q.add(v);l++;
					visited[v]=true; level[v]=l;
					et[index]=v; index++;
				}
			
			}else{
			
				int v = q.removeLast();
				l--;
			
			}
		
		}
	}

}
	
