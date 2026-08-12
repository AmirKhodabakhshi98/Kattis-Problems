package ETE388.Dynamic;
import Kattis.Kattio;


public class ExchangeRates {
    private double ans;
    private int savings = 1000;
    private boolean canada = true; //t/f?

    ExchangeRates(double[] rates){
        ans = rates[rates.length-1];
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
