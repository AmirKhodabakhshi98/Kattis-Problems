package TDDD95.GreedyDynamic1;
import Kattis.*;

import java.util.*;
import java.util.stream.IntStream;

public class SpiderMansWorkout {
    int M;
    int[] distances;
    int height;
    Kattio io;
    char[] path;
    int sum;
    public SpiderMansWorkout(Kattio io){
        M = io.getInt();
        height=0;
        this.io=io;
        distances = new int[M];
        path = new char[M];
        for (int i=0; i<M;i++){
            distances[i]=io.getInt();
        }

        sum = IntStream.of(distances).sum();
        solver();
        //iterativeSolver();
        if (bestH!= Integer.MAX_VALUE){
            io.println(String.valueOf(bestPath));
           // for (char c:
          //       bestP) {
        //        io.print(c);
      //      }
    //        io.println();
        }else io.println("IMPOSSIBLE");

    }

    int bestH = Integer.MAX_VALUE;
    char[] bestPath;
    ArrayList<Character> bestP;



    private class State{
        @Override
        public boolean equals(Object obj) {
            State s = (State) obj;
            return (this.h==s.h && this.i==s.i && this.maxH==s.maxH);
        }

        int h;
        int i;
        int maxH;
        ArrayList<Character> p;
        public State(int h, int i, int maxH, ArrayList<Character> p){
            this.h=h;
            this.i=i;
            this.maxH=maxH;
            this.p=p;
        }
    }

    Set<State> visited = new HashSet<>();

    Stack<State> stack = new Stack<>();
    private void iterativeSolver(){
        stack.push(new State(0,0,0, new ArrayList<>()));

        while (!stack.empty()){
            State state = stack.pop();
            visited.add(state);
     //       io.println(state.h);
            if (state.h<0){
           //     io.println("test1");
                continue;
            }
            if (state.h>(Math.floorDiv(sum,2))){
          //      io.println("test2");
                continue;
            }

            if (state.h>bestH){
          //      io.println("test3");
                continue;
            }

            if (state.h>state.maxH){
            //    io.println("test4");

                state.maxH=state.h;
            }
            if (state.i==M){
           //     io.println("test5");

                if (state.h==0){
                 //   io.println("test6");

                    //io.println("win");
                    if (state.maxH<bestH){
                      //  io.println("test7");
                        bestH=state.maxH;
                        bestPath = Arrays.copyOf(path,path.length);
                        bestP=state.p;
                    }
                }
                continue;
            }

            //path[state.i]='D';
            state.p.add('D');
            stack.push(new State(state.h-distances[state.i],state.i+1, state.maxH,new ArrayList(state.p)));
            state.p.remove(state.p.size()-1);
            state.p.add('U');
            stack.push(new State(state.h+distances[state.i],state.i+1, state.maxH,new ArrayList(state.p)));

        }

    }

    private void solver(){
        solver(0,0,0);

    }

    private void solver(int h,  int i, int maxH){
     //   io.println(h);
        if (h<0){
            return;
        }
        if (h>(Math.floorDiv(sum,2))){
            return;
        }

        if (h>bestH){
            return;
        }

        if (h>maxH){
            maxH=h;
        }
        if (i==M){
            if (h==0){
                //io.println("win");
                if (maxH<bestH){
                    bestH=maxH;
                    bestPath = Arrays.copyOf(path,path.length);

                }
            }
            return;
        }


        path[i]='U';
        solver(h+distances[i],i+1, maxH);
        path[i]='D';
        solver(h-distances[i],i+1,maxH);




    }

    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);
        int N = io.getInt();
        for (int i=0; i<N;i++ ){
            new SpiderMansWorkout(io);
            io.flush();
        }
        io.close();
    }


}
