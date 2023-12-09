import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.*;

/*
 * Author    : joney_000[developer.jaswant@gmail.com]
 * Algorithm : Treap [As Kth Element] Tree + heap & Rope data structure
 * Platform  : Codeforces
 * Ref       : https://threads-iiith.quora.com/Treaps-One-Tree-to-Rule-em-all-Part-1
 *             https://www.youtube.com/watch?v=erKlLEXLKyY 
 *             https://threads-iiith.quora.com/Treaps-One-Tree-to-Rule-em-all-Part-2     
 */

class TreapNode {

  static Random random = new Random();
  int key;
  long prio;
  TreapNode left;
  TreapNode right;
  int count;

  TreapNode(int key) {
    this.key = key;
    this.prio = random.nextLong();;
    count = 1;
  }
}

class TreapPair {
  TreapNode left;
  TreapNode right;

  TreapPair(TreapNode left, TreapNode right) {
    this.left = left;
    this.right = right;
  }
}

public static class TreapOperations {

  public static void updateCount(TreapNode root) {
      root.count = 1 + getCount(root.left) + getCount(root.right);
  }

  public static int getCount(TreapNode root) {
    return root == null ? 0 : root.count;
  }

  public static TreapPair split(TreapNode root, int minRight){
    if(root == null)return new TreapPair(null, null);
    if(root.key >= minRight) {
      TreapPair leftSplit = split(root.left, minRight);
      root.left = leftSplit.right;
      updateCount(root);
      leftSplit.right = root;
      return leftSplit;
    } else {
      TreapPair rightSplit = split(root.right, minRight);
      root.right = rightSplit.left;
      updateCount(root);
      rightSplit.left = root;
      return rightSplit;
    }
  }

  public static TreapNode merge(TreapNode left, TreapNode right){
    if(left == null)
      return right;
    if(right == null)
      return left;
    if(left.prio > right.prio){
      left.right = merge(left.right, right);
      updateCount(left);
      return left;
    }else{
      right.left = merge(left, right.left);
      updateCount(right);
      return right;
    }
  }

  public static TreapNode insert(TreapNode root, int x){
    TreapPair t = split(root, x);
    return merge(merge(t.left, new TreapNode(x)), t.right);
  }

  public static TreapNode remove(TreapNode root, int x){
    if(root == null){
      return null;
    }
    if(x < root.key){
      root.left = remove(root.left, x);
      updateCount(root);
      return root;
    }else if(x > root.key){
      root.right = remove(root.right, x);
      updateCount(root);
      return root;
    }else{
      return merge(root.left, root.right);
    }
  }

  public static int kth(TreapNode root, int k){
    if(k < getCount(root.left))
      return kth(root.left, k);
    else if(k > getCount(root.left))
      return kth(root.right, k - getCount(root.left) - 1);
    return root.key;
  }

  public static void printTree(TreapNode root){
    if(root == null)return;
    printTree(root.left);
    System.out.println(root.key);
    printTree(root.right);
  }
}

class TreapTest { 
  public static void main(String ...args){
    TreapNode rootNode = null;
    Random random = new Random();
    Set<Integer> set = new TreeSet<Integer>();
    for (int i = 0; i < 100000; i++) {
      int x = random.nextInt(100000);
      if (random.nextBoolean()) {
        rootNode = TreapOperations.remove(rootNode, x);
        set.remove(x);
      } else if (!set.contains(x)) {
        rootNode = TreapOperations.insert(rootNode, x);
        set.add(x);
      }
      if (set.size() != TreapOperations.getCount(rootNode))
        throw new RuntimeException();
    }

    TreapOperations.printTree(rootNode);
  }
}