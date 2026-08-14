package ETE388.Dynamic;
import Kattis.Kattio;

//dec format


public class ExchangeRates {
    private double ans = 1000;
    private double savings = 1000;
    private boolean isCAD = true; //t/f?
    private double[] rates;
    private double comission = 0.97;

    ExchangeRates(double[] rates){
        this.rates = rates;
        ans = naive(savings, isCAD, 0 );

    }



    private double naive(double savings, boolean isCAD, int day){
        if (day== rates.length){
            return savings;
        }


        return naive(exchange(savings,rates[day],isCAD),
                !isCAD,day+1);


    }


    private double exchange(double savings, double rate, boolean isCAD){


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
