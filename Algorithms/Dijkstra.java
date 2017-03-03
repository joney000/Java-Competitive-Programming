import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Coded by  	: Jaswant Singh
 * Lang   		: Java
 * Algorithm 	: Dijkstra
 * Date         : 3/march/2015
 */ 
 class Edge implements Comparable<Edge>{
	 public int vertex;
	 public long weight;
	 public long weight1;
	 public long diff; 
	 //public provide flexibility to access from outside the the class
	 //at the cost of security
	 public Edge(){
		this.vertex = 0;
		this.weight = 0L;
	 }
	 public Edge(int node , long weight){
		 this.vertex = node;
		 this.weight = weight;
	 }
	 @Override
	 public int compareTo(Edge e){
		 if(this.weight<e.weight)return -1;
		 else if(this.weight==e.weight) return 0;
		 else return 1;
	 }
 }
 class Djkstra{
			public static ArrayList<Edge> adj[];
			public static int source;
		    //Creating Reader-Writer Buffer of initial size 2000B
			public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in),2000);
			public static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out),2000);			
			public static long INF = Long.MAX_VALUE/100;
			public static int s[] = new int[100005];
			public static long d[] = new long[100005+1]; 
			static {
				Arrays.fill(d,INF);
			}
		
		public Djkstra(){
		}
	    public static void main(String []arg)throws Exception{
			
			String[] s= br.readLine().split(" ");
			int n = Integer.parseInt(s[0]); //no of edges
			int m = Integer.parseInt(s[1]); //no of nodes
			//creating the undirected graph
			adj = new ArrayList[n+1]; //Adjacency list
			for(int i=1;i<=n;i++)adj[i] = new ArrayList<Edge>();
			for(int i=1;i<=m;i++){
				s = br.readLine().split(" ");
				int u  = Integer.parseInt(s[0]);
				int v  = Integer.parseInt(s[1]);
				long w = Long.parseLong(s[2]);
                		adj[u].add(new Edge(v,w));				
				adj[v].add(new Edge(u,w));//if the graph is undirected
			}
			int q = i();
			for(int i=1;i<=q;i++){
			
			source = Integer.parseInt(br.readLine());
			
				//Applying the Djkstra
				djkstra(adj,source,n);
				
			}
			
			out.flush();
			return ;
		}
		public static void djkstra(ArrayList<Edge> []adj,int source,int n)throws Exception{
			
		//	long d[] = new long[n+1]; // initializing the distance vector
			boolean f[] = new boolean[n+1]; // initializing the fronter vector
			
			d[source] = 0L;
			
			PriorityQueue<Edge> pq = new PriorityQueue<Edge>();
			pq.add(new Edge(source,0L));
			
			while(!pq.isEmpty()){
					
				Edge e = pq.remove();
				int u = e.vertex;
				if(f[u])continue; // the current node already in the fronter 
				for(int i=0;i<adj[u].size();i++){
					Edge temp = adj[u].get(i);
					int v = temp.vertex;
					long w = temp.weight;
					if(!f[v]&& d[u]+w < d[v]){
						d[v] = d[u]+w ;
						pq.add(new Edge(v,d[v]));
					}
				}
				f[u] = true ; // we have done with this node	
			}
			printDistances(source,d,n);
		  return ;
		}
		public static void printDistances(int source,long []d,int n)throws Exception{
			for(int i=1; i<=n; i++)out.write("Node "+i+", min weight = "+d[i]+"\n");
		}
 }
