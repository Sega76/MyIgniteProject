package stepik;

import stepik.oop.Label;
import stepik.oop.TextAnalyzer;
import stepik.oop.impl.NegativeTextAnalyzer;
import stepik.oop.impl.SpamAnalyzer;
import stepik.oop.impl.TooLongTextAnalyzer;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.lang.System.*;

public class Test {

    public static void main(String[] args) throws IOException {

        char ch = 'Ы';
        BufferedReader br =  Files.newBufferedReader(Paths.get("Ы"), Charset.forName("UTF-8"));
        byte[] arr = "Ы".getBytes();
                for(byte b:arr) System.out.print(b);
//        byte b = (byte) ch;

//        System.out.println(b);

        }
//    }



//        CharSequence charSequence = new AsciiCharSequence("Sega".getBytes());
//        System.out.println(charSequence.length());
//        System.out.println(charSequence.toString());



//        String[] roles = {
//                "Городничий", "Аммос Федорович",
//                "Артемий Филиппович",
//                "Лука Лукич"};
//        String[] textLines = {"Городничий: Я пригласил вас, господа, с тем, чтобы сообщить вам пренеприятное известие: к нам едет ревизор.",
//                "Аммос Федорович: Как ревизор?",
//                "Артемий Филиппович: Как ревизор?",
//                "Городничий: Ревизор из Петербурга, инкогнито. И еще с секретным предписаньем.",
//                "Аммос Федорович: Вот те на!",
//                "Артемий Филиппович: Вот не было заботы, так подай!",
//                "Лука Лукич: Господи боже! еще и с секретным предписаньем!"};
//        String s = printTextPerRole(roles,textLines);
//        out.println(s);
//        System.out.println(s);
////float fl = 1L;
//long l=4f;


//        MessageDigest md = null;
//        try {
//            md = MessageDigest.getInstance("MD5");
//            byte[] digest = new byte[0];
//            digest = md.digest("abracadabra".getBytes("UTF-8"));
//            for (byte b : digest) {
//                System.out.printf("%02x", b);
//            }
//        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }


//    }

    /**
     * Flips one bit of the given <code>value</code>.
     *
     * @param value    any number
     * @param bitIndex index of the bit to flip, 1 <= bitIndex <= 32
     * @return new value with one bit flipped
     */
    public static int flipBit(int value, int bitIndex) {


        return value ^ (int) Math.pow(2, bitIndex - 1);
    }

    public static char charExpression(int a) {
        return (char) ((char) '\\' + a);
    }


    public static boolean isPalindrome(String text) {
        String reg = "[^a-zA-Z0-9]";
        String orig = new StringBuffer(text).reverse().toString().replaceAll(reg, "").toLowerCase();
//        System.out.println(orig);
        String s1 = text.replaceAll(reg, "").toLowerCase();
//        System.out.println(s1);
        return (orig.equals(s1)); // your implementation here
    }

    public static BigInteger factorial(int value) {
        BigInteger ret = BigInteger.ONE;
        for (int i = 1; i <= value; ++i) ret = ret.multiply(BigInteger.valueOf(i));
        return ret;
    }

    public static boolean isPowerOfTwo(int value) {
        boolean b = Integer.bitCount(Math.abs(value)) == 1;
        return b;
    }

    public static int[] mergeArrays(int[] a1, int[] a2) {
        int[] arr = new int[a1.length + a2.length];
        for (int i = 0; i < a1.length; i++) {
            arr[i] = a1[i];
        }
        for (int i = a1.length; i < arr.length; i++) {
            arr[i] = a2[i - a1.length];
        }
        Arrays.sort(arr);

        return arr; // your implementation here
    }

    public static String printTextPerRole(String[] roles, String[] textLines) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < roles.length; i++) {
            result.append(roles[i] + "\n");
            for (int j = 0; j < textLines.length; j++) {
                if (textLines[j].startsWith(roles[i] + ":")) {
                    result.append(j+1 + ")" + textLines[j].replaceFirst(roles[i] + ":", "") + "\n");
                }
            } {
                if (i!=roles.length-1) result.append("\n=");
            }
        }

        return result.toString();
    }

    public static void moveRobot(Robot robot, int toX, int toY) {
        while(robot.getDirection()!= Direction.UP) robot.turnLeft();

        System.out.println(robot.toString());
//        while (robot.getX() != toX) {
            int xStep = Math.abs(robot.getX() - toX);
        System.out.println("xStep "+xStep);
            if (robot.getX() < toX) {
                robot.turnRight();
                while (xStep > 0) {
                    robot.stepForward();
                    xStep--;
                }
                robot.turnLeft();
            } else {
                robot.turnLeft();
                while (xStep > 0) {
                    robot.stepForward();
                    xStep--;
                }
                robot.turnRight();
            }
//        }
//        while (robot.getY() != toY) {
            int yStep = Math.abs(robot.getY() - toY);
        System.out.println("yStep "+yStep);
            if (robot.getY() < toY) {
                while (yStep > 0) {
                    robot.stepForward();
                    yStep--;
                }
            } else {
                robot.turnLeft();
                robot.turnLeft();
                while (yStep > 0) {
                    robot.stepForward();
                    yStep--;
                }
                robot.turnRight();
                robot.turnRight();
            }
        System.out.println(robot.toString());
            assert robot.getY()==toY;
            assert robot.getX()==toX;
//        }

    }

    public static double integrate(DoubleUnaryOperator f, double a, double b) {
        double step = Math.pow(10,-6);
        System.out.println("step="+step);
        double d = 0;
        for (double i = a; i < b ; i+=step) {
//            System.out.println(f.applyAsDouble(i));
            d+=(step*f.applyAsDouble(i));
        }


        return d;
    }

    public static double sqrt(double x) {
        if (x<0) throw new IllegalArgumentException("Expected non-negative number, got "+x);

        return Math.sqrt(x); // your implementation here
    }


}
