
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : Maximum matching for bipartite graph. Hopcroft-Karp algorithm in O(E * sqrt(V))
 * Platform  : https://www.facebook.com/hackercup
 *
 */

/*    The Main Class                */
 class D{
	
	private InputStream inputStream ;
	private OutputStream outputStream ;
	private FastReader in ;
    	private PrintWriter out ;
	/*
		Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
		 
	*/
	
	//Critical Size Limit : 10^5 + 4
	private final int BUFFER = 100005;
	private int    tempints[] = new int[BUFFER];
	private long   templongs[] = new long[BUFFER];
	private double tempdoubles[] = new double[BUFFER];
	private char   tempchars[] = new char[BUFFER];
	private final long mod = 1000000000+7;
	private final int  INF  = Integer.MAX_VALUE / 10;
	private final long INF_L  = Long.MAX_VALUE / 10;
	
	public D(){}
	public D(boolean stdIO)throws FileNotFoundException{
		//stdIO = false;
		if(stdIO){
			inputStream = System.in;
			outputStream = System.out;
		}else{
			inputStream = new FileInputStream("laundro_matt.txt");
			outputStream = new FileOutputStream("output.txt");
		}
		in = new FastReader(inputStream);
		out = new PrintWriter(outputStream);
		
	}
	
	int n = 0;int m = 0;
	long ans = 0;
	int M = 10000000;
	LinkedList<Integer> adj[] = new LinkedList[200000+1];
	LinkedList<Integer> adj1[] = new LinkedList[200000+1];
	HashSet<Long> hs = new HashSet<Long>();
	HashMap<Long , Long> w = new HashMap<Long,Long>();


  	void run()throws Exception{
	
	//	 int tests = i();
	//	 once();
	//	 for(int t  = 1 ; t<= tests ; t++){
	//	 	clear(n);
			n = i(); m = i();
			Pair p[] = new Pair[m+1];
			for(int i = 1 ; i <=m ; i++){
				p[i] = new Pair(i(),i(),i());
			}
			Arrays.sort(p,1,m+1);
			for(int i = 1 ; i <= n ; i++)adj[i] = new LinkedList<Integer>();
			for(int i = 1 ; i <= n ; i++)adj1[i] = new LinkedList<Integer>();
			//MST prepared
	//		out.write("here\n");
			Graph graph = new Graph(n, 2*m);
			int to = n-1;
			for(int i = 1 ; i <=m ; i++){
			//	if(hs.contains(p[i].a)&&hs.contains(p[i].b)){to++;continue;}
				long u = p[i].a;
				long v = p[i].b;
				hs.add(u);
				hs.add(v);
				
				graph.edge[i-1].src = (int)u-1;
        			graph.edge[i-1].dest = (int)v-1;
        			graph.edge[i-1].weight = (int)p[i].c;
        			graph.edge[m+i-1].src = (int)v-1;
        			graph.edge[m+i-1].dest = (int)u-1;
        			graph.edge[m+i-1].weight = (int)p[i].c;
        			
				
				
				w.put((u<<32) + v, p[i].c);
				w.put((v<<32) + u, p[i].c);
	//			out.write(""+u+" "+v+" "+p[i].c+"\n");
				out.flush();
			}
	//		out.write("hs.size="+hs.size()+"\n");
			// dfs
			Edge result[] = graph.KruskalMST();
			for(int i = 0 ; i < result.length ; i++){
				int u = result[i].src +1;
				int v = result[i].dest + 1;
				int w = result[i].weight;
				adj[(int)u].add((int)v);
				adj[(int)v].add((int)u);
				adj1[(int)u].add((int)v);
				adj1[(int)v].add((int)u); 
			}
			dfs1(1,adj);
			dfs2(1,adj1);
	//		out.write("add ar\n");
			for(int i = M-7; i <= M; i++){
	//			out.write(""+res[i]+" ");
			}
	//		out.write("\n");	 		
		 	for(int i = M ; i >= 1; i--){
		 		if(res[i] > 1){
		 			if(res[i]%2 == 0){
		 				res[i-1] += res[i]/2;
		 				res[i] = 0;
		 			}else{
		 				res[i-1] += res[i]/2;
		 				res[i] = 1;
		 				
		 			}
		 		}
		 		
		 	}
		 	boolean zero = false;
		 	for(int i = 1 ; i <= M; i++){
		 		if(res[i]==0 && zero ){
		 		
		 			out.write(""+res[i]);
		 		
		 		}else if(res[i]==1){
		 		
		 			zero = true;
		 			out.write(""+res[i]);
		 		
		 		}
		 	}
		 	if(!zero){
		 		out.write("0");
		 	}
	//	 	out.write("Case #"+t+": "+ans+"\n");	
		 	
	//	}//end tests
	}//end run
	

	
	long res[] = new long[M+1];
	long cnt[] = new long[200001];
	boolean vis[] = new boolean[200001];

	void dfs1(int root , LinkedList<Integer>[] adj){
		Arrays.fill(cnt,1L);
		LinkedList <Integer> q = new LinkedList<Integer>(); //the stack 
		q.add(root);
		vis[root]=true;
			
		while(!q.isEmpty()){
		
			int u = q.getLast(); //top
			if(adj[u].size()>0){
				int v = adj[u].removeFirst();
				if(!vis[v]){
					q.add(v);
					vis[v]=true; 
				}
			}else{
				int v = q.removeLast();
				
				if(v != 1){
					u = q.getLast();
					cnt[u] += cnt[v];
					
	//				out.write("node:"+v+" cnt="+cnt[v]+"\n");
				}
	//			else out.write("node:"+v+" cnt="+cnt[v]+"\n");
				
			}
		}
	}	
	void dfs2(int root , LinkedList<Integer>[] adj){
		Arrays.fill(vis, false);
		LinkedList <Integer> q = new LinkedList<Integer>(); //the stack 
		q.add(root);
		vis[root]=true;
			
		while(!q.isEmpty()){
		
			int u = q.getLast(); //top
			if(adj[u].size()>0){
				int v = adj[u].removeFirst();
				if(!vis[v]){
					q.add(v);
					vis[v]=true; 
				}
			}else{
				long vv = q.removeLast();
				
				if(vv != 1){
					long uu = q.getLast();
					long ww = w.get((uu<<32) + vv);
					long val = cnt[(int)vv] * (n-cnt[(int)vv]);
					String s = Long.toBinaryString(val);
	//				out.write("calculating for edge "+uu+" "+vv+" "+s+" with shift "+ww+"\n");
	//				out.write("after shift ");
					for(int i = 1 ; i <= s.length(); i++){
						res[M-(int)ww-s.length()+i] += s.charAt(i-1) - '0';
					}
				}
			}
		}
	}
	
		 
//****************************** My Utilities ***********************//
 	void print_r(Object...o){
       	out.write("\n"+Arrays.deepToString(o)+"\n");
        	out.flush();
	}
	
	int hash(String s){
		int base = 31;
		int a = 31;//base = a multiplier
		int mod = 100005;//range [0..100004]
		long val = 0;
		for(int i =  1 ; i<= s.length() ;i++){
			val += base * s.charAt(i-1);
			base = ( a * base ) % 100005;
		}
		return (int)(val % 100005) ;
	}
	
 	boolean isPrime(long n){
  		if(n==1)return false;
  		if(n<=3)return true;
  		if(n%2==0)return false;
  		for(int i=2 ;i <= Math.sqrt(n); i++){
   			if(n%i==0)return false;
  		}
  		return true;
 	}
 	// sieve
 	int[] sieve(int n){
 	     
  		boolean isPrime[] = new boolean[n+1];
  		int p[] = new int[n+1];
  		int idx = 1;
  		// Put above 3 variables globle p[1..idx-1]
  		
  		
  		Arrays.fill(isPrime,true);
  		isPrime[0]=isPrime[1]=false;
  		for(int i = 2 ; i<= n ; i++){
  			if(isPrime[i]){
  				p[idx++] = i;
  				for(int j  = 2* i ; j<= n ; j+=i ){
  					isPrime[j] = false;		
  				}
  					
  			}
  			
  		}
  		return p;
 	}
 	long gcd(long a , long b){
  		if(b==0)return a;
  		return gcd(b , a%b);
 	}
 	long lcm(long a , long b){
  		if(a==0||b==0)return 0;
  		return (a*b)/gcd(a,b);
 	}
 	long mulmod(long a , long b ,long mod){
  		if(a==0||b==0)return 0;
  		if(b==1)return a;
   		long ans = mulmod(a,b/2,mod);
   		ans = (ans*2)% mod;
   		if(b%2==1)ans = (a + ans)% mod;
   		return ans;
 	}
 	long pow(long a , long b ,long mod){
   		if(b==0)return 1;
  		if(b==1)return a;
   		long ans = pow(a,b/2,mod);
   		ans = (ans * ans);
   		if(ans >= mod )ans %= mod;
   		
   		if(b%2==1)ans = (a * ans);
   		if(ans >= mod )ans %= mod;
   		
   		return ans;
 	}
 	// 20*20   nCr Pascal Table
 	long[][] ncrTable(){
  		long ncr[][] = new long[21][21];
  		for(int i=0 ;i<=20 ;i++){ncr[i][0]=1;ncr[i][i]=1;}
  		for(int j=0;j<=20 ;j++){
   			for(int i=j+1;i<= 20 ;i++){
    				ncr[i][j] = ncr[i-1][j]+ncr[i-1][j-1];
   			}
  		}
  	return ncr;
 	}
	//*******************************I/O******************************//	
	int i()throws Exception{
 		//return Integer.parseInt(br.readLine().trim());
 		return in.nextInt();
	}
	int[] is(int n)throws Exception{
  //int arr[] = new int[n+1];
  		for(int i=1 ; i <= n ;i++)tempints[i] = in.nextInt();  
 		return tempints;
	}
	long l()throws Exception{
 		return in.nextLong();
	}
	long[] ls(int n)throws Exception{
 		for(int i=1 ; i <= n ;i++)templongs[i] = in.nextLong();  
 		return templongs;
	}

	double d()throws Exception{
 		return in.nextDouble();
	}
	double[] ds(int n)throws Exception{
  		for(int i=1 ; i <= n ;i++)tempdoubles[i] = in.nextDouble();  
 		return tempdoubles;
	}
	char c()throws Exception{
 		return in.nextCharacter();
	}
	char[] cs(int n)throws Exception{
  		for(int i=1 ; i <= n ;i++)tempchars[i] = in.nextCharacter();  
 		return tempchars;
	}
	String s()throws Exception{
 		return in.nextLine();
	}
	BigInteger bi()throws Exception{
 		return in.nextBigInteger();
	}
//***********************I/O ENDS ***********************//
//*********************** 0.3%f [precision]***********************//
/* roundoff upto 2 digits 
   double roundOff = Math.round(a * 100.0) / 100.0;
                    or
   System.out.printf("%.2f", val);
					
*/
/*
  print upto 2 digits after decimal
  val = ((long)(val * 100.0))/100.0;
  
*/    private void closeResources(){
		 	 out.flush();
    		 out.close();
    		 return;
	}
	public static void main(String[] args) throws java.lang.Exception{
		//let_me_start Shinch Returns 
		
    		 D driver = new D(true);
    		 long start =  System.currentTimeMillis();
    		 driver.run();
    		 long end =  System.currentTimeMillis();
    		 //out.write(" Total Time : "+(end - start)+"\n");
    		 driver.closeResources();
    		 return ;
    		 
	}	 

}

