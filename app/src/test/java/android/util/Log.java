package android.util;

public class Log {

    public static int d(String s1, String s2) {
        System.out.println(s1 + s2);
        return 0;
    }

    public static int e(String s1, String s2, Throwable msg) {
        System.out.println(s1 + s2 + msg);
        return 0;
    }

    public static int i(String s1, String s2) {
        System.out.println(s1 + s2);
        return 0;
    }

    // add other methods if required...
}