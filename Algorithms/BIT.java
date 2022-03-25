package Algorithms;

import Algorithms.InputReaderAndProcessor;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[let_me_start]
 * Algorithm : Bit / fenwick tree
 * Platform  : Codeforces
 * Ref       : https://sanugupta.wordpress.com/2014/08/29/binary-indexed-tree-fenwick-tree/
 */

public class BIT extends AlgorithmsMathematicalOperationsUtility{
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  
  private final int BUFFER = 200005;
  
  private int    auxInts[] = new int[BUFFER];
  private long   auxLongs[] = new long[1];
  private double auxDoubles[] = new double[1];
  private char   auxChars[] = new char[1];
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public BIT(){}
  public BIT(boolean stdIO)throws FileNotFoundException{
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
  
  
  void run()throws Exception{
    int n = i(); int q = i(); int m = i();
    int qry[][] = new int[3][q + 1];// 0=>l, 1=> r, 2=>val
    int a[] = new int[n + 1];

    for(int i = 1; i <= n; i++){
      a[i] = i();
      update(tree, i, a[i]);				// point update this time, 
      update(tree, i + 1, -a[i]);   // if range qry than comment this line and res = qry(tree, idx) - qry(tree, idx - 1)
    }
    
    for(int i = 1; i <= q; i++){
      qry[0][i] = i(); // l
      qry[1][i] = i(); // r
      qry[2][i] = i(); // value
    }
    
    for(int i = 1; i <= q; i++){
      update(tree, qry[0][i], qry[2][i]);
      update(tree, qry[1][i] + 1, qry[2][i]); // range [l , r]
    }

    for(int i = 1; i <= n; i++){
      long res = qry(tree, i); // case of point qry
      out.write(""+res+" ");		
    }

  }// end run

  final int MAX = 200000;
  long tree[] = new long[MAX + 5];  
  
  void update(long tree[], int idx, long value){		// idx to MAX
    while(idx < MAX){
      tree[idx] += value;
      idx += (idx & (-idx));
    }
  }
  
  long qry(long tree[], int idx){
    long ret = 0;
    while(idx > 0){
      ret += tree[idx];
      idx -= (idx & (-idx));
    }
    return ret;
  }

  long lcm(long a, long b){
    if(a == 0 || b == 0)return 0;
    return (a * b)/gcd(a, b);
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
    BIT driver = new BIT(true);
    driver.run();
    driver.closeResources();
  }
}
