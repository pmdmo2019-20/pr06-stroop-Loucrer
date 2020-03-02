package es.iessaladillo.pedrojoya.stroop.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user_player.*

class PlayerAdapter():
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

        var onItemClickListener: ((Int) -> Unit)? = null

        var userList : List<User> = arrayListOf()


        init {
            setHasStableIds(true)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView = layoutInflater.inflate(R.layout.item_user_player, parent, false)
            return ViewHolder(itemView)

        }

        override fun getItemCount(): Int {
            return userList.size
        }

        fun submitList(newList : List<User>){
            userList = newList
            notifyDataSetChanged()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val user : User = userList[position]
            holder.bind(user)
        }

        inner class ViewHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer {
            init {
                containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
            }
            fun bind(user : User) {
                user.run {
                    lblUser.text = user.userName
                    imgUser.setImageResource(user.userImgId)
                    viewCheck.visibility = View.INVISIBLE
                }
            }
        }
}