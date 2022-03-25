package Algorithms;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : Treap [As Kth Element] Tree + heap & Rope data structure
 * Platform  : Codeforces
 * Ref       : https://threads-iiith.quora.com/Treaps-One-Tree-to-Rule-em-all-Part-1
 *             https://www.youtube.com/watch?v=erKlLEXLKyY 
 *             https://threads-iiith.quora.com/Treaps-One-Tree-to-Rule-em-all-Part-2     
 */

class Treap {

  static Random random = new Random();
  int key;
  long prio;
  Treap left;
  Treap right;
  int count;

  Treap(int key) {
    this.key = key;
    this.prio = random.nextLong();;
    count = 1;
  }
}

class TreapPair {
  Treap left;
  Treap right;

  TreapPair(Treap left, Treap right) {
    this.left = left;
    this.right = right;
  }
}

class TreapMain{

  static Random random = new Random();
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

  public TreapMain(){}
  public TreapMain(boolean stdIO)throws FileNotFoundException{
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
    Treap treap = null;
    Set<Integer> set = new TreeSet<Integer>();
    for (int i = 0; i < 100000; i++) {
      int x = random.nextInt(100000);
      if (random.nextBoolean()) {
        treap = remove(treap, x);
        set.remove(x);
      } else if (!set.contains(x)) {
        treap = insert(treap, x);
        set.add(x);
      }
      if (set.size() != getCount(treap))
        throw new RuntimeException();
    }

    print(treap);
  }
  
  void clear(){

  }
  
  void updateCount(Treap root) {
      root.count = 1 + getCount(root.left) + getCount(root.right);
  }

  int getCount(Treap root) {
    return root == null ? 0 : root.count;
  }

  TreapPair split(Treap root, int minRight){
    if(root == null)return new TreapPair(null, null);
    if(root.key >= minRight){
      TreapPair leftSplit = split(root.left, minRight);
      root.left = leftSplit.right;
      updateCount(root);
      leftSplit.right = root;
      return leftSplit;
    }else{
      TreapPair rightSplit = split(root.right, minRight);
      root.right = rightSplit.left;
      updateCount(root);
      rightSplit.left = root;
      return rightSplit;
    }
  }

  Treap merge(Treap left, Treap right){
    if(left == null)
      return right;
    if(right == null)
      return left;
    if(left.prio > right.prio){
      left.right = merge(left.right, right);
      updateCount(left);
      return left;
    }else{
      right.left = merge(left, right.left);
      updateCount(right);
      return right;
    }
  }

  Treap insert(Treap root, int x){
    TreapPair t = split(root, x);
    return merge(merge(t.left, new Treap(x)), t.right);
  }

  Treap remove(Treap root, int x){
    if(root == null){
      return null;
    }
    if(x < root.key){
      root.left = remove(root.left, x);
      updateCount(root);
      return root;
    }else if(x > root.key){
      root.right = remove(root.right, x);
      updateCount(root);
      return root;
    }else{
      return merge(root.left, root.right);
    }
  }

  int kth(Treap root, int k){
    if(k < getCount(root.left))
      return kth(root.left, k);
    else if(k > getCount(root.left))
      return kth(root.right, k - getCount(root.left) - 1);
    return root.key;
  }

  void print(Treap root){
    if(root == null)return;
    print(root.left);
    System.out.println(root.key);
    print(root.right);
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

    TreapMain driver = new TreapMain(true);
    driver.run();
    driver.closeResources();
  }
}
