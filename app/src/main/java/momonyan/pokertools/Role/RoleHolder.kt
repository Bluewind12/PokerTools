package momonyan.pokertools.Role

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.role_card_layout.view.*

class RoleHolder(mView: View) : RecyclerView.ViewHolder(mView) {
    val mRoleEng: TextView = mView.roleEngText  //英語タイトル
    val mRoleJp: TextView = mView.roleJpText    //日本語タイトル
    val mRoleDescription: TextView = mView.roleDesText  //説明
    val mRoleImage: ImageView = mView.roleImageView //画像

    val mRoleCard: CardView = mView.cardView    //カードビュー
}