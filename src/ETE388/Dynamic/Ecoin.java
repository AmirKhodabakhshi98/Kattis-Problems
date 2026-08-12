package ETE388.Dynamic;
import Kattis.Kattio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


//till sen: kolla logik decimaler
// and states the value of the e-modulus that shall be matched exactly.

public class Ecoin {


    Coin[] coins;
    int s; //sqrd
    int[] nbrEachCoin;
    int[] minNbrToReach;
    final String notPossible = "not possible";
    int ans = -1;


    public Ecoin(Coin[] coins, int sIn){
        this.coins=coins;
        this.s=sIn*sIn;
        nbrEachCoin = new int[coins.length];
        minNbrToReach = new int[s+1]; //bästa/minsta antal coins för o komma itll värdert
        Arrays.fill(minNbrToReach, Integer.MAX_VALUE);


        recursive(0, Arrays.copyOf(nbrEachCoin, coins.length));
        if (minNbrToReach[s] != Integer.MAX_VALUE) {
            ans = minNbrToReach[s];
        }

    }


    //om för långsam sen när d funkar byt txb till coinsused return nu när d fixat
    private void recursive(int coinsUsed, int[] nbrEachCoin) {
        int eMod = 0;
        coinsUsed++;
        for (int i = 0; i < coins.length; i++) {
            int[] state = Arrays.copyOf(nbrEachCoin, coins.length);
            state[i]++;
            eMod = eModolus(state);


            if (eMod>s || minNbrToReach[eMod]<=coinsUsed){ //om overshoot, eller, sämre väg hit -> cont
                continue;
            }

            minNbrToReach[eMod] = coinsUsed;
            if (eMod==s){
                continue;
            }
            recursive(coinsUsed,Arrays.copyOf(state, coins.length));
        }

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
        return (int) (Math.pow(convSum,2)+Math.pow(infoSum,2));
    }


    private static class Coin{
        int infoValue;
        int conventionalValue;

        public Coin(int conventionalValue, int infoValue){
            this.conventionalValue=conventionalValue;
            this.infoValue = infoValue;
        }
    }


    private static boolean inputCheck(int conv, int info, int s){
        boolean check = true;
        int left = (int) (Math.pow(conv,2)+Math.pow(info,2));
        if (left > (s*s)) {
            return false;
        }
        return true;

    }


    public static void main(String[] args) {

        Kattio io =  new Kattio();

        int n = io.getInt();
        for (int i = 0; i<n;i++){
            int m = io.getInt();
            int s = io.getInt();
            //Coin[] coins = new Coin[m];
            ArrayList<Coin> coins = new ArrayList<>();

            for (int j=0; j<m; j++){
                //ta bort >s i ett värde om tid behövs
                //nvm finns nt enl upgf
                int conv = io.getInt();
                int info = io.getInt();
                if (inputCheck(conv,info,s)){
                    coins.add(new Coin( conv, info));

                }
            }
            Coin[] coinArray = new Coin[coins.size()];
            coins.toArray(coinArray);
            Ecoin ecoin = new Ecoin(coins.toArray(coinArray),s);
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
