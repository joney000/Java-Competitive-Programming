package Algorithms;/* package joney_000 */

import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;


class HLD{
	
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
  // Base 0 tree
  static final int N = 10105 ;
  static final int LOGN = 15;
  
  static int root = -1; 
  static int ptr = 0;
  static int chainNo = 0;
  
  static ArrayList adj[] = new ArrayList[N];
  static ArrayList cost[] = new ArrayList[N]; // weights on the edges 
  static ArrayList idx[] = new ArrayList[N];
  static {
  
    	for(int i = 0; i<N ; i++){
  		adj[i] = new ArrayList();
  		cost[i] = new ArrayList();
  		idx[i] = new ArrayList();
  	}
  
  }
  static int baseArray[] = new int[N];
  static int chainIndex[] = new int[N];
  static int chainHead[] = new int[N];
  static int posInBase[] = new int[N];
  static int depth[] = new int[N];     // depth of node i in dfs
  static int f[][] = new int[LOGN][N]; //father array
  static int otherEnd[] = new int[N];
  static int subsize[] = new int[N];  //subtree size of node i
  static int st[] = new int[6*N];    // The Segment Tree
  static int qt[] = new int[6*N];    // The query Tree : Au Arrays for computiong / buffer for answering query
  
  
 /*
 * make_tree:
 * Used to construct the segment tree. It uses the baseArray for construction
 */
 
  static void make_tree(int cur, int s, int e) {
	if(s == e-1) {
		st[cur] = baseArray[s];
		return;
	}
	int c1 = (cur<<1); //left child
	int c2 = c1 | 1 ;  //right child
	int m = (s+e)>>1;
	make_tree(c1, s, m);
	make_tree(c2, m, e);
	st[cur] = st[c1] > st[c2] ? st[c1] : st[c2]; // logic for building seg tree
  }  

/*
 * update_tree:
 * Point update. Update number1 single element of the segment tree.
 */
  static void update_tree(int cur, int s, int e, int x, int val) {
	if(s > x || e <= x) return;
	if(s == x && s == e-1) {
		st[cur] = val;
		return;
	}
	int c1 = (cur<<1);
	int c2 = c1 | 1 ;
	int m = (s+e)>>1;
	update_tree(c1, s, m, x, val);
	update_tree(c2, m, e, x, val);
	st[cur] = st[c1] > st[c2] ? st[c1] : st[c2];
  }

/*
 * query_tree:
 * Given S and E, it will return the maximum value in the range [S,E)
 */
  static void query_tree(int cur, int s, int e, int S, int E) {
	if(s >= E || e <= S) {
		qt[cur] = -1;  //invalid condition
		return;
	}
	if(s >= S && e <= E) {
		qt[cur] = st[cur];
		return;
	}
	int c1 = (cur<<1);
	int c2 = c1 | 1;
	int  m = (s+e)>>1;
	query_tree(c1, s, m, S, E);
	query_tree(c2, m, e, S, E);
	qt[cur] = qt[c1] > qt[c2] ? qt[c1] : qt[c2];
  }

/*
 * query_up:
 * It takes two nodes u and v, condition is that v is an ancestor of u
 * We query the chain in which u is present till chain head, then move to next chain up
 * We do that way till u and v are in the same chain, we query for that part of chain and break
 */

