package TDDD95.DataStructures;
import Kattis.*;



import java.util.*;
import java.util.stream.IntStream;

public class IntrospectiveCaching {



    public static void main(String[] args) {
        Kattio io = new Kattio(System.in,System.out);
        int c = io.getInt();
        int n = io.getInt();
        int a = io.getInt();


        LinkedHashSet<Integer> cache = new LinkedHashSet<>(c);

        //cache acceses - output
        int accessCount = 0;
        //keeps track of item in cache needed farthest in future
        TreeSet<Integer> ts = new TreeSet<>(); //turned out faster than priorityqueue
        //next occurence for each item, array of linkedhashsets initialized
        LinkedHashSet[] occurenceList = IntStream.range(0,n)
                .mapToObj(i-> new LinkedHashSet<Integer>())
                .toArray(LinkedHashSet[]::new);

        //items in hash that have no next access, i.e can be freely removed if cache space needed
        LinkedHashSet<Integer> noNextOccurenceSet = new LinkedHashSet<>(n);

        int index = -1;
        int prevNum = -1;
        Integer[] accecess = new Integer[a];

        //read input - access requests
        for (int i=0; i<accecess.length;i++){
            int num = io.getInt();
            accecess[i]=num;
            //trunctuates repeat input.
            if (i>0 && prevNum==num){
                accecess[i]=-1;
                continue;
            }
            prevNum=num;

            //fills cache as we're reading input
            if (cache.size()<c){
                if (cache.add(num)){
                    accessCount++;
                    noNextOccurenceSet.add(num);
                }
                //2 cases - cache not full 1.item exists in cache 2. item was added to cache
                //we want to move on either way, as eg an item thats in cache and was occured again before
                //cache is full doesn't need to have its occurenceList updated with current 'i'.
                continue;
            }else {
                if (index==-1){
                    index=i; //cache full, index to continue operations from full cache in next loop
                }
            }

            //next occurence of an item that's in full cache.
            if (noNextOccurenceSet.contains(num)){
                noNextOccurenceSet.remove(num);
                ts.add(i); //treeset updated with next occurence of an item after cache is filled.
            }
            //occurenceList for next time an item occurs, after cache is full.
            occurenceList[num].add(i);
        }


        //unique items read in <= cache size. we can finish here
        if (index==-1){
            io.println(accessCount);
            io.flush();
            io.close();
            return;
        }


        //processing input from where cache got full
        for (int i=index; i<accecess.length;i++){
            int num = accecess[i];

            //dummy number, used for duplicate trunctuation
            if (num==-1){
                continue;
            }

            int occurence = (int) occurenceList[num].removeFirst();

            //if item is in cache already, update treeSet or indicate it has no more access requests
            if (cache.contains(num)){
                ts.remove(occurence);
                if (!occurenceList[num].isEmpty()){
                    ts.add((Integer) occurenceList[num].getFirst());
                }else {
                    noNextOccurenceSet.add(num);
                }
                continue;
            }
            //case: no next occurence for any item in cache. we can freely remove whichever to make space
            if (ts.isEmpty()){
                noNextOccurenceSet.remove(cache.removeFirst());
            }else {
                //case: some items in cache have no next occurence. can freely remove one of them.
                if (ts.size()<cache.size() && !noNextOccurenceSet.isEmpty()){
                   cache.remove(noNextOccurenceSet.removeFirst());
                }else {
                    //case: all items in cache have a next occurence. remove the one occuring farthest in future
                    cache.remove(accecess[ts.pollLast()]);
                }
            }
            //add new number to cache
            cache.add(num);
            //if the new cache item has a next occurence, add it to ts
            //otherwise indicate it has no next occurence
            if (!occurenceList[num].isEmpty()){
                ts.add((Integer)occurenceList[num].getFirst());
            }else {
                noNextOccurenceSet.add(num);
            }

            accessCount++;
        }

        io.println(accessCount);
        io.flush();
        io.close();
    }
}
