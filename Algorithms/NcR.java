import java.io.*;
import java.util.*;
import java.lang.*;

class DPnCr{

	public static void main(String[] ss)throws Exception{
	
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter out=new BufferedWriter(new OutputStreamWriter(System.out));
		
		int tests=Integer.parseInt(br.readLine());
		for(int t=0;t<tests;t++){
		
			int n=Integer.parseInt(br.readLine());
			int[] num=new int[n];
			String[] s=br.readLine().split(" ");
			for(int i=0;i<n;i++){
					num[i]=Integer.parseInt(s[i]);			
			}
			long [][] c=new long[20+1][20+1];
			//n=20;c(20,r)
			for(int i=0;i<=20;i++){c[i][i]=1;c[i][0]=c[0][i]=0;c[i][1]=i;}
			for(int j=2;j<20;j++){
					for(int i=j+1;i<=20;i++){
						c[i][j]=c[i-1][j]+c[i-1][j-1];
					}
			}
			for(int i=0;i<n;i++){out.write(""+c[20][num[i]]+"\n");out.flush();}
			
		}
		
	}

}
