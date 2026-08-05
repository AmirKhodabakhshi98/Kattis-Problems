package TDDD95.Seminar0;
import Kattis.*;

import java.util.HashSet;
import java.util.Set;

public class CD {




    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);


        while (true) {
            long Jack = io.getLong();
            long Jill = io.getLong();
            Set<Long> set = new HashSet<>(1000000);
            for (long i = 0; i < Jack; i++) {
                set.add(io.getLong());
            }
            long match = 0;
            for (long i = 0; i < Jill; i++) {
                if (set.contains(io.getLong())) {
                    match++;
                }
            }
            io.println(match);
            io.flush();
            io.getInt();
            io.getInt();
            if (!io.ready()){
                break;
            }
        }
        io.close();
    }
    }
