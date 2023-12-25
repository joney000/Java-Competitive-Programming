class Pair {
    public int a;
    public int b;

    public Pair(int a, int b){
        this.a = a;
        this.b = b;
    }
	
	@Override
	public boolean equals(Object pair) {
		if (pair == null) {
		return false;
		}
		Pair otherPair = (Pair)pair;
		if (!(otherPair instanceof Pair)) {
		return false;
		}
		if (this.a == otherPair.a && this.b == otherPair.b){
		return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		long hash = 31;
		long mod = 1000000009;
		hash = (hash + 97 * this.a) % mod; 
		hash = 31 * hash +  97 * this.b;
		hash %= mod;
		return (int)hash;
	}
}
