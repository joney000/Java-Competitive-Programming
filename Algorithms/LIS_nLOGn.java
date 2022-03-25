package Algorithms;/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;
  // return LIS;

class LIS_nLOGn {
    static long ans = 0;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws Exception {


        ans = 0;
        int tests = Integer.parseInt(br.readLine());

        for (int t = 0; t < tests; t++) {

            int n = Integer.parseInt(br.readLine());
            int num[] = new int[n];
            String[] s = br.readLine().split(" ");

            for (int i = 0; i < n; i++) {
                num[i] = Integer.parseInt(s[i]);
            }
            long ans = findLIS(num);
            ans = (ans * (ans - 1) * (ans - 2)) / 6;
            out.write("" + ans + "\n");
            out.flush();
        }
    }

    public static int search(int[] M, int[] A, int i, int L) {
        int j = 0;
        int k = L - 1;
        while (j <= k) {
            int m = (j + k) / 2;
            if (A[M[m]] <= A[i]) j = m + 1;
            else k = m - 1;
        }

        return k;
    }

    public static int findLIS(int[] A) {
        int n = A.length;
        int[] M = new int[n];
        int[] P = new int[n];
        M[0] = 0;
        P[0] = -1;
        int L = 1;

        for (int i = 1; i < n; ++i) {
            int j = search(M, A, i, L);
            if (j == -1) P[i] = -1;
            else P[i] = M[j];

            if (j == L - 1 || A[i] < A[M[j + 1]]) {
                M[j + 1] = i;
                if (j + 2 > L) L = j + 2;
            }
        }

        int[] LIS = new int[L];
        n = L - 1;
        int p = M[n];
        while (n >= 0) {
            LIS[n] = A[p];
            p = P[p];
            n--;
        }
        return L;
    }

    //example BiTSet[] = { 2, 5, 3, 7, 11, 8, 10, 13, 6 };
    public static void PrintLis(int[] n) throws Exception {

        int[] li = new int[n.length];   // holds the lis numbers index 	(indirectly numbers of maximum lis sequence)
        int[] pi = new int[n.length];    // holds previous index of current maximum lis no
        int size = 0;                     //size of maximum lis

        li[0] = 0;
        pi[0] = -1;
        size = 1;

        for (int i = 1; i < n.length; i++) {

            if (n[i] < n[li[0]]) {

                li[0] = i;

            } else if (n[i] > li[size - 1]) {

                pi[i] = li[size - 1];
                li[size] = i;
                size++;

            }
        }
    }
}



















