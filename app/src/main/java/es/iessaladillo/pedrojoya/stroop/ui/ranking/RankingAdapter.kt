package es.iessaladillo.pedrojoya.stroop.ui.ranking

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.Game
import es.iessaladillo.pedrojoya.stroop.data.baseData.entity.User
import es.iessaladillo.pedrojoya.stroop.data.pojo.UserWithGame
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_game_ranking.*

class RankingAdapter(private val application: Application): ListAdapter<UserWithGame ,RankingAdapter.ViewHolder>(StudentDiffCallback) {


    var onItemClickListener: ((Int) -> Unit)? = null

    var filter = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_game_ranking, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userGame: UserWithGame = currentList[position]
        holder.bind(userGame)
    }


    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
        }

        fun bind(games: UserWithGame) {
            games.run {
                imgAvatR.setImageResource(imageId)
                lblName.text =   userName
                lblTotalWords.text = application.getString(R.string.ranking_words, totalWords.toString())
                lblCorrects.text = application.getString(R.string.ranking_corrects, corrects.toString())
                lblPoints2.text = points.toString()
                lblTime.text = application.getString(R.string.ranking_minutes, totalTime.div(60000).toString())
                lblTime.visibility = View.VISIBLE
                when (filter) {
                    "all" -> {
                        lblGameMode.text = application.getString(R.string.ranking_item, gameMode)
                        lblGameMode.visibility = View.VISIBLE
                    }
                    "attempts" -> {
                        lblGameMode.visibility = View.INVISIBLE
                    }
                    "time" -> {
                        lblGameMode.visibility = View.INVISIBLE
                        lblTime.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    object StudentDiffCallback : DiffUtil.ItemCallback<UserWithGame>() {

        override fun areItemsTheSame(oldItem: UserWithGame, newItem: UserWithGame): Boolean =
            oldItem.userName == newItem.userName

        override fun areContentsTheSame(oldItem: UserWithGame, newItem: UserWithGame): Boolean =
            oldItem == newItem

    }


}