package com.amaoamao.hsq.bookeeping.Utils;

import java.util.regex.Pattern;

/**
 * Created by mao on 17-2-28.
 */

public class Utils {
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }
}
