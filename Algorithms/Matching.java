package Algorithms;

import Algorithms.InputReaderAndProcessor;

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
public class Matching{
	
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
	
	public Matching(){}
	public Matching(boolean stdIO)throws FileNotFoundException{
		//stdIO = false;
		if(stdIO){
			inputStream = System.in;
			outputStream = System.out;
		}else{
			inputStream = new FileInputStream("laundro_matt.txt");
			outputStream = new FileOutputStream("output.txt");
		}
		in = new InputReaderAndProcessor(inputStream);
		out = new PrintWriter(outputStream);
		
	}

	
	final int MAXN1 = 50000;
	final int MAXN2 = 50000;
	final int MAXM = 150000;
	int n1, n2, edges;
	int last[] = new int[MAXN1];
	int prev[] = new int[MAXM];
	int head[] = new int[MAXM];
	int matching[] = new int[MAXN2];
	int dist[] = new int[MAXN1];
	int Q[] = new int[MAXN1];
	boolean used[] = new boolean[MAXN1];
	boolean vis[] = new boolean[MAXN1];
	
  	void run()throws Exception{
	
		 int tests = i();
	//	 once();
		 for(int t  = 1 ; t<= tests ; t++){
		 	n1 = i(); n2 = i();int e = i();
		 	clear();
		 	for(int i = 1 ; i<= e ;i++){
		 		addEdge(i() , i());
		 	}
			long ans = maxMatching();
			for(int i =0 ; i<= n2 ; i++)out.write("matching of n2["+i+"] ="+matching[i]+"\n");
		  	out.write("Case #"+t+": "+ans+"\n");	
		 	
		}//end tests
	}//end run
	void once(){
		
	}	
	void clear(){
		edges = 0;
    		Arrays.fill(last, 0, n1, -1);
    		Arrays.fill(matching, 0, n2, 0);
    		Arrays.fill(head ,0, MAXM , 0);
    		Arrays.fill(prev ,0, MAXM , 0);
    		
	}

	void addEdge(int u, int v) {
    		head[edges] = v;
    		prev[edges] = last[u];
    		last[u] = edges++;
	}

	void bfs() {
    		Arrays.fill(dist, 0, n1, -1);
    		int sizeQ = 0;
    		for (int u = 0; u < n1; ++u) {
        		if (!used[u]) {
            		Q[sizeQ++] = u;
            		dist[u] = 0;
        		}
    		}
    		for (int i = 0; i < sizeQ; i++) {
        		int u1 = Q[i];
        		for (int e = last[u1]; e >= 0; e = prev[e]) {
            		int u2 = matching[head[e]];
            		if (u2 >= 0 && dist[u2] < 0) {
                			dist[u2] = dist[u1] + 1;
                			Q[sizeQ++] = u2;
            		}
        		}
    		}
	}

	boolean dfs(int u1) {
    		vis[u1] = true;
    		for (int e = last[u1]; e >= 0; e = prev[e]) {
        		int v = head[e];
        		int u2 = matching[v];
        		if (u2 < 0 || !vis[u2] && dist[u2] == dist[u1] + 1 && dfs(u2)) {
            		matching[v] = u1;
            		used[u1] = true;
            		return true;
        		}
    		}
    		return false;
	}

	int maxMatching() {
    		Arrays.fill(used, 0, n1, false);
    		Arrays.fill(matching, 0, n2, -1);
    		for (int res = 0;;) {
        		bfs();
        		Arrays.fill(vis, 0, n1, false);
        		int f = 0;
        		for (int u = 0; u < n1; ++u)
            		if (!used[u] && dfs(u))
                			++f;
        		if (f <=0)
            		return res;
        		res += f;
    		}
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
    		 Matching driver = new Matching(true);
    		 long start =  System.currentTimeMillis();
    		 driver.run();
    		 long end =  System.currentTimeMillis();
    		 //out.write(" Total Time : "+(end - start)+"\n");
    		 driver.closeResources();
    		 return ;
    		 
	}	 

}
