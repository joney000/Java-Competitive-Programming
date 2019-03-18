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

class Server<T> extends ConsistentHashDataNode<T>{
	String id, ip, contry;
	public Server(String id, String ip, String contry, T serverMetaData){
		super(serverMetaData);
		this.id = id;
		this.ip = ip;
		this.contry = contry;
	}
}

class ConsistentHashing <T> {

	private TreeMap<Long, T> circle;
  private Map<T, List<String>> nodeListMap;
  private int noOfAliasForEachServer;

  public ConsistentHashing(int noOfAliasForEachServer){
  	this.noOfAliasForEachServer = noOfAliasForEachServer;
  	circle = new TreeMap<Long, T>();
  	nodeListMap = new HashMap<T, List<String>>();
  }

  void put(T key, ConsistentHashDataNode value){

  }

  void putAll(List<ConsistentHashDataNode<T>> dataNodes){
  	for(ConsistentHashDataNode<T> dataNode: dataNodes){
  		// put(server.data, server);
  	}
  }
	public static void main(String ... args){
		try{
			ConsistentHashing<ConsistentHashDataNode<String>> cHash = new ConsistentHashing<>(5);

			List <ConsistentHashDataNode<String>> servers = new LinkedList<>();
			for(int i = 0; i < 4; i++){
				servers.add(new Server<String>("server-id-"+i, "109.105.110.5"+i, "India", "server-metadata : id: "+i+" , region : IN/Asia"));
			}

			List <ConsistentHashDataNode<String>> data = new LinkedList<>();
			for(int i = 0; i < 50; i++){
				data.add(new ConsistentHashDataNode<String>("data-node-"+i));
			}
			
			


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


