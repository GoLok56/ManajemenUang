package kost.golok.utility;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {

    private Formatter() {
    }

    public static String formatCurrency(Double value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp ");
        format.setMonetaryDecimalSeparator(',');
        format.setGroupingSeparator('.');
        DecimalFormat df = (DecimalFormat) nf;
        df.setDecimalFormatSymbols(format);
        return df.format(value);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }

}
