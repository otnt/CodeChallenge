/*
 * f(i) = min(f(i-j) + 80 - len(i, j))
 * s(i) = j
 * return min(f(length - j)) where len(j, length) <= 80
 */

public class PrintingNeatly {
    public void printingNeatly(String input) {
        String[] inputs = (" " + input).split(" ");
        Cell[] dp = new Cell[inputs.length];
        dp[0] = new Cell();
        dp[0].length = 0;
        dp[0].cut = 0;
        for(int i = 1; i < inputs.length; i++) {
            int lens = 0;
            int totalLen = Integer.MAX_VALUE;
            dp[i] = new Cell();
            for(int j = i; j >= 1; j--) {
                lens += inputs[j].length();
                if(j != i) {
                    lens++;
                }
                if(lens > 80) {
                    break;
                }
                int len = dp[j - 1].length + 80 - lens;
                if(len < totalLen) {
                    totalLen = len;
                    dp[i].length = len;
                    dp[i].cut = j - 1;
                }
            }
        }

        // Print out
        int cut = dp[inputs.length - 1].cut;
        int lastCut = inputs.length;
        while(cut != 0) {
          String rst = "";
          for(int c = cut; c < lastCut; c++) {
              rst += inputs[c] + " ";
          }
          System.out.printf("cut: %d~%d, substring: %s\n", cut, lastCut - 1, rst);
          lastCut = cut;
          cut = dp[cut].cut;
        }
        String rst = "";
        for(int c = cut; c < lastCut; c++) {
            rst += inputs[c] + " ";
        }
        System.out.printf("cut: %d~%d, substring: %s\n", cut, lastCut - 1, rst);
    }

    public static void main(String[] argv) {
        new PrintingNeatly().printingNeatly(argv[0]);
    }

    class Cell {
        public int length;
        public int cut;
        public String toString() {
            return String.format("length: %d, cut: %d", length, cut);
        }
    }
}
