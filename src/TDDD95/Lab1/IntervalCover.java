package TDDD95.Lab1;
import Kattis.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class IntervalCover {


    private static void intervalCover(double[]target,double[][]intervals){
        Arrays.sort(intervals, Comparator.comparingDouble(a -> a[0]));
        LinkedList<Integer[]> output = new LinkedList<>();
        double start = target[0];
        double end =start-1;
        int cnt = 0;

        for (int i=0; i<intervals[0].length;){
            if (intervals[i][0]<=start){
                end=Math.max(end,intervals[i++][1]);
            }else {
                start=end;
                cnt++;
            }
            if (intervals[i][0]>end || end>=target[1]){
                break;
            }
        }
        if (end<target[1]){
            System.out.println(-1);
        }else {
            System.out.println(cnt);
        }
    }


    public static void main(String[] args) {
        double[][] arr = {{-0.9,-0.1},{-0.2,2},{-0.7,1}};
        intervalCover(new double[]{-0.5,1},arr);
        System.out.println(Arrays.deepToString(arr));

    }


}
