package Algorithms;//pakage joney_000[let_me_start]
//
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : Not Specified
 * Platform  : Codechef[https://www.codechef.com/problems/CHEFTREE]
 *
 */
 
 /*                The Main Class                */
class HldNodesMainNode {
	int cnt = 0;

}
class HldNodesMain
{	
    	private InputStream inputStream ;
	private OutputStream outputStream ;
	private InputReaderAndProcessor in ;
    	private PrintWriter out ;
	/*
		Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
		Size Limit : 10^5 + 4 
	*/
	private final int BUFFER = 100005;
	private int    tempints[] = new int[BUFFER];
	private long   templongs[] = new long[BUFFER];
	private double tempdoubles[] = new double[BUFFER];
	private char   tempchars[] = new char[BUFFER];
	private final long mod = 1000000000+7;
	private final int  INF  = Integer.MAX_VALUE / 10;
	private final long INF_L  = Long.MAX_VALUE / 10;
	
	public HldNodesMain(){}
	public HldNodesMain(boolean stdIO)throws FileNotFoundException{
		if(stdIO){
			inputStream = System.in;
			outputStream = System.out;
		}else{
			inputStream = new FileInputStream("input.txt");
			outputStream = new FileOutputStream("output.txt");
		}
		in = new InputReaderAndProcessor(inputStream);
		out = new PrintWriter(outputStream);
		
	}
      // fine place for Global Variables
	
      final int N = 101005 ;
      int LOGN = 20;
	int w[] = new int[N]; //weight on i'th node
	ArrayList adj[] = new ArrayList[N];
	long A = 0 ;long B = 0;
	int root = 1; 
      int ptr = 0; //total no of element in base array
      int chainNo = 0;
	int baseArray[] = new int[N]; //Base Array For Seg Tree
   	int chainIndex[] = new int[N]; //chainIndex[v] = chainNo
  	int chainHead[] = new int[N]; //chainHead[chainNo] = v 
  	int posInBase[] = new int[N]; //posInBase[v] = ptr
  	int depth[] = new int[N];     // depth of node i in dfs
  	int f[][] = new int[LOGN][N]; //father sparse array
  	int subsize[] = new int[N];  //subtree size of node i
	HldNodesMainNode st[] = new HldNodesMainNode[4 * N + 50000];  // The Segment Tree
  	int log[] = new int[N+5];
  	
  	void run()throws Exception{
	
	//	 int tests = i();
		 once();
	//	 for(int t  = 1 ; t<= tests ; t++){
		 	int n = i(); 
		 //	BiTSet = l(); B = l();
		  	for(int i=1;i<=n;i++){
		   		w[i] = 0;
		   	}
		 	clear(n);
		 	int e = n-1;
		 	for(int i=1; i<=e ;i++){
		   		int u = i(); int v = i();
		   		adj[u].add(v);
		   		adj[v].add(u);	
		 	}
		 	
		 	dfs(root, -1 , 0); // We set up subsize, parent and depth for each node before sparse table
	//		out.write("here"+"\n");
	//		out.flush();
			HLD(root,  -1); // We decomposed the tree and created baseArray
	//		out.write("here1"+"\n");
	//		out.flush();
			
			make_tree(1, 1, ptr-1); // We use baseArray and construct the needed segment tree
	//		out.write("here2"+"\n");
	//		out.flush();
			
			make_sparseTable(n); // sparse table
	//		out.write("here3"+"\n");
	//		out.flush();
	//		for(int i = 1;i<=n;i++){
	//			out.write("vtx ="+i+" posInbase="+posInBase[i]+"\n");
	//			
	//		}
	//		for(int i = 1;i<=chainNo;i++){
	//			out.write("vtx ="+i+" head="+chainHead[i]+"\n");
	//			
	//		}
	//		out.flush();
			int q = i();
			for(int qq = 1 ; qq <= q ; qq++){
				char type = c();
	//			out.write("here qry"+qq+"\n");
	//			out.flush();
			
				if(type=='I'){
					int v = i(); int wt = i();
					change(v , wt);//node update
				}else{
					int u = i() ;int v= i();
					
					int ans = query(u,v);
					//int ans = query_up(v,u);
					//if(ans == INF)out.write("-1"+"\n");
					 out.write(""+ans+"\n");
				//	out.flush();
				}
			
			}
		 	
	//	}//end tests
	}//end run
	void once(){
		
  		for(int i = 2 ; i<= N;i++){
  			log[i] = log[i/2] + 1;
  		}
		for(int i = 0 ; i<= N -1 ; i++){
			adj[i] = new ArrayList<Integer>();
		}
		
		for(int i = 0 ; i<= 4 * N + 50000 - 1; i++ ){
			st[i] = new HldNodesMainNode();
		}
	}	
	void clear(int n){
		chainNo = ptr = root = 1;
		
  		LOGN = log[n]+1;
		for(int i = 0 ; i<= n +10; i++ ){
			adj[i].clear();
			chainHead[i] = -1;
			depth[i] = 0;
			subsize[i] = 0;
			posInBase[i] = 0;
			chainIndex[i] = 0;
			baseArray[i] = 0;
			for(int j=0; j< LOGN; j++) f[j][i] = -1;			
		}
		
		for(int i = 1 ; i<= 4 * n + 500 - 1; i++ ){
			st[i].cnt = 0;	
		}
	}
	void dfs(int cur, int prev, int _depth) {
		f[0][cur] = prev;
		depth[cur] = _depth;
		subsize[cur] = 1;
		for(int i=0; i< adj[cur].size(); i++)
			if((int)adj[cur].get(i) != prev) {
				//otherEnd[(int)idx[cur].get(i)] = (int)adj[cur].get(i);
				dfs((int)adj[cur].get(i), cur, _depth+1);
				subsize[cur] += (int)subsize[(int)adj[cur].get(i)];
		}
  	}
  	void HLD(int curNode, int prev) {
		if(chainHead[chainNo] == -1) {
			chainHead[chainNo] = curNode; // Assign chain head
		}
		chainIndex[curNode] = chainNo;
		posInBase[curNode] = ptr; // Position of this node in baseArray which we will use in Segtree
		baseArray[ptr] = curNode; // baseArray contains the weights in the path / chain
		ptr++;
		int sc = -1;
		// Loop to find special child
		for(int i=0; i<adj[curNode].size(); i++) if((int)adj[curNode].get(i) != prev) {
			if(sc == -1 || subsize[sc] < (int)subsize[(int)adj[curNode].get(i)]) {
				sc = (int)adj[curNode].get(i);
			}
		}
		if(sc != -1) {
			// Expand the chain
			HLD(sc, curNode);
		}
		for(int i=0; i<adj[curNode].size(); i++) if((int)adj[curNode].get(i) != prev) {
			if(sc != (int)adj[curNode].get(i)) {
				// New chains at each normal node
				chainNo++;
				HLD((int)adj[curNode].get(i), curNode);
			}
		}
   	}
   	void make_tree(int curr, int s, int e) {
		if(s == e) {
			st[curr].cnt = w[s];
			return;
		}
		make_tree(2*curr, s, (s+e)/2);
		make_tree(2*curr + 1, ((s+e)/2) + 1, e);
		st[curr].cnt = Math.max(st[2*curr].cnt , st[2*curr + 1].cnt);
  	}
  	void make_sparseTable(int n){
  		// Below Dynamic programming code is ST for LCA.

		for(int i=1; i<LOGN; i++)
			for(int j=1; j<=n; j++)
				if(f[i-1][j] != -1)
					f[i][j] = f[i-1][f[i-1][j]];

	}
	int query(int u, int v) throws IOException{
		int lca = LCA(u, v);
	//	out.write("here4 lca("+u+","+v+")="+lca+"\n");
	//	out.flush();
		int a = query_up(u, lca); // One part of path
	//	out.write("here5 number1(u,lca) = "+number1+"\n");
	//	out.flush();
		int b = query_up(v, lca); // another part of path
	//	out.write("here6 number2(v,lca) = "+number2+"\n");
	//	out.flush();
		int ans = Math.max(a,b); // take the minimum of both paths
		return ans;
  	}
  	int LCA(int u, int v) {
		if(depth[u] < depth[v]) {int temp = u; u = v; v = temp; /*swap*/ }
		int diff = depth[u] - depth[v];
		for(int i=0; i<LOGN; i++) if( ((diff>>i)&1) ==1) u = f[i][u];
		if(u == v) return u;
		for(int i=LOGN-1; i>=0; i--) if(f[i][u] != f[i][v]) {
			u = f[i][u];
			v = f[i][v];
		}
		return f[0][u];
  	}
  	int query_up(int u, int v) {
		//if(u == v) return query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]); // Trivial soln for node
		int uchain = 0; int vchain = chainIndex[v];int ans = 0;
		// uchain and vchain are chain numbers of u and v
		while(true) {
			uchain = chainIndex[u];
			if(uchain == vchain) {
				// Both u and v are in the same chain, so we need to query from u to v, update answer and break.
				// We break because we came from u up till v, we are done
		//		out.write("qtree1: ptr="+(ptr-1)+" "+posInBase[v]+" "+ posInBase[u]+" u="+u+" v="+v+" ans = "+ans+"\n");
		//		out.flush();
	//		
				int a = query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]);
				ans = Math.max(a,ans); // Update answer
				
				break;
			}
		//	out.write("qtree2: ptr="+(ptr-1)+" "+posInBase[chainHead[uchain]]+" "+ posInBase[u]+" u="+u+" chainHead[uchain]= "+chainHead[uchain]+" v="+v+" uchain="+uchain+" ans = "+ans+"\n");
		//	out.flush();
			
			int b = query_tree(1, 1, ptr-1, posInBase[chainHead[uchain]], posInBase[u]);
			// Above is call to segment tree query function. We do from chainHead of u till u. That is the whole chain from
			// start till head. We then update the answer
			ans = Math.max(ans , b );
			u = chainHead[uchain]; // move u to u's chainHead
			u = f[0][u]; //Then move to its parent, that means we changed chains
		}
		return ans;
  	}
  	/*
	 * query_tree:
 	* Given S and E, it will return the maximum value in the range [S,E]
 	
 	*/
  	int query_tree(int curr, int s, int e, int S, int E) {
		if(s>E||e<S||s>e) {
			 //invalid condition
			return 0;
		}
		if(s >= S && e <= E) {
			return st[curr].cnt;
		}
		int a = query_tree(2*curr, s, (s+e)/2 , S , E);
		int b = query_tree(2*curr + 1, ((s+e)/2) + 1, e , S , E);
		return Math.max(a,b);
  	}

  	void change(int v , int wt) {
		w[v] += wt;//point update
		update_tree(1, 1, ptr-1, posInBase[v] , posInBase[v]);
  	}
  	/*
 	 * update_tree:
	 * Point update. Update number1 single element of the segment tree.
 	*/
  	void update_tree(int curr, int s, int e, int S, int E) {
		if(s>E||e<S||s>e) return;
		if(s >= S && e <= E) {
			st[curr].cnt = w[baseArray[s]];//pos in base array is different
			return;
		}

		update_tree(2*curr, s, (s+e)/2 , S , E);
		update_tree(2*curr + 1, ((s+e)/2) + 1, e , S , E);
		st[curr].cnt = Math.max(st[2*curr].cnt , st[2*curr + 1].cnt);
  	}
