package Algorithms;

import Algorithms.InputReaderAndProcessor;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : SA O(N * logN)
 * Platform  : https://www.facebook.com/hackercup
 *
 */

 class SA{
	
	private InputStream inputStream ;
	private OutputStream outputStream ;
	private InputReaderAndProcessor in ;
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
	
	public SA(){}
	public SA(boolean stdIO)throws FileNotFoundException{
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
	final int MAXN = 500005;//critical
	int sa[] = new int[MAXN];
	int rank[] = new int[MAXN];
	String S ;
	
  	void run()throws Exception{
	
		 int tests = i();
		 once();
		 for(int t  = 1 ; t<= tests ; t++){
		 	
		 	String s = s();
		 	clear(s);
		 	suffixArray();//sa[0..n-1]
		 	lcp_cum();//lcp[0..n-2]
		 	lcp_to_S();
		 	long ans = 0 ;
		 	int n = S.length();
		 	out.write("SA[0..N-1] :\n");
   			
		 	for(int i =  0 ; i < n ; i++){
		 		out.write(""+S.substring(sa[i])+"\n");
		 	}
		 	   			
   			out.write("LCP_CUM[0..N-2] :\n");
   			for(int i =  0 ; i < n - 1 ; i++){
		 		out.write(""+lcp_cum[i]+"\n");
		 	}
		 	out.write("LCP_RANDOM(0 , 3) :"+lcp_random(0,3)+"\n");
   			out.write("LCP_RANDOM(3 , 4) :"+lcp_random(3,4)+"\n");
			out.write("LCP_RANDOM(1 , 0) :"+lcp_random(1,0)+"\n");
		 	
		 	out.write("LCP_to_S[0..N-1] :\n");
   			for(int i =  0 ; i < n ; i++){
		 		out.write(""+lcp_to_S[i]+"\n");
		 	}
		 	
		 	
		 //	out.write("Case #"+t+": "+ans+"\n");	
		 	
		}//end tests
	}//end run
	void once(){
	
	}	
	void clear(String s){
		S = s;
		int n = s.length();
		Arrays.fill(sa , 0 , n, 0);//[0..n-1]
		Arrays.fill(rank , 0 , n, 0);//[0..n-1]
	}

	Integer[] order = new Integer[MAXN];
	int[] classes = new int[MAXN];
	int c[] = new int[MAXN];
      int[] cnt = new int[MAXN];
      int temp[] = new int[MAXN];
      
  void suffixArray() {
   	
    int n = S.length();
    
    for (int i = 0; i < n; i++)
      order[i] = n - 1 - i;

    // stable sort of characters
 //   Arrays.sort(order,0 ,n, (number1, number2) -> Character.compare(S.charAt(number1), S.charAt(number2)));
      Arrays.sort(order , 0 , n ,new Comparator(){
    	public int compare(Object a , Object b){
    		int p =  (Integer)a;
    		int q  = (Integer)b;		
    		return Character.compare(S.charAt(p), S.charAt(q));
    	}
    });
    for (int i = 0; i < n; i++) {
      sa[i] = order[i];
      classes[i] = S.charAt(i);
    }

    for (int len = 1; len < n; len *= 2) {
      for(int i = 0 ; i< n ;i++)c[i] = classes[i];
      
      for (int i = 0; i < n; i++) {
        classes[sa[i]] = i > 0 && c[sa[i - 1]] == c[sa[i]] && sa[i - 1] + len < n && c[sa[i - 1] + len / 2] == c[sa[i] + len / 2] ? classes[sa[i - 1]] : i;
      }
      for (int i = 0; i < n; i++)
        cnt[i] = i;
      for(int i = 0 ; i< n ;i++)temp[i] = sa[i];
      for (int i = 0; i < n; i++) {
        int s1 = temp[i] - len;
        if (s1 >= 0)
          sa[cnt[classes[s1]]++] = s1;
      }
    }
    return ;
  }

  // longest common prefixes array in O(n) : lcp of 2 consicutive prefixes
  int lcp_cum[] = new int[MAXN-1];
  int lcp_to_S[] = new int[MAXN];
  
  void lcp_cum() {
    int n = S.length();
    
    for (int i = 0; i < n; i++)
      rank[sa[i]] = i;
    for (int i = 0, h = 0; i < n; i++) {
      if (rank[i] < n - 1) {
        for (int j = sa[rank[i] + 1]; Math.max(i, j) + h < S.length() && S.charAt(i + h) == S.charAt(j + h); ++h)
          ;
        lcp_cum[rank[i]] = h;
        if (h > 0)
          --h;
      }
    }
    return ;
  }
  //LCP of all sa to it's string S
  void lcp_to_S(){
  	int n = S.length();
  	int src = 0;
  	for(int i = 0; i<n ; i++){
  		if(sa[i]== 0)src = i;
  	}
  	lcp_to_S[src] = n;
  	int min = Integer.MAX_VALUE;
  	for(int i = src - 1; i >= 0; i--){
  		min = Math.min(min , lcp_cum[i]);
  		lcp_to_S[i] = min;
  	}
  	min = Integer.MAX_VALUE;
  	for(int i = src + 1 ; i<n ; i++){
  		min = Math.min(min , lcp_cum[i-1]);
  		lcp_to_S[i] = min;
  	
  	}
  	return;
  }
	 
  //LCP of between sa[number1] & sa[number2]  O(N)
  //Can be reduced to O(1) using RMQ_SPARSE_TABLE
  int lcp_random(int a , int b){
  	if(a > b){
  		int temp = a;
  		a = b;
  		b =temp;
  	}
  	if(a == b)return (S.length() - sa[a]);
  	// always number1 <= number2
  	int min = Integer.MAX_VALUE;
  	for(int i = a ; i< b ; i++){
  		min = Math.min(min , lcp_cum[i]);
  	}
  	return min;
  }
	 
//****************************** My Utilities ***********************//
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
 	
 	void print_r(Object... o){
       	out.write("\n"+Arrays.deepToString(o)+"\n");
        	out.flush();
	}
	
	int hash(String s){
		int base = 31;
		int a = 31;//base = number1 multiplier
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
    		 SA driver = new SA(true);
    		 long start =  System.currentTimeMillis();
    		 driver.run();
    		 long end =  System.currentTimeMillis();
    		 //out.write(" Total Time : "+(end - start)+"\n");
    		 driver.closeResources();
    		 return ;
    		 
	}	 

}

