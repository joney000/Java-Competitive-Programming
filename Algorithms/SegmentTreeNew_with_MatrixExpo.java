package Algorithms;

import Algorithms.InputReaderAndProcessor;

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
class SegmentTreeNewNode{
   //public int [] C = new int[3]; 
   public long mat[][] = new long[2][2];

  
   public SegmentTreeNewNode(){
   	this.mat[0][0] = this.mat[1][1] = 1L;
   }
  
}

/*    The Main Class                */
 class E{
	
	private InputStream inputStream ;
	private OutputStream outputStream ;
	private InputReaderAndProcessor in ;
    	private PrintWriter out ;
	/*
		Overhead [Additional Temporary Strorage] but provides memory reusibility for multiple test cases.
		 
	*/
	
	//Critical Size Limit : 10^5 + 4
	private final int BUFFER = 100005;
	private int    tempints[] = new int[BUFFER];
	private long   templongs[] = new long[BUFFER];
	private double tempdoubles[] = new double[BUFFER];
	private char   tempchars[] = new char[BUFFER];
	private final long mod = 1000000000+7;
	private final int  INF  = Integer.MAX_VALUE / 10;
	private final long INF_L  = Long.MAX_VALUE / 10;
	
	public E(){}
	public E(boolean stdIO)throws FileNotFoundException{
		//stdIO = false;
		if(stdIO){
			inputStream = System.in;
			outputStream = System.out;
		}else{
			inputStream = new FileInputStream("laundro_matt.txt");
			outputStream = new FileOutputStream("output.txt");
		}
		in = new InputReaderAndProcessor(inputStream);
		out = new PrintWriter(outputStream);
		
	}


  	void run()throws Exception{
	
	//	 int tests = i();
	//	 once();
	//	 for(int t  = 1 ; t<= tests ; t++){
		 	clear();
		 	int n = i();int q = i();
		 	int num[] = new int[n+1];
		 	for(int i=1;i<=n;i++){
	  			num[i]= i();
			}

		SegmentTreeNewNode[] m = new SegmentTreeNewNode[3*n+5000];//size ~ 2*n+1
			for(int i=1;i<= 3*n + 5000 -1 ;i++)m[i] = new SegmentTreeNewNode();
			
			maketree(1,1,n,m,num);
			
			for(int qq=1;qq<=q;qq++){
				char c = c();
				if(c=='C'){
					int idx = i();int x = i();
					num[idx] = x;	
					update(1,1,n,m,num,idx,idx);
				}else{
					int i = i(); int j = i();
					SegmentTreeNewNode res = query(1,1,n,m,num,i,j);
					long ans = res.mat[0][1];
					out.write(""+ans+"\n");				
				}
			}
			
	//	 	out.write("Case #"+t+": "+ans+"\n");	
		 	
	//	}//end tests
	}//end run
	void mulMat(long [][]ans ,long [][]p , long[][]q ){
   		
   		long var = 0;
   		for(int i=0;i<2;i++){
		    for(int j=0;j<2;j++){
			  for(int k=0;k<2;k++){
				var = (var + (p[i][k]*q[k][j]))%mod;
			  }
		     ans[i][j] = var;
		     var = 0;
		     }
		}
   		
 	}
 	void copy(long [][]ans ,long [][]p ){
   		
   		for(int i=0;i<2;i++){
		    for(int j=0;j<2;j++){
		    		ans[i][j] = p[i][j];
		     }
		}
   		
 	}
	void maketree(int node, int i, int j, SegmentTreeNewNode[] m, int []num){
		if(i==j){
		
		          copy(m[node].mat , expo(num[i]));
		          m[node].mat[0][0] = (m[node].mat[0][0] + 1)%mod;
		          m[node].mat[1][1] = (m[node].mat[1][1]  + 1)%mod;
			//    out.write("node no = "+node+" expo("+num[i]+") = "+"\n");
			//    printMat(m[node].mat);
			    return;
		}
	
		maketree(2*node,i,(i+j)/2,m,num);
		maketree(2*node+1,((i+j)/2)+1,j,m,num);

		mulMat(m[node].mat, m[2*node].mat , m[2*node + 1].mat);
		//out.write("node no = "+node+" range = "+i+" "+j+"\n");
		//printMat(m[node].mat);
	}

	SegmentTreeNewNode query(int node,int l,int r,SegmentTreeNewNode[] m,int []num,int i,int j){
		if(l>j||r<i||l>r)return null;  //invalid condition
		
		if(l>=i&&r<=j) { 
			return m[node];
		}

		SegmentTreeNewNode arr1 = query(2*node,l,(l+r)/2,m,num,i,j);
		SegmentTreeNewNode arr2 = query(2*node+1,((l+r)/2)+1,r,m,num,i,j);
		
		if(arr1==null)return arr2;
		if(arr2==null)return arr1;
		SegmentTreeNewNode arr = new SegmentTreeNewNode();
		//logic
		mulMat(arr.mat,arr1.mat , arr2.mat);
		return arr;
	}

	void update(int node,int l,int r,SegmentTreeNewNode[] m,int []num,int i,int j){
		if(l>j||r<i||l>r)return ;  //invalid condition
		
		if(l>=i&&r<=j) {
			 copy(m[node].mat , expo(num[i]));
			 m[node].mat[0][0] += 1;m[node].mat[1][1] += 1;
			 return;
		}
		
		update(2*node,l,(l+r)/2,m,num,i,j);
	      update(2*node+1,((l+r)/2)+1,r,m,num,i,j);
	     	
	     	mulMat(m[node].mat,m[2*node].mat , m[2*node + 1].mat);
		
     }
	void printMat(long Matrix[][]){
		for(int i = 0 ; i<2; i++){
			for(int j = 0 ; j < 2 ; j++){
				out.write(""+Matrix[i][j]+" ");
			}
			out.write("\n");
		}
	}
	
	void clear(){
		 
		 Matrix[0][0] = Matrix[0][1] = 1L;
		 Matrix[1][0] = 1L ; Matrix[1][1] = 0L;
		 Identity[0][0] = 1L;Identity[0][1] = 0L;
		 Identity[1][0] = 0L;Identity[1][1] = 1L;
   	 	 T[0][0] = T[0][1] = T[1][0] = T[1][1] = 0L;
   	 	 
	}
	
	
	long [][]Matrix = new long[2][2];
	long T [][] = new long [2][2] ;
   	long Identity [][] = new long [2][2] ;

	long[][] expo( long exp){
	   //iterative 
	   clear();
	   long var=0L;
		
	   while(exp>1){
			if(exp%2==0){
				for(int a=0;a<2;a++){
					for(int b=0;b<2;b++){
						for(int k=0;k<2;k++){
							var = (var + (Matrix[a][k]*Matrix[k][b]))%mod;
						}
						T[a][b] = var;
						var = 0;
					}
				}
				for(int i=0;i<2;i++){
					for(int j=0;j<2;j++){
						//coping items
						Matrix[i][j]=T[i][j];
						}
				}
				exp=exp/2;
			
			}else{
			
				for(int a=0;a<2;a++){
					for(int b=0;b<2;b++){
						for(int k=0;k<2;k++){
							var = ( var + (Identity[a][k]*Matrix[k][b] ) )%mod;
						}
					T[a][b] = var;
					var = 0;
					}
				}
			
				for(int i=0;i<2;i++){
					for(int j=0;j<2;j++){
						
						Identity[i][j] = T[i][j];
						
					}
					//out.write("\n");
				}
				//exp WAS ODD
				exp = exp-1;
			
			}
	
		 }
		var=0;
		for(int a=0;a<2;a++){
				for(int b=0;b<2;b++){
					for(int k=0;k<2;k++){
						var = (var + (Matrix[a][k]*Identity[k][b]))%mod;
					}
				T[a][b] = var;
				var = 0;
				}
	      }
            for(int a=0;a<2;a++){
			for(int b=0;b<2;b++){
				Matrix[a][b]=T[a][b];
			}
	   	}	   
	
		return Matrix;	 
	}
 	 
		 
//****************************** My Utilities ***********************//
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
    		 E driver = new E(true);
    		 long start =  System.currentTimeMillis();
    		 driver.run();
    		 long end =  System.currentTimeMillis();
    		 //out.write(" Total Time : "+(end - start)+"\n");
    		 driver.closeResources();
    		 return ;
    		 
	}	 

}

