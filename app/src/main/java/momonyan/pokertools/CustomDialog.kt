package momonyan.pokertools

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView

class CustomDialog : DialogFragment() {
    private var okButtonClickListener: View.OnClickListener? = null
    private lateinit var dialoging: Dialog
    //ダイアログ内の値を返すメソッド
    var inputValue: Long? = 0L

    private lateinit var tvInput: TextView
    private var btNo = arrayOfNulls<Button>(10)    //0～9までのボタン
    private lateinit var btClear: Button
    private lateinit var btOk: Button
    private lateinit var btClose: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //XMLとの紐付け
        val inflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_layout, null, false)
        tvInput = view.findViewById(R.id.tv_input)
        btNo[0] = view.findViewById(R.id.bt_no0) as Button
        btNo[1] = view.findViewById(R.id.bt_no1) as Button
        btNo[2] = view.findViewById(R.id.bt_no2) as Button
        btNo[3] = view.findViewById(R.id.bt_no3) as Button
        btNo[4] = view.findViewById(R.id.bt_no4) as Button
        btNo[5] = view.findViewById(R.id.bt_no5) as Button
        btNo[6] = view.findViewById(R.id.bt_no6) as Button
        btNo[7] = view.findViewById(R.id.bt_no7) as Button
        btNo[8] = view.findViewById(R.id.bt_no8) as Button
        btNo[9] = view.findViewById(R.id.bt_no9) as Button
        btClear = view.findViewById(R.id.bt_clear)
        btOk = view.findViewById(R.id.bt_ok)
        btClose = view.findViewById(R.id.bt_close)

        //ダイアログの作成
        dialoging = Dialog(activity)
        dialoging.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        dialoging.window!!.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        dialoging.setContentView(view)
        dialoging.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //数字ボタン押下時の処理
        for (i in 0..9) {
            btNo[i]!!.setOnClickListener(View.OnClickListener { v ->
                //入力された数値を元に一時的な値を取得
                val tmp = java.lang.Long.parseLong(inputValue.toString() + (v as Button).text.toString())
                //一時的な値が1,000,000,000以上の場合はアラートを表示
                if (tmp >= 1000000000) {
                    AlertDialog.Builder(activity)
                        .setMessage("エラー")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    inputValue = tmp
                    tvInput.text = String.format("%1$,3d", inputValue)
                }
                //OKボタンの活性化（mValueが0以外の場合のみ活性）
                btOk.isEnabled = inputValue != 0L
            })
        }

        //クリアボタン押下時の処理
        btClear.setOnClickListener {
            inputValue = 0L
            tvInput.text = ""
            btOk.isEnabled = false
        }

        //クローズボタン押下時はダイアログを消す
        btClose.setOnClickListener { dialoging.dismiss() }

        //OKボタンのリスナー
        btOk.setOnClickListener(okButtonClickListener)

        return dialoging
    }

    // ダイアログの横幅、高さ、表示位置を設定
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val lp = dialoging.window!!.attributes
        val metrics = resources.displayMetrics
        lp.width = (metrics.widthPixels * 0.8).toInt()//横幅を80%
        //lp.height = (int) (metrics.heightPixels * 0.8);//高さを80%
        //lp.x = 100; //表示位置を指定した分、右へ移動
        //lp.y = 200; //表示位置を指定した分、下へ移動
        dialoging.window!!.attributes = lp
    }

    fun setOnOkButtonClickListener(listener: View.OnClickListener) {
        this.okButtonClickListener = listener
    }

    companion object {

        fun newInstance(): CustomDialog {
            return CustomDialog()
        }
    }
}