package momonyan.pokertools

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.bet_mode_layout.*

class BetModeActivity : AppCompatActivity() {
    //プレイヤー関連
    private lateinit var player1buttons: List<Button>
    private lateinit var player2buttons: List<Button>
    private lateinit var player3buttons: List<Button>
    private lateinit var player4buttons: List<Button>
    //テキスト
    private lateinit var textViews: List<TextView>
    private var playerNum: Int = 0
    private var nextPlayer: Int = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 戻るボタンの処理
            backAlertDialogCreate()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bet_mode_layout)
        //バツボタンの設定、戻るボタンの際の確認
        endButton.setOnClickListener {
            backAlertDialogCreate()
        }
        endButton2.setOnClickListener {
            backAlertDialogCreate()
        }


        var nowPlayer = 0
        player1buttons = listOf(callButton1, foldButton1, raiseButton1)
        player2buttons = listOf(callButton3, foldButton3, raiseButton3)
        player3buttons = listOf(callButton2, foldButton2, raiseButton2)
        player4buttons = listOf(callButton4, foldButton4, raiseButton4)
        textViews = listOf(betCoinButton1, betCoinButton3, betCoinButton2, betCoinButton4)

        playerNum = intent.getIntExtra("Player", 1)
        val baseBet = intent.getIntExtra("BaseBet", 1)
        val haveCoin = mutableListOf(0, 0, 0, 0)


        if (playerNum <= 3) {
            player4buttons.forEach {
                it.visibility = View.INVISIBLE
            }
            textViews[3].visibility = View.INVISIBLE
        }
        if (playerNum <= 2) {
            player3buttons.forEach {
                it.visibility = View.INVISIBLE
            }
            textViews[2].visibility = View.INVISIBLE
        }
        if (playerNum <= 1) {
            player2buttons.forEach {
                it.visibility = View.INVISIBLE
            }
            textViews[1].visibility = View.INVISIBLE

            betCoinsText2.visibility = View.INVISIBLE
            endButton.visibility = View.INVISIBLE
        }
        for (i in 0 until 4) {
            haveCoin[i] = intent.getIntExtra("Coin", 100)
        }

        for (i in 0 until 4) {
            textViews[i].text = getString(R.string.playerBet, haveCoin[i])
        }
        betCoinsText.text = getString(R.string.nowBet, baseBet)
        betCoinsText2.text = getString(R.string.nowBet, baseBet)

        setBetButtons(player1buttons)
        setBetButtons(player2buttons)
        setBetButtons(player3buttons)
        setBetButtons(player4buttons)
        setTable(0)
    }

    private fun backAlertDialogCreate() {
        AlertDialog.Builder(this)
            .setTitle("終了します")
            .setMessage("終了してもよろしいですか?")
            .setPositiveButton("はい") { dialog, which ->
                finish()
            }
            .setNegativeButton("いいえ", null)
            .show()
    }

    private fun setTable(spone: Int) {
        if (spone != 0) {
            setEnabledLists(player1buttons, false)
        } else {
            setEnabledLists(player1buttons, true)
        }
        if (spone != 1) {
            setEnabledLists(player2buttons, false)
        } else {
            setEnabledLists(player2buttons, true)
        }
        if (spone != 2) {
            setEnabledLists(player3buttons, false)
        } else {
            setEnabledLists(player3buttons, true)

        }
        if (spone != 3) {
            setEnabledLists(player4buttons, false)
        } else {
            setEnabledLists(player4buttons, true)
        }
    }

    private fun setEnabledLists(list: List<Button>, bool: Boolean) {
        list.forEach {
            it.isEnabled = bool
        }
    }

    private fun setBetButtons(buttons: List<Button>) {
        buttons[0].setOnClickListener {
            nextPlayer = (nextPlayer + 1) % playerNum
            setTable(nextPlayer)
        }
        buttons[1].setOnClickListener {
            nextPlayer = (nextPlayer + 1) % playerNum
            setTable(nextPlayer)
        }
        buttons[2].setOnClickListener {
            nextPlayer = (nextPlayer + 1) % playerNum
            setTable(nextPlayer)
        }
    }
}