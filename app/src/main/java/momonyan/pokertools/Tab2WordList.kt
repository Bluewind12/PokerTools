package momonyan.pokertools

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tab2_layout.view.*
import momonyan.pokertools.Word.WordAdapter
import momonyan.pokertools.Word.WordDataClass

class Tab2WordList : Fragment() {
    //表示用レイアウト
    private lateinit var viewLayout: View
    private lateinit var wordNameList: Array<String>
    private lateinit var wordNameEngList: Array<String>

    private var mDataList: ArrayList<WordDataClass> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLayout = inflater.inflate(R.layout.tab2_layout, container, false)
        //データセット
        wordNameList = resources.getStringArray(R.array.roleListJp)
        wordNameEngList = resources.getStringArray(R.array.roleListEng)
        // データ作成
        for (i in 1..20) {
            mDataList.add(WordDataClass("eng $i", "jp $i", "test $i"))
            Log.d("TabDataSet", "Tab2:DataNum $i")
        }
        // Adapter作成
        val adapter = WordAdapter(mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        viewLayout.tab2RecyclerView.adapter = adapter
        viewLayout.tab2RecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return viewLayout
    }

}