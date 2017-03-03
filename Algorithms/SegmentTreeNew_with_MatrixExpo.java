
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
class Node{
   //public int [] C = new int[3]; 
   public long mat[][] = new long[2][2];

  
   public Node(){
   	this.mat[0][0] = this.mat[1][1] = 1L;
   }
  
}

/*    The Main Class                */
 class E{
	
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
	
	public E(){}
	public E(boolean stdIO)throws FileNotFoundException{
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


  	void run()throws Exception{
	
	//	 int tests = i();
	//	 once();
	//	 for(int t  = 1 ; t<= tests ; t++){
		 	clear();
		 	int n = i();int q = i();
		 	int num[] = new int[n+1];
		 	for(int i=1;i<=n;i++){
	  			num[i]= i();
			}
			
			Node[] m = new Node[3*n+5000];//size ~ 2*n+1
			for(int i=1;i<= 3*n + 5000 -1 ;i++)m[i] = new Node();
			
			maketree(1,1,n,m,num);
			
			for(int qq=1;qq<=q;qq++){
				char c = c();
				if(c=='C'){
					int idx = i();int x = i();
					num[idx] = x;	
					update(1,1,n,m,num,idx,idx);
				}else{
					int i = i(); int j = i();
					Node res = query(1,1,n,m,num,i,j);
					long ans = res.mat[0][1];
					out.write(""+ans+"\n");				
				}
			}
			
	//	 	out.write("Case #"+t+": "+ans+"\n");	
		 	
	//	}//end tests
	}//end run
	void mulMat(long [][]ans ,long [][]p , long[][]q ){
   		
   		long var = 0;
   		for(int i=0;i<2;i++){
		    for(int j=0;j<2;j++){
			  for(int k=0;k<2;k++){
				var = (var + (p[i][k]*q[k][j]))%mod;
			  }
		     ans[i][j] = var;
		     var = 0;
		     }
		}
   		
 	}
 	void copy(long [][]ans ,long [][]p ){
   		
   		for(int i=0;i<2;i++){
		    for(int j=0;j<2;j++){
		    		ans[i][j] = p[i][j];
		     }
		}
   		
 	}
	void maketree(int node,int i,int j,Node[] m,int []num){
		if(i==j){
		
		          copy(m[node].mat , expo(num[i]));
		          m[node].mat[0][0] = (m[node].mat[0][0] + 1)%mod;
		          m[node].mat[1][1] = (m[node].mat[1][1]  + 1)%mod;
			//    out.write("node no = "+node+" expo("+num[i]+") = "+"\n");
			//    printMat(m[node].mat);
			    return;
		}
	
		maketree(2*node,i,(i+j)/2,m,num);
		maketree(2*node+1,((i+j)/2)+1,j,m,num);

		mulMat(m[node].mat, m[2*node].mat , m[2*node + 1].mat);
		//out.write("node no = "+node+" range = "+i+" "+j+"\n");
		//printMat(m[node].mat);
	}
	
	Node query(int node,int l,int r,Node[] m,int []num,int i,int j){
		if(l>j||r<i||l>r)return null;  //invalid condition
		
		if(l>=i&&r<=j) { 
			return m[node];
		}
		
		Node arr1 = query(2*node,l,(l+r)/2,m,num,i,j);
	      Node arr2 = query(2*node+1,((l+r)/2)+1,r,m,num,i,j);
		
		if(arr1==null)return arr2;
		if(arr2==null)return arr1;
		Node arr = new Node();
		//logic
		mulMat(arr.mat,arr1.mat , arr2.mat);
		return arr;
	}

	void update(int node,int l,int r,Node[] m,int []num,int i,int j){
		if(l>j||r<i||l>r)return ;  //invalid condition
		
		if(l>=i&&r<=j) {
			 copy(m[node].mat , expo(num[i]));
			 m[node].mat[0][0] += 1;m[node].mat[1][1] += 1;
			 return;
		}
		
		update(2*node,l,(l+r)/2,m,num,i,j);
	      update(2*node+1,((l+r)/2)+1,r,m,num,i,j);
	     	
	     	mulMat(m[node].mat,m[2*node].mat , m[2*node + 1].mat);
		
     }
	void printMat(long Matrix[][]){
		for(int i = 0 ; i<2; i++){
			for(int j = 0 ; j < 2 ; j++){
				out.write(""+Matrix[i][j]+" ");
			}
			out.write("\n");
		}
	}
	
	void clear(){
		 
		 Matrix[0][0] = Matrix[0][1] = 1L;
		 Matrix[1][0] = 1L ; Matrix[1][1] = 0L;
		 Identity[0][0] = 1L;Identity[0][1] = 0L;
		 Identity[1][0] = 0L;Identity[1][1] = 1L;
   	 	 T[0][0] = T[0][1] = T[1][0] = T[1][1] = 0L;
   	 	 
	}
	
	
	long [][]Matrix = new long[2][2];
	long T [][] = new long [2][2] ;
   	long Identity [][] = new long [2][2] ;

	long[][] expo( long exp){
	   //iterative 
	   clear();
	   long var=0L;
		
	   while(exp>1){
			if(exp%2==0){
				for(int a=0;a<2;a++){
					for(int b=0;b<2;b++){
						for(int k=0;k<2;k++){
							var = (var + (Matrix[a][k]*Matrix[k][b]))%mod;
						}
						T[a][b] = var;
						var = 0;
					}
				}
				for(int i=0;i<2;i++){
					for(int j=0;j<2;j++){
						//coping items
						Matrix[i][j]=T[i][j];
						}
				}
				exp=exp/2;
			
			}else{
			
				for(int a=0;a<2;a++){
					for(int b=0;b<2;b++){
						for(int k=0;k<2;k++){
							var = ( var + (Identity[a][k]*Matrix[k][b] ) )%mod;
						}
					T[a][b] = var;
					var = 0;
					}
				}
			
				for(int i=0;i<2;i++){
					for(int j=0;j<2;j++){
						
						Identity[i][j] = T[i][j];
						
					}
					//out.write("\n");
				}
				//exp WAS ODD
				exp = exp-1;
			
			}
	
		 }
		var=0;
		for(int a=0;a<2;a++){
				for(int b=0;b<2;b++){
					for(int k=0;k<2;k++){
						var = (var + (Matrix[a][k]*Identity[k][b]))%mod;
					}
				T[a][b] = var;
				var = 0;
				}
	      }
            for(int a=0;a<2;a++){
			for(int b=0;b<2;b++){
				Matrix[a][b]=T[a][b];
			}
	   	}	   
	
		return Matrix;	 
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
		
 
    /*  
    	  // Old Reader Writer
    	  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
	  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */ 	 
    		 E driver = new E(true);
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
 public int id;
 public String name;
 public long b;
 public long a;
 public long c;
 public Pair(){
  this.id = 1000;
  this.name = "s";
  this.a = 0L;
  this.b = 0L;
  this.c = 0L;
 }
 public Pair(int id , long a,long b , long c , String name){
  this.name = name;
  this.id = id;
  this.a = a;
  this.b = b;
  this.c = c;
 }
 public int compareTo(Pair p){
	if(this.a < p.a)return -1;
	else if(this.a > p.a )return 1;
	else {
		if(this.b < p.b)return -1;
		else if(this.b > p.b )return 1;
		else {
			if(this.c < p.c)return -1;
			else if(this.c > p.c )return 1;
			else return -this.name.compareTo(p.name);
		}
	
	}
 }
 public String toString(){
  return "a="+this.a+" b="+this.b;
 }
 
} 
