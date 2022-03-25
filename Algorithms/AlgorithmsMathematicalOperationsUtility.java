package Algorithms;

public class AlgorithmsMathematicalOperationsUtility {
    public long gcd(long a, long b){
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
        ans = mulMod(ans, ans, mod);
        if(ans >= mod)ans %= mod;

        if(b % 2 == 1)ans = mulMod(a, ans, mod);
        if(ans >= mod)ans %= mod;

        return ans;
    }

}
