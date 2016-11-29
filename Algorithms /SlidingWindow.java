//pakage joney_000[let_me_start]
 
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
 /******************** Main Class ***********************/

class E
{

  // sparse table template /// TOPCODER LCA_RMQ	
  public int[] SparseTable;
  public int[][] rmq;
  public int[] a;

  public E(int[] a ,int n, int K,int res[]) {
    
    SparseTable = new int[n+1];
    this.a = a;
	for(int i = 2; i<=n; i++){
      SparseTable[i] = SparseTable[i>>1]+1;
	 // if((1<<SparseTable[i]) >2*K)SparseTable[i] = SparseTable[i>>1];
	}
	
	rmq = new int[3][n];
    // Initializing table O(<nlogn>,<1>)  0 = SparseTable[K];
	for(int i = 0;i<n; i++){  // rmq[i][K-K] = i; // for base level 
		rmq[0][i] = res[i];//res[i] = index of min element from window function
	}
	//SparseTable[K]
    for (int k = SparseTable[K]+1; (1<<k)< n ; k++) {
	 if((1<<k) > 2*K)break;
      for (int i = 0; i + (1<<k)<= n; i++) {
        int x = rmq[k-1-SparseTable[K]][i];
        int y = rmq[k-1-SparseTable[K]][i+(1<<(k-1))];
        if(a[x]<=a[y])rmq[k-SparseTable[K]][i] = x; 
		else rmq[k-SparseTable[K]][i] = y;//assign min index
      }
    }
  }
//query

  public int min(int i, int j ,int K) {
  //  out.write("SparseTable[j-i+1]="+SparseTable[j-i+1]+" "+"SparseTable[K] ="+SparseTable[K]+"\n" );
//	out.flush();
	int k = SparseTable[j-i+1]-SparseTable[K];
	int kk = SparseTable[j-i+1];
	int p = rmq[k][i];
    	int q = rmq[k][j-(1<<kk)+1];
    	if(a[p] <= a[q])return p ;
	else return q;
  }

