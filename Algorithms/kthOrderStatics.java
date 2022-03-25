package Algorithms;//pakage joney_000[let_me_start]
 
import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
 /******************** Main Class ***********************/

 class OrderStatitics
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
	
public static void main(String[] args) throws java.lang.Exception{
		//let_me_start 
	int arr[] = new int[500000+5];
	int temp[] = new int[500000+5];
	int end = 0; int st = 1;  //n = end-st +1 ;
	//for(int t=1;t<=tests;t++){
	  int n = i(); 
	  for(int k=1;k<=n;k++){
	   int q = i();
	   if(q==0){
			  int x = i();
			  end++;
			  arr[end]=x;
	   }else if(q==1){
	       int l=i();int r=i(); int x = i();
		   
	       int max=-1;
		   int xor = -1;
		   for(int i=l;i<=r;i++){
				int tempx = arr[i]^x;
				if(tempx>xor){
				  xor = tempx;
				  max = arr[i];
				}
		   }
		   out.write(""+max+"\n");
		   
	   }else if(q==2){
	      int kk = i();
		  end = end-kk;
	   }else if(q==3){
	       int l=i();int r=i(); int x = i();
		   int cnt =0;
		   for(int i=l;i<=r;i++){
				///int tempx = arr[i]^x;
				if(arr[i]<=x){
				  cnt++;
				}
		   }
		   out.write(""+cnt+"\n");
		   
	   }else {
	      
	        int l=i(); int r = i(); int kk = i();
		  for(int i=l;i<=r;i++){
		    temp[i-l] = arr[i];
		  }
		  int ans = kth(0,r-l,kk,temp);
		  out.write(""+ans+"\n");
		  
	   }
	  
	  }
	  
	  out.flush();
	
    }   
public static int kth(int l,int r,int k,int[] arr)throws Exception{
		
		 // If k is smaller than number of elements in array
    if (k > 0 && k <= r - l + 1)
    {
        int pos = rand(arr, l, r);
        if (pos-l == k-1)return arr[pos];
        if (pos-l > k-1)return kth(l, pos-1, k,arr);
        return kth(pos+1, r, k-pos+l-1,arr);
    }
    return -10000000;
	}
	public static int rand(int arr[], int l, int r)throws Exception
	{
    int n = r-l+1;
    int pivot = ((int)(Math.random()*1000000)) % n;
    swap(arr,l + pivot,r);
    return part(arr, l, r);
	}
	public static void swap(int arr[], int a, int b)throws Exception{
	  int temp = arr[a];
	  arr[a]= arr[b];
	  arr[b]=temp;
	}
	public static int part(int arr[], int l, int r)throws Exception{
    int x = arr[r], i = l;
    for (int j = l; j <= r - 1; j++)
    {
        if (arr[j] <= x)
        {
            swap(arr,i,j);
            i++;
        }
    }
    swap(arr,i,r);
    return i;
}
 
	public static int maketree(int node,int i,int j,int[] m,int []num)throws Exception{
		if(i==j){m[node]=num[i];return m[node];}
	
		int g1 = maketree(2*node,i,(i+j)/2,m,num);
		int g2 = maketree(2*node+1,((i+j)/2)+1,j,m,num);
	
		m[node] = gcd(g1,g2);
		return m[node];
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
  for(int i=1;i<=Math.sqrt(n);i++){
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