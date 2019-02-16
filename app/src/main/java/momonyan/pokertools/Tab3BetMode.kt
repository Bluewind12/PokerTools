package momonyan.pokertools

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.InputFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.tab3_bet_layout.view.*

class Tab3BetMode : Fragment() {
    //表示用レイアウト
    private lateinit var viewLayout: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewLayout = inflater.inflate(R.layout.tab3_bet_layout, container, false)

        //入力制限
        val inputFilter = InputFilter { source, start, end, dest, dstart, dend ->
            if (source.toString().matches("[0-9]+".toRegex())) {
                source
            } else {
                ""
            }
        }
        viewLayout.haveCoinsEditText.filters = arrayOf(inputFilter)
        viewLayout.baseBetCoinEditText.filters = arrayOf(inputFilter)

        viewLayout.betModeStartButton.setOnClickListener {
            val haveCoin = if (viewLayout.haveCoinsEditText.text.toString() != "") {
                viewLayout.haveCoinsEditText.text.toString().toInt()
            } else {
                -1
            }
            val baseBetCoin = if (viewLayout.baseBetCoinEditText.text.toString() != "") {
                viewLayout.baseBetCoinEditText.text.toString().toInt()
            } else {
                -1
            }
            val playNum = when (viewLayout.betPeopleSpinner.selectedItem.toString()) {
                "1人" -> 1
                "2人" -> 2
                "3人" -> 3
                "4人" -> 4
                else -> error("スピナーエラー")
            }
            if (haveCoin == -1 && baseBetCoin == -1) {
                AlertDialog.Builder(context!!)
                    .setTitle("入力エラー")
                    .setMessage("入力されていません")
                    .setPositiveButton("OK", null)
                    .show()
            } else if (haveCoin < baseBetCoin) {
                AlertDialog.Builder(context!!)
                    .setTitle("少ないです")
                    .setMessage("基本掛けコインに対して\n初期コインが少なすぎます")
                    .setPositiveButton("OK", null)
                    .show()
            } else {
                Log.d("CheckTag", "$haveCoin : $baseBetCoin : $playNum")
            }
        }
        //表示に返却
        return viewLayout
    }
}