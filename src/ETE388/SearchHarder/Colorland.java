package ETE388.SearchHarder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Colorland {




    private static HashMap<String,Boolean> resetMap(){
        HashMap<String, Boolean> visited = new HashMap<>();
        visited.put("Blue",false);
        visited.put("Orange",false);
        visited.put("Pink",false);
        visited.put("Green",false);
        visited.put("Red",false);
        visited.put("Yellow",false);

        return visited;
    }


    private static void naive(String[] squares){
        HashMap<String, Boolean> visited = resetMap();

        int steps = 0;
        int maxStep = 0;
        int currStep = 0;
        boolean bool = false;

        for (int i=0; i<squares.length;i++){
            bool = false;
            if (!visited.get(squares[i])){
                visited.put(squares[i],true);
                currStep = i;
            }


            if (!visited.containsValue(false)){
                visited = resetMap();
                steps++;
                bool = true;
                //i=currStep;
            }
            if (i==squares.length-1){
                if (currStep != squares.length-1){
                    visited = resetMap();
                    i=currStep;
                    steps++;
                } else if (!bool) {
                    steps++;
                }
            }
        }
        System.out.println(steps);
    }


    enum colors{
        Blue, Orange, Pink, Green, Red, Yellow
    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();

        Set<Long> set = new HashSet<>();
        String[] squares = new String[n];

        for (int i=0; i<n; i++){
            squares[i] = scanner.next();
        }
        naive(squares);



    }
}
