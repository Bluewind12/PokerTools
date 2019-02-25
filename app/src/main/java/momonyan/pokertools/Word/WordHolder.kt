package momonyan.pokertools.Word

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.word_card_layout.view.*

class WordHolder(mView: View) : RecyclerView.ViewHolder(mView) {
    val mWordEng: TextView = mView.wordEngText  //英語タイトル
    val mWordJp: TextView = mView.wordJpText    //日本語タイトル
    val mWordDescription: TextView = mView.wordDesText  //説明

    val mWordCardView: CardView = mView.cardView2   //カードビュー
}