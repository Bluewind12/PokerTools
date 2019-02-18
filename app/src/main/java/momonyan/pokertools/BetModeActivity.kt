package momonyan.pokertools

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.bet_mode_layout.*
import java.util.*

class BetModeActivity : AppCompatActivity() {
    //プレイヤー関連
    private lateinit var player1buttons: List<Button>   //プレイヤー１用
    private lateinit var player2buttons: List<Button>   //プレイヤー２用
    private lateinit var player3buttons: List<Button>   //プレイヤー３用
    private lateinit var player4buttons: List<Button>   //プレイヤー４用
    //テキスト
    private lateinit var textViews: List<TextView>  //手元の所持金表示用

    private var playerNum: Int = 0  //プレイ人数
    private var nextPlayer: Int = 0 //手番の判別用
    private var betCoin = 0 //現在の掛け金
    private var lastRaisePlayer = 0 //最高レイズを行なってるプレイヤー
    private var playerState: MutableList<String> = mutableListOf("play", "play", "play", "play")    //現在のプレイヤーステート
    private var haveCoin: MutableList<Int> = mutableListOf(0, 0, 0, 0)   //所持コイン数
    private var playerBetCoins: MutableList<Int> = mutableListOf(0, 0, 0, 0)    //bet額
    private var baseBet = 0 //場代

    /**
     * 戻るボタンを押した時にダイアログを出す処理
     */
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

        //前のIntentからのデータ引き継ぎ
        playerNum = intent.getIntExtra("Player", 1)
        baseBet = intent.getIntExtra("BaseBet", 1)

        /*
        プレイヤーごとにボタンをList化
        二人プレイ時に特殊な動作
        */
        player1buttons = listOf(callButton1, foldButton1, raiseButton1)
        if (playerNum <= 2) {
            player3buttons = listOf(callButton2, foldButton2, raiseButton2)
            player2buttons = listOf(callButton3, foldButton3, raiseButton3)
            textViews = listOf(betCoinText1, betCoinText3, betCoinText2, betCoinText4)
        } else {
            player3buttons = listOf(callButton2, foldButton2, raiseButton2)
            player2buttons = listOf(callButton3, foldButton3, raiseButton3)
            textViews = listOf(betCoinText1, betCoinText2, betCoinText3, betCoinText4)
        }
        player4buttons = listOf(callButton4, foldButton4, raiseButton4)

        //現在の掛けコイン数のセット
        betCoin = baseBet

        //プレイ人数に王するように表示の切り替え
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
        //所持コインのセット
        haveCoin[0] = intent.getIntExtra("Coin", 100) - baseBet
        haveCoin[1] = intent.getIntExtra("Coin2", 100) - baseBet
        haveCoin[2] = intent.getIntExtra("Coin3", 100) - baseBet
        haveCoin[3] = intent.getIntExtra("Coin4", 100) - baseBet

        //各プレイヤーの掛けコインの初期セット、表示
        for (i in 0 until 4) {
            playerBetCoins[i] = baseBet
            textViews[i].text = getString(R.string.playerBet, haveCoin[i])
        }

        //中央に表示
        betCoinsText.text = getString(R.string.nowBet, baseBet)
        betCoinsText2.text = getString(R.string.nowBet, baseBet)

        //プレイヤーの対応するようにボタンに動作セット
        setBetButtons(player1buttons, 0)
        if (playerNum <= 2) {
            setBetButtons(player2buttons, 1)
            setBetButtons(player3buttons, 2)
        } else {
            setBetButtons(player2buttons, 2)
            setBetButtons(player3buttons, 1)
        }
        setBetButtons(player4buttons, 3)

        //動作開始
        setTable(0)


