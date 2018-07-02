import java.lang.String;
import java.util.Vector;
import java.util.Arrays;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import java.util.InputMismatchException;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.lang.Math;
import java.math.BigInteger;

/*
 * Author    : joney_000[Jaswant Singh][E-mail: developer.jaswant@gmail.com]
 * Algorithm : FFT-Fast Fourier Transform, Polynomial Multiplication Time: O(N log N) Space: O(N) , N = polynomial order
 * Platform  : Codeforces
 * Ref       : https://codeforces.com/blog/entry/43499
 *             https://gist.github.com/meooow25/0d61a01c0621efde7a83e1ef1dce898d
**/

class FastFourierTransform{
  static long m1 = 1007, m2 = 1109;

  static void fft(double[] a, double[] b, boolean invert) {
    int count = a.length;
    for (int i = 1, j = 0; i < count; i++) {
      int bit = count >> 1;
      for (; j >= bit; bit >>= 1)
          j -= bit;
      j += bit;
      if (i < j) {
        double temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        temp = b[i];
        b[i] = b[j];
        b[j] = temp;
      }
    }

    for (int len = 2; len <= count; len <<= 1) {
      int halfLen = len >> 1;
      double angle = 2 * Math.PI / len;
      if (invert)
        angle = -angle;
      double wLenA = Math.cos(angle);
      double wLenB = Math.sin(angle);
      for (int i = 0; i < count; i += len) {
        double wA = 1;
        double wB = 0;
        for (int j = 0; j < halfLen; j++) {
          double uA = a[i + j];
          double uB = b[i + j];
          double vA = a[i + j + halfLen] * wA - b[i + j + halfLen] * wB;
          double vB = a[i + j + halfLen] * wB + b[i + j + halfLen] * wA;
          a[i + j] = uA + vA;
          b[i + j] = uB + vB;
          a[i + j + halfLen] = uA - vA;
          b[i + j + halfLen] = uB - vB;
          double nextWA = wA * wLenA - wB * wLenB;
          wB = wA * wLenB + wB * wLenA;
          wA = nextWA;
        }
      }
    }

    if(invert) {
      for(int i = 0; i < count; i++) {
        a[i] /= count;
        b[i] /= count;
      }
    }
  }
  
  static long[] multiply(long[] a, long[] b) {
    int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
    resultSize = Math.max(resultSize, 1);
    double[] aReal = new double[resultSize];
    double[] aImaginary = new double[resultSize];
    double[] bReal = new double[resultSize];
    double[] bImaginary = new double[resultSize];
    for (int i = 0; i < a.length; i++)
        aReal[i] = a[i];
    for (int i = 0; i < b.length; i++)
        bReal[i] = b[i];
    fft(aReal, aImaginary, false);
    fft(bReal, bImaginary, false);
    
    // Linear convolution
    for (int i = 0; i < resultSize; i++) {
        double real = aReal[i] * bReal[i] - aImaginary[i] * bImaginary[i];
        aImaginary[i] = aImaginary[i] * bReal[i] + bImaginary[i] * aReal[i];
        aReal[i] = real;
    }

    fft(aReal, aImaginary, true);
    long[] result = new long[resultSize];
    for (int i = 0; i < resultSize; i++)
        result[i] = Math.round(aReal[i]);
    return result;
  }

  static long[] polynomialPow(long[] b, int pow){

    long a[] = new long[b.length];
    a[0] = 1;
    int k = pow;
    while(k > 0){
        if(k % 2 == 1)
          a = FastFourierTransform.multiply(a, b);
        b = FastFourierTransform.multiply(b, b);
        k /= 2;
    }
    return a;
  }
  
}
public class A{ 
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private FastReader in ;
  private PrintWriter out ;
  
  
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public A(){}
  public A(boolean stdIO)throws FileNotFoundException{
    // stdIO = false;
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

  final int MAXN = 1001;


  void run()throws Exception{
    


    int n = i();  int k = i(); 

    long st = System.currentTimeMillis();
    
    long a[] = new long[MAXN];
    long b[] = new long[MAXN];

    // for(int i = 0; i <= n; i++){
    //   a[i] = (long)(Math.random() * 100000);
    // }
      
    // for(int i = 0; i <= n; i++){
    //   a[i] = (long)(Math.random() * 100000);
    // }
    
    // long res[] = FastFourierTransform.multiply(a, b);
    // long res[] = FastFourierTransform.polynomialPow(a, k);

    // for(int i = 0; i <= 2 * n ; i++)out.write(""+res[i]+" ");

    

    long end = System.currentTimeMillis();
    // out.write("\nres size = "+res.length+" , time = "+(end - st)/1000.0);
    // out.flush();
  }
  
  void clear(){

  }

  long gcd(long a, long b){
    if(b == 0)return a;
    return gcd(b, a % b);
  }

  long lcm(long a, long b){
    if(a == 0 || b == 0)return 0;
    return (a * b)/gcd(a, b);
  }

  long mulMod(long a, long b, long mod){
    if(a == 0 || b == 0)return 0;
    if(b == 1)return a;
    long ans = mulMod(a, b/2, mod);
    ans = (ans * 2) % mod;
    if(b % 2 == 1)ans = (a + ans)% mod;
    return ans;
  }

  long pow(long a, long b, long mod){
    if(b == 0)return 1;
    if(b == 1)return a;
    long ans = pow(a, b/2, mod);
    ans = (ans * ans);
    if(ans >= mod)ans %= mod;

    if(b % 2 == 1)ans = (a * ans);
    if(ans >= mod)ans %= mod;

    return ans;
  }

  // 20*20   nCr Pascal Table
  long[][] ncrTable(){
    long ncr[][] = new long[21][21];

    for(int i = 0; i <= 20; i++){
      ncr[i][0] = ncr[i][i] = 1L;
    }

    for(int j = 0; j <= 20; j++){
      for(int i = j + 1; i <= 20; i++){
        ncr[i][j] = ncr[i-1][j] + ncr[i-1][j-1];
      }
    }

    return ncr;
  }

  int i()throws Exception{
    return in.nextInt();
  }

  long l()throws Exception{
    return in.nextLong();
  }

  double d()throws Exception{
    return in.nextDouble();
  }

  char c()throws Exception{
    return in.nextCharacter();
  }

  String s()throws Exception{
    return in.nextLine();
  }

  BigInteger bi()throws Exception{
    return in.nextBigInteger();
  }

  private void closeResources(){
    out.flush();
    out.close();
    return;
  }

//  IMP: roundoff upto 2 digits 
//  double roundOff = Math.round(a * 100.0) / 100.0;
//                    or
//  System.out.printf("%.2f", val);

//  print upto 2 digits after decimal
//  val = ((long)(val * 100.0))/100.0;    

  public static void main(String[] args) throws java.lang.Exception{
  
    A driver = new A(true);
    driver.run();
    driver.closeResources();
  }
}

class FastReader{

  private boolean finished = false;

  private InputStream stream;
  private byte[] buf = new byte[4 * 1024];
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
    if(this.a == p.a){
     return this.b - p.b;  
   }
   return this.a - p.a; 
  }

  @Override
  public String toString(){
    return "a = " + this.a + " b = " + this.b;
  }
} 

