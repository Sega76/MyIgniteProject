package stepik.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Map<String, Long> map = Arrays.stream(br.readLine().toLowerCase().split("\\W"))

//        Map<String, Long> map =
//                Stream.of(str.split("\\W"))
                .filter(s -> s.length() > 0)
                .map(String::toLowerCase)

                .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                        )
                );

        map.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(s->s.getKey())
                .forEach(System.out::println);
    }
}

