    /* Miller-Rabin primality test, iteration signifies the accuracy of the test */
    // test number if p is prime or not using 'iterations'.
    boolean Miller(long p,int iteration){
        long LOWER_RANGE = 1; //assign lower range value
        long UPPER_RANGE = p-1; //assign upper range value
        Random randomGenerator = new Random();
        if(p<2){
            return false;
        }
        if(p!=2 && p%2==0){
            return false;
        }
         long s=p-1;
        while(s%2==0){
            s/=2;
        }
        for(int i=0;i<iteration;i++){
            long randomValue = LOWER_RANGE + (long)(randomGenerator.nextDouble()*(UPPER_RANGE - LOWER_RANGE));
             long a = randomValue %(p-1)+1,temp=s;
             long mod= pow(a,temp,p);
            while(temp!=p-1 && mod!=1 && mod!=p-1){
                mod = pow(mod,mod,p);
                temp *= 2;
            }
            if(mod!=p-1 && temp%2==0){
                return false;
            }
        }
        return true;
    }
    // sieve

    // Better Optioin : BigInteger is Probable prime
import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    
    static long next(long x) {
        if (x == 0) {
            return 0;
        }
        long y = x / 10;
        long y1 = next(y + (x % 10 > 7 ? 1 : 0));
        if (y != y1) {
            return 10 * y1 + 2;
        }
        return y1 * 10 + (x % 10 <= 2 ? 2 : x % 10 <= 3 ? 3 : x % 10 <= 5 ? 5 : 7);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long first = in.nextLong();
        long last = in.nextLong();
        long ans = 0;
        first = next(first);
        while (first <= last) {
            //System.err.println(first);
            if (BigInteger.valueOf(first).isProbablePrime(20)) {    // 20 is rounds
                ans++;
            }
            first = next(first + 1);
        }
        System.out.println(ans);
    }
}
