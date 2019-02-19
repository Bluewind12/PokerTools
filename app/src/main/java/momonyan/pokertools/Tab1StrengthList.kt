package momonyan.pokertools

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tab1_layout.view.*
import momonyan.pokertools.Role.RoleAdapter
import momonyan.pokertools.Role.RoleDataClass

class Tab1StrengthList : Fragment() {
    //表示用レイアウト
    private lateinit var viewLayout: View
    private lateinit var roleNameList: Array<String>
    private lateinit var roleNameEngList: Array<String>
    private lateinit var roleDescription: Array<String>
    private lateinit var roleDrawable: Array<Int>

    private var mDataList: ArrayList<RoleDataClass> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLayout = inflater.inflate(R.layout.tab1_layout, container, false)
        //データセット
        roleNameList = resources.getStringArray(R.array.roleListJp)
        roleNameEngList = resources.getStringArray(R.array.roleListEng)
        roleDescription = resources.getStringArray(R.array.roleDescription)
        roleDrawable = arrayOf(
            R.drawable.role_1_royal,
            R.drawable.role_2_straight_flash,
            R.drawable.role_3_four,
            R.drawable.role_4_full,
            R.drawable.role_5_flash,
            R.drawable.role_6_starate,
            R.drawable.role_7_three,
            R.drawable.role_8_two,
            R.drawable.role_9_one,
            R.drawable.role_10_high
        )
        // データ作成
        if (mDataList.isEmpty()) {
            for (i in 0 until roleNameEngList.size) {
                mDataList.add(
                    RoleDataClass(
                        "${roleNameEngList[i]}",
                        "${roleNameList[i]}",
                        "説明\n${roleDescription[i]}",
                        roleDrawable[i]
                    )
                )
                Log.d("TabDataSet", "Tab1:DataNum $i")
            }
        }
        // Adapter作成
        val adapter = RoleAdapter(mDataList)

        // RecyclerViewにAdapterとLayoutManagerの設定
        viewLayout.tab1RecyclerView.adapter = adapter
        viewLayout.tab1RecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return viewLayout
    }

}