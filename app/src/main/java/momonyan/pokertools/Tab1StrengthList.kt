package momonyan.pokertools

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tab1_layout.view.*

class Tab1StrengthList : Fragment() {
    //表示用レイアウト
    private lateinit var viewLayout: View
    private lateinit var roleNameList: Array<String>
    private lateinit var roleNameEngList: Array<String>

    private var mDataList: ArrayList<RoleDataClass> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLayout = inflater.inflate(R.layout.tab1_layout, container, false)
        //データセット
        roleNameList = resources.getStringArray(R.array.roleListJp)
        roleNameEngList = resources.getStringArray(R.array.roleListEng)
        // データ作成
        for (i in 1..20) {
            mDataList.add(RoleDataClass("eng $i", "jp $i", "test $i"))
            Log.d("TestTag", "$i")
        }
        // Adapter作成
        val adapter = RoleAdapter(this, mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        viewLayout.tab1RecyclerView.adapter = adapter
        viewLayout.tab1RecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return viewLayout
    }

}