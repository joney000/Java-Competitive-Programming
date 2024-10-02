/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : Disjoint Set Union O(log n) + path optimization
 * Platform  : Codeforces/Leetcode. eg. problem: https://leetcode.com/problems/satisfiability-of-equality-equations/
 */
class DSU {
    private int[] parentOf;
    private int[] depth;
    private int size;

    public DSU(int size) {
        this.size = size;
        this.depth = new int[size + 1];
        this.parentOf = new int[size + 1];
        clear(size);
    }
    
    // reset 
    public void clear(int range) {
        this.size = range;
        for (int pos = 1; pos <= range; pos++) {
            depth[pos] = 0;
            parentOf[pos] = pos;
        }
    }

    // Time: O(log n), Auxiliary Space: O(1)
    int getRoot(int node) {
        int root = node;
        // finding root
        while (root != parentOf[root]) {
            root = parentOf[root];
        }
        // update chain for new parent
        while (node != parentOf[node]) {
            int next = parentOf[node];
            parentOf[node] = root;
            node = next;
        }
        return root;
    }

    // Time: O(log n), Auxiliary Space: O(1)
    void joinSet(int a, int b) {
        int rootA = getRoot(a);
        int rootB = getRoot(b);
        if (rootA == rootB) {
            return;
        }
        if (depth[rootA] >= depth[rootB]) {
            depth[rootA] = Math.max(depth[rootA], 1 + depth[rootB]);
            parentOf[rootB] = rootA;
        } else {
            depth[rootB] = Math.max(depth[rootB], 1 + depth[rootA]);
            parentOf[rootA] = rootB;
        }
    }

    int getNoOfTrees() {
        int uniqueRoots = 0;
        for (int pos = 1; pos <= size; pos++) {
            if (pos == getRoot(pos)) {
                uniqueRoots++;// root
            }
        }
        return uniqueRoots;
    }
}
