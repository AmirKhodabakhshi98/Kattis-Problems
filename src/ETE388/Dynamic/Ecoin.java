package ETE388.Dynamic;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.IntStream;

import Kattis.Kattio;


public class Ecoin {


    Coin[] coins;
    int s;
    int[] nbrEachCoin;
    HashMap<Integer, Integer> nbrEachCoinMap = new HashMap<>();
    int[] minNbrToReach;
    final String notPossible = "not possible";
    int ans = -1;


    public Ecoin(Coin[] coins, int s){
        this.coins=coins;
        this.s=s;
        nbrEachCoin = new int[coins.length];
        minNbrToReach = new int[s+1]; //bästa/minsta antal coins för o komma itll värdert
        Arrays.fill(minNbrToReach, Integer.MAX_VALUE);


        int[] ans = recursive(0, Arrays.copyOf(nbrEachCoin, coins.length));
        if (ans!=null){
            this.ans = (IntStream.of(ans).sum());
        }

    }


    //om för långsam sen när d funkar byt txb till coinsused return nu när d fixat
    private int[] recursive(int coinsUsed, int[] nbrEachCoin) {


        int eMod = 0;
        coinsUsed++;
        for (int i = 0; i < coins.length; i++) {
            nbrEachCoin[i]++;
            eMod = eModolus(nbrEachCoin);
            nbrEachCoin[i]--;
            if (eMod>s || minNbrToReach[eMod]<=coinsUsed){ //om overshoot, eller, sämre väg hit -> cont
                continue;
            }
            nbrEachCoin[i]++;
            minNbrToReach[eMod] = coinsUsed;
            if (eMod==s){
                return nbrEachCoin;
            }

            return recursive(coinsUsed,Arrays.copyOf(nbrEachCoin, coins.length));

        }
        return null;
    }


    private int eModolus(int[] nbrEachCoin){
        int convSum = 0;
        int infoSum = 0;

        for (int i = 0; i < coins.length; i++) {
            if (nbrEachCoin[i] == 0){
                continue;
            }
            convSum += coins[i].conventionalValue*nbrEachCoin[i];
            infoSum += coins[i].infoValue*nbrEachCoin[i];
        }
        return (int) Math.sqrt(Math.pow(convSum,2)+Math.pow(infoSum,2));
    }


    private static class Coin{
        int infoValue;
        int conventionalValue;
        int singleCoinValue; //nvm för nu
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
            if (ecoin.ans != -1){
                io.println(ecoin.ans);
            }
            else io.println("not possible");
        }

        io.flush();
        io.close();

    }
}
