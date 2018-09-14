package stepik.collection;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CollMain {

    public static void main(String[] args) {

        System.out.println("".split(" ").length);

//        Set<Integer> sInt3 =symmetricDifference(new HashSet<>(Arrays.asList(1,2,3)), new HashSet<>(Arrays.asList(0,1,2)));
//        System.out.println(sInt3.toString());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        List<String> list = new ArrayList<>();
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (line!=null) {
            String[] arr = line.split(" ");
            for (int i = 1; i < arr.length; i += 2) {
                list.add(arr[i]);
            }
        }
        Collections.reverse(list);
        if (list.size() > 1) list.stream().forEach(s -> System.out.print(s + " "));


    }

    public static <T> Set<T> symmetricDifference(Set<? extends T> set1, Set<? extends T> set2) {
        Collection<T> set11 = new HashSet<>(set1);
        Collection<T> set12 = new HashSet<>(set2);
        Collection<T> set13 = new HashSet<>(set1);
        set11.addAll(set12);
        set13.retainAll(set12);
        set11.removeAll(set13);
        return (Set<T>) set11;
    }
}
