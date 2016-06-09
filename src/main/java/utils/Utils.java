package utils;

/**
 * Created by forte on 09/06/16.
 */
public class Utils {
    public static boolean stringValido(String str, int longitud) {
        return str != null && !str.isEmpty() && str.length() <= longitud;
    }
}
