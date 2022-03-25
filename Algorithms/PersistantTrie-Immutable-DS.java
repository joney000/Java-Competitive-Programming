package Algorithms;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/**
*  @Auther : Jaswant Singh [jaswant.singh@practo.com, jaswantsinghyadav007@gmail.com]
*  @Date   : Tue Jun  6 23:08:33 IST 2017
*  @Algorithm: Persistent Data Structure : Persistent Trie Implementation/ IMMUTABLE DATA STRUCTURE
*  <Time Complexity, Space Complexity> : < O(Q * Log N , O(N * Log N))>   Q: No of Queries , N : No of Nodes
*  @Desc : Preprocessing < O(N log N) , O(N log N)> and O(L) per query. L = trie depth
*/


class NodePersistent {

  public NodePersistent children[];
  public NodePersistent() {
    children = new NodePersistent[2];
    children[0] = null;
    children[1] = null;
  }
  public NodePersistent(int a, int b) {
    children = new NodePersistent[2];
    if(a > 0)children[0] = new NodePersistent();
    if(b > 0)children[1] = new NodePersistent();
  }
  @Override
  public String toString() {
    return "zeros :=> " + (children[0] != null) + " ,ones :=> " + (children[1] != null) + "\n";
  }

}

class PersistentTrie {

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
    private final long mod = 1000000000 + 7;
    private final int  INF  = Integer.MAX_VALUE / 10;
    private final long INF_L  = Long.MAX_VALUE / 100;

    public PersistentTrie() {}
    public PersistentTrie(boolean stdIO)throws FileNotFoundException {
  //stdIO = false;
      if (stdIO) {
        inputStream = System.in;
        outputStream = System.out;
      } else {
        inputStream = new FileInputStream("input.txt");
        outputStream = new FileOutputStream("output.txt");
      }
      in = new InputReaderAndProcessor(inputStream);
      out = new PrintWriter(outputStream);
    }

    int n, Q;
    int curr_n = 0;
    int root_id ;
    int root_key ;
    HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
    int key[];
    int id[];
    int f[];
  NodePersistent TRIE[];                   // Immutable Trie for each NodePersistent

  void run()throws Exception {

    n = i(); Q = i();
    key  = new int[200000 + 1];
    id   = new int[200000 + 1];
    f    = new int[200000 + 1];
    TRIE  = new NodePersistent[200000 + 1];

    root_id = i(); root_key = i();
    id[1] = root_id;
    key[1] = root_key;
    hm.put(root_id, ++curr_n);

    for (int i = 0; i <= 200000 ; i++) {
      TRIE[i] = new NodePersistent();
    }

    buildTrie(1);

    for (int i = 1; i <= n - 1; i++) {
      int a = i(); int b = i(); int k = i();
      int u = get(a); int v = get(b);
      if (u == -1) {
        hm.put(a, ++curr_n);
        u = curr_n;
        id[curr_n] = a;
      }
      if (v == -1) {
        hm.put(b, ++curr_n);
        v = curr_n;
        id[curr_n] = b;
      }
      key[u] = k;   // u <-- v   u(k)
      f[u] = v;
      buildTrie(u);     // set father and build IMMUTABLE TRIE : Persistant DS
    }

    int last_answer = 0;
    for (int i = 1; i <= Q; i++) {
      long t = l();
      t ^= last_answer;
      if (t == 0) {
        int u = i(); int v = i(); int k = i();
        u ^= last_answer;
        v ^= last_answer;
        k ^= last_answer;
        hm.put(v, ++curr_n);
        key[curr_n] = k;
        id[curr_n] = v;
        v = curr_n;
        f[v] = get(u);
        buildTrie(v);
      }else {
        int v = i(); int k = i();
        v ^= last_answer;
        k ^= last_answer;
        v = get(v);
        int min_answer = getMinTrie(getFixedLengthString(Integer.toBinaryString(k)), TRIE[v], k);
        int max_answer = getMaxTrie(getFixedLengthString(Integer.toBinaryString(k)), TRIE[v], k);
        out.write("" + min_answer + " " + max_answer + "\n");
        out.flush();
        // update last_answer
        last_answer = min_answer ^ max_answer;
     }
     out.flush();
   }
    //out.write("Case #"+tt+": "+res+"\n");
    //}//end tests
    }//end run

