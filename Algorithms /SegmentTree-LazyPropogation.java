
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
/*
 * Author    : joney_000[let_me_start]
 * Algorithm : Maximum matching for bipartite graph. Hopcroft-Karp algorithm in O(E * sqrt(V))
 * Platform  : https://www.facebook.com/hackercup
 *
 */
 class Node{

   public long a = 0 , b = 0;
   public long a1 = 0 , b1 = 0 ; 
   int t1 = 0;

   public long ref = 0;// only ref point required
   public long a2 = 0 , b2 = 0 ; 
   int t2 = 0;

   public Node(){

   }
   @Override
   public String toString(){
    return "a ="+a+" b="+b+"\na1="+a1+" b1="+b1+" t1="+t1+"\n"+"a2="+a2+" b2="+b2+" t2="+t2+" ref="+ref;
   }
  
  }
/*    The Main Class                */

 class B{
  
  private InputStream inputStream ;
  private OutputStream outputStream ;
  private FastReader in ;
  private PrintWriter out ;
  /*
    Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
     
  */
  
  //Critical Index Limit : [0..10^5 + 4]
  private final int BUFFER = 100005;
  private int    tempints[] = new int[BUFFER];
  private long   templongs[] = new long[BUFFER];
  private double tempdoubles[] = new double[BUFFER];
  private char   tempchars[] = new char[BUFFER];
  private final long mod = 1000000000+7L;
  private final int  INF  = Integer.MAX_VALUE / 10;
  private final long INF_L  = Long.MAX_VALUE / 10;
  
  public A(){}
  public A(boolean stdIO)throws FileNotFoundException{
    //stdIO = false;
    if(stdIO){
      inputStream = System.in;
      outputStream = System.out;
    }else{
      inputStream = new FileInputStream("laundro_matt.txt");
      outputStream = new FileOutputStream("output.txt");
    }
    in = new FastReader(inputStream);
    out = new PrintWriter(outputStream);
    
  }

    long two[] = new long[32];
    long five[] = new long[32];
    int twoCount = 0;
    int fiveCount = 0;

    void prec(){
        long x = 2;
        for(int i = 1 ; i <= 60; i++){
            two[i] = x;
            x *= 2;
            if(x > 1000000000L){
                twoCount = i;
                break;
            }
        }
        x = 5;
        for(int i = 1 ; i <= 60; i++){
            five[i] = x;
            x *= 5;
            if(x > 1000000000L){
                fiveCount = i;
                break;
            }
        }
/*         out.write("wto\n");
        for(int i = 1; i <= twoCount; i++){
          out.write(""+two[i]+" ");
        }
        out.write("five\n");
        for(int i = 1; i <= fiveCount; i++){
          out.write(""+five[i]+" ");
        }
        out.write("\n");
*/    }
    long getTwoCount(long x){
        long cnt = 0;
        for(int i = 1 ; i <= twoCount; i++){
            cnt += x/two[i];
            if(x/two[i] == 0)return cnt;
        }
        return cnt;
    }
    long getFiveCount(long x){
        long cnt = 0;
        for(int i = 1 ; i <= fiveCount; i++){
            cnt += x/five[i];
            if(x/five[i] == 0)return cnt;
        }
        return cnt;
    }

    void run()throws Exception{
       prec();
     
     //  out.write("cnt="+(getTwoCount(mod))+"\n");
     //  out.write("cnt="+(getTwoCount(mod-1))+"\n");
      // int tests = i();
       int tests =10000000;
     //  out.write("\n\n\n\n");out.write("\n\n\n\n");
       for(int t = 1 ; t <= tests ; t++){
         //   int n = i(); int Q = i();
            int n = 10; int Q = 10;
       //     int n = 1 + (int)(Math.random()*1000) % 10;

         //   int Q = 1 + (int)(Math.random()*1000) % 10;
            
            int num[] = new int[n+1];
            long arr[] = new long[n+1];
            for(int i = 1; i <= n ; i++)arr[i] = num[i] = 1 + (int)(Math.random()*1000) % 10;
          //  for(int i = 1; i <= n ; i++)arr[i] = num[i] = i();
          //  for(int i = 1; i <= n ; i++)num[i] = i();
            Node[] m = new Node[3*n+50000];//size = 2*n+1
            for(int i=1;i<=3*n+49099;i++)m[i] = new Node();

            maketree(1,1,n,m,num);
            //printTree(1,1,n,m);
            long res = 0L;
            long test_res = 0;
            for(int q = 1 ; q <= Q ; q++){
                int type = 1 + (int)(Math.random()*1000) % 3; int l = 1 + (int)(Math.random()*1000) % n; int r = l + (int)(Math.random()*1000) % n;
             //   int type = i(); int l = i(); int r = i();
                if (r > n) r = l;
            //    out.write("\n\n\n\n New Query");out.write("\n\n\n\n");
                out.write("\nqry="+q+" ");
                out.write("type="+type+" l="+l+" r="+r+"");
                
                if(type==1){
              //    int x = i();
                    int x = 1 + (int)(Math.random()*10000) % 10;
                    out.write(" x="+x+"\n");
                    update1(1,1,n,m,num,l,r,getTwoCount(x)-getTwoCount(x-1),getFiveCount(x)-getFiveCount(x-1),q);
                    long val = x;
                    for(int i = l ; i <= r; i++)arr[i] *= val;

                }else if(type == 2){
                    int x = 1 + (int)(Math.random()*10000) % 10;
              //      int x = i();
                    out.write(" x="+x+"\n");
                    int ref = l;
                    update2(1,1,n,m,num,l,r,getTwoCount(x)-getTwoCount(x-1),getFiveCount(x)-getFiveCount(x-1),q,ref);
                    long val = x;
                    for(int i = l ; i <= r; i++)arr[i] = (i-l+1)*val;
                }else {
                    Node ans = query(1,1,n,m,num,l,r);
                    //out.write("Original :"+(Math.min(ans.a,ans.b))+"\n");
                    out.write("\n");
                      long val = 1L;

                      for(int i = l ; i <= r; i++)val *= arr[i];
                      test_res += getZero(val);
                     out.write("Test  :                                   "+(getZero(val)==Math.min(ans.a,ans.b))+"\n");
                      if(getZero(val) != Math.min(ans.a,ans.b)){
                      
                        out.write("WRONG ANS ");
                        out.write("Given = "+Math.min(ans.a,ans.b)+" Brute = "+getZero(val)+"\n");
                      for(int i = 1 ; i <= n ; i++){
                        out.write(""+arr[i]+" ");
                      }
                      out.write("\n");
                      for(int i = 1 ; i <= n ; i++){
                        out.write(""+num[i]+" ");
                      }
                      out.write("\n");
                      return;
                    }
                   
                    out.write("\n");
                  res += Math.min(ans.a,ans.b);
                }
           //     out.flush();
          //      printTree(1,1,n,m);
               // Node ans = query(1,1,n,m,num,1,n);
              //  out.write(""+(Math.min(ans.a,ans.b))+"\n");
                
            }
   //         out.write(""+res+"\n");
            out.write("Major :                                   "+(res==test_res)+"\n");
            //printTree(1,1,n,m);
       }
  //  }//end tests
  }//end run
  int getZero(long x){
    String s = ""+x;
    int cnt = 0;
    for(int i = s.length(); i >=1 ; i--){
      if(s.charAt(i-1)=='0')cnt++;
      else break;
    }
    return cnt;
  }
  void maketree(int node,int i,int j,Node[] m,int []num){
      if(i==j){
        m[node].a = getTwoCount(num[i])-getTwoCount(num[i]-1);
        m[node].b = getFiveCount(num[i])-getFiveCount(num[i]-1);
        return;
      }
  
      maketree(2*node,i,(i+j)/2,m,num);
      maketree(2*node+1,((i+j)/2)+1,j,m,num);
      //logic
      m[node].a = m[2*node].a + m[2*node + 1].a;
      m[node].b = m[2*node].b + m[2*node + 1].b;

  //  print(node,m);
  }
  void printTree(int node,int i,int j,Node[] m){
      if(i==j){
        out.write("\n node="+node+" l = "+i+" r="+j+" "+"at "+m[node]+"\n");
        return;
      }
  
      printTree(2*node,i,(i+j)/2,m);
      printTree(2*node+1,((i+j)/2)+1,j,m);
      //logic
     // m[node].a = m[2*node].a + m[2*node + 1].a;
     // m[node].b = m[2*node].b + m[2*node + 1].b;
      out.write("\n node="+node+" l = "+i+" r="+j+" "+"at "+m[node]+"\n");
  //  print(node,m);
  }
  void pushUpdate(int node,int l,int r,Node[] m,int []num,int i,int j){
    if(l>r)return ;  //invalid condition

    if(m[node].t1 > m[node].t2){
      long len = r - l + 1;
      // Update type 2
      if(m[node].t2 != 0){
        m[node].a = len * m[node].a2 + getTwoCount(r - m[node].ref + 1) - getTwoCount(l - m[node].ref + 1 - 1);
        m[node].b = len * m[node].b2 + getFiveCount(r - m[node].ref + 1) - getFiveCount(l - m[node].ref + 1 - 1);
      }
      // Update type 1
     // if(m[node].t1 != 0){
        m[node].a += len * m[node].a1;
        m[node].b += len * m[node].b1;
      
        if(m[node].t1 != 0){
        // Push type 1 update to child
        if(l < r){   // restrict l==r
          m[2*node].a1 += m[node].a1;
          m[2*node].b1 += m[node].b1;
          m[2*node + 1].a1 += m[node].a1;
          m[2*node + 1].b1 += m[node].b1;
          m[2*node].t1 = m[2*node+1].t1 = m[node].t1;
        }
      }
      if(m[node].t2 != 0){
        // push type 2 update to child
        if(l < r){   // restrict l==r
          m[2*node].a2 = m[node].a2;
          m[2*node].b2 = m[node].b2;
          m[2*node + 1].a2 = m[node].a2;
          m[2*node + 1].b2 = m[node].b2;
          
          m[2*node].t2 = m[2*node+1].t2 = m[node].t2;
          m[2*node].ref = m[2*node+1].ref = m[node].ref;
        }
      }
      
    }else if(m[node].t1 < m[node].t2){
      // Reject Update type 1
      m[node].a = 0;
      m[node].b = 0;
      m[node].a1 = m[node].b1 = 0;
      // Update type 2
      long len = r - l + 1;
      // Update type 2
      m[node].a = len * m[node].a2 + getTwoCount(r - m[node].ref + 1) - getTwoCount(l - m[node].ref + 1 - 1);
      m[node].b = len * m[node].b2 + getFiveCount(r - m[node].ref + 1) - getFiveCount(l - m[node].ref + 1 - 1);
    //  out.write("In Update Node :"+node+" l="+l+" r="+r+" "+m[node]+"\n");
      // push type 2 update to child
      if(l < r){   // restrict l==r
        m[2*node].a2 = m[node].a2;
        m[2*node].b2 = m[node].b2;
        m[2*node + 1].a2 = m[node].a2;
        m[2*node + 1].b2 = m[node].b2;
        
        m[2*node].t2 = m[2*node+1].t2 = m[node].t2;
        m[2*node].ref = m[2*node+1].ref = m[node].ref;
      }
    }
      // Mark Current Node Updated
      m[node].a1 = m[node].b1 = m[node].t1 = 0;
      m[node].a2 = m[node].b2 = m[node].ref = m[node].t2 = 0;
  }

  void update1(int node,int l,int r,Node[] m,int []num,int i,int j, long p,long q, int time){
      pushUpdate(node,l,r,m,num,i,j);
      pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
      pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);
      if(l>j||r<i||l>r)return ;  //invalid condition
      // for type - 1 update
      

      if(l>=i&&r<=j) { 
        m[node].a1 += p;
        m[node].b1 += q;
        m[node].t1 = time;
        pushUpdate(node,l,r,m,num,i,j);
        pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
        pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);        
        return;
      }
      
      update1(2*node,l,(l+r)/2,m,num,i,j,p,q,time);
      update1(2*node+1,((l+r)/2)+1,r,m,num,i,j,p,q,time);
      
      pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
      pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j); 
      //logic
      m[node].a = m[2*node].a + m[2*node + 1].a;
      m[node].b = m[2*node].b + m[2*node + 1].b;

  //  print(node,m);
  }
  void update2(int node,int l,int r,Node[] m,int []num,int i,int j, long p,long q, int time,int ref){
      pushUpdate(node,l,r,m,num,i,j);
      pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
      pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);
      if(l>j||r<i||l>r)return;  //invalid condition
      
      

      if(l>=i&&r<=j) {
        m[node].a2 = p;
        m[node].b2 = q;
        m[node].t2 = time;
        m[node].ref = ref;
        pushUpdate(node,l,r,m,num,i,j);
        pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
        pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);
        return;
      }

      update2(2*node,l,(l+r)/2,m,num,i,j,p,q,time,ref);
      update2(2*node+1,((l+r)/2)+1,r,m,num,i,j,p,q,time,ref);
      
      pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
      pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);
      //logic
      m[node].a = m[2*node].a + m[2*node + 1].a;
      m[node].b = m[2*node].b + m[2*node + 1].b;

  //  print(node,m);
  }
  Node query(int node,int l,int r,Node[] m,int []num,int i,int j){
    pushUpdate(node,l,r,m,num,i,j);
    pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
    pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);
    if(l>j||r<i||l>r)return null;  //invalid condition
    // for type - 1 update
    

    if(l>=i&&r<=j) { 
      
      return m[node]; 
    }
    pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
    pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j);
    m[node].a = m[2*node].a + m[2*node + 1].a;
    m[node].b = m[2*node].b + m[2*node + 1].b;
    Node arr1 = query(2*node,l,(l+r)/2,m,num,i,j);
    Node arr2 = query(2*node+1,((l+r)/2)+1,r,m,num,i,j);
    
   // pushUpdate(2*node,l,(l+r)/2,m,num,i,j);
   // pushUpdate(2*node+1,((l+r)/2)+1,r,m,num,i,j); 
    
    if(arr1==null)return arr2;
    if(arr2==null)return arr1;
    Node arr = new Node();
    //logic
    arr.a = arr1.a + arr2.a;
    arr.b = arr1.b + arr2.b;
    return arr;
  }

  void print_r(Object...o){
        out.write("\n"+Arrays.deepToString(o)+"\n");
        out.flush();
  }
  
  int hash(String s){
    int base = 31;
    int a = 31;//base = a multiplier
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
   double roundOff = Math.round(a * 100.0) / 100.0;
                    or
   System.out.printf("%.2f", val);
          
*/
/*
  print upto 2 digits after decimal
  val = ((long)(val * 100.0))/100.0;
  
*/    private void closeResources(){
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
         B driver = new B(true);
         long start =  System.currentTimeMillis();
         driver.run();
         long end =  System.currentTimeMillis();
         //out.write(" Total Time : "+(end - start)+"\n");
         driver.closeResources();
         return ;
         
  }  

}

