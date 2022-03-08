package com.encoders.siginsignupusingfirebase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.encoders.siginsignupusingfirebase.R
import com.encoders.siginsignupusingfirebase.User

class UsersListAdapter (
    var items: MutableList<User>,
    var clickListner: onUserlickListner
) :
    RecyclerView.Adapter<UserlistViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserlistViewHolder {
        return UserlistViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_users_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserlistViewHolder, position: Int) {
        holder.initialize(items[position], clickListner)

    }



}

class UserlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



    private var name: TextView? = itemView.findViewById(R.id.name)
    private var designation: TextView? = itemView.findViewById(R.id.designation)




    fun initialize(user: User, action: onUserlickListner) {

        name?.text = user.username
        designation?.text = user.designation

        itemView.setOnClickListener {
            action.onUserlickListner(user, adapterPosition)
        }



    }

}

interface onUserlickListner {
    fun onUserlickListner(user: User, position: Int)
}