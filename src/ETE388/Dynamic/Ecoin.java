package ETE388.Dynamic;

import java.util.Arrays;
import java.util.Scanner;
import Kattis.Kattio;


public class Ecoin {


    Coin[] coins;
    int s;
    int[] nbrEachCoin;


    public Ecoin(Coin[] coins, int s){
        this.coins=coins;
        this.s=s;
        nbrEachCoin = new int[coins.length];
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


    private static class Coin{
        int infoValue;
        int conventionalValue;
        int singleCoinValue;
        int amount = 0;

        public Coin(int conventionalValue, int infoValue){
            this.conventionalValue=conventionalValue;
            this.infoValue = infoValue;
        }
    }


    public static void main(String[] args) {

        Kattio io =  new Kattio();

        int n = io.getInt();
        for (int i = 0; i<n;i++){
            int m = io.getInt();
            int s = io.getInt();
            Coin[] coins = new Coin[m];
            for (int j=0; j<m; j++){
                coins[j] = new Coin(io.getInt(),io.getInt());
            }
            Ecoin ecoin = new Ecoin(coins,s);
            //ecoin.ans or sth
        }

    }
}
