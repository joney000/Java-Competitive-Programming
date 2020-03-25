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
 * Ref       : Hash Functions, Fast Data Backups, Distributed Systems, 
 */

class ConsistentHashDataNode <T> {
	T data;
	public ConsistentHashDataNode(T data){
		this.data = data;
	}

	@Override
	public boolean equals(Object obj){
		return this.data.equals((T)obj);
	}

	@Override
	public int hashCode(){
		return this.data.hashCode();
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

class ConsistentHashing <K, V> {

    private TreeMap<Long, V> circle;
    private HashMap<V, List<String>> nodeListMap;
    private int noOfAliasForEachServer;

    public ConsistentHashing(int noOfAliasForEachServer){
    	this.noOfAliasForEachServer = noOfAliasForEachServer;
    	circle = new TreeMap<Long, V>();
    	nodeListMap = new HashMap<V, List<String>>();
    }

    void put(String key, V value){
    	Long hash = getHash(key);
    	circle.put(hash, value);
    }

    V remove(String key){
    	if(circle.containsKey(key)){
    		return circle.remove(key);
    	}
    	return null;
    }

 	void addServer(K key, V value){
 		put(key.toString(), value);
 		for(int replicaId = 0; replicaId < noOfAliasForEachServer; replicaId++){
 			String keyStr = key.toString() + " replica ~ "+replicaId;
  		put(keyStr, value);
  		nodeListMap.get(value).add(keyStr);
 		}
 	}
  
    void removeServer(K key){
        remove(key.toString());
        for(int replicaId = 0; replicaId < noOfAliasForEachServer; replicaId++){
            String keyStr = key.toString() + " replica ~ "+replicaId;
            remove(keyStr);
        }
	}

    V getServerNode(String val) {
        Long hashing = getHash(val);
        SortedMap<Long, V> tail = circle.tailMap(hashing);
        return circle.get(tail.size() == 0 ? circle.firstKey() : tail.firstKey());
    }

	public static void main(String ... args){
		try{
			ConsistentHashing<String, ConsistentHashDataNode<String>> cHash = new ConsistentHashing<>(5);
			List <ConsistentHashDataNode<String>> servers = new LinkedList<>();
			
            for(int serverId = 0; serverId < 4; serverId++){
				ConsistentHashDataNode<String> newServer = new Server<String>("server-id-"+serverId,
                    "109.105.110.5" + serverId,
                    "India",
                    "server-metadata : id = " + serverId + ", region : IN/Asia");
				servers.add(newServer);
				cHash.addServer(newServer.data, newServer);	// Adding new server to circle
			}

			List <ConsistentHashDataNode<String>> data = new LinkedList<>();
			for(int dataNodeId = 0; dataNodeId < 50; dataNodeId++){
				data.add(new ConsistentHashDataNode<String>("data-node-"+dataNodeId));
			}

		}catch(RuntimeException ex){
			System.err.println("Computing Failed Stacktrace: " + ex.toString());
		}
	}

    /**
    * Credit: MurmurHash from SMHasher written by Austin Appleby
    * Ref   : https://en.wikipedia.org/wiki/MurmurHash
    */
    public Long getHash(String key){
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


