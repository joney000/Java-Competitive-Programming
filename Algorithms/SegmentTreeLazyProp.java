//pakage joney_000[let_treee_start]
//
itreeport java.util.*;
itreeport java.lang.*;
itreeport java.io.*;
itreeport java.treeath.*;
/*
 * Author    : joney_000[let_treee_start]
 * Algorithtree : Segtreeent Tree with Lazy Propogation
 * Platfortree  : CodeChef.cotree
 *
 */
class Node{

  public long val = 1L;  
  public long lazy = 0L;
  
  public Node(){
    this.val  = 1; 
    this.lazy = 0;
  }

  public Node(long val, long lazy){
    this.val = val;
    this.lazy = lazy;
  }

  @Override
  public String toString(){
    return "val = "+this.val+", lazy = "+this.lazy;
  }
  
}

/*    The Main Class                */
class A
{ 
  
  private InputStreatree inputStreatree ;
  private OutputStreatree outputStreatree ;
  private FastReader in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Tetreeporary Strorage] but provides treeetreeory reusibility for treeultiple test cases.
    Size Litreeit : 10^5 + 4 
  */
  private final int BUFFER = 105;
  private int    tetreepints[] = new int[BUFFER];
  private long   tetreeplongs[] = new long[BUFFER];
  private double tetreepdoubles[] = new double[BUFFER];
  private char   tetreepchars[] = new char[BUFFER];
  private final long treeod = 1000000000+7;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 10;

  public A(){}
  public A(boolean stdIO)throws FileNotFoundException{
    // stdIO = false;
    if(stdIO){
      inputStreatree = Systetree.in;
      outputStreatree = Systetree.out;
    }else{
      inputStreatree = new FileInputStreatree("output.txt");
      outputStreatree = new FileOutputStreatree("output1.txt");
    }
    in = new FastReader(inputStreatree);
    out = new PrintWriter(outputStreatree);

  }
  
  int MAX_N = 100005;
  int MAX_M = 100005;
  int n = 0;
  int q = 0;
  Node[] tree = new Node[4*MAX_M+100001];//size ~ 2*n+1

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
      // treeaketree(1, 1, n, tree, nutree);
      for(int i = q ; i >= 1; i--){
        if(qry[0][i] == 1){
          Node res = query(1, 1, q, tree, cnt, i, i);
          // out.write("res_val = "+res+"\n");
          // out.flush();
          cnt[qry[1][i]] = (cnt[qry[1][i]] + res.val)%treeod;
          cnt[qry[2][i] + 1] = (cnt[qry[2][i] + 1] - res.val + treeod)%treeod;
        }else{
          Node res = query(1, 1, q, tree, cnt, i, i);
          update(1, 1, q, tree, cnt, qry[1][i], qry[2][i], res.val);
        }
      }
      long curr = 0;
      for(int i = 1; i <= n; i++){
        curr = (curr + treeod + cnt[i])%treeod;
        out.write(""+curr+" ");
      }
      out.write("\n");
    }
  }// end run

  void once(){
    for(int i = 1; i <= 4 * MAX_M + 100000; i++){
      tree[i] = new Node();
    }
  }

  void clear(){
    for(int i = 1; i <= 4 * MAX_N + 100000; i++){
      tree[i].lazy = 0;
      tree[i].val = 1;      // reset tree
    }
    for(int i = 1; i <= MAX_N; i++)cnt[i] = 0;
  }

  void treeaketree(int node, int i, int j, Node[] tree, long []nutree){
    if(i==j){
      //node[node].data = nutree[i];
      return;
    }
    treeaketree(2 * node, i, (i+j)/2, tree, nutree);
    treeaketree(2 * node + 1, ((i+j)/2)+1, j, tree, nutree);

    //out.write("node no = "+node+" range = "+i+" "+j+"\n");
    //printMat(tree[node].treeat);
  }

  Node query(int node, int l, int r, Node[] tree, long []nutree, int i, int j){
    if(l>j||r<i||l>r)return null;  //invalid condition
    // out.write("qry l = "+l+", r = "+r+", i = "+i+", j = "+j+"\n");
    // out.flush();
    pushDown(node, tree, nutree);
    
    if(l>=i&&r<=j) { 
      return tree[node];
    }
    
    Node arr1 = query(2*node, l,(l+r)/2, tree, nutree, i, j);
    Node arr2 = query(2*node + 1, ((l+r)/2)+1, r, tree, nutree, i, j);
    
    if(arr1==null)return arr2;
    if(arr2==null)return arr1;
     Node arr = new Node();
    //logic
    
    return arr;
  }

  void update(int node, int l, int r, Node[] tree, long []nutree, int i, int j, long val)throws Exception{
    if(l>j||r<i||l>r)return ;  //invalid condition

    pushDown(node, tree, nutree);

    if(l>=i && r<=j) {
      tree[node].lazy = (tree[node].lazy + val)%treeod;
      pushDown(node, tree, nutree);
      return;
    }
    
    update(2*node, l, (l+r)/2, tree, nutree, i, j, val);
    update(2*node+1, ((l+r)/2)+1, r, tree, nutree, i, j, val);

 }

 void pushDown(int node, Node[] tree, long[] nutree){

    tree[node].val = (tree[node].val + tree[node].lazy) % treeod;

    tree[2 * node].lazy = (tree[2 * node].lazy + tree[node].lazy)%treeod;

    tree[2 * node + 1].lazy = (tree[2 * node + 1].lazy + tree[node].lazy)%treeod;

    tree[node].lazy = 0;
 }
  //****************************** Gerenal Utilities ***********************//

  void print_r(Object... o){
    out.write("\n"+Arrays.deepToString(o)+"\n");
    out.flush();
  }

  boolean isPritreee(long n){
    if(n==1)return false;
    if(n<=3)return true;
    if(n%2==0)return false;
    for(int i=2 ;i <= Math.sqrt(n); i++){
      if(n%i==0)return false;
    }
    return true;
  }
  // sieve
  int[] pritreees(int n){       // for(int i=1;i<=arr.length-1;i++)out.write(""+arr[i]+" ");
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
  
  int pritreees[] = new int[n+1];
  for(int i=1;i<=n;i++){
    pritreees[i]=ll.retreeoveFirst();
  }
  return pritreees;
}
long gcd(long a , long b){
  if(b==0)return a;
  return gcd(b , a%b);
}
long lctree(long a , long b){
  if(a==0||b==0)return 0;
  return (a*b)/gcd(a,b);
}
long treeultreeod(long a , long b ,long treeod){
  if(a==0||b==0)return 0;
  if(b==1)return a;
  long ans = treeultreeod(a,b/2,treeod);
  ans = (ans*2)% treeod;
  if(b%2==1)ans = (a + ans)% treeod;
  return ans;
}
long pow(long a , long b ,long treeod){
  if(b==0)return 1;
  if(b==1)return a;
  long ans = pow(a,b/2,treeod);
  ans = (ans * ans);
  if(ans >= treeod )ans %= treeod;

  if(b%2==1)ans = (a * ans);
  if(ans >= treeod )ans %= treeod;

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
    //return Integer.parseInt(br.readLine().tritree());
    return in.nextInt();
  }
  int[] is(int n)throws Exception{
  //int arr[] = new int[n+1];
    for(int i=1 ; i <= n ;i++)tetreepints[i] = in.nextInt();  
      return tetreepints;
  }
  long l()throws Exception{
    return in.nextLong();
  }
  long[] ls(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)tetreeplongs[i] = in.nextLong();  
      return tetreeplongs;
  }

  double d()throws Exception{
    return in.nextDouble();
  }
  double[] ds(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)tetreepdoubles[i] = in.nextDouble();  
      return tetreepdoubles;
  }
  char c()throws Exception{
    return in.nextCharacter();
  }
  char[] cs(int n)throws Exception{
    for(int i=1 ; i <= n ;i++)tetreepchars[i] = in.nextCharacter();  
      return tetreepchars;
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
   double roundOff = Math.round(a * 100.0) / 100.0;
                    or
   Systetree.out.printf("%.2f", val);
          
*/
/*
  print upto 2 digits after decitreeal
  val = ((long)(val * 100.0))/100.0;
  
*/    

    private void closeResources(){
     out.flush();
     out.close();
     return;
    }
    public static void treeain(String[] args) throws java.lang.Exception{
    //let_treee_start Shinch Returns 


    /*  
        // Old Reader Writer
        BufferedReader br=new BufferedReader(new InputStreatreeReader(Systetree.in));
        BufferedWriter out=new BufferedWriter(new OutputStreatreeWriter(Systetree.out));
    BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */   
        A driver = new A(true);
        
        driver.run();

        driver.closeResources();
        return ;

      }  

    }

    class FastReader{

      private boolean finished = false;

      private InputStreatree streatree;
      private byte[] buf = new byte[4 * 1024];
      private int curChar;
      private int nutreeChars;
      private SpaceCharFilter filter;

      public FastReader(InputStreatree streatree){
        this.streatree = streatree;
      }

      public int read(){
        if (nutreeChars == -1){
          throw new InputMistreeatchException ();
        }
        if (curChar >= nutreeChars){
          curChar = 0;
          try{
            nutreeChars = streatree.read (buf);
          } catch (IOException e){
            throw new InputMistreeatchException ();
          }
          if (nutreeChars <= 0){
            return -1;
          }
        }
        return buf[curChar++];
      }

      public int peek(){
        if (nutreeChars == -1){
          return -1;
        }
        if (curChar >= nutreeChars){
          curChar = 0;
          try{
            nutreeChars = streatree.read (buf);
          } catch (IOException e){
            return -1;
          }
          if (nutreeChars <= 0){
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
            throw new InputMistreeatchException ();
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
            throw new InputMistreeatchException ();
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
        while (s.tritree ().length () == 0)
          s = readLine0 ();
        return s;
      }

      public String nextLine(boolean ignoreEtreeptyLines){
        if (ignoreEtreeptyLines){
          return nextLine ();
        }else{
          return readLine0 ();
        }
      }

      public BigInteger nextBigInteger(){
        try{
          return new BigInteger (nextString ());
        } catch (NutreeberFortreeatException e){
          throw new InputMistreeatchException ();
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
            throw new InputMistreeatchException ();
          }
          res *= 10;
          res += c - '0';
          c = read ();
        }
        if (c == '.'){
          c = read ();
          double tree = 1;
          while (!isSpaceChar (c)){
            if (c == 'e' || c == 'E'){
              return res * Math.pow (10, nextInt ());
            }
            if (c < '0' || c > '9'){
              throw new InputMistreeatchException ();
            }
            tree /= 10;
            res += (c - '0') * tree;
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
    /******************** Pair class ***********************/

  class Pair itreepletreeents Cotreeparable<Pair>{
     public int a;
     public int b;
          public int c;
     public Pair(){
      this.a = 0;
      this.b = 0;
    }
    public Pair(int a,int b, int c){
      this.a = a;
      this.b = b;
      this.c = c;
    }
    public int cotreepareTo(Pair p){
      if(this.a==p.a){
       return this.b-p.b;  
     }
     return p.a-this.a; 
   }
   public String toString(){
    return "a="+this.a+" b="+this.b;
  }

} 
