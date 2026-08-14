package ETE388.Dynamic;
import Kattis.Kattio;


public class ExchangeRates {
    private double ans = 1000;
    private double savings = 1000;
    private boolean isCAD = true; //t/f?
    private double[] rates;
    private double comission = 0.97;
    private double cadUsdDay[][];

    ExchangeRates(double[] rates){
        this.rates = rates;
        cadUsdDay = new double[rates.length][2];
        naive(savings, isCAD, 0 );
        ans = cadUsdDay[rates.length-1][0];
    }




    int calls = 0;
    private void naive(double savings, boolean isCAD, int day){
        calls++;
        boolean improvementFoundDoNothing = true;
        boolean improvementFoundExchanged = true;

        double exchanged = exchange(savings, rates[day], isCAD);

        if (isCAD && savings > cadUsdDay[day][0]){
            cadUsdDay[day][0] = savings;
        }else if (!isCAD && savings > cadUsdDay[day][1] ){

            cadUsdDay[day][1] = savings;
        } else {
            improvementFoundDoNothing = false;
        }

        if (!isCAD && exchanged > cadUsdDay[day][0]){
            cadUsdDay[day][0] = exchanged;
        }else if (isCAD && exchanged > cadUsdDay[day][1] ){
            cadUsdDay[day][1] = exchanged;
        }else {improvementFoundExchanged = false;}


        day++;
        if (day == rates.length){
            return;
        }

        if (improvementFoundDoNothing){
            naive(savings,isCAD,day);
        }

        if (improvementFoundExchanged){
            naive(exchanged,!isCAD,day);
        }
    }


    private double exchange(double savings, double rate, boolean isCAD){
        double ans = 0;
        if (isCAD){
            ans = (savings/rate)*comission;
        }else {
            ans =  savings*rate*comission;
        }
        ans = Math.floor(ans*100)/100;
        return ans;
    }

    public static void main(String[] args) {
    Kattio io = new Kattio();

    int d = io.getInt();
    while (d != 0){
        double[] rates = new double[d];
        for (int i = 0; i < d; i++){
            rates[i] = io.getDouble();
        }
        ExchangeRates er = new ExchangeRates(rates);
        io.printf("%.2f%n",er.ans);
        d = io.getInt();
    }
    io.flush();
    io.close();
    }
}
