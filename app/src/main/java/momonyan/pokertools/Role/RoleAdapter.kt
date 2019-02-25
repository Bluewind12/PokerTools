package momonyan.pokertools.Role

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import momonyan.pokertools.R

class RoleAdapter(private val mValues: ArrayList<RoleDataClass>) : RecyclerView.Adapter<RoleHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoleHolder {
        //レイアウトの設定(inflate)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.role_card_layout, parent, false)
        //Holderの生成
        return RoleHolder(view)
    }

    override fun onBindViewHolder(holder: RoleHolder, position: Int) {
        val item = mValues[position]
        holder.mRoleEng.text = item.eng
        holder.mRoleJp.text = item.jp
        holder.mRoleDescription.text = item.description
        holder.mRoleImage.setImageResource(item.image)

        holder.mRoleCard.setOnClickListener {
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }
}