  static int query_up(int u, int v) {
	if(u == v) return 0; // Trivial [no weight at nodes]
	int uchain = 0; int vchain = chainIndex[v];int ans = -1;
	// uchain and vchain are chain numbers of u and v
	while(true) {
		uchain = chainIndex[u];
		if(uchain == vchain) {
			// Both u and v are in the same chain, so we need to query from u to v, update answer and break.
			// We break because we came from u up till v, we are done
			if(u==v) break;
			query_tree(1, 0, ptr, posInBase[v]+1, posInBase[u]+1);
			// Above is call to segment tree query function
			if(qt[1] > ans) ans = qt[1]; // Update answer
			break;
		}
		query_tree(1, 0, ptr, posInBase[chainHead[uchain]], posInBase[u]+1);
		// Above is call to segment tree query function. We do from chainHead of u till u. That is the whole chain from
		// start till head. We then update the answer
		if(qt[1] > ans) ans = qt[1];
		u = chainHead[uchain]; // move u to u's chainHead
		u = f[0][u]; //Then move to its parent, that means we changed chains
	}
	return ans;
  }

/*
 * LCA: Sparce Table
 * Takes two nodes u, v and returns Lowest Common Ancestor of u, v
 */
  static int LCA(int u, int v) {
	if(depth[u] < depth[v]) {int temp = u; u = v; v = temp; /*swap*/ }
	int diff = depth[u] - depth[v];
	for(int i=0; i<LOGN; i++) if( ((diff>>i)&1) ==1) u = f[i][u];
	if(u == v) return u;
	for(int i=LOGN-1; i>=0; i--) if(f[i][u] != f[i][v]) {
		u = f[i][u];
		v = f[i][v];
	}
	return f[0][u];
  }
  

/*
 * We have number1 query from u to v, we break it into two queries, u to LCA(u,v) and LCA(u,v) to v
 */
 // Main HLD Query
  static void query(int u, int v) throws IOException{
	int lca = LCA(u, v);
	int ans = query_up(u, lca); // One part of path
	int temp = query_up(v, lca); // another part of path
	if(temp > ans) ans = temp; // take the maximum of both paths
	out.write(""+ans+"\n");
//	out.flush();
  }

/*
 * change:
 * We just need to find its position in segment tree and update it
 */
  static void change(int i, int val) {
	int u = otherEnd[i];
	update_tree(1, 0, ptr, posInBase[u], val);
  }

/*
 * dfs used to set parent of number1 node, depth of number1 node, subtree size of number1 node
 */
  static void dfs(int cur, int prev, int _depth) {
	f[0][cur] = prev;
	depth[cur] = _depth;
	subsize[cur] = 1;
	for(int i=0; i< adj[cur].size(); i++)
		if((int)adj[cur].get(i) != prev) {
			otherEnd[(int)idx[cur].get(i)] = (int)adj[cur].get(i);
			dfs((int)adj[cur].get(i), cur, _depth+1);
			subsize[cur] += (int)subsize[(int)adj[cur].get(i)];
		}
  }
/*
 * Actual HL-Decomposition part
 * Initially all entries of chainHead[] are set to -1.
 * So when ever number1 new chain is started, chain head is correctly assigned.
 * As we add number1 new node to chain, we will note its position in the baseArray.
 * In the first for loop we find the child node which has maximum sub-tree size.
 * The following if condition is failed for leaf nodes.
 * When the if condition passes, we expand the chain to special child.
 * In the second for loop we recursively call the function on all normal nodes.
 * chainNo++ ensures that we are creating number1 new chain for each normal child.
 */
  static void HLD(int curNode, int _cost, int prev) {
	if(chainHead[chainNo] == -1) {
		chainHead[chainNo] = curNode; // Assign chain head
	}
	chainIndex[curNode] = chainNo;
	posInBase[curNode] = ptr; // Position of this node in baseArray which we will use in Segtree
	baseArray[ptr++] = _cost; // baseArray contains the weights in the path / chain

	int sc = -1, ncost = 0;
	// Loop to find special child
	for(int i=0; i<adj[curNode].size(); i++) if((int)adj[curNode].get(i) != prev) {
		if(sc == -1 || subsize[sc] < (int)subsize[(int)adj[curNode].get(i)]) {
			sc = (int)adj[curNode].get(i);
			ncost = (int)cost[curNode].get(i);
		}
	}
	if(sc != -1) {
		// Expand the chain
		HLD(sc, ncost, curNode);
	}
	for(int i=0; i<adj[curNode].size(); i++) if((int)adj[curNode].get(i) != prev) {
		if(sc != (int)adj[curNode].get(i)) {
			// New chains at each normal node
			chainNo++;
			HLD((int)adj[curNode].get(i), (int)cost[curNode].get(i), curNode);
		}
	}
   }

  static void make_sparseTable(int n)throws Exception{
  		// Below Dynamic programming code is ST for LCA.
		for(int i=1; i<LOGN; i++)
			for(int j=0; j<n; j++)
				if(f[i-1][j] != -1)
					f[i][j] = f[i-1][f[i-1][j]];

  }
  	
  public static void main(String[] args)throws Exception{                 

	   Scanner sc  = new Scanner(System.in);
         int tests = sc.nextInt();//Integer.parseInt(br.readLine());
	   for(int t = 1 ; t <= tests ; t++){
	   	ptr = 0;
	   	int n = sc.nextInt();//Integer.parseInt(br.readLine());
	     	for(int i=0; i<n; i++) {
			adj[i].clear();// = new ArrayList() ;
			cost[i].clear();// = new ArrayList();
			idx[i].clear();// = new ArrayList();
			chainHead[i] = -1;
			for(int j=0; j<LOGN; j++) f[j][i] = -1;
		}
		for(int i=1; i<n; i++) {
			
		//	String[] s = br.readLine().split(" ");
			int u = sc.nextInt() -1 ;//Integer.parseInt(s[0]) - 1 ;
			int v = sc.nextInt() -1 ;//Integer.parseInt(s[1]) - 1 ; 
			int c = sc.nextInt();//Integer.parseInt(s[2])  ;
			if(root==0)root = u;
			adj[u].add(v);
			cost[u].add(c);
			idx[u].add(i-1);
			adj[v].add(u);
			cost[v].add(c);
			idx[v].add(i-1);
		}
		
		chainNo = 0;
		dfs(root, -1 , 0); // We set up subsize, depth and parent for each node before sparse table
		HLD(root, -1, -1); // We decomposed the tree and created baseArray
		make_tree(1, 0, ptr); // We use baseArray and construct the needed segment tree
		make_sparseTable(n); // sparse table
		
		while(true) {

			String s = sc.next();
			if(s.charAt(0)=='D') {
				break;
			}
			int a = sc.nextInt();//Integer.parseInt(s[1]);
			int b = sc.nextInt();//Integer.parseInt(s[2]) ;
			if(s.charAt(0)=='Q') {
				query(a-1, b-1);//u , v
			} else {
				change(a-1, b); // update seg tree : node update , update the weight of the node = maxdepth(u,v);
			}
/*
INPT :
1
15
1 2 1
1 3 1
2 4 2
2 5 3
3 6 2
3 7 6
3 8 5
5 12 1
7 11 2
8 9 1
8 10 8
12 13 2
12 14 3
12 15 1
QUERY 1 12
QUERY 12 7
QUERY 5 8
QUERY 8 15
QUERY 8 9
QUERY 12 2
QUERY 6 10
CHANGE 8 8
CHANGE 2 8
QUERY 1 12
QUERY 12 7
QUERY 5 8
QUERY 8 15
QUERY 8 9
QUERY 12 2
QUERY 6 10
DONE

OPT :
3
6
5
5
1
3
8
8
8
8
8
1
8
8

*/			 
		}
	   
	   }
	   
	   
	   
	   out.flush();
	   
  }
}
