/*
 * Description:
 * The lock panel for android cell phone is drawing a line among nine points.
 * The rule is, 
 * 1.you could not jump through a non-visited point between two
 * points. But you do could pass by a visited point.
 * For example
 * 1 2 3
 * 4 5 6
 * 7 8 9
 * If 2 is not visited, then 1->3 is not allowed.
 * But 2->5->4->1->3 is allowed because 2 is already visited.
 *
 * 2.Each point could visit at most once.
 *
 * Write a program to show how many different combinations are there given path
 * length from 4 to 9 inclusively.
 */

/*
 * Thought:
 * It seems really complex(well, it is actually). But we still could simplify/
 * abstract it as a DFS problem.
 * The key to DFS problem:
 * 1.Where to go: given a visited matrix and a rule function, respond with
 * a list of feasible points to go
 * 2.How to avoid repitation: use a visited matrix, but when recursive back,
 * remove the visited mark
 * 3.When to stop: use a counter to record recursion level, when it is among
 * 4 to 9, then this is a feasible solution.
 *
 * After this thought, the problem becomes quite clear right? The key problem
 * is how to design the rule function. One method is use Manhaton Distance, 
 * if two points have Manhaton Distance of 1 or 3, then they are fine, just 
 * check if the point has already visited. If Manhaton Distance is 4, then
 * need to check the middle point. If is 2, there are two conditions, if it's
 * diagonal(could be detected using difference of Manhaton Distance on x and
 * y dimension), then it's okay, otherwise, still need to check the middle 
 * point.
 */

/*
 * Pseudocode:
 * result := 0
 *
 * main DFS:
 * visited := 9*9 matrix of false
 * level := 0
 * for each cell in matrix:
 *   mark the cell as visited
 *   helperDFS(cell id, level, visited)
 *   mark the cell as not visited
 * return result
 *
 * 
 * helperDFS(cell id, level, visited):
 * level++
 * if level in range [4,9]:
 *   result++
 * mark the cell as visited
 * for each cell in feasibleCell(cell id, visited):
 *   helperDFS(cell id, level, visited)
 * mark the cell as not visited
 *
 * feasibleCell(cell id, visited):
 * feasibleCellList := empty list
 * for each non visited cell c in visited:
 *   dist := manhatonDistance(cell id, c) 
 *   if dist is 1 or 3:
 *     feasibleCellList push c
 *   else if dist is 4:
 *     if middle point of cell id and c is visited:
 *       feasibleCellList push c
 *   else if cell id and c is on diagnal:
 *     feasibleCellList push c
 *   else
 *     if middle point of cell id and c is visited:
 *       feasibleCellList push c
 * return feasibleCellList
 */

import java.util.List;
import java.util.ArrayList;

public class LockPanel {

    public int result = 0;
    public Point[] points = new Point[]{
        new Point(-1,-1),
        new Point(0, 0), new Point(0, 1), new Point(0, 2),
        new Point(1, 0), new Point(1, 1), new Point(1, 2),
        new Point(2, 0), new Point(2, 1), new Point(2, 2)
    };
    public List<Integer> resultList = new ArrayList<Integer>();

    //mainDFS
    public int findPathNum() {
        boolean [][] visited = new boolean[3][3];
        int level = 0;

        for(int i = 1; i <= 9; i++) {
            Point p = points[i];
            helperDFS(i, level, visited);
        }

        return result;
    }

    public void helperDFS(int cellId, int level, boolean[][] visited) {
        level++;
        resultList.add(cellId);

        if(4 <= level && level <= 9) {
            result++;
            System.out.println(resultList);
        }

        Point p = points[cellId];
        visited[p.row][p.col] = true;
        List<Integer> feasibleCellList = feasibleCell(cellId, visited);
        for(Integer id: feasibleCellList) {
            helperDFS(id, level, visited);
        }
        visited[p.row][p.col] = false;

        resultList.remove(resultList.size() - 1);
    }

    public List<Integer> feasibleCell(int cellId, boolean[][] visited) {
        List<Integer> feasibleCellList = new ArrayList<Integer>();

        Point startPoint = points[cellId];
        Point p;
        for(int i = 1; i <= 9; i++) {
            p = points[i];
            if(visited[p.row][p.col]) {
                continue;
            }

            int md = manhadonDistance(startPoint, p);
            int middle;
            switch(md) {
                case 1:
                case 3:
                    feasibleCellList.add(i);
                    break;
                case 4:
                    middle = (cellId + i)/2;
                    p = points[middle];
                    if(visited[p.row][p.col]) {
                        feasibleCellList.add(i);
                    }
                    break;
                case 2:
                    if(isDiagnol(cellId, i)) {
                        feasibleCellList.add(i);
                    }
                    else {
                        middle = (cellId + i) / 2;
                        p = points[middle];
                        if(visited[p.row][p.col]) {
                            feasibleCellList.add(i);
                        }
                    }
                    break;
            }
        }

        return feasibleCellList;
    }

    public int manhadonDistance(Point p1, Point p2) {
        return Math.abs(p1.row - p2.row) + Math.abs(p1.col - p2.col);
    }

    public boolean isDiagnol(int cell1, int cell2) {
        Point p1 = points[cell1];
        Point p2 = points[cell2];
        return Math.abs(p1.row-p2.row) == Math.abs(p1.col-p2.col);
    }

    class Point {
        public int row, col;
        public Point(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    public static void main(String[] argv) {
        LockPanel lp = new LockPanel();
        System.out.println(lp.findPathNum());
    }
}
