package momonyan.pokertools

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.bet_mode_layout.*
import java.util.*

class BetModeActivity : AppCompatActivity() {
    //プレイヤー関連
    //コール、フォールド、レイズ
    private lateinit var player1buttons: List<Button>
    private lateinit var player2buttons: List<Button>
    private lateinit var player3buttons: List<Button>
    private lateinit var player4buttons: List<Button>
    //テキスト
    private lateinit var textViews: List<TextView>
    private var playerNum: Int = 0
    private var nextPlayer: Int = 0
    private var betCoin = 0
    private var lastRaisePlayer = 0
    private var playerState: MutableList<String> = mutableListOf("play", "play", "play", "play")
    private var haveCoin: MutableList<Int> = mutableListOf(0, 0, 0, 0)
    private var playerBetCoins: MutableList<Int> = mutableListOf(0, 0, 0, 0)
    private var baseBet = 0

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


        player1buttons = listOf(callButton1, foldButton1, raiseButton1)
        player2buttons = listOf(callButton3, foldButton3, raiseButton3)
        player3buttons = listOf(callButton2, foldButton2, raiseButton2)
        player4buttons = listOf(callButton4, foldButton4, raiseButton4)
        textViews = listOf(betCoinButton1, betCoinButton3, betCoinButton2, betCoinButton4)

        playerNum = intent.getIntExtra("Player", 1)
        baseBet = intent.getIntExtra("BaseBet", 1)
        lastRaisePlayer = 0
        Log.d("どの", "$lastRaisePlayer")
        betCoin = baseBet

        if (playerNum <= 3) {
            player4buttons.forEach {
                it.visibility = View.INVISIBLE
            }
            textViews[3].visibility = View.INVISIBLE
            playerState[3] = "nothing"
        }
        if (playerNum <= 2) {
            player3buttons.forEach {
                it.visibility = View.INVISIBLE
            }
            textViews[2].visibility = View.INVISIBLE
            playerState[2] = "nothing"
        }
        if (playerNum <= 1) {
            player2buttons.forEach {
                it.visibility = View.INVISIBLE
            }
            textViews[1].visibility = View.INVISIBLE
            playerState[1] = "nothing"
            betCoinsText2.visibility = View.INVISIBLE
            endButton.visibility = View.INVISIBLE
        }
        for (i in 0 until 4) {
            haveCoin[i] = intent.getIntExtra("Coin", 100) - baseBet
            playerBetCoins[i] = baseBet
            textViews[i].text = getString(R.string.playerBet, haveCoin[i])
        }
        betCoinsText.text = getString(R.string.nowBet, baseBet)
        betCoinsText2.text = getString(R.string.nowBet, baseBet)

        setBetButtons(player1buttons, 0)
        setBetButtons(player2buttons, 1)
        setBetButtons(player3buttons, 2)
        setBetButtons(player4buttons, 3)
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
            setEnabledLists(player3buttons, false)
        } else {
            setEnabledLists(player3buttons, true)
        }
        if (spone != 2) {
            setEnabledLists(player2buttons, false)
        } else {
            setEnabledLists(player2buttons, true)
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

    private fun setBetButtons(buttons: List<Button>, player: Int) {
        //コール
        buttons[0].setOnClickListener {
            haveCoin[player] -= (betCoin - playerBetCoins[player])
            textViews[player].text = getString(R.string.playerBet, haveCoin[player])
            nextPlayerAdd()
        }
        //フォールド
        buttons[1].setOnClickListener {
            playerState[player] = "fold"
            nextPlayerAdd()
        }
        //レイズ
        buttons[2].setOnClickListener {
            playerBetCoins[player] += 10
            betCoin += 10
            haveCoin[player] -= 10
            lastRaisePlayer = player
            betCoinsText.text = getString(R.string.nowBet, betCoin)
            betCoinsText2.text = getString(R.string.nowBet, betCoin)
            textViews[player].text = getString(R.string.playerBet, haveCoin[player])
            nextPlayerAdd()
            Log.d("どの", "$lastRaisePlayer")

        }
    }

    private fun winnerButton(bool: Boolean) {
        if (bool) {
            val playCount = playerState.count { it.equals("play") }
            player1buttons[0].isEnabled = true
            player2buttons[0].isEnabled = true
            player3buttons[0].isEnabled = true
            player4buttons[0].isEnabled = true

            player1buttons[0].setOnClickListener {
                haveCoin[0] += betCoin * playCount
                resetTable()
            }
            player3buttons[0].setOnClickListener {
                haveCoin[1] += betCoin * playCount
                resetTable()
            }
            player2buttons[0].setOnClickListener {
                haveCoin[2] += betCoin * playCount
                resetTable()
            }
            player4buttons[0].setOnClickListener {
                haveCoin[3] += betCoin * playCount
                resetTable()
            }
        } else {
            player1buttons[0].isEnabled = false
            player2buttons[0].isEnabled = false
            player3buttons[0].isEnabled = false
            player4buttons[0].isEnabled = false
            player1buttons[0].setOnClickListener {
                haveCoin[0] -= (betCoin - playerBetCoins[0])
                nextPlayerAdd()
            }
            player3buttons[0].setOnClickListener {
                haveCoin[1] -= (betCoin - playerBetCoins[1])
                nextPlayerAdd()
            }
            player2buttons[0].setOnClickListener {
                haveCoin[2] -= (betCoin - playerBetCoins[2])
                nextPlayerAdd()
            }
            player4buttons[0].setOnClickListener {
                haveCoin[3] -= (betCoin - playerBetCoins[3])
                nextPlayerAdd()
            }
        }
    }

    private fun nextPlayerAdd() {
        nextPlayer = (nextPlayer + 1) % playerNum
        Log.d("どのプレイヤーのターンか", "プレイヤー$nextPlayer")
        if (nextPlayer == lastRaisePlayer) {
            showDoun()
        } else {
            if (playerState[nextPlayer].equals("play")) {
                setTable(nextPlayer)
            } else {
                nextPlayerAdd()
            }
        }
    }

    private fun resetTable() {
        betCoin = baseBet
        for (i in 0 until 4) {
            playerState[i] = "play"
            haveCoin[i] -= baseBet
            playerBetCoins[i] = baseBet
            textViews[i].text = getString(R.string.playerBet, haveCoin[i])
        }
        betCoinsText.text = getString(R.string.nowBet, baseBet)
        betCoinsText2.text = getString(R.string.nowBet, baseBet)
        winnerButton(false)
        val rand = Random().nextInt(playerNum)
        lastRaisePlayer = rand
        nextPlayer = rand
        when (rand) {
            0 -> setEnabledLists(player1buttons, true)
            1 -> setEnabledLists(player2buttons, true)
            2 -> setEnabledLists(player3buttons, true)
            3 -> setEnabledLists(player4buttons, true)
        }
        Log.d("どの", "$lastRaisePlayer")
    }

    private fun showDoun() {
        setEnabledLists(player1buttons, false)
        setEnabledLists(player2buttons, false)
        setEnabledLists(player3buttons, false)
        setEnabledLists(player4buttons, false)
        winnerButton(true)

    }
}