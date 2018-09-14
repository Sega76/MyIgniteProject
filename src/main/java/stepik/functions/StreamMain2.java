package stepik.functions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMain2 {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed sodales consectetur purus at faucibus." +
                " Donec mi quam, tempor vel ipsum non, faucibus suscipit massa. " +
                "Morbi lacinia velit blandit tincidunt efficitur. Vestibulum eget metus imperdiet sapien laoreet faucibus. " +
                "Nunc eget vehicula mauris, ac auctor lorem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                "Integer vel odio nec mi tempor dignissim.";

            // write your code here
            HashMap<String, Integer> result = new HashMap<>();
            // Создаем отображение каждого слова на его количество
            (new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)))
                    .lines()
                    .flatMap(l -> Stream.of(l.split("[\\p{Punct}\\s]+")))
                    .map(w -> w.toLowerCase())
                    .forEach(w -> {
                        if(result.containsKey(w)) result.put(w, result.get(w) + 1);
                        else result.put(w, 1);
                    });
            // Сортируем отображение по количеству слов в обратном порядке, по словам в лексиграфическом
            // и выводим первых 10 элементов в stdout
            result.entrySet()
                    .stream()
                    .sorted((e1, e2) -> {
                        if(e1.getValue() == e2.getValue()) return e1.getKey().compareTo(e2.getKey());
                        else return e2.getValue().compareTo(e1.getValue());})
                    .limit(10)
                    .forEach(e -> System.out.println(e.getKey()));
        }

}
