/*
 * Author    : joney_000[let_me_start][jaswantsinghyadav007@gmail.com]
 * Algorithm : Disjoint Set Union O(log n) + path optimization
 * Platform  : Codeforces/Leetcode. eg. problem: https://leetcode.com/problems/satisfiability-of-equality-equations/
 */

class DSU {

    // Time: O(log n), Auxiliary Space: O(1)
    private int getRoot(int node, int[] parentOf){
        int root = node;
        // finding root
        while(root != parentOf[root]){
            root = parentOf[root];
        }
        // update chain for new parent
        while(node != parentOf[node]){
            int next = parentOf[node];
            parentOf[node] = root;
            node = next;
        }
        return root;
    }

	// Time: O(log n), Auxiliary Space: O(1)
    private void joinSet(int a, int b, int[] parentOf, int[] depth){
        int rootA = getRoot(a, parentOf);
        int rootB = getRoot(b, parentOf);
        if(rootA == rootB){
            return;
        }
        if(depth[rootA] >= depth[rootB]){
            depth[rootA] = Math.max(depth[rootA], 1 + depth[rootB]);
            parentOf[rootB] = rootA;
        }else{
            depth[rootB] = Math.max(depth[rootB], 1 + depth[rootA]);
            parentOf[rootA] = rootB;
        }
    }
	
    private void joinSets(String[] equations, int[] parentOf, int[] depth){
        for(String equation: equations){
            int var1 = equation.charAt(0) - 'a';
            int var2 = equation.charAt(3) - 'a';
            char not = equation.charAt(1);
            if(not == '='){
               joinSet(var1, var2, parentOf, depth);
            }
        }
    }   
	// Time: O(log n), Auxiliary Space: O(1), PS: In this problem you will need constant space
	// but in general you need to hold the info for each node's ancestors. that typically
	// leads to O(N) auxiliary space
    public boolean equationsPossible(String[] equations) {
        if(equations == null || equations.length <= 0){
            return true;
        }
        // disjoint sets
        int []parentOf = new int[26];
        int []depth = new int[26];
        for(int pos = 0; pos < 26; pos++){
            depth[pos] = 0;
            parentOf[pos] = pos;
        }
        joinSets(equations, parentOf, depth);
        for(String equation: equations){
            int var1 = equation.charAt(0) - 'a';
            int var2 = equation.charAt(3) - 'a';
            char not = equation.charAt(1);
            if(not == '!'){    
                if(var1 == var2){
                    return false;
                }
                if(getRoot(var1, parentOf) == getRoot(var2, parentOf)){
                    return false;
                }
            }
        }
        return true;
    }
}