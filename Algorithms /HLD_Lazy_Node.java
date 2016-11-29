//pakage joney_000[let_me_start]
//
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : Not Specified
 * Platform  : https://www.hackerrank.com/contests/w12/challenges/white-falcon-and-tree
 *
 */
 
 
/*    The Main Class                */
class Node {
	long a = 1L;  // Identity  I = [a , b] = [1 , 0]
	long b = 0L;
	long aa = 1L;
	long bb = 0L;
	int lazyTime = 0;
	long lazya = 1L;
	long lazyb = 0;
	private final long mod = 1000000000+7;
	public Node(){}
	public Node(long a , long b , long c , long d){
		this.a = a ;
		this.b = b ;
		this.aa = c;
		this.bb = d;
	}
	// U --> V upward 
	public Node add(Node u , Node v){
		if(u==null)return v;
		if(v==null)return u;
		Node c = new Node();
		c.a =  (u.a * v.a ); 
		if(c.a >= mod ) c.a %= mod ;
		c.b = ( v.b + v.a * u.b);
		if(c.b >= mod) c.b %= mod;
		return c;
	}
	
	public Node add_r(Node u , Node v){
		if(u==null)return v;
		if(v==null)return u;
		Node c = new Node();
		c.aa =  ( u.aa * v.aa ) ;
		if(c.aa >= mod )c.aa %= mod; 
		c.bb = ( v.bb + v.aa * u.bb); 
		if(c.bb >= mod)c.bb %= mod ;
		return c;
	}
	@Override
	public String toString(){
		return "a="+this.a+" b="+this.b+" aa="+this.aa+" bb="+this.bb+" lazya="+this.lazya+" lazyb="+this.lazyb+" lazyTime="+this.lazyTime;
	
	}
	
}
class A
{	
    	private InputStream inputStream ;
	private OutputStream outputStream ;
	private FastReader in ;
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
	
	public A(){}
	public A(boolean stdIO)throws FileNotFoundException{
		if(stdIO){
			inputStream = System.in;
			outputStream = System.out;
		}else{
			inputStream = new FileInputStream("input.txt");
			outputStream = new FileOutputStream("output.txt");
		}
		in = new FastReader(inputStream);
		out = new PrintWriter(outputStream);
		
	}
      // fine place for Global Variables
	
      final int N = 51005 ;
      int LOGN = 20;
	int wa[] = new int[N]; //weight on i'th node
	int wb[] = new int[N]; //weight on i'th node
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
  	Node st[] = new Node[4 * N + 50000];  // The Segment Tree
  	int log[] = new int[N+5];
  	
