package TDDD95.DataStructures;
import Kattis.*;

import java.util.*;

public class Turbo {



    private static int[] makeFenwickTree(int[] arr){
        int[] tree = Arrays.copyOf(arr,arr.length);

        for (int i =0; i<tree.length;i++){
            int p = i+(i&-i);
            if (p< tree.length){
                tree[p] = tree[p]+tree[i];
            }
        }
        return tree;
    }

    private static int getCumSum(int[]tree,int i){
        int sum = 0;
        while (i>0){
            sum += tree[i];
            i-= i&-i;
        }
        return sum;
    }

    private static void delete(int[] tree, int i){
            while (i<tree.length){
                tree[i] -=1; //delete operation is 1 --> 0
                i+=i&-i;
            }
    }

    //nwe plan outline
    //1. map numbers to their orig index
    //2. fenwick tree init as 1 for each item
    //3. FENWIck create
    // 4. delete --> set value 0
    // 5. cumsum to orig index should give updated index.
    //så det blir som att vi tar bort varje sorterad nummer,
    //kvar blir osorterade nummer
    //så vi räknar platser till början/slutet av osorterade nummer så har vi swaps för ett visst nmr
    //fenwick ger index för nummer i osorterade set.
    //1->osorterad, 0->sorterad
    private static void turboSort(int N, Kattio io){
        HashMap<Integer,Integer> origIndexes = new HashMap<>();
        int[] arr = new int[N+1];
        int[] fenwickTree;

        arr[0]=0;

        int num;
        for (int i = 1; i<=N;i++){
            num = io.getInt();
            origIndexes.put(num,i); //1.
            arr[i]=1; //faster than arrays.fill before loop? //2.
        }
        fenwickTree = makeFenwickTree(arr); //3.

        int lim = Math.floorDiv(N,2)+1;
        int origIndex;
        int updatedIndex;
        int updatedN = N;
        int swaps;
        for (int i =1; i<lim;i++){
            origIndex = origIndexes.get(i);
            //updatedIndex = fenwick cumsum(origIndex)
            updatedIndex=getCumSum(fenwickTree,origIndex); //5
            //swaps = updatedIndex-1; right?
            swaps = updatedIndex-1;
            //set fenwick(i)=0
            delete(fenwickTree,origIndex); //4
            //updatedN--
            updatedN--;
            //print swaps
            io.println(swaps);

            origIndex = origIndexes.get(N-i+1);
            //updatedIndex = fenwick cumsum(origIndex)
            updatedIndex = getCumSum(fenwickTree,origIndex);
            //swaps = updatedN-updatedIndex; right??
            swaps = updatedN-updatedIndex;
            //set fenwick(N-i+1)=0
            delete(fenwickTree,origIndex);
            //updatedN--
            updatedN--;
            //print swaps
            io.println(swaps);

        }

        //if odd nbr array mid elem should get automatically sorted by this point
        if (N%2!=0){
            io.println(0);
        }

        io.flush();
        io.close();
    }


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int N = io.getInt();
        turboSort(N,io);
    }
}
