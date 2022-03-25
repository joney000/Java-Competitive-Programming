package Algorithms;

import Algorithms.InputReaderAndProcessor;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : Sparse Table and LCA
 * Platform  : Codeforces
 * Ref       : Time: O(n log n), space O(n log n)   ps: query time: O(1)
 */

public class SparseTable{
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  
  private final int BUFFER = 100005;
  
  private int    auxInts[] = new int[BUFFER];
  private long   auxLongs[] = new long[1];
  private double auxDoubles[] = new double[1];
  private char   auxChars[] = new char[1];
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public SparseTable(){}
  public SparseTable(boolean stdIO)throws FileNotFoundException{
    // stdIO = false;
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

  final int LOGN = 20;
  final int MAXN = (1 << 20) + 1;
  int[] log2 = new int[MAXN + 1];
  int[][] rmq = new int[LOGN + 1][MAXN + 1];
  int []a = new int[MAXN + 1];  // base array: 0 based indexing.
  int n, q;

  // Time and Space : O(n * logn)  ; assumming 0 based indexing here; gernally I follow 1 based.
  void buildTable(){
    //flor log_2(x)
    for(int i = 2; i <= n; i++){
      log2[i] = log2[i >> 1] + 1;
    }

    for(int i = 0; i < log2[n]; i++){
      for(int j = 0; j < n; j++){
        rmq[i][j] = INF;        // depends on min vs max range query
      }
    }

    for(int i = 0; i < n; i++){
      rmq[0][i] = i;
    }

    for(int k = 1; (1 << k) < n ; k++){
      for(int i = 0; i + (1 << k) <= n; i++){
        int x = rmq[k - 1][i];
        int y = rmq[k - 1][i + (1 << k-1)];
        if(a[x] <= a[y])rmq[k][i] = x; 
        else rmq[k][i] = y;   // Assign min index
      }
    }
  }

  // query
  int minRangeQuery(int i, int j) {
    assert (i <= j) : "invalid query";
    int k = log2[j - i];
    int p = rmq[k][i];
    int q = rmq[k][j - (1 << k) + 1];
    if(a[p] <= a[q])return p;
    else return q;
  }

  void run()throws Exception{
    n = i(); q = i();
    for(int i = 0; i < n; i++){
      a[i] = i();
    }
    buildTable();
    for(int query = 0; query < q; query++){
      int l = i(); int r = i(); 
      // base 0 index
      if(l > r){
        int hold = l;
        l = r;
        r = hold;
      }
      assert (r < n) : "invalid query";  // run program : java -ea _CLASSNAME_ t to enable assertions
      out.write("L["+query+"]= "+l+" R["+query+"]= "+ r +" answer index: " + minRangeQuery(l, r) + "\n");
    }
    
  }// end run

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

  int[] is(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxInts[i] = in.nextInt();  
      return auxInts;
  }

  long l()throws Exception{
    return in.nextLong();
  }

  long[] ls(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxLongs[i] = in.nextLong();  
      return auxLongs;
  }

  double d()throws Exception{
    return in.nextDouble();
  }

  double[] ds(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxDoubles[i] = in.nextDouble();  
      return auxDoubles;
  }

  char c()throws Exception{
    return in.nextCharacter();
  }

  char[] cs(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)auxChars[i] = in.nextCharacter();  
      return auxChars;
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
//  double roundOff = Math.round(number1 * 100.0) / 100.0;
//                    or
//  System.out.printf("%.2f", val);

//  print upto 2 digits after decimal
//  val = ((long)(val * 100.0))/100.0;    

  public static void main(String[] args) throws java.lang.Exception{
  
    SparseTable driver = new SparseTable(true);
    driver.run();
    driver.closeResources();
  }
}
