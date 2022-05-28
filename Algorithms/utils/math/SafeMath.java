package utils.math;
/**
 *  math utility for basic operation preventing integer\long datatype range overflow.
 * */
public final class SafeMath {
  
  // finding gcd using euclidean algorithm
  // https://en.wikipedia.org/wiki/Euclidean_algorithm
  public static long gcd(long a, long b){
    if(b == 0)return a;
    return gcd(b, a % b);
  }
  
  public static long lcm(long a, long b){
    if(a == 0 || b == 0)return 0;
    return (a * b)/gcd(a, b);
  }
  
  // computes a*b % mod  but using associative property i.e.
  // a*b % c = ((a%c) * (b%c))%c
  // using devide and conquer to avoid long range overflow
  public static long multiplyWithMod(long a, long b, long mod){
    if(a == 0 || b == 0)
      return 0;
    if(b == 1)
      return a;
    long ans = multiplyWithMod(a, b/2, mod);
    ans = (ans * 2) % mod;
    if(b % 2 == 1)
      ans = (a + ans)% mod;
    return ans;
  }
  
  // computes base^exponent % mod
  // using devide and conquer to avoid long range overflow
  public static long power(long base, long exponent, long mod){
    if(exponent == 0)
      return 1;
    if(exponent == 1)
      return base;
    long ans = power(base, exponent/2, mod);
    ans = multiplyWithMod(ans, ans, mod);
    if(ans >= mod)
      ans %= mod;
    if(exponent % 2 == 1)
      ans = multiplyWithMod(base, ans, mod);
    if(ans >= mod)
      ans %= mod;
    return ans;
  }
  
  // precompute 20*20 fixed size nCr Pascal Table
  static final int MAX_PASCAL_NUMBER = 21;
  public static long[][] ncrTable(){
    long ncr[][] = new long[MAX_PASCAL_NUMBER][MAX_PASCAL_NUMBER];
    for(int i = 0; i < MAX_PASCAL_NUMBER; i++){
      ncr[i][0] = ncr[i][i] = 1L;
    }
    for(int j = 0; j < MAX_PASCAL_NUMBER; j++){
      for(int i = j + 1; i < MAX_PASCAL_NUMBER; i++){
        ncr[i][j] = ncr[i-1][j] + ncr[i-1][j-1];
      }
    }
    return ncr;
  }
}
