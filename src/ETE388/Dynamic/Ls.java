package ETE388.Dynamic;

public class Ls {




    private static boolean Ls(char[] s, char[] p){

        boolean[][] arr = new boolean[s.length+1][p.length+1];
        arr[0][0]=true;

        for (int j =1; j<=p.length;j++){
            if (p[j-1]=='*'){
                arr[0][j]=arr[0][j-1];
            }
        }


        for (int i = 1; i<s.length+1;i++ ){
            for (int j=1; j<p.length+1; j++){

                if (p[j-1]==s[i-1]){
                    arr[i][j]=arr[i-1][j-1];
                }

                else if (p[j-1]=='*'){
                    if (arr[i-1][j] || arr[i][j-1]){
                        arr[i][j] = true;
                    }
                }
            }
        }

        return arr[s.length][p.length];
    }




    public static void main(String[] args) {
        Kattio io = new Kattio(System.in, System.out);

        char[] p = io.getWord().toCharArray();
        int n = io.getInt();
      //  String[]files = new String[n];

        for (int i = 0; i<n; i++){
            String s = io.getWord();
            if (Ls(s.toCharArray(),p)){
                io.println(s);
            }
        }
        io.flush();
        io.close();


    }
}