class FastReader{

	private boolean finished = false;

	private InputStream stream;
	private byte[] buf = new byte[4*1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;

	public FastReader(InputStream stream){
		this.stream = stream;
	}

	public int read(){
		if (numChars == -1){
			throw new InputMismatchException ();
		}
		if (curChar >= numChars){
			curChar = 0;
			try{
				numChars = stream.read (buf);
			} catch (IOException e){
				throw new InputMismatchException ();
			}
			if (numChars <= 0){
				return -1;
			}
		}
		return buf[curChar++];
	}

	public int peek(){
		if (numChars == -1){
			return -1;
		}
		if (curChar >= numChars){
			curChar = 0;
			try{
				numChars = stream.read (buf);
			} catch (IOException e){
				return -1;
			}
			if (numChars <= 0){
				return -1;
			}
		}
		return buf[curChar];
	}

	public int nextInt(){
		int c = read ();
		while (isSpaceChar (c))
			c = read ();
		int sgn = 1;
		if (c == '-'){
			sgn = -1;
			c = read ();
		}
		int res = 0;
		do{
			if(c==','){
				c = read();
			}
			if (c < '0' || c > '9'){
				throw new InputMismatchException ();
			}
			res *= 10;
			res += c - '0';
			c = read ();
		} while (!isSpaceChar (c));
		return res * sgn;
	}

	public long nextLong(){
		int c = read ();
		while (isSpaceChar (c))
			c = read ();
		int sgn = 1;
		if (c == '-'){
			sgn = -1;
			c = read ();
		}
		long res = 0;
		do{
			if (c < '0' || c > '9'){
				throw new InputMismatchException ();
			}
			res *= 10;
			res += c - '0';
			c = read ();
		} while (!isSpaceChar (c));
		return res * sgn;
	}

	public String nextString(){
		int c = read ();
		while (isSpaceChar (c))
			c = read ();
		StringBuilder res = new StringBuilder ();
		do{
			res.appendCodePoint (c);
			c = read ();
		} while (!isSpaceChar (c));
		return res.toString ();
	}

	public boolean isSpaceChar(int c){
		if (filter != null){
			return filter.isSpaceChar (c);
		}
		return isWhitespace (c);
	}

	public static boolean isWhitespace(int c){
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}

	private String readLine0(){
		StringBuilder buf = new StringBuilder ();
		int c = read ();
		while (c != '\n' && c != -1){
			if (c != '\r'){
				buf.appendCodePoint (c);
			}
			c = read ();
		}
		return buf.toString ();
	}

	public String nextLine(){
		String s = readLine0 ();
		while (s.trim ().length () == 0)
			s = readLine0 ();
		return s;
	}

	public String nextLine(boolean ignoreEmptyLines){
		if (ignoreEmptyLines){
			return nextLine ();
		}else{
			return readLine0 ();
		}
	}

	public BigInteger nextBigInteger(){
		try{
			return new BigInteger (nextString ());
		} catch (NumberFormatException e){
			throw new InputMismatchException ();
		}
	}

	public char nextCharacter(){
		int c = read ();
		while (isSpaceChar (c))
			c = read ();
		return (char) c;
	}

	public double nextDouble(){
		int c = read ();
		while (isSpaceChar (c))
			c = read ();
		int sgn = 1;
		if (c == '-'){
			sgn = -1;
			c = read ();
		}
		double res = 0;
		while (!isSpaceChar (c) && c != '.'){
			if (c == 'e' || c == 'E'){
				return res * Math.pow (10, nextInt ());
			}
			if (c < '0' || c > '9'){
				throw new InputMismatchException ();
			}
			res *= 10;
			res += c - '0';
			c = read ();
		}
		if (c == '.'){
			c = read ();
			double m = 1;
			while (!isSpaceChar (c)){
				if (c == 'e' || c == 'E'){
					return res * Math.pow (10, nextInt ());
				}
				if (c < '0' || c > '9'){
					throw new InputMismatchException ();
				}
				m /= 10;
				res += (c - '0') * m;
				c = read ();
			}
		}
		return res * sgn;
	}

	public boolean isExhausted(){
		int value;
		while (isSpaceChar (value = peek ()) && value != -1)
			read ();
		return value == -1;
	}

	public String next(){
		return nextString ();
	}

	public SpaceCharFilter getFilter(){
		return filter;
	}

	public void setFilter(SpaceCharFilter filter){
		this.filter = filter;
	}

	public interface SpaceCharFilter{
		public boolean isSpaceChar(int ch);
	}
}
 /******************** Pair class ***********************/
 
