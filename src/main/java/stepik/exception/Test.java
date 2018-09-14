package stepik.exception;

import java.util.stream.Stream;

public class Test {

    public static void main(String[] args) {

        CaseInsensitiveString cIS = new CaseInsensitiveString("Sega");
        String s = "sega";
        System.out.println(cIS.equals(s));
        System.out.println(s.equals(cIS));

//            System.out.println(getCallerClassAndMethodName());
//            anotherMethod();
        }


    private static void anotherMethod() {
        System.out.println(getCallerClassAndMethodName());
    }


    public static String getCallerClassAndMethodName() {
//        System.out.println("start");
        String s = null;
        StackTraceElement[] steckTrace = null;

        steckTrace = (new Exception()).getStackTrace();
        if (steckTrace.length<3) return null;
//                Stream.of(steckTrace).forEach(System.out::println);
        s = steckTrace[2].getClassName() + "#" + steckTrace[2].getMethodName();


        return s;
    }
}
