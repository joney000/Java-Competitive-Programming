package Algorithms;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : DFS-Grid
 * Platform  : Codeforces
 * Ref       : 
 */

public class DFS_Grid {
  
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

  public DFS_Grid(){}
  public DFS_Grid(boolean stdIO)throws FileNotFoundException{
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

  int n, m;
  // 8 Dir
  // int dx[] = {-1 ,-1 , -1 , 0 , 0, 1 ,1 ,1};
  // int directionVertical[] = {-1 , 0 ,  1 ,-1 , 1,-1 ,0 ,1};
  // 4 Dir
  int dx[] = {-1, 1, 0, 0};
  int dy[] = { 0, 0, 1, -1};

  void run() throws Exception{
    
    n = i(); m = i();
    clear();

    for(int i = 1; i <= n; i++){
      for(int j = 1; j <= m; j++){
        char ch = c();
        mat[i][j] = ch;
      }
    }
    int cnt = 0;
    for(int i = 1; i <= n; i++){
      for(int j = 1; j <= m; j++){
        if(mat[i][j] == '.' && !vis[i][j]){
          dfs(i, j);
          cnt++;
        }
      }
    }
    int ans = cnt;
    out.write(""+ans+"\n");    
    // }
  }// end run
  
  final int MAX_N = 1005;
  boolean vis[][] = new boolean[MAX_N][MAX_N];
  char mat[][]    = new char[MAX_N][MAX_N];
  
  void clear(){

  }

  boolean isValid(int i , int j){
    if(i <= n && i >= 1 && j <= m && j>= 1 && (!vis[i][j]))return true;
    else return false;
  }

  void dfs(int xroot, int yroot){

    LinkedList <Integer> xq = new LinkedList<Integer>();
    LinkedList <Integer> yq = new LinkedList<Integer>();

    // int l = 0;//level and will be marked at the time of adding into queue
    LinkedList<Integer> level_q = new LinkedList<Integer>();
    xq.add(xroot);
    yq.add(yroot);
    vis[xroot][yroot] = true;
    //level[root]=0;
    //level_q.add(l);

    while(!xq.isEmpty()){
    
        int ux = xq.getLast(); //first
        int uy = yq.getLast(); //first
        // l = level_q.removeFirst();
        //level[u] = l;
        boolean noUnvisitedChild = true;

        for(int i = 0 ; i <= 3 ; i++){
          int vx = ux + dx[i] ;
          int vy = uy + dy[i];  
          if(isValid(vx ,vy) && mat[vx][vy]=='.'){
            
            vis[vx][vy] = true;
            xq.add(vx); yq.add(vy); // Path

            noUnvisitedChild = false;
              // level_q.add(l+1);
              // f[v] = u;
              // level[v] = l+1;
          }
        }
        if(noUnvisitedChild){
          xq.removeLast();
          yq.removeLast();
        }
    }

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
  
    DFS_Grid driver = new DFS_Grid(true);
    driver.run();
    driver.closeResources();
  }
}
