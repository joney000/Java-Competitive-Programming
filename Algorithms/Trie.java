package Algorithms;

import Algorithms.InputReaderAndProcessor;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
 
/**
  *  HackerEarth
  *  @Auther : Jaswant Singh [jaswant.singh@practo.com, jaswantsinghyadav007@gmail.com]
  *  @Date : Saturday 20 May 2017 01:34:34 AM IST
  *  <Time Complexity, Space Complexity> : < O(Q * N), O(N))>
  */
 class TrieNode{
 // public boolean isLeaf = false;
  public int prefix_count;// = 0;
  //public int exact_count = 0;
  public HashMap <Character,TrieNode> children ;//= new HashMap<Character,TrieNode>();
  public TrieNode(){
    prefix_count = 0;
    children = new HashMap<Character,TrieNode>();
  }
  @Override
  public String toString(){
    return "prefix_count"+this.prefix_count + " " + " children: "+ children;
  }
 }
 
class Trie {
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Temporary Storage] but provides memory reusability for multiple test cases. 
  */
  
  //Critical Size Limit : 10^5 + 4
  private final int BUFFER = 3;
  private int    tempints[] = new int[BUFFER];
  private long   templongs[] = new long[BUFFER];
  private double tempdoubles[] = new double[BUFFER];
  private char   tempchars[] = new char[BUFFER];
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 100;
  
  public Trie(){}
  public Trie(boolean stdIO)throws FileNotFoundException{
    //stdIO = false;
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

    // int tests = i();
    // for(int t = 1; t <= tests; t++){
    
    int n = i(); int Q = i();
    String s[] = new String[n+1];
    for(int i = 1; i <= n; i++){
      s[i] = (new StringBuilder(s())).reverse().toString();
    }
    TrieNode root = new TrieNode();
    for(int q = 1; q <= Q; q++){
      int x = i();
     
      int id = 0;
      for(int i = 1; i <= x; i++){
        int xx = i();
        word = s[xx];
        add(root, 0);
        
      }
      int ans = 0;
      for(int i = 1; i <= id; i++){
        int[] buf = new int[0];
        word = s[buf[i]]; // befor qry it should be assign to word
        ans = qry(root, 0);
        // out.write(""+"q = "+q+" ans = "+qry(root, 0)+" str = "+word+"\n");
      }
      out.write(""+ans+"\n");
    }
    

    //out.write(""+ans+"\n");
    //out.write("Case #"+tt+": "+res+"\n");
   //}//end tests  
  }//end run

  String word = "";
  void add(TrieNode curr, int pos){
    while(true){
      if(pos >= word.length())return;   // Invalid
        char ch = word.charAt(pos);
      TrieNode child = curr.children.get(ch);
        if(child == null){
          child  = new TrieNode();
        }
        child.prefix_count += 1;
        curr.children.put(ch, child);
        pos++;
        curr = child;
      }
    //add(child, pos+1);
  }
  
  int qry(TrieNode curr, int pos){       // This is the function logics as per problem 
    while(true){
      if(pos > word.length() - 1)return 0;
      if(curr == null)return 0;
      char ch = word.charAt(pos);
      TrieNode child = curr.children.get(ch);
      if(pos == word.length() - 1){
        return child == null ? 0 : child.prefix_count;
      }
      pos++;
      curr = child;
    }
  }

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
  
*/    
  private void closeResources(){
    out.flush();
    out.close();
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
    Trie driver = new Trie(true);
   long start =  System.currentTimeMillis();
   driver.run();
   long end =  System.currentTimeMillis();
   //out.write(" Total Time : "+(end - start)+"\n");
   driver.closeResources();
   return ;
         
  }   
}