  	void run()throws Exception{
	
	//	 int tests = i();
		 once();
	//	 for(int t  = 1 ; t<= tests ; t++){
		 	int n = i(); 
		 //	A = l(); B = l();
		  	for(int i=1;i<=n;i++){
		   		wa[i] = i();
		   		wb[i] = i();
		   	}
		 	clear(n);
		 	int e = n-1;
		 	for(int i=1; i<=e ;i++){
		   		int u = i(); int v = i();
		   		adj[u].add(v);
		   		adj[v].add(u);	
		 	}
		 	
		 	dfs(root, -1 , 0); // We set up subsize, parent and depth for each node before sparse table
			HLD(root,  -1); // We decomposed the tree and created baseArray
			make_tree(1, 1, ptr-1); // We use baseArray and construct the needed segment tree
			make_sparseTable(n); // sparse table
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
				int type = i();
			
				if(type==1){
					int u = i(); int v = i(); int a = i(); int b = i();
					int queryTime = qq;
					change(u,v,a,b,queryTime);//Range Update with Lazy Propogation
					
				}else{
					int u = i() ;int v= i(); int x = i();
					int ans = query(u,v , x);
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
			st[i] = new Node();	
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
		
	//	for(int i = 1 ; i<= 4 * n + 500 - 1; i++ ){
	//		st[i].cnt = 0;	
	//	}
	}
	void dfs(int cur, int prev, int _depth) {
		f[0][cur] = prev;
		depth[cur] = _depth;
		subsize[cur] = 1;
		for(int i=0; i< adj[cur].size(); i++)
			if((int)adj[cur].get(i) != prev) {
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
			st[curr].a = wa[baseArray[s]];
			st[curr].b = wb[baseArray[s]];
			st[curr].aa = wa[baseArray[s]];
			st[curr].bb = wb[baseArray[s]];
			
			return;
		}
		make_tree(2*curr, s, (s+e)/2);
		make_tree(2*curr + 1, ((s+e)/2) + 1, e);
		
		Node a = st[curr].add(st[2*curr] , st[2*curr + 1]);
		Node b = st[curr].add_r(st[2*curr+1] , st[2*curr]);
		st[curr].a = a.a;
		st[curr].b = a.b;
		st[curr].aa = b.aa;
		st[curr].bb = b.bb;
 		
	}
  	void make_sparseTable(int n){
  		// Below Dynamic programming code is SparseTable for LCA.

		for(int i=1; i<LOGN; i++)
			for(int j=1; j<=n; j++)
				if(f[i-1][j] != -1)
					f[i][j] = f[i-1][f[i-1][j]];

	}
	int query(int u, int v, int x) throws IOException{
		int lca = LCA(u, v);
	//	out.write("here4 lca("+u+","+v+")="+lca+"\n");
	//	out.flush();
		Node a = query_up_1(u, lca); // One part of path
	//	print_r(a);
	//	out.write("here5 a(u,lca) = "+a+"\n");
	//	out.flush();
		Node b = query_up(v, lca); // another part of path
	//	print_r(b);
	//	out.write("here6 b(v,lca) = "+b+"\n");
	//	out.flush();
		a.a = a.aa;
		a.b = a.bb;
		Node res = a.add(a,b);
		long ans = (res.a * x + res.b);
		if(ans >= mod)ans %= mod;
		
		return (int)ans;
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
  	Node query_up(int u, int v) {
		//if(u == v) return query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]); // Trivial soln for node
		int uchain = 0; int vchain = chainIndex[v];Node ans = null;
		// uchain and vchain are chain numbers of u and v
		while(true) {
			uchain = chainIndex[u];
			if(uchain == vchain) {
				// Both u and v are in the same chain, so we need to query from u to v, update answer and break.
				// We break because we came from u up till v, we are done
		//		out.write("qtree1: ptr="+(ptr-1)+" "+posInBase[v]+" "+ posInBase[u]+" u="+u+" v="+v+" ans = "+ans+"\n");
		//		out.flush();
	//		
			//	Node a = query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]); // Actual
				Node a = query_tree(1, 1, ptr-1, posInBase[v]+1, posInBase[u]); //for this ques only
				if(ans == null)ans = a;
				else ans = ans.add(a , ans);
				//ans = Math.max(a,ans); // Update answer
				
				break;
			}
		//	out.write("qtree2: ptr="+(ptr-1)+" "+posInBase[chainHead[uchain]]+" "+ posInBase[u]+" u="+u+" chainHead[uchain]= "+chainHead[uchain]+" v="+v+" uchain="+uchain+" ans = "+ans+"\n");
		//	out.flush();
			
			Node b = query_tree(1, 1, ptr-1, posInBase[chainHead[uchain]], posInBase[u]);
			// Above is call to segment tree query function. We do from chainHead of u till u. That is the whole chain from
			// start till head. We then update the answer
			if(ans ==null)ans = b;
			else ans = ans.add(b , ans);
			u = chainHead[uchain]; // move u to u's chainHead
			u = f[0][u]; //Then move to its parent, that means we changed chains
		}
		return ans;
  	}
  	Node query_up_1(int u, int v) {
		//if(u == v) return query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]); // Trivial soln for node
		int uchain = 0; int vchain = chainIndex[v];Node ans = null;
		// uchain and vchain are chain numbers of u and v
		while(true) {
			uchain = chainIndex[u];
			if(uchain == vchain) {
				// Both u and v are in the same chain, so we need to query from u to v, update answer and break.
				// We break because we came from u up till v, we are done
		//		out.write("qtree1: ptr="+(ptr-1)+" "+posInBase[v]+" "+ posInBase[u]+" u="+u+" v="+v+" ans = "+ans+"\n");
		//		out.flush();
	//		
				Node a = query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]);
				if(ans == null)ans = a;
				else ans = ans.add_r(ans,a);
				//ans = Math.max(a,ans); // Update answer
				
				break ;
			}
		//	out.write("qtree2: ptr="+(ptr-1)+" "+posInBase[chainHead[uchain]]+" "+ posInBase[u]+" u="+u+" chainHead[uchain]= "+chainHead[uchain]+" v="+v+" uchain="+uchain+" ans = "+ans+"\n");
		//	out.flush();
			
			Node b = query_tree(1, 1, ptr-1, posInBase[chainHead[uchain]], posInBase[u]);
			// Above is call to segment tree query function. We do from chainHead of u till u. That is the whole chain from
			// start till head. We then update the answer
			if(ans ==null)ans = b;
			else ans = ans.add_r(ans , b);
			u = chainHead[uchain]; // move u to u's chainHead
			u = f[0][u]; //Then move to its parent, that means we changed chains
		}
		return ans;
  	}
  	
