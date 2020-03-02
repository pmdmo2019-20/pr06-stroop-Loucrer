package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_game_ranking.*

class RankingAdapter(private val application: Application): RecyclerView.Adapter<RankingAdapter.ViewHolder>() {


    var onItemClickListener: ((Int) -> Unit)? = null

    var gameList: List<Game> = arrayListOf()


    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_game_ranking, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userGame: Game = gameList[position]
        holder.bind(userGame)
    }

    fun submitList(newList: List<Game>) {
        gameList = newList
        notifyDataSetChanged()
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }

        }

        @SuppressLint("StringFormatInvalid")
        fun bind(games: Game) {
            games.run {
                imgAvatR.setImageResource(user.userImgId)
                lblName.text = user.userName
                lblGameMode.text = gameMode
                lblTime.text = totalTime.toString()
                lblTotalWords.text = totalWords.toString()
                lblCorrects.text = corrects.toString()
                lblPoints2.text = points.toString()
            }
        }
    }


}