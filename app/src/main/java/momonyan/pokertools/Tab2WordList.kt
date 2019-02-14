package momonyan.pokertools

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class Tab2WordList : Fragment() {
    //表示用レイアウト
    private lateinit var viewLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLayout = inflater.inflate(R.layout.tab2_layout, container, false)

        //表示に返却
        return viewLayout
    }
}