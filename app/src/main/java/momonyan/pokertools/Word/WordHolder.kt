package momonyan.pokertools.Word

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.word_card_layout.view.*

class WordHolder(mView: View) : RecyclerView.ViewHolder(mView) {
    val mWordEng: TextView = mView.wordEngText
    val mWordJp: TextView = mView.wordJpText
    val mWordDescription: TextView = mView.wordDesText

}