    int get(int id) {
      Integer I = (Integer)hm.get(id);
      if (I == null)return -1;
      else return I.intValue();
    }


    void buildTrie(int u)throws Exception {
      TRIE[u] = new NodePersistent();
    addToImmutableTrie(getFixedLengthString(Integer.toBinaryString(key[u])), TRIE[f[u]], TRIE[u]); // IMMutability : add to parent
  }

  String s = "0000000000000000000000000000000000";
  String getFixedLengthString(String x) {
    return s.substring(x.length()) + x;
  }
  // Path Copy Logic
  // Never change the old copy i.e. Mutability
  void addToImmutableTrie(String x, NodePersistent old, NodePersistent copy)throws Exception {

    NodePersistent child_old = null;
    NodePersistent child_copy = null;
    for (int pos = 0; pos <= x.length() - 1; pos++) {
      if(copy==null)copy = new NodePersistent();

      if (x.charAt(pos) == '0') {
        if(old == null){
        copy.children[0] = new NodePersistent();                    // Key always have to make new node along copy path
        copy.children[1] = null;
      }else if(old.children[0]==null){
          copy.children[0] = new NodePersistent();                 // Key always have to make new node along copy path
          copy.children[1] =  old.children[1];
        }else{
          copy.children[0] = new NodePersistent(1,0);//old.children[0];  // Key always have to make new node along copy path
          copy.children[1] = old.children[1];
        }
        child_copy = copy.children[0];
        if(old == null || old.children[0]==null)child_old = null;
        else child_old = old.children[0];
      }else {
        if(old == null){
          copy.children[0] = null;
          copy.children[1] = new NodePersistent();
        }else if(old.children[0]==null){
          copy.children[0] = old.children[0];
          copy.children[1] = new NodePersistent();
        }else{
          copy.children[0] = old.children[0];
          copy.children[1] = new NodePersistent(0, 1);//old.children[1];
        }
        child_copy = copy.children[1];
        if(old == null || old.children[1]==null)child_old = null;
        else child_old = old.children[1];
      }
      copy = child_copy;
      old = child_old;
    }
  }

  int getMinTrie(String x, NodePersistent root, int k)throws Exception {
    NodePersistent curr = root;
    long min = 0;
    for (int i = 0; i <= x.length() - 1; i++) {
      NodePersistent child;
      char inv = x.charAt(i) == '0' ? '1' : '0';
      NodePersistent cnt = curr.children[x.charAt(i)-'0'];
      if (cnt != null) {
        min = ( min << 1 ) + 0;
        child = curr.children[x.charAt(i)-'0'];
      } else {
        min = ( min << 1 ) + 1;
        child = curr.children[inv-'0'];
      }
      curr = child;
    }
    return (int)min;
  }

  int getMaxTrie(String x, NodePersistent root, int k)throws Exception {
    NodePersistent curr = root;
    long max = 0;
    for (int i = 0; i <= x.length() - 1; i++) {
      NodePersistent child;
      char inv = x.charAt(i) == '0' ? '1' : '0';
      NodePersistent cnt = curr.children[inv-'0'];
      if (cnt != null) {
        max = ( max << 1 ) + 1;
        child = curr.children[inv-'0'];
      } else {
        max = ( max << 1 ) + 0;
        child = curr.children[x.charAt(i)-'0'];
      }
      curr = child;
    }
    return (int)max;
  }
  void print_r(NodePersistent p)throws Exception {
    NodePersistent q = p;
    LinkedList<NodePersistent> queue = new LinkedList<NodePersistent>();
    queue.add(q); 
    while(!queue.isEmpty()){
      q = (NodePersistent)queue.removeFirst();
      out.write(""+q+" ");
      out.flush(); 
      if(q.children[0] != null)queue.add(q.children[0]);
      if(q.children[1] != null)queue.add(q.children[1]); 
    }
    out.write("\n");
    out.flush();
  }