        //バツボタンの設定、戻るボタンの際の確認
        endButton.setOnClickListener {
            backAlertDialogCreate()
        }
        endButton2.setOnClickListener {
            backAlertDialogCreate()
        }
    }

    /**
     * 終了時の動作
     */
    private fun backAlertDialogCreate() {
        AlertDialog.Builder(this)
            .setTitle("終了します")
            .setMessage("終了してもよろしいですか?")
            .setPositiveButton("はい") { _, _ ->
                finish()
            }
            .setNegativeButton("いいえ", null)
            .show()
    }

    /**
     * 手番のプレイヤーを動かせるようにする
     * @param spone:手番プレイヤー
     */
    private fun setTable(spone: Int) {
        if (spone != 0) {
            setEnabledLists(player1buttons, false)
        } else {
            setEnabledLists(player1buttons, true)
        }

        if (playerNum <= 2) {
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
        } else {
            if (spone != 2) {
                setEnabledLists(player2buttons, false)
            } else {
                setEnabledLists(player2buttons, true)
            }

            if (spone != 1) {
                setEnabledLists(player3buttons, false)
            } else {
                setEnabledLists(player3buttons, true)
            }
        }

        if (spone != 3) {
            setEnabledLists(player4buttons, false)
        } else {
            setEnabledLists(player4buttons, true)
        }
    }

    /**
     * ButtonのListに対して対応するBool値にEnabledを設定する
     */
    private fun setEnabledLists(list: List<Button>, bool: Boolean) {
        list.forEach {
            it.isEnabled = bool
        }
    }

    /**
     * ボタンの初期設定
     */
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
        }
    }

    /**
     * ショーダウン時のボタンの設定の変更を行う
     * @param bool true:ショーダウン時の動作用,false:ベット時の動作
     */
    private fun winnerButton(bool: Boolean) {
        if (bool) {
            val playCount = playerState.count { it == "play" }
            if ((playerState.count { it == "fold" }) == playerNum) {
                resetTable()
            }
            if (playerState[0] != "fold") {
                player1buttons[0].isEnabled = true
            }
            if (playerState[1] != "fold") {
                player2buttons[0].isEnabled = true
            }
            if (playerState[2] != "fold") {
                player3buttons[0].isEnabled = true
            }
            if (playerState[3] != "fold") {
                player4buttons[0].isEnabled = true
            }
            player1buttons[0].text = getString(R.string.winner)
            player2buttons[0].text = getString(R.string.winner)
            player3buttons[0].text = getString(R.string.winner)
            player4buttons[0].text = getString(R.string.winner)

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

            player1buttons[0].text = getString(R.string.call)
            player2buttons[0].text = getString(R.string.call)
            player3buttons[0].text = getString(R.string.call)
            player4buttons[0].text = getString(R.string.call)

            setBetButtons(player1buttons, 0)
            if (playerNum > 2) {
                setBetButtons(player2buttons, 2)
                setBetButtons(player3buttons, 1)
            } else {
                setBetButtons(player2buttons, 1)
                setBetButtons(player3buttons, 2)
            }
            setBetButtons(player4buttons, 3)

        }
    }

    /**
     * 手番を次のプレイヤーに渡す動作を行う
     */
    private fun nextPlayerAdd() {
        setStateColors()
        nextPlayer = (nextPlayer + 1) % playerNum
        if (nextPlayer == lastRaisePlayer) {
            showDown()
        } else {
            if (playerState[nextPlayer] == "play") {
                setTable(nextPlayer)
            } else {
                nextPlayerAdd()
            }
        }
    }

    /**
     * 場の初期化を行う
     */
    private fun resetTable() {
        betCoin = baseBet
        for (i in 0 until playerNum) {
            playerState[i] = "play"
        }
        for (i in 0 until 4) {
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
        setTable(rand)
    }

    /**
     * ショーダウンの際に行うことを簡易的に行う
     */
    private fun showDown() {
        setEnabledLists(player1buttons, false)
        setEnabledLists(player2buttons, false)
        setEnabledLists(player3buttons, false)
        setEnabledLists(player4buttons, false)
        winnerButton(true)

    }

    /**
     *　現在のプレイヤーのステートと最高レイズ者を取得しテキストの色の変更を行う
     */
    private fun setStateColors() {
        for (i in 0 until 4) {
            when {
                playerState[i].equals("fold") -> textViews[i].setTextColor(resources.getColor(R.color.grayColor))
                lastRaisePlayer == i -> textViews[i].setTextColor(resources.getColor(R.color.redColor))
                else -> textViews[i].setTextColor(resources.getColor(R.color.defaultTextColor))
            }
        }
    }
}