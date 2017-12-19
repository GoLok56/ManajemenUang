package io.github.golok56.manajemenuang.ui.transaction

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import io.github.golok56.manajemenuang.R
import io.github.golok56.manajemenuang.models.Transaction
import io.github.golok56.manajemenuang.utility.Formatter

import kotlinx.android.synthetic.main.item_transaction.view.*

/**
 * Custom adapter untuk mengatur bagaimana [android.widget.ListView] transaksi terisi
 *
 * @author Satria Adi Putra
 * @version 1.0
 */
class TransactionAdapter
/**
 * Constructor untuk RecordAdapter yang memanggil Parent Constructor [ArrayAdapter]
 *
 * @param context      Context dari [View] yang aktif
 * @param transactions [ArrayList] yang berisi kumpulan [Transaction]
 */
internal constructor(
        context: Context,
        transactions: List<Transaction>
) : ArrayAdapter<Transaction>(context, 0, transactions) {

    /**
     * @param pos    Posisi item
     * @param view   [View] yang akan diisi
     * @param parent Parent yang akan menjadi root view
     * @return [View] yang telah diisi
     */
    override fun getView(pos: Int, view: View?, parent: ViewGroup): View {
        val transaction = getItem(pos)
        var mView = view
        if (mView == null) {
            mView = LayoutInflater
                    .from(context)
                    .inflate(R.layout.item_transaction, parent, false)
        }

        mView?.tvDate?.text = transaction.date
        mView?.tvNominal?.text = Formatter.formatCurrency(transaction.amount)
        mView?.tvType?.text = transaction.typeText

        return mView!!
    }



}
