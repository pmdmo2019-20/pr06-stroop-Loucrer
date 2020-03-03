package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.data.models.Card
import es.iessaladillo.pedrojoya.stroop.data.models.Page
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_card_dashboard.*
import kotlinx.android.synthetic.main.item_page_assistant.*
import kotlinx.android.synthetic.main.item_page_assistant.view.*

class AssistantFragmentAdapter(var pageList: ArrayList<Page>) : RecyclerView.Adapter<AssistantFragmentAdapter.ViewHolder>(){

    private var currentPosition :Int = 0
    var onItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.item_page_assistant, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = pageList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val page: Page = pageList[position]
        currentPosition = position
        holder.bind(page)
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
            // Escuchador de evento en el item
            init {
                btnFinish.setOnClickListener { onItemClickListener?.invoke(adapterPosition) }
            }
            fun bind(page: Page) {
                page.run {
                    // containerView es la vista entera del xml que vamos ir rellenando
                    containerView.setBackgroundResource(page.colorId)
                    containerView.imageIcon.setImageResource(page.logoId)
                    containerView.lblContent.text = page.text
                    // Si estamos en el ultimo item, mostramos el boton
                    if( adapterPosition == pageList.size -1 ){
                        containerView.btnFinish.visibility= View.VISIBLE
                    }else{
                        containerView.btnFinish.visibility= View.INVISIBLE
                    }
                }
            }

    }
}