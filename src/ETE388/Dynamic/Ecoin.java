package ETE388.Dynamic;

import java.util.Arrays;
import java.util.Scanner;

public class Ecoin {


    Coin[] coins;
    int s;
    Kattio io;
    int[] nbrEachCoin;


    public Ecoin(Coin[] coins, int s, Kattio io){
        this.coins=coins;
        this.s=s;
        this.io=io;
        nbrEachCoin = new int[coins.length];
        recursiveNaive(coins[0],s);

    }



    private void recursiveNaive(Coin coin, int value){



        for (int i=0; i<coins.length; i++){
            coins[i].amount++;
            value = eModulus();
            recursiveNaive(coins[i],value);
        }

    }

    private int eModulus(){
        int convSum = 0;
        int infoSum = 0;
        for (int i=0; i<coins.length; i++){ //lim+1?
            convSum+= coins[i].conventionalValue * coins[i].amount; //* nbrEachCoin[i];
            infoSum+= coins[i].infoValue * coins[i].amount; //* nbrEachCoin[i];
        }
        return (int) Math.sqrt(Math.pow(convSum,2)+Math.pow(infoSum,2));
    }


/*
    private int eModulus(int lim, int[][] dp, int index){
        int convSum = 0;
        int infoSum = 0;
        for (int i=0; i<lim; i++){ //lim+1?
            convSum+= coins[i].conventionalValue * dp[index][i]; //* nbrEachCoin[i];
            infoSum+= coins[i].infoValue * dp[index][i]; //* nbrEachCoin[i];
        }
        return (int) Math.sqrt(Math.pow(convSum,2)+Math.pow(infoSum,2));
    }
*/






    private static class Coin{
        int infoValue;
        int conventionalValue;
        int singleCoinValue;
        int amount = 0;

        public Coin(int conventionalValue, int infoValue){
            this.conventionalValue=conventionalValue;
            this.infoValue = infoValue;
            singleCoinValue = (int) Math.sqrt(Math.pow(conventionalValue,2)+Math.pow(infoValue,2));
        }


    }

    //There is no limit on how many e-coins of each type that may be used to match the given e-modulus.
    public static void main(String[] args) {

        Kattio io = new Kattio(System.in, System.out);

        int n = io.getInt();
        for (int i = 0; i<n;i++){
            int m = io.getInt();
            int s = io.getInt();
            Coin[] coins = new Coin[m];
            for (int j=0; j<m; j++){
                coins[j] = new Coin(io.getInt(),io.getInt());
            }
            new Ecoin(coins,s,io);
        }


    }
}
