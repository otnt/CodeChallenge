/*
 * Leetcode problem Meeting Room 2, https://leetcode.com/problems/meeting-rooms-ii/
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei), find the minimum number of conference rooms required.
 * 
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return 2.
 * 
 * 1.ask interviewer to resolve ambiguity - what data type? how much data? what
 * assumption? who is users?
 * 1.1 [Q1] -
 * 1.2 [Q2] -
 * 1.3 [Q3] - 
 * 
 * 2.design algorithm - space & time complexity? what happen if huge amount of 
 * data? did u make right trade-off? what scenario has different trade-off?
 * 2.1 [Idea] - 
 * This problem is very similar to Meeting Room 1. Except that instead of
 * when two start points are met and we return false, we use a number is 
 * record maximum value that several meeting rooms are schedule at the
 * same time.
 *
 * 2.2 [Time complexity] - O(NlogN)
 * 2.3 [Space complexity] - O(N)
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

public class P253MeetingRoomsII{
    public int minMeetingRooms(Interval[] intervals) {
        if(intervals == null) {
            throw new NullPointerException();
        }
        if(intervals.length == 0) {
            return 0;
        }

        ArrayList<Timepoint> timepoints = new ArrayList<Timepoint>(intervals.length * 2);
        for(Interval interval : intervals) {
            timepoints.add(new Timepoint(interval.start, Timepoint.START));
            timepoints.add(new Timepoint(interval.end, Timepoint.END));
        }
        Collections.sort(timepoints, new Comparator<Timepoint>(){
            public int compare(Timepoint tp1, Timepoint tp2) {
                if(tp1.getTime() == tp2.getTime()) {
                    if(tp1.getType() == tp2.getType()) {
                        return 0;
                    }
                    if(tp1.getType() == Timepoint.START) {
                        return 1;
                    }
                    return -1;
                }
                return tp1.getTime() - tp2.getTime();
            }
        });

        int rooms = 0, maxRoom = 0;
        for(Timepoint timepoint : timepoints) {
            if(timepoint.getType() == Timepoint.START) {
                rooms ++;
                maxRoom = Math.max(maxRoom, rooms);
            }
            else {
                rooms --;
            }
        }
        
        return maxRoom;
    }

    class Timepoint {
        public static final int START = 0;
        public static final int END = 1;
        private int time, type;

        public Timepoint(int time, int type) {
            setTime(time);
            setType(type);
        }

        public int getTime() {return time;}
        public int getType() {return type;}
        public void setTime(int time) {this.time = time;}
        public void setType(int type) {this.type = type;}
    }

    class Interval {
       int start;
       int end;
       Interval() { start = 0; end = 0; }
       Interval(int s, int e) { start = s; end = e; }
    }
    

    public static void test() {
    }

    public static void main(String[] argv) {
        test();
    }
}