class FastReader{

  private boolean finished = false;

  private InputStream stream;
  private byte[] buf = new byte[4*1024];
  private int curChar;
  private int numChars;
  private SpaceCharFilter filter;

  public FastReader(InputStream stream){
    this.stream = stream;
  }

  public int read(){
    if (numChars == -1){
      throw new InputMismatchException ();
    }
    if (curChar >= numChars){
      curChar = 0;
      try{
        numChars = stream.read (buf);
      } catch (IOException e){
        throw new InputMismatchException ();
      }
      if (numChars <= 0){
        return -1;
      }
    }
    return buf[curChar++];
  }

  public int peek(){
    if (numChars == -1){
      return -1;
    }
    if (curChar >= numChars){
      curChar = 0;
      try{
        numChars = stream.read (buf);
      } catch (IOException e){
        return -1;
      }
      if (numChars <= 0){
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
        throw new InputMismatchException ();
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
        throw new InputMismatchException ();
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
    while (s.trim ().length () == 0)
      s = readLine0 ();
    return s;
  }

  public String nextLine(boolean ignoreEmptyLines){
    if (ignoreEmptyLines){
      return nextLine ();
    }else{
      return readLine0 ();
    }
  }

  public BigInteger nextBigInteger(){
    try{
      return new BigInteger (nextString ());
    } catch (NumberFormatException e){
      throw new InputMismatchException ();
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
        throw new InputMismatchException ();
      }
      res *= 10;
      res += c - '0';
      c = read ();
    }
    if (c == '.'){
      c = read ();
      double m = 1;
      while (!isSpaceChar (c)){
        if (c == 'e' || c == 'E'){
          return res * Math.pow (10, nextInt ());
        }
        if (c < '0' || c > '9'){
          throw new InputMismatchException ();
        }
        m /= 10;
        res += (c - '0') * m;
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
 
 class Pair implements Comparable<Pair>{

 public int a;
 public int b;

 public Pair(){
  this.a = 0;
  this.b = 0;
 }
 public Pair(int a, int b){
  this.a = a;
  this.b = b;
 }
 public int compareTo(Pair p){
    if(this.b < p.b)return -1;
    else if(this.b > p.b )return 1;
    else {
      if(this.a < p.a)return -1;
      else if(this.a > p.a)return 1;
      else return 0;
    }
 }
 public String toString(){
  return "a="+this.a+" b="+this.b;
 }
 
} 