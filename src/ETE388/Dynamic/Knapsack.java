package ETE388.Dynamic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Knapsack {
    int capacity;
    int[][] objects;
    int[][] arr;
    Item items[];


    //arr ist om d för långsamt
    private static class Item{
        int value;
        int weight;

        public Item(int value, int weight){
            this.value=value;
            this.weight=weight;
        }

    }

    public Knapsack(int capacity, Item[]items){
        this.capacity=capacity;
        this.items=items;
        arr = new int[items.length+1][capacity+1];
        solve();
    }

    private void solve(){

        for (int i = 1; i< arr.length;i++){
            Item item = items[i-1];
            for (int j=0; j< arr[0].length;j++){
                if (item.weight<=j){
                    arr[i][j] = Math.max(arr[i-1][j],item.value+arr[i-1][j-item.weight]);
                }
                else {
                    arr[i][j]=arr[i-1][j];
                }
            }
        }

        int i = arr.length-1;
        int j = arr[0].length-1;
        int value = arr[i][j];
        ArrayList<Integer> indexes = new ArrayList<>();
        if (value==0){
            System.out.println(0);
            return;
        }

        while (i>=1 && j>=0){

            Item item = items[i - 1];

           if (arr[i-1][j] < arr[i][j]){

               //System.out.print(i-1 + " ");
               j-=item.weight;
               indexes.add(i-1);
           }else {
               j--;
           }
           i--;

       }

        System.out.println(indexes.size());
        for (int nbr:
             indexes) {
            System.out.print(nbr + " ");
        }
        System.out.println();

     //   System.out.println(arr[arr.length-1][capacity]);


    }



    public static void main(String[] args) throws IOException {
/*
        Kattio io = new Kattio(System.in, System.out);

        while (true){

            int capacity = io.getInt();
            //int[][] objects = new int[io.getInt()][2];
            Item[] items = new Item[io.getInt()];
            for (int i = 0; i<items.length; i++){
                //objects[i][0] = io.getInt();
                //objects[i][1] = io.getInt();
                items[i] = new Item(io.getInt(),io.getInt());
            }

            new Knapsack(capacity,items);
            io.flush();

            if (!io.ready()){
                break;
            }
        }

        io.close();

        */


        //test stuff
       // for (int x=  0; x<1000; x++) {
       // while (true){
            int capacity = 3;
            Item[] items = new Item[1];
            Random random = new Random();
            for (int i = 0; i < items.length; i++) {
                //items[i] = new Item(random.nextInt(1), random.nextInt(1));
                items[i] = new Item(1, 3);
            }
        new Knapsack(capacity,items);
   //     }


    }
}
