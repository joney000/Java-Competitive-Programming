import java.util.LinkedList;
/**
* Time: O(N log N + Q * log N), each query is answered in log N time. Space: O(N log N)
* Use:
* Your BinaryLifting object will be instantiated and called as such:
* BinaryLifting obj = new BinaryLifting(n, parent);
* int param_1 = obj.getKthAncestor(node,k);
* ref: https://leetcode.com/problems/kth-ancestor-of-a-tree-node/ and https://www.youtube.com/watch?v=oib-XsjFa-M
*/
class BinaryLifting {
  // preprocess
  // O(N log N)
  // precompute the answer for power of 2
  private int[][] atLevel; // atLevel[nodeId][level] means what is the predecessor at 2^level higher
  private int MAX_LOG = 0;
  boolean vis[];
  public BinaryLifting(int n, int[] parent) {
      MAX_LOG = 0;
      vis = new boolean[n];
      while(n >= (1 << MAX_LOG)){
          MAX_LOG++;
      }
      atLevel = new int[n][MAX_LOG];
      for(int nodeId = 0; nodeId < n; nodeId++){
          for(int level = 0; level < MAX_LOG; level++){
              atLevel[nodeId][level] = -1;
          }
      }
      for(int nodeId = 1; nodeId <= n - 1; nodeId++){
          if(vis[nodeId])continue;
          LinkedList<Integer> unVisited = new LinkedList<Integer>(); // linked list as a stack for unvisited node
          int currentNode = nodeId;
          while(currentNode != -1 && !vis[currentNode]){
              unVisited.addLast(currentNode);
              currentNode = parent[currentNode];
          }
          while(!unVisited.isEmpty()){
              int topUnvisitedNode = unVisited.removeLast();
              atLevel[topUnvisitedNode][0] = parent[topUnvisitedNode];
              for(int level = 1; level <= MAX_LOG - 1; level++){
                  if(atLevel[topUnvisitedNode][level - 1] != -1){
                      atLevel[topUnvisitedNode][level] = atLevel[atLevel[topUnvisitedNode][level - 1]][level - 1];
                  }else{
                      break;
                  }
              }
              vis[topUnvisitedNode] = true;
          }
      }
  }
  
  public int getKthAncestor(int node, int k) {
      int kthAncestor = node;
      for(int level = MAX_LOG - 1; level >= 0; level--){ // at ancestor at 2^level
          if((k & (1 << level)) > 0){ // check if ith bit is set
              // every numer can be represented by sum of power of 2
              kthAncestor = atLevel[kthAncestor][level];
              if(kthAncestor == -1){
                  break;
              }
          }
      }
      return kthAncestor;
  }
}

