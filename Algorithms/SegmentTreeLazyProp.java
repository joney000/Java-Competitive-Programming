package Algorithms;//pakage joney_000[let_me_start]
//
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : Segment Tree with Lazy Propogation
 * Platform  : CodeChef.com
 *
 */
class SegmentTreeNode {

  public long val = 1L;  
  public long lazy = 0L;
  
  public SegmentTreeNode(){
    this.val  = 1; 
    this.lazy = 0;
  }

  public SegmentTreeNode(long val, long lazy){
    this.val = val;
    this.lazy = lazy;
  }

  @Override
  public String toString(){
    return "val = "+this.val+", lazy = "+this.lazy;
  }
  
}

/*    The Main Class                */
class SegmentTreeLazyProp
{ 
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
    Size Limit : 10^5 + 4 
  */
  private final int BUFFER = 105;
  private int    tempints[] = new int[BUFFER];
  private long   templongs[] = new long[BUFFER];
  private double tempdoubles[] = new double[BUFFER];
  private char   tempchars[] = new char[BUFFER];
  private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public SegmentTreeLazyProp(){}
  public SegmentTreeLazyProp(boolean stdIO)throws FileNotFoundException{
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
  
  int MAX_N = 100005;
  int MAX_M = 100005;
  int n = 0;
  int q = 0;
  SegmentTreeNode[] m = new SegmentTreeNode[4*MAX_M+100001];//size ~ 2*n+1

  // prob specific
  int qry[][] = new int[3][MAX_M + 5];
  long cnt[] = new long[MAX_N + 5];

  void run()throws Exception{
    int tests = i();
    once();
    for(int t = 1; t <= tests; t++){
      n = i(); q = i();
      clear();
      for(int i = 1; i <= q; i++){
        qry[0][i] = i(); qry[1][i] = i(); qry[2][i] = i();
      }
      // maketree(1, 1, n, m, num);
      for(int i = q ; i >= 1; i--){
        if(qry[0][i] == 1){
          SegmentTreeNode res = query(1, 1, q, m, cnt, i, i);
          // out.write("res_val = "+res+"\n");
          // out.flush();
          cnt[qry[1][i]] = (cnt[qry[1][i]] + res.val)%mod;
          cnt[qry[2][i] + 1] = (cnt[qry[2][i] + 1] - res.val + mod)%mod;
        }else{
          SegmentTreeNode res = query(1, 1, q, m, cnt, i, i);
          update(1, 1, q, m, cnt, qry[1][i], qry[2][i], res.val);
        }
      }
      long curr = 0;
      for(int i = 1; i <= n; i++){
        curr = (curr + mod + cnt[i])%mod;
        out.write(""+curr+" ");
      }
      out.write("\n");
    }
  }// end run

  void once(){
    for(int i = 1; i <= 4 * MAX_M + 100000; i++){
      m[i] = new SegmentTreeNode();
    }
  }

  void clear(){
    for(int i = 1; i <= 4 * MAX_N + 100000; i++){
      m[i].lazy = 0;
      m[i].val = 1;      // reset tree
    }
    for(int i = 1; i <= MAX_N; i++)cnt[i] = 0;
  }

  void maketree(int node, int i, int j, SegmentTreeNode[] m, long []num){
    if(i==j){
      //node[node].data = num[i];
      return;
    }
    maketree(2 * node, i, (i+j)/2, m, num);
    maketree(2 * node + 1, ((i+j)/2)+1, j, m, num);

    //out.write("node no = "+node+" range = "+i+" "+j+"\n");
    //printMat(m[node].mat);
  }

  SegmentTreeNode query(int node, int l, int r, SegmentTreeNode[] m, long []num, int i, int j){
    if(l>j||r<i||l>r)return null;  //invalid condition
    // out.write("qry l = "+l+", r = "+r+", i = "+i+", j = "+j+"\n");
    // out.flush();
    pushDown(node, m, num);
    
    if(l>=i&&r<=j) { 
      return m[node];
    }

    SegmentTreeNode arr1 = query(2*node, l,(l+r)/2, m, num, i, j);
    SegmentTreeNode arr2 = query(2*node + 1, ((l+r)/2)+1, r, m, num, i, j);
    
    if(arr1==null)return arr2;
    if(arr2==null)return arr1;
    SegmentTreeNode arr = new SegmentTreeNode();
    //logic
    
    return arr;
  }

  void update(int node, int l, int r, SegmentTreeNode[] m, long []num, int i, int j, long val)throws Exception{
    if(l>j||r<i||l>r)return ;  //invalid condition

    pushDown(node, m, num);

    if(l>=i && r<=j) {
      m[node].lazy = (m[node].lazy + val)%mod;
      pushDown(node, m, num);
      return;
    }
    
    update(2*node, l, (l+r)/2, m, num, i, j, val);
    update(2*node+1, ((l+r)/2)+1, r, m, num, i, j, val);

 }

 void pushDown(int node, SegmentTreeNode[] m, long[] num){

    m[node].val = (m[node].val + m[node].lazy) % mod;

    m[2 * node].lazy = (m[2 * node].lazy + m[node].lazy)%mod;

    m[2 * node + 1].lazy = (m[2 * node + 1].lazy + m[node].lazy)%mod;

    m[node].lazy = 0;
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
        SegmentTreeLazyProp driver = new SegmentTreeLazyProp(true);
        
        driver.run();

        driver.closeResources();
        return ;

      }  
    }