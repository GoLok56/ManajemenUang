package kost.golok.object.utility;

import java.text.DecimalFormat;

public class Formatter {

    private Formatter() {
    }

    public static String format(Double value) {
        DecimalFormat formatter = new DecimalFormat("Rp ###,###.00");
        return formatter.format(value);
    }

}
