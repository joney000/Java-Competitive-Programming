# Java-Competitive-Programming

In This Repository, I have written some of the important Algorithms and Data Structures efficiently in Java with proper references to time and space complexity. These Pre-cooked and well-tested codes helps to implement larger hackathon problems in lesser time.

## Algorithms:
| Algorithm        | Big-O Time, Big-O Space           | Comments/Symbols  |
| :----------- |:-------------:| :----- |
| [DFS - 2-D Grid](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/DFS_Grid.java) | O(M * N), O(M * N) | M * N = dimensions of matrix
| [DFS - Adjency List](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/DFSAdjacencyList.java) | O(V + E), O(V + E) | V = No of vertices in Graph, E = No of edges in Graph
| [BFS - 2-D Grid](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/BFS_GRID.java) | O(M * N), O(M * N)|  M * N = dimensions of matrix
| [BFS - Adjency List](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/BFSAndLCA.java)| O(V + E), O(V + E) | V = No of vertices in Graph,  E = No of edges in Graph
| [LCA - Lowest Common Ancestor](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/BFSAndLCA.java)| O(V), O(V)
| [LCA - Using Seg Tree](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/LCA.java)| O(log V), O(V + E)| Using Euler tour and Segment Tree, preprocessing/building tree = O(N) &  Each Query = O(log N)
| [All Pair Shortest Path](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/AllPairShortestPath.java)  |  O(V^3), O(V + E) | Floyd Warshall algorithm
| [Longest Common Subsequence](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/LCS.java) | O(M * N), O(M * N)   | Finding LCS of N & M length string using Dynamic Programming
| [Binary Search](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/BinarySearch.java)| O(log(N), O(N)  | Search in N size sorted array
| [Lower Bound Search](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/lower_bound%20_%20upper_bound.java) | O(log(N), O(1)  | Unlike HashSetComparator, Java Doesn't Provide Lower Bound, Upper Bound for already sorted elements in the Collections
| [Upper Bound Search](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/lower_bound%20_%20upper_bound.java) | O(log(N), O(1)  | 
| [Maximal Matching](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Matching.java) | O(√V x E), O(V + E)  | Maximum matching for bipartite graph using Hopcroft-Karp algorithm 
| [Minimum Cost Maximal Matching - Hungarian algorithm](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/HungarianAlgorithm-MinCost-Maximal-Matching.java) | O(N^3), O(N^2)
| [Matrix Exponentiation](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/MatrixExpo.java) | O(N^3 * log(X)) ,O(M * N) | Exponentiation by squaring / divide and conquer MATRIX[N, N] ^ X
| [Segment Tree](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/SegmentTreeNew_with_MatrixExpo.java)  | O(Q * log(N)), O(N)  | Q = no of queries , N = no of nodes , per query time = O(log N)
| [Segment Tree Lazy Propagation](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/SegmentTreeLazyProp.java)|  O(Q * log(N)), O(N) |Q = no of queries , N = no of nodes , tree construction time = O(N), per query time = O(log N), range update time: O(log N)
| [Sparse Table](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/SparseTable.java)  | O(Q * O(1) + N * log(N)), O(N * log(N))  | per query time = O(1) and precompute time and space: O(N * log(N))
| [Merge Sort](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/MergeSort.java)| O(N * log(N)), O(N)  | divide and conquer algorithm
| [Miller Prime Test](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Miller-prime-test.java) | soft-O notation Õ((log n)^4) with constant space
| [Kruskal- Minimum Spanning Tree](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/MST-prims.java) | O(E log V) , O(V + E) |
| [BIT - Binary Index Tree / Fenwick Tree](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/BIT.java)| O(log N), O(N)  |  per query time: O(log(N))
| [Two Pointers](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/twopointer.java) | O(N), O(N) | two directional scan on sorted array
| [Binary Search Tree - BST](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/bst.java)| O(N), O(N) | 
| [Maximum Subarray Sum](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/MxxSubArraySum.java)| O(N), O(N) |  Kadane's algorithm
| [Immutable Data Structures, Persistent Data Structurs -  Persistent Trie](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/PersistantTrie-Immutable-DS.java)| O(N * log N), O(N)| query & update time: O(log N). It's frequently used where you have to maintain multiple version of your data structure typically in lograthimic time.
| [Dijkstra](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Dijkstra.java) | O((E+v) log V)), O(V + E)
| [Z - Function](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Zee_StringMatching.java) | O(P + T), O(P + T) | Leaner time string matching / occurrence finding of pattern string P into Large Text string T.
| [Heavy Light Decomposition](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/HLD_Edeges.java) | O(N * log^2 N), O(N)| per query time = (log N)^2
| [Interval Merge](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Interval%20Merge.java)| O(log N), O(N)
| [Knapsack](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Knapsack.java)| O(N * S), (N * S) | N = elements, S = MAX_Sum
| [Suffix Array and LCP - Longest Common Prefix](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/SuffixArray%2CHashingSeive.java)| O(N * log(N)), O(N)
| [Squre Root Decomposition](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/SqureRootDecomposition.java)| O(N * √N), O(N) | the range of N number can be divided in √N blocks
| [Kth Order Statics](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/kthOrderStatics.java)|O(N), O(N) | K’th Smallest/Largest Element in Unsorted Array
| [Trie / Prefix Tree](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/Trie.java)| O(N * L), O(N * L)| if there are N strings of L size, per query time(Prefix information) = O(L) 
| [LIS - Longest Increasing Subsequence](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/LIS_nLOGn.java)| O(N * log(N)), O(N)
| [Priority Queue](https://github.com/joney000/Java-Competitive-Programming/blob/master/Algorithms/PriorityQueue.java)| O(log(N)), O(N) | N = no of objects in the queue. peek: O(1), poll/add: O(log n)

## Contributions

Want to contribute in corrections or enhancement? Great!
Please raise number1 PR, or drop number1 mail at developer.jaswant@gmail.com .

## I also highly recommed to read [Introduction to Algorithms(CLRS book)](https://en.wikipedia.org/wiki/Introduction_to_Algorithms) and same algorithm implementation from other authors, it will give you diverse set of ideas to solve same algorithmic challenges.