  	/*
	 * query_tree:
 	* Given S and E, it will return the maximum value in the range [S,E]
 	
 	*/
  	Node query_tree(int curr, int s, int e, int S, int E) {
		
		if(st[curr].lazyTime != 0){
				
			set(curr , s , e);
		}
		if(s>E||e<S||s>e) {
			 //invalid condition
			return null;
		}
		if(s >= S && e <= E) {
		
			return st[curr];
		}
		Node a = query_tree(2*curr, s, (s+e)/2 , S , E);
		Node b = query_tree(2*curr + 1, ((s+e)/2) + 1, e , S , E);
		if(b==null)return a;
		if(a==null)return b;
		Node res = new Node();
		Node p = a.add(a,b);
		Node q = a.add_r(b,a);
		res.a = p.a;
		res.b = p.b;
		res.aa = q.aa;
		res.bb = q.bb;
		return res;
  	}

  	void change(int u , int v , int a , int b, int qtime) {
		int lca = LCA(u,v);
		update_up(u,lca, a, b , qtime);
		update_up(v,lca, a, b , qtime);
	}
	void update_up(int u, int v , int a , int b, int qtime) {
		//if(u == v) return query_tree(1, 1, ptr-1, posInBase[v], posInBase[u]); // Trivial soln for node
		int uchain = 0; int vchain = chainIndex[v];Node ans = null;
		// uchain and vchain are chain numbers of u and v
		while(true) {
			uchain = chainIndex[u];
			if(uchain == vchain) {
				// Both u and v are in the same chain, so we need to query from u to v, update answer and break.
				// We break because we came from u up till v, we are done
		//		out.write("qtree1: ptr="+(ptr-1)+" "+posInBase[v]+" "+ posInBase[u]+" u="+u+" v="+v+" ans = "+ans+"\n");
		//		out.flush();
	//		
				update_tree(1, 1, ptr-1, posInBase[v], posInBase[u] ,  a ,  b ,  qtime);
				
				//ans = Math.max(a,ans); // Update answer
				
				break;
			}
		//	out.write("qtree2: ptr="+(ptr-1)+" "+posInBase[chainHead[uchain]]+" "+ posInBase[u]+" u="+u+" chainHead[uchain]= "+chainHead[uchain]+" v="+v+" uchain="+uchain+" ans = "+ans+"\n");
		//	out.flush();
			
			update_tree(1, 1, ptr-1, posInBase[chainHead[uchain]], posInBase[u] ,  a ,  b ,  qtime);
			// Above is call to segment tree query function. We do from chainHead of u till u. That is the whole chain from
			// start till head. We then update the answer
			u = chainHead[uchain]; // move u to u's chainHead
			u = f[0][u]; //Then move to its parent, that means we changed chains
		}
		return ;
  	}
  	/*
 	 * update_tree:
	 * Point update. Update a single element of the segment tree.
 	*/
  	void update_tree(int curr, int s, int e, int S, int E , int a , int b , int qtime){
  	
		if(st[curr].lazyTime != 0){
				
			set(curr , s , e);
		}
		if(s>E||e<S||s>e) {
			 //invalid condition
			return ;
		}
		if(s >= S && e <= E) {
			
			st[curr].lazyTime = qtime;
			st[curr].lazya = a;
			st[curr].lazyb = b;
			set(curr , s , e );
			return ;
		}
		update_tree(2*curr, s, (s+e)/2 , S , E , a , b, qtime);
		update_tree(2*curr + 1, ((s+e)/2) + 1, e , S , E , a, b , qtime);
		set(2*curr , s ,(s+e)/2 );
		set(2*curr + 1, ((s+e)/2) + 1, e);
		Node aa = st[2*curr];
		Node bb = st[2*curr + 1];
		Node res = new Node();
		Node p = aa.add(aa,bb);
		Node q = aa.add_r(bb,aa);
		res.a = p.a;
		res.b = p.b;
		res.aa = q.aa;
		res.bb = q.bb;
		st[curr] = res;

  	}
  	void set(int curr , int s , int e){
  		if(st[curr].lazyTime ==0)return;
  		if(s > e)return ;
  		// update if segment is lazy
  		
  		st[curr].a = st[curr].aa = pow(st[curr].lazya , e-s + 1 , mod);
		
		
		if(st[curr].lazya==0)st[curr].b = st[curr].bb = st[curr].lazyb;
		else if(st[curr].lazya == 1){
			long x = (((long)( e-s + 1)) * st[curr].lazyb);
			if(x >= mod) x = x%mod;
			st[curr].b = st[curr].bb =  x ;
		}else {
				long nu = pow(st[curr].lazya , e-s + 1 , mod) - 1;
				if(nu < 0) nu += mod;
				long de = ( st[curr].lazya - 1 );
				long y = (st[curr].lazyb * nu);
				if(y >= mod) y = y%mod;
				 
				long x = ((y)* pow(de , mod - 2 , mod));
				if(x >= mod )x = x %mod;
				st[curr].b = st[curr].bb = x;
		}
		
		// pushdown lazy update if curr != leaf node
		if((s != e)&&(st[2*curr].lazyTime < st[curr].lazyTime)){
				st[2*curr].lazyTime = st[curr].lazyTime;
				st[2*curr].lazya = st[curr].lazya;
				st[2*curr].lazyb = st[curr].lazyb;
						
		}
		if((s != e)&&(st[2*curr + 1].lazyTime < st[curr].lazyTime)){
				st[2*curr + 1].lazyTime = st[curr].lazyTime;
				st[2*curr + 1].lazya = st[curr].lazya;
				st[2*curr + 1].lazyb = st[curr].lazyb;
						
		}
		st[curr].lazyTime = 0;
		st[curr].lazya = 0;
		st[curr].lazyb = 1;
		return ;
  
    	}
//****************************** My Utilities ***********************//
 	void print_r(Object... o){
       	out.write("\n"+Arrays.deepToString(o)+"\n");
        	out.flush();
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
 	int[] primes(int n){       // for(int i=1;i<=arr.length-1;i++)out.write(""+arr[i]+" ");
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
		
 
    /*  
    	  // Old Reader Writer
    	  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
	  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */ 	 
    		 A driver = new A(true);
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
	private byte[] buf = new byte[8*1024];
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
  return "a="+this.a+" b="+this.b;
 }
 
} 
