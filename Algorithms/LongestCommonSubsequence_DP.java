
     
    import java.util.*;
    import java.lang.*;
    import java.io.*;
     
    /* Name of the class has to be "Main" only if the class is public. */
    class Ideone
    {
    static int lcs(String s1,String s2)
    {
    int n=s1.length();
    int m=s2.length();
    int LCS[][] = new int[n+1][m+1];
     
    for(int i=0; i<=n; i++)
    LCS[i][0] = 0;
    for(int j=0; j<=m; j++)
    LCS[0][j] = 0;
     
    for(int i=1; i<n+1; i++)
    for(int j=1; j<m+1; j++)
    {
    if(s1.charAt(i-1)==s2.charAt(j-1))
    LCS[i][j] = 1 + LCS[i-1][j-1];
    else
    LCS[i][j] = Math.max(LCS[i][j-1],LCS[i-1][j]);
    }
    return LCS[n][m];
    }
    public static void main (String[] args) throws java.lang.Exception
    {
    BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
    String s1 = br.readLine();
    String s2 = br.readLine();
    int res= lcs(s1,s2);
    System.out.println("length of LCS is : "+res);
    }
    }