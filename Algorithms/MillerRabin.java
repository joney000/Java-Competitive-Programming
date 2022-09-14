/**
 * Author    : joney_000 [developer.jaswant@gmail.com]
 * Algorithm : Miller Rabin
 * Platform  : Codejam
 * Ref       : https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test#Testing_against_small_sets_of_bases
 */

class MillerRabin {

  /* @input: `number` for which we need to check weather it is prime or not
   * @description: this is the deterministic varient of the miller ribbin
  */
  boolean isPrime(long number){
    if(number < 2){
      return false;
    }
    int smallPrimes[] = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    for(int prime : smallPrimes){
      if(number % prime == 0){
        return number == prime;
      }
    }
    int trailingZeros = Long.numberOfTrailingZeros(number - 1);
    long power = (number - 1) >> trailingZeros;
    long bases[] = {2, 7, 61}; 
    // sufficient for number < 4,759,123,141
    // we dont need to test all the base a < 2(ln number)2
    for(long base: bases){
      long exp = pow(base % number, power, number);
      if(exp <= 1 || exp != number - 1){
        continue;
      }
      for(int i = 0; i < trailingZeros - 1 && exp != number - 1; i++){
        exp = (exp * exp) % number; // warning: integer overflow, use mulMod in case of int\long overflow
      }
      if(exp != number - 1){
        return false;
      }
    }
    return true;
  }

  long pow(long a, long b, long mod){
    if(b == 0)
      return 1;
    if(b == 1)
      return a % mod;
    long ans = pow(a, b/2, mod);
    ans = (ans * ans) % mod; 
    // mulMod(ans, ans, mod); use when ans^2 does int or long overflow. 
    // this will perform multiplication using divide and conquer
    if(b % 2 == 1)
      ans = (a * ans) % mod; // warning: integer overflow
    return ans;
  }
}