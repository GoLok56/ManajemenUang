package io.github.golok56.manajemenuang.utility

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    fun formatCurrency(value: Double?): String {
        val nf = NumberFormat.getCurrencyInstance()
        val format = DecimalFormatSymbols()
        format.currencySymbol = "Rp "
        format.monetaryDecimalSeparator = ','
        format.groupingSeparator = '.'
        val df = nf as DecimalFormat
        df.decimalFormatSymbols = format
        return df.format(value)
    }

    fun formatDate(dateFormat: String): String {
        val format = SimpleDateFormat(dateFormat)
        return format.format(Date())
    }

}
