package com.naldana.ejemplo08

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.naldana.ejemplo08.models.Pokemon
import com.naldana.ejemplo08.utilities.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: PokemonAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bt_search_pokemon.setOnClickListener {
            FetchPokemonTask().execute("${et_pokemon_number.text}")
        }

    }


    fun initRecycler(pokemonlist: MutableList<Pokemon>) {

        viewManager = LinearLayoutManager(this)

        viewAdapter = PokemonAdapter(pokemonlist)

        rv_pokemon_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    private inner class FetchPokemonTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg pokemonNumbers: String): String? {

            if (pokemonNumbers.isEmpty()) {
                return null
            }

            val ID = pokemonNumbers[0]

            val pokeAPI = NetworkUtils().buildUrl()

            return try {
                NetworkUtils().getResponseFromHttpUrl(pokeAPI)
            } catch (e: IOException) {
                e.printStackTrace()
                ""
            }

        }

        override fun onPostExecute(pokemonInfo: String) {

            if (pokemonInfo.isNotEmpty()) {
                val root = JSONObject(pokemonInfo)
                val pokemonsjson = root.getJSONArray("results")
                val pokemon = MutableList(pokemonsjson.length()) { i ->
                    val pokemn = JSONObject(pokemonsjson[i].toString())
                    Pokemon(i, pokemn.getString("name"), pokemn.getString("url"))
                }
                initRecycler(pokemon)
            }
        }
    }
}
