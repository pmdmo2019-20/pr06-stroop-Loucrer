package es.iessaladillo.pedrojoya.stroop.ui.playerAdd

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_avatar_player.*

class PlayerAddAdapter() :RecyclerView.Adapter<PlayerAddAdapter.ViewHolder>() {

    private var avatarList: List<Int> = arrayListOf()
    var onItemClickListener: ((Int) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_avatar_player, parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount() = avatarList.size

    fun submitList(newList : List<Int>){
        avatarList = newList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val avatar: Int = avatarList[position]
        holder.bind(avatar)
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }

        }
        fun bind(avatar:Int) {
            imgAvat.setImageResource(avatar)
            viewCheck.visibility=View.INVISIBLE
        }
    }
}
