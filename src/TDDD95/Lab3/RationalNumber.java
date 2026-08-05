package TDDD95.Lab3;
//Kattis problem rational arithmethic

import Kattis.Kattio;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * Amir Khodabakhshi - 15/apr-2025
 * Class to represent rational numbers. Constructor takes two longs/ints representing numerator/denominator
 * (named in the class after Täljare/Nämnare as I can never remember which is which in English).
 * Supports basic arithmethics with add, subtract, divide and multiply operators, which all return a new RationalNumber class with the result
 * of the operation.
 * Additionaly, supports the six common comparison operators, verified using the comparisonTest method to sort an array
 * using the implemented comparison operators.
 *
 * Upon construction, the rational number is always simplified, using the modolu version of Euclids algorithm, thus
 * any resulting class from an operation is always also in simplified form. Also means that comparisons methods can be done easier
 * as the class objects are always in simplified form, downside being that we can't modify the value inside a class obj once made.
 * The minus sign is also always stored in the numerator.
 *
 * The toString method is made for the Kattis problem rational arithmethic.
 * Input assumptions are also made with that problem in mind, most importantly that division with 0 won't happen.
 *
 *  Sources: https://en.wikipedia.org/wiki/Euclidean_algorithm
 */


public class RationalNumber implements Comparable<RationalNumber> {

    private long t;
    private long n;

    public RationalNumber(long t, long n){
        this.t = t;
        this.n = n;
        simplify();
    }


    //simplifies the number
    private void simplify(){

        long gcdNum = gcd(n,t);
        n /= gcdNum;
        t /= gcdNum;
        if (n<0) {
            n = n * -1;
            t = t * -1;
        }
    }

    //Modolu version of Euclids algorithm
    private long gcd(long a, long b){
        if (b==0) {
            return a;
        }
        return gcd(b, a % b);
    }


    public RationalNumber add(RationalNumber rn){
        long tSum = t*rn.n + n*rn.t;
        long nSum = n*rn.n;
        return new RationalNumber(tSum,nSum);
    }


    public RationalNumber subtract(RationalNumber rn){
        long tDiff = t*rn.n - n*rn.t;
        long nDiff = n*rn.n;
        return new RationalNumber(tDiff,nDiff);
    }


    public RationalNumber multiply(RationalNumber rn){
        return new RationalNumber( t*rn.t,n*rn.n);
    }

    public RationalNumber divide(RationalNumber rn){
        long tDiv = t*rn.n;
        long nDiv = n*rn.t;
        return new RationalNumber(tDiv,nDiv);
    }

    public boolean equal(RationalNumber rn){
        return t == rn.t && n == rn.n;
    }

    //simulating getting the same denominatr, then comparing the numerator
    public boolean greater(RationalNumber rn){
        return  t*rn.n>rn.t*n;
    }

    public boolean notEqual(RationalNumber rn){
        return !equal(rn);
    }

    public boolean less(RationalNumber rn){
        return !greater(rn);
    }
    public boolean greaterEqual(RationalNumber rn){
        return equal(rn) || greater(rn);
    }
    public boolean lessEqual(RationalNumber rn){
        return equal(rn) || less(rn);
    }

    @Override
    //Formatting based on rational arithmethic kattis problem
    public String toString(){
        return t + " / " + n;
    }




    private void comparisonTest(){
        int n = 10;
        int lowerBound = -10;
        int upper = 10;
        RationalNumber[] arr = new RationalNumber[n];
        Random random = new Random(1234);
        for (int i = 0;i < n;i++){
            int denom;
            do {
                denom = random.nextInt(lowerBound, upper);
            } while (denom == 0);
            arr[i] = new RationalNumber(random.nextInt(lowerBound, upper), denom);
        }
        Arrays.sort(arr, RationalNumber::compareTo);
        double[] test = new double[n];
        for (int i = 0;i < n;i++){
            test[i] = (double) arr[i].t /arr[i].n;
        }
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(test));

    }

    @Override
    public int compareTo(RationalNumber o) {
        if (equal(o) && greaterEqual(o) && lessEqual(o)){
            return 0;
        }else if (notEqual(o) && greater(o) && !less(o) ){
            return 1;
        }

        return -1;
    }


    public static void main(String[] args) {
        // RationalNumber rn = new RationalNumber(1,1);
        //  rn.comparisonTest();

        Kattio io = new Kattio();
        int n = io.getInt();
        for (int i = 0; i < n; i++) {
            RationalNumber rn1 = new RationalNumber(io.getInt(), io.getInt());
            char operation = io.getWord().charAt(0);
            RationalNumber rn2 = new RationalNumber(io.getInt(), io.getInt());

            if (operation == '+') {
                io.println(rn1.add(rn2).toString());
            }else if (operation == '-') {
                io.println(rn1.subtract(rn2).toString());
            }else if (operation == '*') {
                io.println(rn1.multiply(rn2).toString());
            }else {
                io.println(rn1.divide(rn2).toString());
            }

        }
        io.flush();
        io.close();

    }

}
