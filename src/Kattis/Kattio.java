package Kattis; /** Simple yet moderately fast I/O routines.
 *
 * Example usage:
 *
 * Kattis.Kattio io = new Kattis.Kattio(System.in, System.out);
 *
 * while (io.hasMoreTokens()) {
 *    int n = io.getInt();
 *    double d = io.getDouble();
 *    double ans = d*n;
 *
 *    io.println("Answer: " + ans);
 * }
 *
 * io.close();
 *
 *
 * Some notes:
 *
 * - When done, you should always do io.close() or io.flush() on the
 *   Kattis.Kattio-instance, otherwise, you may lose output.
 *
 * - The getInt(), getDouble(), and getLong() methods will throw an
 *   exception if there is no more data in the input, so it is generally
 *   a good idea to use hasMoreTokens() to check for end-of-file.
 *
 * @author: Kattis
 */


import java.io.*;
import java.util.StringTokenizer;

public class Kattio extends PrintWriter {

    //default initialization to system.in/out so I don't have to type it in each time
    //Added by Amir Khodabakhshi 5/feb-2025
    public Kattio(){
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(System.in));
    }

    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    //Added 4/apr-25 //Amir
    //gets full line, maybe one of the other methods already does it idk
    public String getLine() {
        try {

            return r.readLine();
        } catch (IOException e) { }
        return null;
    }

    //Added 11/07-2023 // Amir Khodabakhshi
    //Fixes a bug where input size is uncertain
    public boolean ready() {
        try {
            if (r.ready()){
                return true;
            }
        }catch (IOException e){}

        return false;
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public double getDouble() {
        return Double.parseDouble(nextToken());
    }

    public long getLong() {
        return Long.parseLong(nextToken());
    }

    public String getWord() {
        return nextToken();
    }



    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}