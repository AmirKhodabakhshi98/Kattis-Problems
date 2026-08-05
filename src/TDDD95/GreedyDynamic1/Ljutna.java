package TDDD95.GreedyDynamic1;
import Kattis.*;

import java.math.BigInteger;
import java.util.*;
import java.math.*;


public class Ljutna {






        public static void main(String[] args) {
            Kattio io = new Kattio(System.in, System.out);


            long nbrOfCandies = io.getLong();
            int nbrOfChildren = io.getInt();

            long[] children = new long[nbrOfChildren];

            long totalDemand=0;

            for(int i=0; i<nbrOfChildren; i++){
                long child = io.getLong();
                children[i]=child;
                 totalDemand+=child;
            }

            Arrays.sort(children);

            int multiplier = 1;
            long temp = 0;
            int index = nbrOfChildren;

            //multiplier signifies number of kids we have iterated over so far
            //we start with the kid who demands most candy. We calculate difference between them and the kid just before times multiplier.
            //we continue this until temp reaches the number of candies we have.
            //When loop terminates we will know the index where no one before it will get any candy
            //Thus the general idea is that we want to find the point where the kids before/after wont/will get candy in this step

            for (int i=nbrOfChildren-1;i>0;i--){
                index--;
                temp += multiplier*(children[i]-children[i-1]);
                if (temp>=nbrOfCandies){
                    break;
                }
                multiplier++;
            }


            //we skip the first index kids, and sum the remainders demands.
            //subtracting by the number of candies we have and dividing by number of kids after the skip
            //gives us ceiling average demand those kids should have
            long partial =  Arrays.stream(children).skip(index).sum();
            partial-=nbrOfCandies;
            partial = Math.ceilDiv(partial,multiplier);
            for (int i = nbrOfChildren-1; i>=index;i--){
                nbrOfCandies-=children[i]-partial;
                children[i]=partial;
            }


            Arrays.sort(children);

            //now any remaining candy is distributed one at a time starting with most demanding kid
            //I think at most it'll be 1 candy / kid, but otherwise it'll just wrap around and loop again
            int j = nbrOfChildren;
            while (nbrOfCandies>0){
                j--;

                if (children[j]!=0) {
                    children[j]--;
                    nbrOfCandies--;
                }
                if (j==0){
                    j=nbrOfChildren;
                }
            }




            BigInteger anger = BigInteger.ZERO;
            for (long c:
                 children) {
              BigInteger c1 = new BigInteger(String.valueOf(c));
              anger = anger.add(c1.multiply(c1));
            }

            io.println(anger);
            io.flush();
            io.close();

        }
    }
