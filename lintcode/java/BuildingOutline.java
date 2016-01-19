/*
 * Lintcode problem Building Outline http://www.lintcode.com/en/problem/building-outline/
 *
 * Given N buildings in a x-axis，each building is a rectangle and can be represented 
 * by a triple (start, end, height)，where start is the start position on x-axis, end
 * is the end position on x-axis and height is the height of the building. Buildings
 * may overlap if you see them from far away，find the outline of them。
 *
 * An outline can be represented by a triple, (start, end, height), where start is the
 * start position on x-axis of the outline, end is the end position on x-axis and 
 * height is the height of the outline.
 *
 * Example
 * Given 3 buildings：
 * [
 *   [1, 3, 3],
 *   [2, 4, 4],
 *   [5, 6, 1]
 * ]
 * The outlines are：
 * [
 *   [1, 2, 3],
 *   [2, 4, 4],
 *   [5, 6, 1]
 * ]
 *
 *
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] -
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - This problem gives us a lot of "ranges", which leads us to use
 * the so-called "scanning line" method. Imagine we use a vertical line to
 * scan all the buildings, when we met a starting point, we could either,
 * 1.no previous start points, then save it, update start position
 * 2.higher than previous point, then output previous range, and save this point, update start position
 * 3.shorter or equal to previous point, just ignore it
 * When we met an ending point, we could either,
 * 1.if is highest point in current saved points, then pop it
 * if second highest point is of the same height, then to next loop
 * if second highest point is less height, then output range, update start position
 * 2.smaller than hightest point, find this point and remove it
 *
 * To support a good find maximum operation and delete operation, we need a so-called
 * hash priority queue data structure, which supports O(logN) for insertion, find maximum operation,
 * and delete operations.
 *
 * 2.2 [Time complexity] - O(NlogN) for sorting input range and for scanning
 * 2.3 [Space complexity] - O(logN) for sorting, O(N) for priority queue
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
 * 5.test code and carefully fix mistakes - extreme case, 0, negative, null, 
 * max, min? user input error (null, negative value)? general case?
 * 5.1[Unit tests]
 */

public class BuildingOutline{
    public ArrayList<ArrayList<Integer>> buildingOutline(int[][] buildings) {
        if(buildings == null) {
            throw new NullPointerException();
        }
        if(buildings.length == 0 || buildings[0].length == 0) {
            return new ArrayList<ArrayList<Integer>>();
        }

        //1.get all range, and sort by position
        List<Outline> outlines = new ArrayList<Outline>(2 * buildings.length);
        int id = 0;
        for(int[] building : buildings) {
            outlines.add(new Outline(building[0], building[2], Outline.START, id));
            outlines.add(new Outline(building[1], building[2], Outline.END, id));
            id++;
        }
        Collections.sort(outlines);

        //2.traverse all range, use maximum heap to maintain currently buildings
        //and to find highest one
        //The rules are:
        //If met a start outline
        //1.If heap is empty, save outline, update start position
        //2.If new outline height is larger than previous heightest, output range
        //and save new outline, update start position
        //3.Otherwise, save new outline
        //
        //If met a end outline
        //1.If new outline height is equal to previous heightest, pop out previous
        //heighest, then
        //1.1 If now new previous highest is still the same, then break
        //1.2 If new previous highest is smaller, then output range, update start position
        //2.If new outline height is smaller, remove that height from heap
        PriorityQueue<Outline> maxHeap = new PriorityQueue<Outline>(
                buildings.length, new Comparator<Outline>() {
                    public int compare(Outline ol1, Outline ol2) {
                        return - (ol1.getHeight() - ol2.getHeight());
                    }
                });
        ArrayList<ArrayList<Integer>> rst = new ArrayList<ArrayList<Integer>>();
        int from = 0;
        for(Outline outline : outlines) {
            if(outline.getType() == Outline.START) {
                //1.heap is empty, save outline, update start position
                if(maxHeap.isEmpty()) {
                    from = outline.getPosition();
                }
                //2.If new outline height is larger than previous heightest, output range
                //and save new outline, update start position
                else if(outline.getHeight() > maxHeap.peek().getHeight()) {
                    rst.add(new ArrayList<Integer>(Arrays.asList(from, outline.getPosition(), maxHeap.peek().getHeight())));
                    from = outline.getPosition();
                }
                //3.Otherwise, save new outline

                maxHeap.offer(outline);
            }
            else {
                //1.If new outline height is equal to previous heightest, pop out previous
                //heighest, then
                if(outline.getHeight() == maxHeap.peek().getHeight()) {
                    Outline prevHigh = maxHeap.poll();
                    //1.1 If now new previous highest is still the same, then break
                    //1.2 If new previous highest is smaller, then output range, update start position
                    if(maxHeap.isEmpty() || outline.getHeight() > maxHeap.peek().getHeight()) {
                        rst.add(new ArrayList<Integer>(Arrays.asList(from, outline.getPosition(), outline.getHeight())));
                        from = outline.getPosition();
                    }
                }
                //2.If new outline height is smaller, remove that height from heap
                else {
                    maxHeap.remove(outline);
                }
            }
        }

        return rst;
    }

    class Outline implements Comparable<Outline>{
        public final static int START = 0;
        public final static int END = 1;
        private int position, height, type, id;

        public Outline(int position, int height, int type, int id) {
            this.position = position;
            this.height = height;
            this.type = type;
            this.id = id;
        }

        public int getPosition() {return position;}
        public int getHeight() {return height;}
        public int getType() {return type;}
        public int getId() {return id;}

        @Override
        public int compareTo(Outline outline) {
            if(position == outline.getPosition()) {
                if(type == outline.getType()) {
                    if(type == Outline.START) {
                        return - (height - outline.getHeight());
                    }
                    else {
                        return height - outline.getHeight();
                    }
                }
                return type == Outline.START ? -1 : 1;
            }
            return position - outline.getPosition();
        }

        @Override
        public boolean equals(Object outline) {
            return height == ((Outline)outline).getHeight();
        }

        @Override
        public int hashCode() {
            return Integer.toString(id).hashCode();
        }
    }

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
