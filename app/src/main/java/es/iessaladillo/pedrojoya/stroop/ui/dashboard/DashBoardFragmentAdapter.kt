package es.iessaladillo.pedrojoya.stroop.ui.dashboard

import kotlinx.android.synthetic.main.item_card_dashboard.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.models.Card
import kotlinx.android.extensions.LayoutContainer

class DashBoardFragmentAdapter(var cardList: List<Card>) :
    RecyclerView.Adapter<DashBoardFragmentAdapter.ViewHolder>() {

    // Para detectar el click del item
    var onItemClickListener: ((Int) -> Unit)? = null

    // Obtenemos el layout de los item para inflar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_card_dashboard, parent, false)
        return ViewHolder(itemView)

    }

    override fun getItemCount() = cardList.size

    // pintamos la carta segun la posicion
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card: Card = cardList[position]
        holder.bind(card)
    }


    // El viewHolder se puede decir que es el encargado de pintar el item e interectura con él.
    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        // Escuchador de evento en el item
        init {
            containerView.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
        }
        fun bind(card: Card) {
            card.run {
                imgBtn.setImageResource(imgId)
                lblBtn.text=text
                containerView.run {
                    imgBtn.setColorFilter(context.resources.getColor(colorId))
                    lblBtn.setTextColor(context.resources.getColor(colorId))
                }
            }
        }
    }
}