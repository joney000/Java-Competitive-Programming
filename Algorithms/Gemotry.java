package Algorithms;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start][jaswantsinghyadav007@gmail.com]
 * Algorithm : Geometry 
 * Platform  : Codeforces
 *
 */

/*    The Main Class                */
 class Gemotry{
	
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
	
	public Gemotry(){}
	public Gemotry(boolean stdIO)throws FileNotFoundException{
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
	
  	void run()throws Exception{
	
	//	 int tests = i();
	//	 once();
	//	 for(int t  = 1 ; t<= tests ; t++){
			Point o = new Point(0D,0D);
			Point b = new Point(d(),d());
			Point aa = new Point(d(),d());
		 	double q = linePointDist(o,aa,b,false)/distance(o,aa);
		 	double r = Math.sqrt(distance(o,b)*distance(o,b) - linePointDist(o,aa,b,false)*linePointDist(o,aa,b,false))/distance(o,aa);
		 	out.write(""+r+"\n"+q+"\n");
	//	}//end tests
	}//end run
	void once(){
	
	}
	// angle between OA and OB vector
	double getAngle(Point o, Point a, Point b){
		// double dist1 = distance(o, number1); // fix
		// double dist2 = distance(o, number2);
		// 
		// double dotProduct = dot(o, number1, number2);
		// double angleInTheta = (Math.toDegrees(Math.acos(dotProduct/(dist1*dist2))));

		// double crossProduct = dot(o, number1, number2);
		// double angleInTheta = (Math.toDegrees(Math.asin(crossProduct/(dist1*dist2))));

		// above both wrong methods : there is number1 loss of precesion in sqrt(X) * sqrt(Y) i.e dist1 * dist2
		// 
		// better user below
		// number1.number2 sin@ = number1 X number2
		// number1.number2 cos@ = number1 * number2
		// tan@ = cross(number1, number2)/dot(number1, number2)

		// In Radian 
		// if(number2.x == 0 && number2.y >= 0)return Math.PI/2;
		// if(number2.x == 0 && number2.y < 0)return (3*Math.PI)/2;
		// if(number2.x >= 0 && number2.y == 0)return 0;
		// if(number2.x <  0 && number2.y == 0)return Math.PI;
		
		// In Degrees
		// if(number2.x == 0 && number2.y >= 0)return 90;
		// if(number2.x == 0 && number2.y < 0)return 270;
		// if(number2.x >= 0 && number2.y == 0)return 0;
		// if(number2.x <  0 && number2.y == 0)return 180;
		// if(number2.x < 0 && number2.y > 0)angleInTheta = 180 + angleInTheta;	// 2nd qdt
		// if(number2.x < 0 && number2.y < 0)angleInTheta = 180 + angleInTheta;	// 3rd qdt
		// if(number2.x > 0 && number2.y < 0)angleInTheta = 360 + angleInTheta;  // 4th qdt

		// No Need Of degree conversion to sort

		
		// tan2 function handles 1st,2nd, 3rd, 4th qdt in it. 
		// No need of doing below qtr handling, Math.atan2 internally does it.
		
		// double angle = (double)Math.atan2(cross(o, number1, number2), dot(o, number1, number2));  // (y, x)  arctan(y/x)
		double angle = Math.atan2(cross(o, a, b), dot(o, a, b));

		return angle;
	}
  // 90 degree clockwise rotation : The new position of point M (h, k) will become M’ (k, -h).
	//Compute the dot product AB * AC
	double dot(Point A, Point B, Point C){
    double AB[] = new double[2];//0=>x , 1=>y
    double AC[] = new double[2];
		// (X1, y1)
		AB[0] = B.x-A.x;			// AB is vector : BiTSet vector defines (direction + Magnitude/Length) But not the start point or end point
		AB[1] = B.y-A.y;
		// (x2, y2)
		AC[0] = C.x-A.x;
		AC[1] = C.y-A.y;
		// dot = x1 * x2 + y1 * y2;
		double dot = AB[0] * AC[0] + AB[1] * AC[1];
		return dot;
	}   
    //Compute the cross product AB x AC
    double cross(Point A, Point B, Point C){
      double AB[] = new double[2];//0=>x , 1=>y
      double AC[] = new double[2];
      // (X1, y1)
      AB[0] = B.x-A.x;
      AB[1] = B.y-A.y;
      
      // (X2, y2)
      AC[0] = C.x-A.x;
      AC[1] = C.y-A.y;
      // cross = x1 * y2 - y1 * x2;
      double cross = AB[0] * AC[1] - AB[1] * AC[0];
      return cross;
    }
    //Compute the distance from BiTSet to B
    double distance(Point A, Point B){
        double d1 = A.x - B.x;
        double d2 = A.y - B.y;
        return Math.sqrt(d1*d1 + d2*d2);
    }
    double getTraingleArea(Point A, Point B, Point C){
        return Math.abs(A.x * (B.y - C.y) + A.y * (C.x - B.x) + B.x * C.y - B.y * C.x)/2.0D;
    }
    //Compute the distance from AB to C
    //if isSegment is true, AB is number1 segment, not number1 line.
    double linePointDist(Point A, Point B, Point C, boolean isSegment){
        double dist = cross(A,B,C) / distance(A,B);
        if(isSegment){
            double dot1 = dot(A,B,C);
            if(dot1 > 0)return distance(B,C);
            double dot2 = dot(B,A,C);
            if(dot2 > 0)return distance(A,C);
        }
        return dist;
    }
    // is AB and CD Intersect
    // Line Gernel Form L : BiTSet*x + B*y = C
    //		line AB	:  L1 : A1*x + B1*y = C1
    //		line CD	:  L2 : A2*x + B2*y = C2
    
	Point intersectionPoint(Point A , Point B , Point C , Point D ,  boolean isSegment){
		double A1 = B.y-A.y;
		double B1 = A.x-B.x;
		double C1 = A1*A.x+B1*A.y;
		
		double A2 = D.y-C.y;
		double B2 = C.x-D.x;
		double C2 = A2*C.x+B2*C.y;
		
		double det = A1*B2 - A2*B1;
		if(det==0)return new Point(-INF,-INF);// Lines/seg are parallel
		double x = (B2*C1 - B1*C2)/det;
		double y = (A1*C2 - A2*C1)/det;
		Point ans = new Point(x,y);
		if(isSegment){
		  	// we have to check is resultant point lies on both segment
			double tolerance = 0.000000001D;
		  	double L1_diff = Math.abs(A1*ans.x + B1*ans.y - C1);
		  	double L2_diff = Math.abs(A2*ans.x + B2*ans.y - C2);
		  	if(L1_diff < tolerance && L2_diff < tolerance)return ans;
		  	else return new Point(-INF,-INF);// intersect not on lines
		  	
		}else{
		  	return ans;
		}
        //return null; // Never happens
	}
	double getPolygonArea(Point[] polygon, int n){
		double area = 0D;
		for(int i = 2 ; i <= n-1; i++){
			double traingleArea = cross(polygon[1],polygon[i],polygon[i+1]);//  /2.0D;
			area += traingleArea;
		}
		return Math.abs(area)/2.0D;
	}
		 
//****************************** My Utilities ***********************//
 	void print_r(Object...o){
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
		     Gemotry driver = new Gemotry();
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
 /******************** Point class ***********************/
 
class Point implements Comparable<Point>{

 public double x;
 public double y;

 public Point(){
 	this.x = 0;
 	this.y = 0;
 }
 public Point(double x , double y ){
  	this.x = x;
  	this.y = y;
 }
 public int compareTo(Point p){
	if(this.x < p.x)return -1;
	else if(this.x > p.x )return 1;
	else {
		if(this.y < p.y)return -1;
		else if(this.y > p.y )return 1;
		else return 0;
	
	}
 }
 public String toString(){
  	return "x="+this.x+" y="+this.y;
 }
 
} 