    public static InputStream inputStream = System.in;
	public static OutputStream outputStream = System.out;
	public static FastReader in = new FastReader(inputStream);;
    public static PrintWriter out = new PrintWriter(outputStream);;
	/*
	Overhead [Additional Temporary Strorage]
	*/
	public static int tempints[] = new int[100005];
	public static long templongs[] = new long[100005];
	public static double tempdoubles[] = new double[100005];
	public static char tempchars[] = new char[100005];
	public static long mod = 1000000000+7;
	public static int L[] = new int[10000007];
	public static int R[] = new int[10000007];
	public static int arr[] = new int[10002007];	
	public static void main(String[] args) throws java.lang.Exception{
		//let_me_start 
		 //int tests=i();
		 //int arr[] = is(n);
		 //String ans = "No";
		 
			
		int n = i(); int k =i(); int q =i();
		int a = i(); int b = i(); int c = i(); int d =i();
		int e = i(); int f = i(); int r = i(); 
		int s = i(); int t = i(); int m = i(); arr[1] =i();
		long test = t;
		//long t1 = System.currentTimeMillis();
		arr[0] = arr[1];
		long var = arr[0];
		for(int x=1;x<=n-1;x++){
			test = (test * t)%s;
			if(test<=r){
				var = ((a*var)%m)*var +b*(var) + c;
				
				if(var>=m)var = var%m;
				arr[x] = (int)var;
			}
			else{
				var = ((d*var)%m)*var + e*(var) + f;
				
				if(var>=m)var = var%m;
				arr[x] = (int)var;
			}
		}
	//for(int x=0;x<=n-1;x++)arr[x] = arr[x+1];
	//	out.write("arr=");
	//	for(int i=0;i<=n-1;i++)out.write(""+arr[i]+" ");
	//	out.write("\n");
	//	out.flush();
		long l1 = l();long la = l(); long lc = l(); long lm = l(); 
		long d1 =l(); long da = l(); long dc = l(); long dm = l();
		long sum =0; long mul =1L;
		for(int i =1 ;i<=q ;i++){
			l1 = ((la * (l1) + lc));
			if(l1>=lm)l1 = l1 %lm;
			d1 = ((da * (d1) + dc));
			if(d1>=dm)d1 = (d1 % dm);
			L[i] = (int)l1 +1 ;
			R[i] = (int)Math.min(L[i]+k-1+d1 , n);
	//		out.write(" Li="+L[i]+" R[i]="+R[i]+"\n");
	//		out.flush();
		}
		//long t2 = System.currentTimeMillis();
		//out.write(""+(t2-t1)/1000000.0+"\n");
//		out.flush();
		int array[] = window(arr,n,k);
	//	out.write("printing min : ");
//		for(int i=0;i<=n-1;i++)out.write(""+array[i]+" ");
	//	out.write("\n");
		E st = new E(arr,n,k,array); //sparse table
//		Shinchan trying SPARSE TABLE FIRST TIME HOPE IT WILL RUN..:D..:D
		
		
		//int[] M = new int[4*n+10000];//size = 2*n+1
		//Arrays.fill(M,Integer.MAX_VALUE);
	//	maketree(1,1,n,M,arr);
		for(int i =1 ;i<=q ;i++){
			int idx = st.min((int)L[i]-1,(int)R[i]-1,k);
	//		out.write("each qry idx of array = "+idx+"\n");
				
			long ans = arr[idx];
	//	    out.write("each qry ans of query = "+ans+"\n");
			sum = (sum + ans );
			mul = (mul * ans );
			if(mul>=mod)mul = (mul)%mod;
		}
		out.write(""+sum+" "+mul);
		out.flush();
		return;
	}


//****************************** Utilities ***********************//

public static int[] window(int arr[], int n, int k)throws Exception
{   //Studied from geeks for geeks and also given in leetcode
    LinkedList <Integer> dq = new LinkedList<Integer>();
    int pos[] = new int[n+1];
	int pow = 0,val=1;
	while(val<=k){
		val = val *2;
		pow ++;
	}
	k = val/2;;
	//out.write("modified window size = "+k+"\n");
	//out.flush(); 	
	int i=0;
    for(i = 0; i<k; i++){
        while ( (!dq.isEmpty()) && arr[i] <= arr[dq.getLast()])
            dq.removeLast();  // Remove from rear
        // Add new element at rear of queue
         dq.addLast(i);
    }
    int idx = 0;
    for(;i<n;i++){
       // System.out.print(arr[dq.getFirst()]+" ");
			pos[idx] = dq.getFirst();
			idx ++;
        // Remove the elements which are out of this window
        while ( (!dq.isEmpty()) && dq.getFirst() <= i - k)
            dq.removeFirst();  // Remove from front of queue
		while ( (!dq.isEmpty()) && arr[i] <= arr[dq.getLast()])
             dq.removeLast();
 
         // Add current element at the rear of dq
        dq.addLast(i);
    }
 
    // Print the maximum element of last window
//    System.out.print(arr[dq.getFirst()]+" ");
	pos[idx] = dq.getFirst();
	idx++;
	return pos;
}
	public static void maketree(int node,int i,int j,int[] m,int []num)throws Exception{
		if(i==j){m[node]=i;return;}
	
		maketree(2*node,i,(i+j)/2,m,num);
		maketree(2*node+1,((i+j)/2)+1,j,m,num);
	
		if(num[m[2*node]]<=num[m[2*node+1]])m[node]=m[2*node];
		else m[node]=m[2*node+1];
	}
	public static int query(int node,int l,int r,int[] m,int []num,int i,int j)throws Exception{
		if(l>j||r<i||l>r)return -1;  //invalid condition
		if(l>=i&&r<=j) return m[node];
		
		int p1 = query(2*node,l,(l+r)/2,m,num,i,j);
		int p2 = query(2*node+1,((l+r)/2)+1,r,m,num,i,j);
		
		if(p1==-1)return p2;
		if(p2==-1)return p1;
		if(num[p1]<=num[p2])return p1;
		else return p2;
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
 // sieve
 public static int[] primes(int n)throws Exception{       // for(int i=1;i<=arr.length-1;i++)out.write(""+arr[i]+" ");
  boolean arr[] = new boolean[n+1];
  Arrays.fill(arr,true);
  for(int i=1;i<=Math.sqrt(n);i++){
	if(!arr[i])continue;
	for(int j = 2*i ;j<=n;j+=i){
		arr[i]=false;
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
 public static long gcd (long a , long b)throws Exception{
  if(b==0)return a;
  return gcd(b , a%b);
 }
 public static long lcm (long a , long b)throws Exception{
  if(a==0||b==0)return 0;
  return (a*b)/gcd(a,b);
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
   if(b==1)return a%mod;
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
//*******************************I/O******************************//	
public static int i()throws Exception{
 //return Integer.parseInt(br.readLine().trim());
 return in.nextInt();
}
public static int[] is(int n)throws Exception{
  //int arr[] = new int[n+1];
  for(int i=1 ; i <= n ;i++)tempints[i] = in.nextInt();  
 return tempints;
}
public static long l()throws Exception{
 return in.nextLong();
}
public static long[] ls(int n)throws Exception{
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
  
*/
}

class FastReader{

	private boolean finished = false;

	private InputStream stream;
	private byte[] buf = new byte[4096];
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

















