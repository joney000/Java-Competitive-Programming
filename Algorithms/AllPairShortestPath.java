package Algorithms;

import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : All Pair Shortest Path
 * Platform  : Codeforces
 * Ref       : Time Complexity: O(N^3), Space Complexity: O(N^2)
 */

class AllPairShortestPath extends AlgorithmsMathematicalOperationsUtility {
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  
  private final int BUFFER = 100005;
  
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public AllPairShortestPath(){}
  public AllPairShortestPath(boolean stdIO)throws FileNotFoundException{
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
  
  final int MAX_N = 100;
  long cost[][] = new long[MAX_N + 1][MAX_N + 1];
  long weight[][] = new long[MAX_N + 1][MAX_N + 1];

  void run()throws Exception{
    int n = i();
    int ans = 0;
    initialize();
    out.write(""+ans+"\n");
  }

  void initialize(){
    for(int i = 1; i <= MAX_N; i++){
      for(int j = 1; j <= MAX_N; j++){
        weight[i][j] = INF_L;
        if(i==j)weight[i][j] = 0L;
      }
    }
  }

  void allPairShortestPath(int n){
    for (int i = 1; i <= n; i++){
      for (int j = 1; j <= n; j++){
        cost[i][j] = weight[i][j];
      }
    }
    // order matters: k->i->j 
    for(int k = 1; k <= n; k++){
      for (int i = 1; i <= n; i++){
        for (int j = 1; j <= n; j++){
          if(cost[i][k] + cost[k][j] < cost[i][j]){
              cost[i][j] = cost[i][k] + cost[k][j];
          }
        }
      }
    }
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
//  double roundOff = Math.round(number1 * 100.0) / 100.0;
//                    or
//  System.out.printf("%.2f", val);

//  print upto 2 digits after decimal
//  val = ((long)(val * 100.0))/100.0;    

  public static void main(String[] args) throws java.lang.Exception{

    AllPairShortestPath driver = new AllPairShortestPath(true);
    driver.run();
    driver.closeResources();
  }
}
