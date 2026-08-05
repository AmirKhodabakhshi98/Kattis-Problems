package TDDD95.GreedyDynamic1;
import Kattis.*;

import java.math.BigInteger;
import java.util.Arrays;

public class LjutnaWhile {


    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        long candyTotal = io.getLong();
        int numOfChildren = io.getInt();
        long[] children = new long[ numOfChildren];



        for (int i=0; i< numOfChildren; i++){
            children[i]=io.getLong();
        }
        Arrays.sort(children);

        int i = numOfChildren-1;
        while (candyTotal>0){
            if (i==0){
                candyTotal--;
                children[0]--;
                i=numOfChildren-1;
                continue;
            }

            long temp1 = children[i];
            long temp2 = children[i-1];
            long temp3 = temp1-temp2;
            if (temp3>0 ){
                if (candyTotal-temp3<0){
                    children[i]--;
                    candyTotal--;
                }else {
                    children[i] -= temp3;
                    candyTotal -= temp3;
                }
            }
            else {
                children[i]--;
                candyTotal--;
                i--;
            }


        }



        //long anger = 0;
        BigInteger anger = BigInteger.ZERO;
        for (long child: children)
        {
            anger = anger.add(BigInteger.valueOf((long) Math.pow(child,2)));
        }

        io.println(anger);
        io.flush();
        io.close();

    }




}




