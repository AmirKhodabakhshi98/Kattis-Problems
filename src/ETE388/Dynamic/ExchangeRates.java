package ETE388.Dynamic;
import Kattis.Kattio;

//dec format


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
        System.err.println("\n" + calls + "\n");
    }


    //add rounding mode fix


    int calls = 0;
    private void naive(double savings, boolean isCAD, int day){
        calls++;
        System.err.println("calls:" + calls + " savings:" + savings + " isCAD:" + isCAD + " day:" + day);
        boolean improvementFound = true;

        double exchanged = exchange(savings, rates[day], isCAD);

        if (isCAD && savings > cadUsdDay[day][0]){
            cadUsdDay[day][0] = savings;
         //  System.err.println("day: " + day + "cad saving; " + savings);
        }else if (!isCAD && savings > cadUsdDay[day][1] ){
          //  System.err.println("day: " + day + "usd saving; " + savings);

            cadUsdDay[day][1] = savings;
        } else {
            improvementFound = false;
        }

        if (!isCAD && exchanged > cadUsdDay[day][0]){
            cadUsdDay[day][0] = exchanged;
          //  System.err.println("day: " + day + "cad saving; " + exchanged);
        }else if (isCAD && exchanged > cadUsdDay[day][1] ){
           // System.err.println("day: " + day + "usd saving; " + exchanged);

            cadUsdDay[day][1] = exchanged;
        }else {improvementFound = false;}


        day++;
        //if (day == rates.length){
         //   return;
       // }

        if (!improvementFound || day == rates.length){
            return;
        }

        naive(savings,isCAD,day);//gör nt nåt
        //double exchanged = exchange(savings, rates[day], isCAD);
        naive(exchanged,!isCAD,day);

    }




    /*
    private double naive(double savings, boolean isCAD, int day){

        if (isCAD && savings > cadUsdDay[day][0]){
            cadUsdDay[day][0] = savings;
        }else if (!isCAD && savings > cadUsdDay[day][1] ){
            cadUsdDay[day][1] = savings;
        }else {
            return savings;
        }

        if (day == rates.length){
            return savings;
        }





        return -1;
    }
*/

    private double exchange(double savings, double rate, boolean isCAD){
       // System.err.println(isCAD);

        if (isCAD){
            return (savings/rate)*comission;
        }
        return savings*rate*comission;
    }



    static void main() {
    Kattio io = new Kattio();

    int d = io.getInt();
    while (d != 0){
        double[] rates = new double[d];
        for (int i = 0; i < d; i++){
            rates[i] = io.getDouble();
        }
        ExchangeRates er = new ExchangeRates(rates);
        io.println(er.ans);
        d = io.getInt();
    }

    io.flush();
    io.close();

    }


}
