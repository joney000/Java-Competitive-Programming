package Algorithms;

class EularTotient {
  /*
  * compute the eular totient(https://en.wikipedia.org/wiki/Euler%27s_totient_function)
  *   of the given numer, speeding up the computation when we have prime numbers 
  * 
  * time complexity: O(k log n) where k = no of prime factors
  * this method is advantagious when we have large numbers whose prime factors
  * are small numbers eg. eular totient of 16'402'500'000 = 2^5 * 3^8 * 5^7 can be computed
  * in only 3 steps.
  */
  private long totient(long n , long primes[]){ 
    long result = n;    
    for(int i=0; primes[i] <= n; i++) 
    {
      if(n < primes[i])
        break;
      if(n % primes[i] == 0)
        result -= result / primes[i]; 
      while (n % primes[i] == 0)
        n /= primes[i];
    }
    if(n > 1)
      result -= result / n; 
    return result; 
  }
}
