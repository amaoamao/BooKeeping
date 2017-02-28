package com.amaoamao.hsq.bookeeping.Utils;

import com.amaoamao.hsq.bookeeping.Entity.Debt;
import com.annimon.stream.Stream;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by mao on 17-2-28.
 */

public class Utils {
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }


    public static double sumAll(List<Debt> all) {
        return Stream.of(all).mapToDouble(debt -> (debt.getIn() ? 1.0 : -1.0) * debt.getAmount()).sum();
    }

    public static double inThisMonth(List<Debt> all, Calendar c) {
        SimpleDateFormat formateYYMM = new SimpleDateFormat("YYYY-MM", Locale.getDefault());
        return Stream.of(all).filter(debt -> debt.getIn() && formateYYMM.format(debt.getTimeCreated()).equals(formateYYMM.format(c.getTime()))).mapToDouble(Debt::getAmount).sum();
    }

    public static double outThisMonth(List<Debt> all, Calendar c) {
        SimpleDateFormat formateYYMM = new SimpleDateFormat("YYYY-MM", Locale.getDefault());

        return Stream.of(all).filter(debt -> !debt.getIn() && formateYYMM.format(debt.getTimeCreated()).equals(formateYYMM.format(c.getTime())))
                .mapToDouble(debt -> -1.0 * debt.getAmount()).sum();
    }
}
