/*
 * Lintcode problem traping rain water II, http://www.lintcode.com/en/problem/trapping-rain-water-ii/
 * Given n x m non-negative integers representing an elevation map 2d where the area
 * of each cell is 1 x 1, compute how much water it is able to trap after raining.
 * Example
 * Given 5*4 matrix
 *
 * [12,13,0,12]
 * [13,4,13,12]
 * [13,8,10,12]
 * [12,13,12,12]
 * [13,13,13,13]
 * return 14.
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] - all positive? - yes
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - The key idea is almost the same as trapping rain water 1: First we get
 * boundary of all cells, then we move innerward from the smallest boundary, for each
 * move we could calculate that area's trapping water. So, we have to maintain the
 * smallest elements, thus we should use a minimum priority queue.
 * Also, for all elements that we have already calculated(including first generated boundary),
 * we should not calculate them again, so we need a visited map to support this.
 * 2.2 [Time complexity] - O(N * M * log(N + M))
 * 2.3 [Space complexity] - O(N * M)
 * 2.4 [If huge amount of data] - 
 * 
 * 3.write pseudocode first, but make sure tell interviewer you will write real
 * code later
 * 3.1 Write code on paper
 * 3.2 Test code on paper
 *
 * 4.write code on a moderate pace
 * 4.1 Type code as-is on paper into computer, list all compile and other errors
 *
 * 5.test code and carefully fix mispolls - extreme case, 0, negative, null, 
 * max, min? user input error (null, negative value)? general case?
 * 5.1[Unit tests]
 */

public class TrapingRainWater2{
    public int trapRainWater(int[][] heights) {
        if(heights == null) {
            throw new NullPointerException();
        }
        if(heights.length == 0 || heights[0].length == 0) {
            return 0;
        }

        int N = heights.length,
            M = heights[0].length,
            volume = 0;
        boolean[][] visited = new boolean[N][M];
        PriorityQueue<Element> boundaryPq = new PriorityQueue<Element>(
                2 * N + 2 * M, new Comparator<Element>(){
                    public int compare(Element e1, Element e2) {
                        return e1.getHeight() - e2.getHeight();
                    }
                });

        //1.get all boundary in priority queue
        for(int r : new int[]{0, N - 1}) {
            for(int c = 0; c < M; c++) {
                if(!visited[r][c]) {
                    visited[r][c] = true;
                    boundaryPq.offer(new Element(heights[r][c], r, c));
                }
            }
        }
        for(int r = 0; r < N; r++) {
            for(int c : new int[]{0, M - 1}) {
                if(!visited[r][c]) {
                    visited[r][c] = true;
                    boundaryPq.offer(new Element(heights[r][c], r, c));
                }
            }
        }

        //2.loop over all boundary until it is empty, do BFS on 
        //each elements, store new boundary, update volume
        while(!boundaryPq.isEmpty()) {
            volume += bfs(heights, visited, boundaryPq);
            System.out.println(boundaryPq.size());
        }

        return volume;
    }

    private int bfs(int[][] heights, boolean[][] visited, PriorityQueue<Element> boundaryPq) {
        int N = heights.length,
            M = heights[0].length;
        Queue<Element> queue = new LinkedList<Element>();
        Element e = boundaryPq.poll();
        int height = e.getHeight(),
            volume = 0;
        queue.offer(e);
        while(!queue.isEmpty()) {
            Element ee = queue.poll();
            int eeHeight = ee.getHeight(),
                eeRow = ee.getRow(),
                eeCol = ee.getCol();
            visited[eeRow][eeCol] = true;
            volume += height - eeHeight;

            addNearby(eeRow > 0, eeRow - 1, eeCol, height, heights, queue, boundaryPq, visited);
            addNearby(eeRow < N - 1, eeRow + 1, eeCol, height, heights, queue, boundaryPq, visited);
            addNearby(eeCol > 0, eeRow, eeCol - 1, height, heights, queue, boundaryPq, visited);
            addNearby(eeCol < M - 1, eeRow, eeCol + 1, height, heights, queue, boundaryPq, visited);
        }

        return volume;
    }

    private void addNearby(boolean condition, int r, int c, int height, int[][] heights, 
            Queue<Element> queue, PriorityQueue<Element> boundaryPq, boolean[][] visited) {
        if(condition && !visited[r][c]) {
            visited[r][c] = true;
            int nHeight = heights[r][c];
            Element ne = new Element(heights[r][c], r, c);
            if(nHeight <= height) {
                queue.offer(ne);
            }
            else {
                boundaryPq.offer(ne);
            }
        }
    }

    class Element {
        private int height, row, col;

        public Element(int height, int row, int col) {
            setHeight(height);
            setRow(row);
            setCol(col);
        }

        public int getHeight() {return height;}
        public int getRow() {return row;}
        public int getCol() {return col;}
        public void setHeight(int height) {this.height = height;}
        public void setRow(int row) {this.row = row;}
        public void setCol(int col) {this.col = col;}
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
