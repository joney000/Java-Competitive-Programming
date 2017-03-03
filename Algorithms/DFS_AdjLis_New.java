

import io.FastReader;
import java.io.PrintWriter;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.math.*;

class A {
       FastReader in ;
       PrintWriter out;
       void set(FastReader in , PrintWriter out){
                this.in = in;
                this.out = out;
        }
        private final int BUFFER = 200005;
        private int    []tempints = new int[BUFFER];
        private long   []templongs = new long[BUFFER];
        private double []tempdoubles = new double[BUFFER];
        private char   []tempchars = new char[BUFFER];
        private final long mod = 1000000000+7;
        private final int  INF  = Integer.MAX_VALUE / 10;
        private final long INF_L  = Long.MAX_VALUE / 10;
        boolean ans = false;
        int a = 0;
        int b = 0;
        HashMap<Integer, Pair> hm = new HashMap<Integer, Pair>();
        long tot = 0;
        void solve() {
            set(in,out);
            int n = i();
            LinkedList <Integer> adj1[] = new LinkedList[n+1];
            LinkedList <Integer> adj2[] = new LinkedList[n+1];

            for(int i = 1 ; i <= n ; i++){
                adj1[i]= new LinkedList<>();
                adj2[i] = new LinkedList<>();
            }
            int root = 0;
            for(int i = 1 ; i <= n ; i++){
                int p = i(); int q = i();
                if(p !=0) {
                    adj1[i].add(p);
                    adj1[p].add(i);
                    adj2[i].add(p);
                    adj2[p].add(i);
                }else{
                    root = i;
                }
                c[i] = q;
                tot += q;
            }
            dfs1(adj1, root, n);
            clear(n);
            dfs2(adj2, root, n);
            if(ans && (a!=0 || b !=0)){
                out.write(""+a+" "+b+"\n");
            }else{
                out.write("-1\n");
            }
         //   out.write(""+ans+"");
           // out.write(""+a+" "+b+"\n");
        }
        void once(){

        }
        void clear(int n){
            for(int i = 1; i <= n; i++)vis[i] = false;
         //   for(int i = 1; i <= n; i++)out.write("c="+c[i]+"\n");
        }
        int []c = new int[1000005];
        boolean []vis = new boolean[1000005];
        int []f = new int[1000005];
        void dfs1(LinkedList<Integer> adj[] ,int root, int n){

            LinkedList <Integer> q = new LinkedList<Integer>(); //the stack
            int l = 0;//level

            q.add(root);
            vis[root]=true;
            f[root] = 0;
            while(!q.isEmpty()){

                int u = q.getLast(); //top
             //   level[u]=l;

                if(adj[u].size()>0){
                    int v = adj[u].removeFirst();
                    if(!vis[v]){
                        q.add(v);
                        l++;
                        vis[v]=true;
                        f[v] = u;
                    }

                }else{

                    int v = q.removeLast();
                    c[f[v]] += c[v];    // cum subtree sum
                    l--;
                }
            }
        }

        void dfs2(LinkedList<Integer> adj[] ,int root, int n){

            LinkedList <Integer> q = new LinkedList<Integer>(); //the stack
            int l = 0;//level

            q.add(root);
            vis[root]=true;
//            Pair p = (Pair)hm.get(c[root]);
//            if(p == null || p.a == 0){
//                //
//            }else{
//                p.a -= 1;
//                p.b.removeFirst();
//                hm.put(c[root], p);
//            }
            while(!q.isEmpty()){

                int u = q.getLast(); //top
                //   level[u]=l;

                if(adj[u].size()>0){
                    int v = adj[u].removeFirst();
                    if(!vis[v]){
                        q.add(v);
                        l++;
                        vis[v]=true;
                        Pair p = (Pair)hm.get(c[v]);
                        if(p == null || p.a == 0){
                            //
                        }else{
                            p.a -= 1;
                            p.b.removeFirst();
                            hm.put(c[v], p);
                        }

                    }

                }else{

                    int v = q.removeLast();
                    l--;
                    Pair p = (Pair)hm.get(c[v]);
                    if(p == null || p.a == 0){
                        p = new Pair();
                        p.a = 1;
                        p.b = new LinkedList<>();
                        p.b.add(v);
                    }else{
                        if((tot - 2 * c[v] ) == c[v]){
                            ans = true;
                            a = v;
                            b = (Integer) p.b.getFirst();
                        }
                        p.a += 1;
                        p.b.add(v);
                        hm.put(c[v], p);
                    }
                }

            }
        }

        //****************************** My Utilities ***********************//
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
        int i(){
            //return Integer.parseInt(br.readLine().trim());
            return in.nextInt();
        }
        int[] is(int n){
            //int arr[] = new int[n+1];
            for(int i=1 ; i <= n ;i++)tempints[i] = in.nextInt();
            return tempints;
        }
        long l(){
            return in.nextLong();
        }
        long[] ls(int n){
            for(int i=1 ; i <= n ;i++)templongs[i] = in.nextLong();
            return templongs;
        }

        double d(){
            return in.nextDouble();
        }
        double[] ds(int n){
            for(int i=1 ; i <= n ;i++)tempdoubles[i] = in.nextDouble();
            return tempdoubles;
        }
        char c(){
            return in.nextCharacter();
        }
        char[] cs(int n){
            for(int i=1 ; i <= n ;i++)tempchars[i] = in.nextCharacter();
            return tempchars;
        }
        String s(){
            return in.nextLine();
        }
        BigInteger bi(){
            return in.nextBigInteger();
        }



}
class Pair implements Comparable<Pair>{
    public int a;
    public LinkedList<Integer> b;
    public Pair(){
        this.a = 0;
        this.b = new LinkedList<>();
    }
    public Pair(int a,LinkedList<Integer> b){
        this.a = a;
        this.b = b;
    }
    public int compareTo(Pair p){
        if(this.a==p.a){
            return this.b.size()-p.b.size();
        }
        return this.a-p.a;
    }
    public String toString(){
        return "a="+this.a+" b="+this.b;
    }

}