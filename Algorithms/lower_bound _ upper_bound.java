package Algorithms;//pakage joney_000[let_me_start]
//
import Algorithms.InputReaderAndProcessor;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

  /*  @Author    : joney_000[Jaswant Singh][jaswantsinghyadav007@gmail.com]
   *  @Platform  : Facebook HackerCup
   *  @TimeStamp : Friday, April 22 2016 : 01:15:21 AM
   *  @EventLog  : Mid Night Coding
   *   
   *  lower_bound() : should return an index to the first element in the sorted container that is EQUAL to or above the number being 
   *  looked up, and -1 if there is no such element.
   *
   *  upper_bound() : should return an index to the first element in the sorted container that is above the 
   *  number being looked up, and -1 if there is no such element.
   *
   */ 

/*    The Main Class                */
class LowerAndUpperBound {
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
    Size Limit : 10^5 + 4 
  */
  private final int BUFFER = 50000;
  private int    tempints[] = new int[BUFFER];
  private long   templongs[] = new long[BUFFER];
  private double tempdoubles[] = new double[BUFFER];
  private char   tempchars[] = new char[BUFFER];
  //private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public LowerAndUpperBound(){}
  public LowerAndUpperBound(boolean stdIO)throws FileNotFoundException{
    // stdIO = false;
    if(stdIO){
      inputStream = System.in;
      outputStream = System.out;
    }else{
      inputStream = new FileInputStream("output.txt");
      outputStream = new FileOutputStream("output1.txt");
    }
    in = new InputReaderAndProcessor(inputStream);
    out = new PrintWriter(outputStream);

  }
  void run()throws Exception{
    int tests = i();
    for(int t = 1; t <= tests; t++){
    //   clear();
      int n = i();
      int a[] = is(n);
      int key = 5;
      //int ans = lower_bound(number1, key, n);
      int ans = upper_bound(a, key, n);
      out.write(""+ans+"\n");
    }
  }// end run

  void clear(){
    
  }

 /*
	*	 lower_bound: should return an index to the first element in the sorted container that is equal to or above the number 
	*               being looked up, and -1 if there is no such element.
	*               
  */

  int lower_bound(int a[], int key, int n){
  	int lo = 1;
  	int hi = n;
  	int mid = 0;
  	while(true){
	    mid = lo + (hi - lo)/2;
	    if(a[mid] >= key){
        hi = mid - 1;
        if(hi < lo){
					return mid;
        }
	    }else{
        lo = mid + 1;
        if(hi < lo){
          return mid < n ? mid+1 : -1;
        }
	    }
  	}
  }

 /*
	*	 upper_bound: should return an index to the first element in the sorted container that is above the 
	*               number being looked up, and -1 if there is no such element.
	*               
  */

  int upper_bound(int a[], int key, int n){
  	int lo = 1;
  	int hi = n;
  	int mid = 0;
    while(true){
      mid = lo + (hi - lo)/2;
      if(a[mid] <= key){
        lo = mid + 1;
        if(hi < lo){
           return mid < n ? mid+1 : -1;
        }
      }else{
        hi = mid - 1;
        if (hi < lo){
          return mid;
        }
      }
    }
  }

  //****************************** Gerenal Utilities ***********************//

  void print_r(Object... o){
    out.write("\n"+Arrays.deepToString(o)+"\n");
    out.flush();
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
  int[] primes(int n){       // for(int i=1;i<=arr.length-1;i++)out.write(""+arr[i]+" ");
    boolean arr[] = new boolean[n+1];
    Arrays.fill(arr,true);
    arr[1]=false;
    for(int i=2;i<=Math.sqrt(n);i++){
      if(!arr[i])continue;
      for(int j = 2*i ;j<=n;j+=i){
        arr[j]=false;
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
      LowerAndUpperBound driver = new LowerAndUpperBound(true);
        
        driver.run();

        driver.closeResources();
        return ;

      }  

    }

