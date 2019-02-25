package momonyan.pokertools.Word

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import momonyan.pokertools.R

class WordAdapter(private val mValues: ArrayList<WordDataClass>) : RecyclerView.Adapter<WordHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        //レイアウトの設定(inflate)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_card_layout, parent, false)
        //Holderの生成
        return WordHolder(view)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        val item = mValues[position]
        holder.mWordEng.text = item.eng
        holder.mWordJp.text = item.jp
        holder.mWordDescription.text = item.description


        holder.mWordCardView.setOnLongClickListener {
            true
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}