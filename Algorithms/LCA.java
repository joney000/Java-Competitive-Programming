package Algorithms;//pakage joney_000[let_me_start]
//
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : N/BiTSet
 * Platform  : N/BiTSet
 *
 */


/*    The Main Class                */
class LCA
{ 
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private InputReaderAndProcessor in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
    Size Limit : 10^5 + 4 
  */
  private final int BUFFER = 5;
  private int    tempints[] = new int[BUFFER];
  private long   templongs[] = new long[BUFFER];
  private double tempdoubles[] = new double[BUFFER];
  private char   tempchars[] = new char[BUFFER];
  //private final long mod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public LCA(){}
  public LCA(boolean stdIO)throws FileNotFoundException{
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
  int MAX_N = 200005;
  int n = 0; int m = MAX_N - 1;                       // tree = (v, e) 
  int level[]  = new int[MAX_N + 1];                  // l[v] = level of node v = et[i]
  
  int et[] = new int[3 * MAX_N + 1];                  // euler tour [1..2N] 
  int f[]  = new int[3 * MAX_N + 1];                  // f[v] = first occurence of node v in the et  
  int node[]  = new int[6 * MAX_N + MAX_N/100];       // Seg tree   size = 3 * (2N)
  
  LinkedList<Integer> adj[] = new LinkedList[MAX_N + 1]; //Adjency List
  int index = 1;

  void run()throws Exception{
    int tests = i();
    once();
    for(int t = 1; t <= tests; t++){
      clear();
      n = i(); m = n - 1;
      for(int i = 1; i <= m; i++){
        int u = i(); int v = i();
        adj[u].add(v);
        adj[v].add(u);
      }
      LinkedList<Integer> adj1[] = getCopy(adj, n);     // wow 
      dfs(adj1, 1, n);    //Assuming that node 1 is the root node
      // printEt(et, index);
      maketree(1, 1, index, node, level); // index is arround 2 N - 1 but can be greater     
      // out.write("\n index = "+index+"\n");
      // for(int i = 1; i <= n; i++)out.write(""+f[i]+" ");
      // out.write("\n");out.flush();
      // printtree(1, 1, index, node, level);
      int q = i();
      for(int qq = 1; qq <= q; qq++){
        int u = i(); int v = i();
        if(f[u] > f[v]){        // who appeared first in et
          int x = u;
          u = v;
          v = x;
        }
        int lca = et[query(1, 1, index, node, f[u], f[v], level)];
        out.write("lca("+u+", "+v+") = "+lca+"\n");
      }
      
    }
  }// end run

  void once(){
    
  }

  void clear(){
    for(int i = 1; i <= MAX_N; i++){
      adj[i] = new LinkedList<Integer>();
      level[i] = INF;
      f[i] = 0;
    }
    for(int i = 1; i <= 6 * MAX_N + MAX_N/100 - 1; i++){
      node[i]  = -1;
    }
  }

  void printEt(int[] et , int n)throws Exception{
    out.write("\nEuler Tour\n");out.flush();
    for(int i=1; i <= n ;i++){
      out.write(" "+ et[i]);
    }
    out.flush();
  }
  
  // Maintain mutability
  LinkedList<Integer>[] getCopy(LinkedList<Integer> adj[], int n)throws Exception{
    LinkedList<Integer> adj_copy[] = new LinkedList[n + 1];
    for(int i = 1; i <= n; i++){
      adj_copy[i] = new LinkedList<Integer>();
      for(int x: adj[i]){
        adj_copy[i].add(x);
      }
    }
    return adj_copy; 
  }

  void dfs(LinkedList<Integer> adj[], int root, int n)throws Exception{
  
    boolean vis[] = new boolean[n+1]; 
    LinkedList <Integer> q = new LinkedList<Integer>();
    index = 1;
    int l = 0;               //level
    
    q.add(root);
    vis[root] = true;
    
    while(!q.isEmpty()){
    
      int u = q.getLast();        // The Stack
      level[u] = l; 
      if(f[u] == 0)f[u] = index;    // setting first occurence
      
      if(et[index-1] != u){       // IMP : if it's not leaf node
        et[index] = u;
        index++;
      }

      if(adj[u].size()>0){  
        int v = adj[u].removeFirst();
        if(!vis[v]){
          q.add(v);
          l++;
          vis[v]    = true;
          level[v]  = l;
          et[index] = v; 
          if(f[v] == 0)f[v] = index;    // setting first occurence
          index++;
        }

      }else {
        int v = q.removeLast();
        l--;
      }
    
    }
    --index; // et[1..index]
  }

  void printtree(int idx,int i,int j,int[] node,int []L)throws Exception{
    if(i==j){
      out.write("\n node value btwn i ="+i+" j="+j+" is"+node[idx]);out.flush();
      return;
    }
  
    printtree(2*idx, i, (i+j)/2, node, L);
    printtree(2*idx+1, ((i+j)/2)+1, j, node, L);
    out.write("\n node value btwn i ="+i+" j="+j+" is"+node[idx]);out.flush();
    
  }

  void maketree(int idx, int i, int j, int[] node, int []L)throws Exception{
    if(i==j){
      node[idx] = i;          // node holds the et index
      return;
    }
  
    maketree(2 * idx, i, (i+j)/2, node, L);
    maketree(2 * idx + 1, ((i+j)/2)+1, j, node, L);
  
    if(L[et[node[2*idx]]] <= L[et[node[2*idx + 1]]])node[idx] = node[2 * idx];
    else node[idx] = node[2*idx + 1];
  }

  int query(int idx, int l, int r, int[] node, int i, int j, int []L)throws Exception{
    if(l>j||r<i||l>r)return -1;  //invalid condition
    if(l>=i && r<=j) return node[idx];
    
    int p1 = query(2*idx, l, (l+r)/2, node, i, j, L);
    int p2 = query(2*idx+1, ((l+r)/2)+1, r, node, i, j, L);
    
    if(p1==-1)return p2;
    if(p2==-1)return p1;
    if(L[et[p1]] <= L[et[p2]])return p1;
    else return p2;
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
        LCA driver = new LCA(true);
        driver.run();
        driver.closeResources();
        return ;

      }  
    }
