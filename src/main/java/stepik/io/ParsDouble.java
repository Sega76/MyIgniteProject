package stepik.io;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParsDouble {
    public static void main(String[] args) {
        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> list= new ArrayList<>();
        double i = 0;
        String line = null;
        String line2 = "5";
        try {
//
        while((line=bReader.readLine())!=null){
            list.addAll(Arrays.asList(line.split(" ")));
        System.out.println(list.toString());
        }}
        catch (IOException e){
//
        }

        for (String s : list) {
            try {
                i += Double.parseDouble(s);
            } catch (Exception e) {
                continue;
            }
        }
        System.out.format("%.6f", i);
    }
}
