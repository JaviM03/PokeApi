package com.naldana.ejemplo08

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.naldana.ejemplo08.models.Pokemon
import kotlinx.android.synthetic.main.list_item_pokemon.view.*

class PokemonAdapter(val items: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    // TODO: Para contar elementos creados
    private var countViews: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_pokemon, parent, false)

        /*
         * TODO: Muestra el valor de contador de view creadas solo se hace aqui, para asegurar
         * que solo se asigne el valor aqui
         */
        view.findViewById<TextView>(R.id.count_element).text = countViews.toString()
        countViews++
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Pokemon) = with(itemView) {
            tv_pokemon_name.text = item.name
            tv_pokemon_id.text = item.id.toString()
        }
    }

}