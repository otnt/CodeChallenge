import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.File;
import java.util.HashMap;
import org.apache.commons.collections4.trie.*;

public class Performance {
    public static void main(String argv[]) {
        try{
        //get all words
        List<String> words = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File("out"))));
        String word = null;
        while((word = br.readLine()) != null) {
            words.add(new String(word));
        }

        //test performance
        //1.shuffle all words, then insert all words
        //2.shuffle again, then search all words
        Performance pf = new Performance();
        int testTimes = 10 * 1;
        long start = 0, end = 0;

        for(int i = 0; i < testTimes; i++) {
            TrieTree<Boolean> tt = new TrieTree<Boolean>(128);
            HashMap<String, Boolean> hm = new HashMap<String, Boolean>();
            PatriciaTrie<Boolean> pt = new PatriciaTrie<Boolean>();

            //1
            pf.shuffle(words);
            //pf.insertUsingTrieTree(tt, words);
            //pf.insertUsingHashMap(hm, words);
            pf.insertUsingPatriciaTrie(pt, words);

            //2
            pf.shuffle(words);
            //pf.findUsingTrieTree(tt, words);
            //pf.findUsingHashMap(hm, words);
            pf.findUsingPatriciaTrie(pt, words);
        }
        
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertUsingHashMap(HashMap<String, Boolean> hm, List<String> words) {
        for(String w : words) {
            hm.put(w, true);
        }
    }

    private void findUsingHashMap(HashMap<String, Boolean> hm, List<String> words) {
        for(String w : words) {
            hm.get(w);
        }
    }

    private void insertUsingPatriciaTrie(PatriciaTrie<Boolean> pt, List<String> words) {
        for(String w : words) {
            pt.put(w, true);
        }
    }

    private void findUsingPatriciaTrie(PatriciaTrie<Boolean> pt, List<String> words) {
        for(String w : words) {
            pt.get(w);
        }
    }

    private void insertUsingTrieTree(TrieTree<Boolean> tt, List<String> words) {
       int[] index = null;
       int length = 0;

        for(String w : words) {
            length = w.length();
            index = new int[length];
            for(int p = 0; p < length; p++) {
                index[p] = (int)(w.charAt(p) % 128);
            }
            tt.insert(index, true);
        }
    }

    private void findUsingTrieTree(TrieTree<Boolean> tt, List<String> words) {
       int[] index = null;
       int length = 0;

       for(String w : words) {
           length = w.length();
           index = new int[length];
           for(int p = 0; p < length; p++) {
               index[p] = (int)(w.charAt(p) % 128);
           }
           tt.get(index);
       }
    }

    private void shuffle(List<String> list) {
        int size = list.size();
        Random random = new Random();
        for(int i = size - 1; i > 0; i--) {
            swap(list, i, random.nextInt(i + 1));
        }
    }

    private void swap(List<String> list, int p1, int p2) {
        String t1 = list.get(p1);
        String t2 = list.get(p2);
        list.set(p1, t2);
        list.set(p2, t1);
    }
}
