    package Algorithms;//pakage joney_000[let_me_start]

    import java.util.*;
    import java.lang.*;
    import java.io.*;
    import java.math.*;

    import static Algorithms.LIS_nLOGn.ans;
    /*
     * Author    : joney_000[let_me_start]
     * Algorithm : Not Specified
     * Platform  : CodeForces
     */

     /*                The Main Class                */
     class HashSetComparator
    {
        public static InputStream inputStream = System.in;
        public static OutputStream outputStream = System.out;
        public static InputReaderAndProcessor in = new InputReaderAndProcessor(inputStream);;
        public static PrintWriter out = new PrintWriter(outputStream);;
        /*
        Overhead [Additional Temporary Strorage]
        */
        public static int tempints[] = new int[100005];
        public static long templongs[] = new long[100005];
        public static double tempdoubles[] = new double[100005];
        public static char tempchars[] = new char[100005];
        public static long mod = 1000000000+7;

        public static void main(String[] args) throws java.lang.Exception {
                //let_me_start
        /*  BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
            BufferedReader br=new BufferedReader(new FileReader("input.txt"));
            BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
        */

                int tests = i();
                HashMap<NumberComparator, NumberComparator> hm1 = null;
                HashSet<NumberComparator> hx = null;
                HashSet<NumberComparator> hy = null;
                for (int t = 1; t <= tests; t++) {
                    int n = i();
                    hm1 = new HashMap<NumberComparator, NumberComparator>();
                    hx = new HashSet<NumberComparator>();
                    hy = new HashSet<NumberComparator>();
                    int a = 0, b = 0, c = 0, aa = 0, bb = 0;
                    int ans = 0, g = 0;

                    hy.add(new NumberComparator(a, c));
                    NumberComparator I = hm1.get(new NumberComparator(aa, bb));
                    if (I == null) {
                        NumberComparator p = new NumberComparator(aa, bb);
                        if (c == 0) g = 1;
                        hm1.put(p, new NumberComparator(c, g));
                    } else {
                        if (c == 0) g = 1;
                        hm1.put(I, new NumberComparator(c, g));
                    }


                /*	Integer I = hm1.get(new NumberComparator(number1  ,c ));
                    if(I == null){
                        hm2.put(new NumberComparator(number1 , c) ,1);
                    }else{
                        hm2.put(new NumberComparator(number1 , c) ,I.intValue()+1);
                    }
                */
                }
                //	  out.write("hx = "+hx.size()+" hy = "+hy.size()+"ans = "+ans+"\n");
                //  Iterator it = hm1.entrySet().iterator();
                ans = Math.max(hx.size(), hy.size());
               /*
               while (it.hasNext()) {
                       NumberComparator pair = (NumberComparator)it.next();

                 ans = Math.max(ans , (pair.s.size()));
               }
               */

                Iterator it = hm1.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    //		 out.write(""+(NumberComparator)pair.getKey()+" set size="+((NumberComparator)pair.getKey()).s.size()+"\n");
                    ans = Math.max(ans, ((NumberComparator) pair.getKey()).b);
                }
                out.write("" + ans + "\n");
                out.flush();
                return;
        }
    //****************************** Utilities ***********************//

     public static boolean isPrime(long n)throws Exception{
      if(n==1)return false;
      if(n<=3)return true;
      if(n%2==0)return false;
      for(int i=2 ;i <= Math.sqrt(n); i++){
       if(n%i==0)return false;
      }
      return true;
     }
     // sieve
     public static int[] primes(int n)throws Exception{       // for(int i=1;i<=arr.length-1;i++)out.write(""+arr[i]+" ");
      boolean arr[] = new boolean[n+1];
      Arrays.fill(arr,true);
      arr[1]=false;
      for(int i=2;i<=Math.sqrt(n);i++){
        if(!arr[i])continue;
        for(int j = 2*i ;j<=n;j+=i){
            arr[i]=false;
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
     public static int gcd (int a , int b)throws Exception{
      if(b==0)return a;
      return gcd(b , a%b);
     }
     public static long gcd (long a , long b)throws Exception{
      if(b==0)return a;
      return gcd(b , a%b);
     }
     public static long lcm (long a , long b)throws Exception{
      if(a==0||b==0)return 0;
      return (a*b)/gcd(a,b);
     }
     public static long mulmod(long a , long b ,long mod)throws Exception{
       if(a==0||b==0)return 0;
       if(b==1)return a;
       long ans = mulmod(a,b/2,mod);
       ans = (ans*2)% mod;
       if(b%2==1)ans = (a + ans)% mod;
       return ans;
     }
     public static long pow(long a , long b ,long mod)throws Exception{
       if(b==0)return 1;
       if(b==1)return a;
       long ans = pow(a,b/2,mod);
       ans = (ans * ans)% mod;
       if(b%2==1)ans = (a * ans)% mod;
       return ans;
     }
     // 20*20   nCr Pascal Table
     public static long[][] ncrTable()throws Exception{
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
    public static int i()throws Exception{
     //return Integer.parseInt(br.readLine().trim());
     return in.nextInt();
    }
    public static int[] is(int n)throws Exception{
      //int arr[] = new int[n+1];
      for(int i=1 ; i <= n ;i++)tempints[i] = in.nextInt();
     return tempints;
    }
    public static long l()throws Exception{
     return in.nextLong();
    }
    public static long[] ls(int n)throws Exception{
      for(int i=1 ; i <= n ;i++)templongs[i] = in.nextLong();
     return templongs;
    }

    public static double d()throws Exception{
     return in.nextDouble();
    }
    public static double[] ds(int n)throws Exception{
      for(int i=1 ; i <= n ;i++)tempdoubles[i] = in.nextDouble();
     return tempdoubles;
    }
    public static char c()throws Exception{
     return in.nextCharacter();
    }
    public static char[] cs(int n)throws Exception{
      for(int i=1 ; i <= n ;i++)tempchars[i] = in.nextCharacter();
     return tempchars;
    }
    public static String s()throws Exception{
     return in.nextLine();
    }
    public static BigInteger bi()throws Exception{
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
    }

