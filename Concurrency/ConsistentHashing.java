import java.util.TreeMap;
import java.util.SortedMap;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
/**
 * Author    : joney_000[ developer.jaswant@gmail.com ]
 * Algorithm : Consistent Hashing Circle
 * Platform  : Generic Distributed Cache Data Nodes, Databases [eg. Memcached ]
 * Ref       : Hash Functions, Fast Data Backups, Distributed Systems
 */

class ConsistentHashDataNode <T> {
	T data;
	public ConsistentHashDataNode(T data){
		this.data = data;
	}
}

class Server{
	String id, ip, contry;
	public Server(String id, String ip, String contry){
		this.id = id;
		this.ip = ip;
		this.contry = contry;	
	}
}

class ConsistentHashing <T> {

	private TreeMap<Long, T> nodeMap;
  private Map<T, List<String>> nodeListMap;
  private int noOfAliasForEachServer;
  
	public static void main(String ... args){
		try{
			List <Server> servers = new LinkedList<>();
			for(int i = 0; i < 4; i++)servers.add(new Server("server-id-"+i, "109.105.110.5"+i, "India"));

			List <ConsistentHashDataNode<Integer>> data = new LinkedList<>();
			for(int i = 0; i < 50; i++)data.add(new ConsistentHashDataNode<Integer>(i));
			
			ConsistentHashing cHash = new ConsistentHashing();


		}catch(RuntimeException ex){
			System.err.println("Runtime Exception Stacktrace: " + ex.toString());
		}
	}
 










  /**
   * Credit: MurmurHash from SMHasher written by Austin Appleby
   * Ref   : https://en.wikipedia.org/wiki/MurmurHash
   */
  public Long hash(String key){
    ByteBuffer buf = ByteBuffer.wrap(key.getBytes());
    int seed = 0x1234ABCD;
    ByteOrder byteOrder = buf.order();
    buf.order(ByteOrder.LITTLE_ENDIAN);
    long m = 0xc6a4a7935bd1e995L;
    int r  = 47;
    long h = seed ^ (buf.remaining() * m);
    long k;
    while(buf.remaining() >= 8){
      k = buf.getLong();
      k *= m;
      k ^= k >>> r;
      k *= m;

      h ^= k;
      h *= m;
    }
    if(buf.remaining() > 0){
      ByteBuffer finish = ByteBuffer.allocate(8).order(
          											ByteOrder.LITTLE_ENDIAN);
      // for big-endian version, do this first:
      // finish.position(8-buf.remaining());
      finish.put(buf).rewind();
      h ^= finish.getLong();
      h *= m;
    }
    h ^= h >>> r;
    h *= m;
    h ^= h >>> r;
    buf.order(byteOrder);
    return h;
  }	
}


