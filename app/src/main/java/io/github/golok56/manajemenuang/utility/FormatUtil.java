package io.github.golok56.manajemenuang.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {

    private FormatUtil() {
    }

    public static String formatCurrency(double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp ");
        format.setMonetaryDecimalSeparator(',');
        format.setGroupingSeparator('.');
        DecimalFormat df = (DecimalFormat) nf;
        df.setDecimalFormatSymbols(format);
        return df.format(value);
    }

    public static String formatDate(String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date());
    }

}
