package es.iessaladillo.pedrojoya.stroop.ui.player

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.adapters.AdapterViewBindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user_player.*


class PlayerAdapter(): ListAdapter< User ,PlayerAdapter.ViewHolder>(StudentDiffCallback) {

    var  posicionPlayer = -1

    var onItemClickListener: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_user_player, parent, false)
        return ViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user : User = currentList[position]
        holder.bind(user)
    }


    inner class ViewHolder(override val containerView: View) :RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClickListener?.invoke(adapterPosition)
            }
        }


        fun bind(user : User) {
            user.run {
                lblUser.text = user.userName
                imgUser.setImageResource(user.imageId)
                if(posicionPlayer == adapterPosition){
                    lblBarCheck.visibility = View.VISIBLE
                } else {
                    lblBarCheck.visibility = View.INVISIBLE
                }
            }
        }
    }

    object StudentDiffCallback : DiffUtil.ItemCallback<User>() {

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
            oldItem == newItem

    }

}