import java.util.HashMap;

/* @author: jaswant developer.jaswant@gmail.com
 * @algorithm: hashing
 * @use: holding frequency map, similar to multiset in c++
*/
class MultiSet<K> {
	private HashMap<K, Integer> multiSet = new HashMap<K, Integer>();

	int get(K key){
		return multiSet.getOrDefault(key, 0);
	}

	void add(K key){
		multiSet.put(key, get(key)+ 1);
	}

	void remove(K key){
		int freq = get(key);
		if(freq == 1){
			multiSet.remove(key);
		}else{
			multiSet.put(key, freq - 1);
		}
	}
  
	@Override
	public String toString(){
		return multiSet.toString();
	}
}
