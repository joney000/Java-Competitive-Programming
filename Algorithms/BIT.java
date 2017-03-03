import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Coded by  	: jaswant Singh
 * Lang   		: Java
 * Algorithm 	: Not Specified
 * Date           : 29/Nov/2015
 */ 
 class Solution implements Comparable<Solution>{
	 public int vertex;
	 public int weight; 
	 //public provide flexibility to access from outside the the class
	 //at the cost of security
	 public Solution(){
		this.vertex = 0;
		this.weight = 0;
	 }
	 public Solution(int node , int weight){
		 this.vertex = node;
		 this.weight = weight;
	 }
	 @Override
	 public int compareTo(Solution e){
		 if(this.weight<e.weight)return -1;
		 else if(this.weight==e.weight) return 0;
		 else return 1;
	 }
	 @Override
	 public String toString(){
	 	return " vertex = "+ this.vertex + " weight= "+this.weight ;
	 }
     //Fast Reader implementation , handles large I/O files
    private boolean finished = false;

	private InputStream stream;
	private byte[] buf = new byte[1024];  //input Buffer
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;		  

	public Solution(InputStream stream){
		this.stream = stream;
	}
    public static InputStream inputStream = System.in;
	public static OutputStream outputStream = System.out;
	public static Solution in = new Solution(inputStream);
    public static PrintWriter out = new PrintWriter(outputStream);
	/*
	 *  Overhead [Additional Temporary Storage] 
	 *  But it save a lot of time and Re Allocation of Space . it Reuse The same Buffers for all the Test Cases.
	 *  take care of Limit : 10^5 + 5 (Runtime Error:OverFlow)
	 */
	public static int tempints[] = new int[100005];
	public static long templongs[] = new long[100005];
	public static double tempdoubles[] = new double[100005];
	public static char tempchars[] = new char[100005];
	public static long mod = 1000000000+7;
 	public static LinkedList<Integer> adj[];
			
	
		    //Creating Reader-Writer Buffer of initial size 2000B
			//public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in),2000);
		//	public static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out),2000);			
	public static long INF = Integer.MAX_VALUE/1000;
			
				

	public static void main(String []arg)throws Exception{
			
			//int tests = i();
		//	for(int t = 1 ; t <= tests; t++){
				
				int n = i();
				int freq[] = is(n);
				long tree[] = new long[n+100];
				//Building the tree
				for(int i =1 ; i<= n ; i++){
					update(tree ,i , freq[i]);
				}
				int q = i();
				for(int i =1 ; i <= q ; i++){
					int idx = i();
					long ans = query(tree ,idx);
				 	out.write(""+ans+"\n");	out.flush();	
				}
				
		//	}		
			out.flush();
			return ;
		}
	
		
//                   My General Utilities for Contest                //
 void update(long []tree , int idx , int val){
     
     while (idx < tree.length){
        tree[idx] += val;
        idx += (idx & -idx);
    }
}
 long query(long []tree , int idx){
     
    long sum = 0;
    while (idx > 0){
        sum += tree[idx];
        idx -= (idx & -idx);
    }
    return sum;
}

 
 public static boolean isPrime(long n)throws Exception{
	if(n==1)return false;
	if(n<=3)return true;
	if(n%2==0)return false;
	for(int i=2 ;i <= Math.sqrt(n); i++){
		if(n%i==0)return false;
	}
	return true;
 }
 public static long gcd (long a , long b)throws Exception{
	if(b==0)return a;
	return gcd(b , a%b);
 }
 public static long lcm (long a , long b)throws Exception{
	if(a==0||b==0)return 0;
	return (a*b)/gcd(a,b);         //TAKE CARE OF Integer RANGE OVERFLOW
 }
 public static long mulmod(long a , long b ,long mod)throws Exception{
	if(a==0||b==0)return 0;
	if(b==1)return a;
	long ans = mulmod(a,b/2,mod);
	ans = (ans*2)% mod;
	if(b%2==1)ans = (a + ans)% mod;
	return ans;
 }
 public static long pow(long a , long b ,long mod)throws Exception{
	if(b==0)return 1;
	if(b==1)return a;
	long ans = pow(a,b/2,mod);
	ans = (ans * ans)% mod;
	if(b%2==1)ans = (a * ans)% mod;
	return ans;
 }
 // 20*20   nCr Pascal Table
 public static long[][] ncrTable()throws Exception{
	long ncr[][] = new long[21][21];
	for(int i=0 ;i<=20 ;i++){ncr[i][0]=1;ncr[i][i]=1;}
	for(int j=0;j<=20 ;j++){
		for(int i=j+1;i<= 20 ;i++){
			ncr[i][j] = ncr[i-1][j]+ncr[i-1][j-1];
		}
	}
	return ncr;
 }
//							My I/O function                    	//
public static int i()throws Exception{
 return in.nextInt();                   					   //read a single Integer
}
public static int[] is(int n)throws Exception{               
  for(int i=1 ; i <= n ;i++)tempints[i] = in.nextInt();        //read N integers
 return tempints;
}
public static long l()throws Exception{                        //read a single Long
 return in.nextLong();
}
public static long[] ls(int n)throws Exception{				   //read N Long type digits
  for(int i=1 ; i <= n ;i++)templongs[i] = in.nextLong();  
 return templongs;
}

public static double d()throws Exception{
  return in.nextDouble();
}
public static double[] ds(int n)throws Exception{
  for(int i=1 ; i <= n ;i++)tempdoubles[i] = in.nextDouble();  
 return tempdoubles;
}
public static char c()throws Exception{
 return in.nextCharacter();
}
public static char[] cs(int n)throws Exception{
  for(int i=1 ; i <= n ;i++)tempchars[i] = in.nextCharacter();  
 return tempchars;
}
public static String s()throws Exception{
 return in.nextLine();
}
public static BigInteger bi()throws Exception{
 return in.nextBigInteger();
}

//*********************** for 0.2%f [precision data]***********************//
 /* 
  * roundoff upto 2 digits 
  * double roundOff = Math.round(a * 100.0) / 100.0;
  *                  or
  * System.out.printf("%.2f", val);
  *				
  */
/*
  *print upto 2 digits after decimal
  *val = ((long)(val * 100.0))/100.0;
  *
*/

   
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


