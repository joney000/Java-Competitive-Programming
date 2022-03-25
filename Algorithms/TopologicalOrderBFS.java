package Algorithms;// Time Complexity : O(N) and Space O(N) Heap space,
// Advantage over stack approach: handle cycle detection

import java.util.LinkedList;

class TopologicalOrderBfs {
    public boolean isCycle(int numCourses, int[][] prerequisites) {
        if(numCourses <= 1)return true;
        try{
            
            int d[] = new int[numCourses];
	   				// Graph as Adj List
						LinkedList<Integer>[] adj = new LinkedList[numCourses];
	  				for(int vertex = 0; vertex < numCourses; vertex ++){
							adj[vertex] = new LinkedList<Integer>();
						}

            for(int[] edge: prerequisites){
                d[edge[1]]++;
                adj[edge[0]].add(edge[1]); // directed
            }
	   				LinkedList<Integer> queue = new LinkedList<>();
            for(int vertex = 0; vertex < numCourses; vertex ++){
							if(d[vertex] == 0){
								queue.add(vertex);
							}
						}
				    LinkedList<Integer> topologicalOrder = new LinkedList<>();
				    while(!queue.isEmpty()){
							int parent = (Integer)queue.removeFirst();
							topologicalOrder.add(parent);
							for(int child: adj[parent]){
								d[child]--;
								if(d[child] == 0){
									queue.addLast(child);
								}
							}
						}
						return topologicalOrder.size() == numCourses;
        }catch(Exception exc){
            // Logger.logException to centralized monitoring system
        	   return false;
				}
    }
}