  int hash(String s) {
    int base = 31;
    int a = 31;//base = number1 multiplier
    int mod = 100005;//range [0..100004]
    long val = 0;
    for (int i =  1 ; i <= s.length() ; i++) {
      val += base * s.charAt(i - 1);
      base = ( a * base ) % 100005;
    }
    return (int)(val % 100005) ;
  }

  boolean isPrime(long n) {
    if (n == 1)return false;
    if (n <= 3)return true;
    if (n % 2 == 0)return false;
    for (int i = 2 ; i <= Math.sqrt(n); i++) {
      if (n % i == 0)return false;
    }
    return true;
  }
// sieve
  int[] sieve(int n) {

    boolean isPrime[] = new boolean[n + 1];
    int p[] = new int[n + 1];
    int idx = 1;
    // Put above 3 variables globle p[1..idx-1]


    Arrays.fill(isPrime, true);
    isPrime[0] = isPrime[1] = false;
    for (int i = 2 ; i <= n ; i++) {
      if (isPrime[i]) {
        p[idx++] = i;
        for (int j  = 2 * i ; j <= n ; j += i ) {
          isPrime[j] = false;
        }

      }

    }
    return p;
  }
  long gcd(long a , long b) {
    if (b == 0)return a;
    return gcd(b , a % b);
  }
  long lcm(long a , long b) {
    if (a == 0 || b == 0)return 0;
    return (a * b) / gcd(a, b);
  }
  long mulmod(long a , long b , long mod) {
    if (a == 0 || b == 0)return 0;
    if (b == 1)return a;
    long ans = mulmod(a, b / 2, mod);
    ans = (ans * 2) % mod;
    if (b % 2 == 1)ans = (a + ans) % mod;
    return ans;
  }
  long pow(long a , long b , long mod) {
    if (b == 0)return 1;
    if (b == 1)return a;
    long ans = pow(a, b / 2, mod);
    ans = (ans * ans);
    if (ans >= mod )ans %= mod;

    if (b % 2 == 1)ans = (a * ans);
    if (ans >= mod )ans %= mod;

    return ans;
  }
  // 20*20   nCr Pascal Table
  long[][] ncrTable() {
    long ncr[][] = new long[21][21];
    for (int i = 0 ; i <= 20 ; i++){ncr[i][0] = 1; ncr[i][i] = 1;}

      for (int j = 0; j <= 20 ; j++) {
        for (int i = j + 1; i <= 20 ; i++) {
          ncr[i][j] = ncr[i - 1][j] + ncr[i - 1][j - 1];
        }
      }
      return ncr;
    }
//*******************************I/O******************************//
    int i()throws Exception {
  //return Integer.parseInt(br.readLine().trim());
      return in.nextInt();
    }
    int[] is(int n)throws Exception {
  //int arr[] = new int[n+1];
      for (int i = 1 ; i <= n ; i++)tempints[i] = in.nextInt();
        return tempints;
    }
    long l()throws Exception {
      return in.nextLong();
    }
    long[] ls(int n)throws Exception {
      for (int i = 1 ; i <= n ; i++)templongs[i] = in.nextLong();
        return templongs;
    }

    double d()throws Exception {
      return in.nextDouble();
    }
    double[] ds(int n)throws Exception {
      for (int i = 1 ; i <= n ; i++)tempdoubles[i] = in.nextDouble();
        return tempdoubles;
    }
    char c()throws Exception {
      return in.nextCharacter();
    }
    char[] cs(int n)throws Exception {
      for (int i = 1 ; i <= n ; i++)tempchars[i] = in.nextCharacter();
        return tempchars;
    }
    String s()throws Exception {
      return in.nextLine();
    }
    BigInteger bi()throws Exception {
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

  private void closeResources() {
    out.flush();
    out.close();
    return;
  }
  public static void main(String[] args) throws java.lang.Exception {
  //let_me_start Shinch Returns


  /*
      // Old Reader Writer
      BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
      BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
  */

    PersistentTrie driver = new PersistentTrie(true);
      long start =  System.currentTimeMillis();
      driver.run();
      long end =  System.currentTimeMillis();
      driver.closeResources();
      return ;

    }
  }