//****************************** My Utilities ***********************//
 	void var_dump(Object... o) {
       	 out.write("\n"+Arrays.deepToString(o)+"\n");
        	out.flush();
	}

 	boolean isPrime(long n)throws Exception{
  		if(n==1)return false;
  		if(n<=3)return true;
  		if(n%2==0)return false;
  		for(int i=2 ;i <= Math.sqrt(n); i++){
   			if(n%i==0)return false;
  		}
  		return true;
 	}
 	// sieve
 	int[] primes(int n)throws Exception{       // for(int i=1;i<=arr.length-1;i++)out.write(""+arr[i]+" ");
  		boolean arr[] = new boolean[n+1];
  		Arrays.fill(arr,true);
  		arr[1]=false;
  		for(int i=2;i<=Math.sqrt(n);i++){
			if(!arr[i])continue;
			for(int j = 2*i ;j<=n;j+=i){
				arr[j]=false;
			}
  		}
  		LinkedList<Integer> ll = new LinkedList<Integer>();
  		for(int i=1;i<=n;i++){	
   			if(arr[i])ll.add(i);
  		}
  		n = ll.size();
  
  		int primes[] = new int[n+1];
  		for(int i=1;i<=n;i++){
    			primes[i]=ll.removeFirst();
  		}
  		return primes;
 	}
 	long gcd(long a , long b)throws Exception{
  		if(b==0)return a;
  		return gcd(b , a%b);
 	}
 	long lcm(long a , long b)throws Exception{
  		if(a==0||b==0)return 0;
  		return (a*b)/gcd(a,b);
 	}
 	long mulmod(long a , long b ,long mod)throws Exception{
  		if(a==0||b==0)return 0;
  		if(b==1)return a;
   		long ans = mulmod(a,b/2,mod);
   		ans = (ans*2)% mod;
   		if(b%2==1)ans = (a + ans)% mod;
   		return ans;
 	}
 	long pow(long a , long b ,long mod)throws Exception{
   		if(b==0)return 1;
  		if(b==1)return a;
   		long ans = pow(a,b/2,mod);
   		ans = (ans * ans)% mod;
   		if(b%2==1)ans = (a * ans)% mod;
   		return ans;
 	}
 	// 20*20   nCr Pascal Table
 	long[][] ncrTable()throws Exception{
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
   double roundOff = Math.round(number1 * 100.0) / 100.0;
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
		
 
    /*  
    	  // Old Reader Writer
    	  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
	  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */
		HldNodesMain driver = new HldNodesMain();
    		 long start =  System.currentTimeMillis();
    		 driver.run();
    		 long end =  System.currentTimeMillis();
    		 //out.write(" Total Time : "+(end - start)+"\n");
    		 driver.closeResources();
    		 return ;
    		 
	}	 

}

 /******************** Pair class ***********************/
 
 class Pair implements Comparable<Pair>{
 public int a;
 public int b;
 public Pair(){
  this.a = 0;
  this.b = 0;
 }
 public Pair(int a,int b){
  this.a = a;
  this.b = b;
 }
 public int compareTo(Pair p){
  if(this.a==p.a){
   return this.b-p.b;  
  }
  return this.a-p.a; 
 }
 public String toString(){
  return "number1="+this.a+" number2="+this.b;
 }
 
} 
