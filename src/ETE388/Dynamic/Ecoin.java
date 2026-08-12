package ETE388.Dynamic;
import Kattis.Kattio;

import java.util.ArrayList;
import java.util.Arrays;



public class Ecoin {


    Coin[] coins;
    int s; //sqrd
    int[] nbrEachCoin;
    int ans = Integer.MAX_VALUE;


    int[][] convInfo;
    public Ecoin(Coin[] coins, int sIn){
        this.coins=coins;
        this.s=sIn*sIn;
        nbrEachCoin = new int[coins.length];


        convInfo = new int[sIn+1][sIn+1];
        for (int[] ints : convInfo) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }

        recursive(0, 0,0);


    }



    private void recursive(int coinsUsed, int conv, int info) {
        coinsUsed++;


        for (int i = 0; i < coins.length; i++) {
            int convSum= conv+ coins[i].conventionalValue;
            int infoSum = info + coins[i].infoValue;

            int convSumSquared = sumSquared(convSum);
            int infoSumSquared = sumSquared(infoSum);
            int convInfoSum = convSumSquared + infoSumSquared;

            if (convInfoSum>s ){ //om overshoot, eller, sämre väg hit -> cont
                continue;
            }

            if (convInfo[convSum][infoSum]<=coinsUsed){
                continue;
            }

            convInfo[convSum][infoSum]=coinsUsed;

            if (convInfoSum==s){
                if (coinsUsed < ans){
                    ans = coinsUsed;
                }
                continue;
            }

            recursive(coinsUsed,convSum,infoSum);
        }

    }


    private int sumSquared(int sum){
        return sum*sum;
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
            ArrayList<Coin> coins = new ArrayList<>();

            for (int j=0; j<m; j++){
                int conv = io.getInt();
                int info = io.getInt();
                if (inputCheck(conv,info,s)){
                    coins.add(new Coin( conv, info));
                }
            }

            Coin[] coinArray = new Coin[coins.size()];
            coins.toArray(coinArray);
            Ecoin ecoin = new Ecoin(coins.toArray(coinArray),s);

            if (ecoin.ans != Integer.MAX_VALUE){
                io.println(ecoin.ans);
            }
            else io.println("not possible");
        }

        io.flush();
        io.close();

    }
}