 class Pair implements Comparable<Pair>{

 public long b;
 public long a;
 public long c;
 public Pair(){

  this.a = 0L;
  this.b = 0L;
  this.c = 0L;
 }
 public Pair( long a,long b , long c ){

  this.a = a;
  this.b = b;
  this.c = c;
 }
 public int compareTo(Pair p){
	if(this.c < p.c)return -1;
	else if(this.c > p.c )return 1;
	else return 0;
 }
 public String toString(){
  return "a="+this.a+" b="+this.b;
 }
 
} 
class Graph
{
  
    class subset
    {
        int parent, rank;
    };
 
    int V, E;    // V-> no. of vertices & E->no.of edges
    Edge edge[]; // collection of all edges
 

    Graph(int v, int e)
    {
        V = v;
        E = e;
        edge = new Edge[E];
        for (int i=0; i<e; ++i)
            edge[i] = new Edge();
    }
 
    // A utility function to find set of an element i
    // (uses path compression technique)
    int find(subset subsets[], int i)
    {
        // find root and make root as parent of i (path compression)
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);
 
        return subsets[i].parent;
    }
 
    // A function that does union of two sets of x and y
    // (uses union by rank)
    void Union(subset subsets[], int x, int y)
    {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);
 
        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
 
         else
        {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
 
     Edge[] KruskalMST()
    {
        Edge result[] = new Edge[V];  // Tnis will store the resultant MST
        int e = 0;  // An index variable, used for result[]
        int i = 0;  // An index variable, used for sorted edges
        for (i=0; i<V; ++i)
            result[i] = new Edge();
 
          Arrays.sort(edge);
 
        subset subsets[] = new subset[V];
        for(i=0; i<V; ++i)
            subsets[i]=new subset();
 
        for (int v = 0; v < V; ++v)
        {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }
 
        i = 0;  // Index used to pick next edge
 
        while (e < V - 1)
        {
            Edge next_edge = new Edge();
            next_edge = edge[i++];
 
            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);
        if (x != y)
            {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
 
                            
        return result;
    }
}
  class Edge implements Comparable<Edge>{
        int src, dest, weight;

        public int compareTo(Edge compareEdge)
        {
            return this.weight-compareEdge.weight;
        }
    }
