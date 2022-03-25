package Algorithms;

import java.util.PriorityQueue;

class PriorityQueueSolution {

    class Point{
        int x, y;
        int distance;
        public Point(int x, int y){
            this.x = x;
            this.y = y;
            this.distance = x *  x + y  * y;
        }
        
    }

    // returns the K Closest points from origin (0, 0)
    // Time: O(n log k), space: O(k)
    public int[][] kClosest(int[][] points, int k) {
        if(points.length == 0 || k > points.length){
            return null;
        }
        int numPoints = points.length;
        PriorityQueue<Point> pQueue = new PriorityQueue<Point>(k + 1, (a, b) -> (b.distance - a.distance)); // max elem on top
        for(int[] point: points){
            pQueue.add(new Point(point[0], point[1]));
            if(pQueue.size() > k){
                pQueue.poll();
            }
        }
        int[][] sortedElements = new int[k][2];
        for(int pos = k - 1; pos >= 0; pos--){
            Point point = (Point)pQueue.poll();
            sortedElements[pos][0] = point.x;
            sortedElements[pos][1] = point.y;
        }
        return sortedElements;
    }
}