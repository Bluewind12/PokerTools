package momonyan.pokertools

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.role_card_layout.view.*

class RoleHolder(mView: View) : RecyclerView.ViewHolder(mView) {

    val mRoleEng: TextView = mView.roleEngText
    val mRoleJp: TextView = mView.roleJpText
    val mRoleDescription: TextView = mView.roleDesText

}