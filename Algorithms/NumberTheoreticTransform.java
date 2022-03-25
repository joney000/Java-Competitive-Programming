package Algorithms;

import java.util.Arrays;
import java.util.ArrayList;

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
 * Algorithm : NTT- Number Theoretic Transform, Polynomial Multiplication Time: O(N log N) Space: O(N) ,
 *             N = polynomial order woth given mod of the form mod = p * q + 1  p,q > 0
 * Platform  : Codeforces
 * Ref       : https://codeforces.com/blog/entry/43499
 *             https://gist.github.com/meooow25/0d61a01c0621efde7a83e1ef1dce898d
**/

class NumberTheoreticTransform {
 
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private FastReader in ;
  private PrintWriter out ;
  
  
  private final long mod = 998244353;
  private final int  INF  = Integer.MAX_VALUE;
  private final long INF_L  = Long.MAX_VALUE / 10;
 
  public NumberTheoreticTransform(){}
  public NumberTheoreticTransform(boolean stdIO)throws FileNotFoundException{
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
 
  final int MAX_N = 1 << 15;

  void run()throws Exception{
    int n = i(); int m = i();
    long a[] = new long[n];
    long b[] = new long[n];
    
    for(int i = 1; i <= n; i++)a[i] = l();
    for(int i = 1; i <= n; i++)a[i] = l();
    // NumberTheoryTransform.findSmallestPrimitiveRoot(mod);

    long res[] = NumberTheoryTransform.multiply(a, b);

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
 
  public static void main(String[] args) throws java.lang.Exception{
    NumberTheoreticTransform driver = new NumberTheoreticTransform(true);
    driver.run();
    driver.closeResources();
  }
}

class NumberTheoryTransform {
 
  private static long mod  = 998244353; 
  private static long primitiveRoot = 3;      // w  = omega = number1 = primitive_root
  private static long primitiveRootInverse = 332748118;  
  private static final int MAX_N = 1 << 15;
  private static long A[] = new long[MAX_N];
  private static long B[] = new long[MAX_N];
  
  static void findSmallestPrimitiveRoot(long primeNo){
    int sz = (int)Math.sqrt(primeNo) + 1;
    boolean isPrime[] = new boolean[sz + 1];
    Arrays.fill(isPrime, 2, sz  + 1, true);
    for(int i = 2; i <= sz; i++){
      if(isPrime[i]){
        for(int j = i + i; j <= sz; j += i){
          isPrime[j] = false;
        }
      }
    }
    
    long val = primeNo - 1;
    ArrayList<Integer> primtFactors = new ArrayList<Integer>();
    for(int i = 2; i <= sz; i++){
      if(isPrime[i]){
        if(val % i == 0)primtFactors.add(i);
        while(val % i == 0)val /= i;
      }
    }
    if(val > 1)primtFactors.add((int)val);
    // System.out.print("printFactors: "+primtFactors+"\n");
    for(primitiveRoot = 2; primitiveRoot <= primeNo - 1; primitiveRoot++){   // try every number
      boolean check = true;
      for(int x: primtFactors){   // divisors of primeNo - 1
        if(pow(primitiveRoot, (primeNo - 1)/x, primeNo) == 1){
          check = false;
          break;
        }
      }
      if(check)break;
    }
    System.out.print("primitive_root("+mod+") or w = "+ primitiveRoot +"\n");
    primitiveRootInverse = pow(primitiveRoot, primeNo - 2, primeNo);
    System.out.print("inverse of w(omega) = "+ primitiveRootInverse +"\n");
  }
 
  static long pow(long a, long b, long mod){
    if(b == 0)return 1;
    if(b == 1)return a;
    long ans = pow(a, b/2, mod);
    ans = (ans * ans);
    if(ans >= mod)ans %= mod;
    if(b % 2 == 1)ans = (a * ans);
    if(ans >= mod)ans %= mod;
    return ans;
  }
 
  static void ntt_inplace(long[] a, int n, boolean invert) {
   
    for(int i = 0; i < n; ++i){
      int j = 0;
      int x = i, y = n - 1;
      while(y > 0) {
        j = (j << 1) + (x & 1);
        x >>= 1;
        y >>= 1;
      }
      if(i < j){
        long temp = a[i];
        a[i] = a[j];
        a[j] = temp;
      }
    }
 
    for (int len = 2; len <= n; len <<= 1) {
      long root = invert ? pow(primitiveRootInverse, (mod - 1)/len, mod) : pow(primitiveRoot, (mod - 1)/len, mod);
      for (int i = 0; i < n; i += len) {
        long w = 1L;
        for (int j = 0; j < len / 2; j++) {
            long u = a[i + j], v = (a[i + j + len/2] * w) % mod;
            a[i+j] = u + v < mod ? u + v : u + v - mod;
            a[i + j + len/2] = u - v >= 0 ? u - v : u - v + mod;
            w = (w * root) % mod;
        }
      }
    }
 
    if(invert){
      long invN = pow(n, mod - 2, mod); // 1/n % p  
      for (int i = 0; i < n; ++i)a[i] = (a[i] * invN) % mod;   // c[i] = c[i] / n  % mod   
    }
  }
  
  static long[] multiply(long[] a, long[] b) {
    int resultSize = Integer.highestOneBit(Math.max(a.length, b.length) - 1) << 2;
    resultSize = Math.max(resultSize, 1);
    long[] C = new long[resultSize];
 
    for(int i = 0; i < a.length; i++)A[i] = a[i];
    for(int i = 0; i < b.length; i++)B[i] = b[i];
    for(int i = a.length; i < resultSize; i++)A[i] = 0;
    for(int i = b.length; i < resultSize; i++)B[i] = 0;
    
    // if(resultSize <= 20){
    //   naiveMultiply(BiTSet, B, C, resultSize);
    //   return C;
    // }
 
    ntt_inplace(A, resultSize, false);
    ntt_inplace(B, resultSize, false);
    for (int i = 0; i < resultSize; ++i){C[i] = A[i] * B[i]; if(C[i] >= mod)C[i] %= mod;} // Linear convolution
    ntt_inplace(C, resultSize, true);
    return C;
  }
 
  static void naiveMultiply(long a[], long b[], long c[],int n){
    for(int i = 0 ; i< n ; i++) c[i]=0 ;
    for(int i = 0 ; i < n ; i++){
      for(int j = 0 ; j < n ; j++){
        if(i + j >= n) continue;
        c[i + j] += a[i] * b[j];
        if(c[i + j] >= mod)c[i + j] %= mod ;
      }
    }
  }
 
  static long[] polynomialPow(long[] b, int pow){
    long a[] = new long[b.length];
    a[0] = 1;
    int k = pow;
    while(k > 0){
        if(k % 2 == 1)
          a = multiply(a, b);
        b = multiply(b, b);
        k /= 2;
    }
    return a;
  }
  
  static long[] polynomialPowBrute(long[] b, int pow){
    pow += 2;
    long a[] = new long[b.length];
    a[0] = 1;
    for(int i = 1; i <= pow; i++){
      System.out.print("at pow "+i+"\n");
      System.out.println("");
      a = multiply(a, b);
      for(int x = 0; x < Math.min(a.length, 40); x++){
        if(a[x] != 0){
          System.out.print(""+x+" ");
        }
      }
      System.out.flush();
    }
    return a;
  }
}
