package momonyan.pokertools

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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
        viewLayout.haveCoinsEditText2.filters = arrayOf(inputFilter)
        viewLayout.haveCoinsEditText3.filters = arrayOf(inputFilter)
        viewLayout.haveCoinsEditText4.filters = arrayOf(inputFilter)
        viewLayout.baseBetCoinEditText.filters = arrayOf(inputFilter)

        viewLayout.betModeStartButton.setOnClickListener {
            val intent = Intent(context, BetModeActivity::class.java)
            val haveCoin = if (viewLayout.haveCoinsEditText.text.toString() != "") {
                viewLayout.haveCoinsEditText.text.toString().toInt()
            } else {
                100
            }
            val haveCoin2 = if (viewLayout.haveCoinsEditText2.text.toString() != "") {
                viewLayout.haveCoinsEditText2.text.toString().toInt()
            } else {
                100
            }
            val haveCoin3 = if (viewLayout.haveCoinsEditText3.text.toString() != "") {
                viewLayout.haveCoinsEditText3.text.toString().toInt()
            } else {
                100
            }
            val haveCoin4 = if (viewLayout.haveCoinsEditText4.text.toString() != "") {
                viewLayout.haveCoinsEditText4.text.toString().toInt()
            } else {
                100
            }
            val baseBetCoin = if (viewLayout.baseBetCoinEditText.text.toString() != "") {
                viewLayout.baseBetCoinEditText.text.toString().toInt()
            } else {
                1
            }
            val playNum = when (viewLayout.betPeopleSpinner.selectedItem.toString()) {
                "1人" -> 1
                "2人" -> 2
                "3人" -> 3
                "4人" -> 4
                else -> error("スピナーエラー")
            }

            Log.d("CheckTag", "$haveCoin : $baseBetCoin : $playNum")
            intent.putExtra("Player", playNum)
            intent.putExtra("BaseBet", baseBetCoin)
            intent.putExtra("Coin", haveCoin)
            intent.putExtra("Coin2", haveCoin2)
            intent.putExtra("Coin3", haveCoin3)
            intent.putExtra("Coin4", haveCoin4)
            startActivity(intent)

        }
        //表示に返却
        return viewLayout
    }
}