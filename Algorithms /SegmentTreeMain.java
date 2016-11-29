/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
 class Node{
   //public int [] C = new int[3]; 
   public long T = 0;
      
   
   public int l = 0 , r = 0;
  
   public Node(){
   
   }
  
  }
 class F{
  
  public static long merge =0;
  public static void main(String[] args)throws Exception

    {  
	
	/*  BufferedReader br=new BufferedReader(new FileReader("input.txt"));
        BufferedWriter out=new BufferedWriter(new FileWriter("output.txt"));
    */
	BufferedReader br=new BufferedReader(new InputStreamReader(System.in),2000);
    BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out),2000);
	String[] s = br.readLine().split(" ");	
	int n = Integer.parseInt(s[0]);int q = Integer.parseInt(s[1]);
	String ss = br.readLine();
	int num[] = new int[n+1];
	for(int i=1;i<=ss.length();i++){
	  num[i]= (ss.charAt(i-1)-'0')%3;
	}
	 
	Node[] m = new Node[3*n+1];//size = 2*n+1
	for(int i=1;i<=3*n;i++)m[i] = new Node();
    
	maketree(1,1,n,m,num);
	
	for(int qq=1;qq<=q;qq++){
	s = br.readLine().split(" ");
    int sgn = Integer.parseInt(s[0]);	
	int i = Integer.parseInt(s[1]); int j=Integer.parseInt(s[2]);
	if(sgn==1){
	   num[i]=j%3;
	   
	   update(1,1,n,m,num,i,i);
	
	}else{ 
	  Node ans = query(1,1,n,m,num,i,j);
	   
	  long res = 0;
	  res = ans.L[0]+ans.M[0]+ans.R[0];
	  if(ans.T==0)res--;
	  //res-=merge;
	  out.write(""+res+"\n");out.flush();
	  //out.write("tot mer="+merge+"\n");
	  out.flush();
	}
	}
	
    }   
	public static void maketree(int node,int i,int j,Node[] m,int []num)throws Exception{
		if(i==j){
		
		        m[node].T = num[i];
			    m[node].R[num[i]]=1;m[node].L[num[i]]=1;
				//print(node,m);	
			 return;
		}
	
		maketree(2*node,i,(i+j)/2,m,num);
		maketree(2*node+1,((i+j)/2)+1,j,m,num);
		//logic
		for(int k=0;k<=2;k++)m[node].L[k]=m[2*node].L[k];
		for(int k=0;k<=2;k++)m[node].L[(int)((m[2*node].T + k)%3)] += m[2*node+1].L[k];
		//left updated  
		for(int k=0;k<=2;k++)m[node].R[k]=m[2*node+1].R[k];
		for(int k=0;k<=2;k++)m[node].R[(int)((m[2*node+1].T + k)%3)] += m[2*node].R[k];
		//right updated
		//m[node].Merge = m[2*node].Merge + m[2*node+1].Merge ;
		m[node].T=( m[2*node].T + m[2*node+1].T )%3;
		//if(m[node].T==0)m[node].Merge += 1;
		//total updated
		for(int k=0;k<=2;k++)m[node].M[k] = m[2*node].M[k] + m[2*node+1].M[k] ;
		for(int k=0;k<=2;k++){
			if(m[2*node].T==k)m[node].M[k] += Math.max(0,m[2*node].R[k]-1);
			 else m[node].M[k] += Math.max(0,m[2*node].R[k]);
			if(m[2*node+1].T==k)m[node].M[k] += Math.max(0,m[2*node+1].L[k]-1);
			 else m[node].M[k] += Math.max(0,m[2*node+1].L[k]);
		}
		
		for(int k=0;k<=2;k++)for(int s=0;s<=2;s++){
		    if(m[2*node].T==k&&m[2*node+1].T==s)
		      m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k]-1,0))*(Math.max(0,m[2*node+1].L[s]-1));
			else if(m[2*node].T!=k&&m[2*node+1].T==s) 
			  m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k],0))*(Math.max(0,m[2*node+1].L[s]-1));
			else if(m[2*node].T==k&&m[2*node+1].T!=s)
              m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k]-1,0))*(Math.max(0,m[2*node+1].L[s]));			
			else if(m[2*node].T!=k&&m[2*node+1].T!=s)
              m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k],0))*(Math.max(0,m[2*node+1].L[s]));				
		}
		//m[node].M[(int)((m[2*node].T + m[2*node+1].T)%3)] -= 1 ;
	//	print(node,m);
	}
	
	public static void print(int node,Node[] m)throws Exception{
	 System.out.println("printing node "+node);
	 System.out.println("T= "+m[node].T);
	 //System.out.println("Merge= "+m[node].Merge);
	 
	 for(int i=0;i<=2;i++){
	   System.out.println("L,M,R"+m[node].L[i]+" "+m[node].M[i]+" "+m[node].R[i]);
	 }
	}
	public static Node query(int node,int l,int r,Node[] m,int []num,int i,int j)throws Exception{
		if(l>j||r<i||l>r)return null;  //invalid condition
		//if(l>=i&&r<=j) return m[node].L[0]+m[node].M[0]+m[node].R[0]-1;
		if(l>=i&&r<=j) { return m[node]; }
		
		Node arr1 = query(2*node,l,(l+r)/2,m,num,i,j);
	    Node arr2 = query(2*node+1,((l+r)/2)+1,r,m,num,i,j);
		
		if(arr1==null)return arr2;
		if(arr2==null)return arr1;
		Node arr = new Node();
		//logic
		for(int k=0;k<=2;k++)arr.L[k]=arr1.L[k];
		for(int k=0;k<=2;k++)arr.L[(int)((arr1.T + k)%3)] += arr2.L[k];
		//left updated  
		for(int k=0;k<=2;k++)arr.R[k]=arr2.R[k];
		for(int k=0;k<=2;k++)arr.R[(int)((arr2.T + k)%3)] += arr1.R[k];
		//right updated
		
		arr.T=( arr1.T + arr2.T )%3;
		//total updated
		
		for(int k=0;k<=2;k++)arr.M[k] = arr1.M[k] + arr2.M[k] ;
		
		for(int k=0;k<=2;k++){
		       if(arr1.T==k)arr.M[k] += Math.max(0,arr1.R[k]-1);
			    else arr.M[k] += arr1.R[k];
			   if(arr2.T==k)arr.M[k]+= Math.max(0,arr2.L[k]-1);
			    else arr.M[k]+= arr2.L[k];
		}	   
		for(int k=0;k<=2;k++)for(int s=0;s<=2;s++){
				if(arr1.T==k&&arr2.T==s)
					arr.M[(int)((k+s)%3)] += (Math.max(0,arr1.R[k]-1))*(Math.max(0,(arr2.L[s]-1)));
				else if(arr1.T!=k&&arr2.T==s)
					arr.M[(int)((k+s)%3)] += (Math.max(0,arr1.R[k]))*(Math.max(0,(arr2.L[s]-1)));
				else if(arr1.T==k&&arr2.T!=s)
					arr.M[(int)((k+s)%3)] += (Math.max(0,arr1.R[k]-1))*(Math.max(0,(arr2.L[s])));
				else if(arr1.T!=k&&arr2.T!=s)
					arr.M[(int)((k+s)%3)] += (Math.max(0,arr1.R[k]))*(Math.max(0,(arr2.L[s])));
				
		}
		// arr.M[(int)((k+s)%3)] += arr1.R[k]*arr2.L[s];
	    //arr.M[(int)((arr1.T + arr2.T)%3)] -= 1 ;
		//print(node,m);
		
		return arr;
	}

	public static void update(int node,int l,int r,Node[] m,int []num,int i,int j)throws Exception{
		if(l>j||r<i||l>r)return ;  //invalid condition
		//if(l>=i&&r<=j) return m[node].L[0]+m[node].M[0]+m[node].R[0]-1;
		if(l>=i&&r<=j) {
		       for(int k=0;k<=2;k++){
				 m[node].L[k]=m[node].R[k]=m[node].M[k]=0;
		       }
		        m[node].T = num[i];
			    m[node].R[num[i]]=1;m[node].L[num[i]]=1;
				//print(node,m);	
			 return;
	
		}
		
		update(2*node,l,(l+r)/2,m,num,i,j);
	    update(2*node+1,((l+r)/2)+1,r,m,num,i,j);
	     
		//logic
		m[node].T=0;
		for(int k=0;k<=2;k++){
				m[node].L[k]=m[node].R[k]=m[node].M[k]=0;
		}
		for(int k=0;k<=2;k++)m[node].L[k]=m[2*node].L[k];
		for(int k=0;k<=2;k++)m[node].L[(int)((m[2*node].T + k)%3)] += m[2*node+1].L[k];
		//left updated  
		for(int k=0;k<=2;k++)m[node].R[k]=m[2*node+1].R[k];
		for(int k=0;k<=2;k++)m[node].R[(int)((m[2*node+1].T + k)%3)] += m[2*node].R[k];
		//right updated
		//m[node].Merge = m[2*node].Merge + m[2*node+1].Merge ;
		m[node].T=( m[2*node].T + m[2*node+1].T )%3;
		//if(m[node].T==0)m[node].Merge += 1;
		//total updated
		for(int k=0;k<=2;k++)m[node].M[k] = m[2*node].M[k] + m[2*node+1].M[k] ;
		for(int k=0;k<=2;k++){
			if(m[2*node].T==k)m[node].M[k] += Math.max(0,m[2*node].R[k]-1);
			 else m[node].M[k] += Math.max(0,m[2*node].R[k]);
			if(m[2*node+1].T==k)m[node].M[k] += Math.max(0,m[2*node+1].L[k]-1);
			 else m[node].M[k] += Math.max(0,m[2*node+1].L[k]);
		}
		
		for(int k=0;k<=2;k++)for(int s=0;s<=2;s++){
		    if(m[2*node].T==k&&m[2*node+1].T==s)
		      m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k]-1,0))*(Math.max(0,m[2*node+1].L[s]-1));
			else if(m[2*node].T!=k&&m[2*node+1].T==s) 
			  m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k],0))*(Math.max(0,m[2*node+1].L[s]-1));
			else if(m[2*node].T==k&&m[2*node+1].T!=s)
              m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k]-1,0))*(Math.max(0,m[2*node+1].L[s]));			
			else if(m[2*node].T!=k&&m[2*node+1].T!=s)
              m[node].M[(int)((k+s)%3)] += (Math.max(m[2*node].R[k],0))*(Math.max(0,m[2*node+1].L[s]));				
		}
		//m[node].M[(int)((m[2*node].T + m[2*node+1].T)%3)] -= 1 ;
		//print(node,m);
   }
	public static boolean isPrime(long n)throws Exception{
		if(n==1)return false;
		if(n==2||n==3)return true;
		for(int i=2;i<=Math.sqrt(n);i++){
			if(n%i==0)return false;
		}
	return true;
	}
	public static long gcd(long a, long b)throws Exception{
		if(b==0)return a;
		return gcd(b,a%b);
	}
	public static long lcm(long a, long b)throws Exception{
		if(b==0||a==0)return 0;
		return (a*b)/gcd(a,b);
	}
	public static long pow(long a,long b,long mod)throws Exception{
		if(b==1)return a%mod;
		if(b==0)return 1;
		long ans=pow(a,b/2,mod);
		ans=(ans*ans)%mod;
		if(b%2!=0)ans=(ans*a)%mod;
	
		return ans;
	}
 } //end of class        
        
