package com.example.akniyet.lab2floatingactionbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private var actorList: MutableList<Actor>? = ArrayList()
    private var fabBtn: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)
        if (savedInstanceState != null)
            actorList = savedInstanceState.getParcelableArrayList("list_Actor")
        else
            actorList = ArrayList()

        if (actorList != null)
            viewAdapter = ActorAdapter(actorList!!)

        fabBtn = findViewById<View>(R.id.FloatingButton) as FloatingActionButton
        fabBtn!!.setOnClickListener {
            actorList!!.add(Actor("Johnny Depp", "Pirates of Caribbean"))
            viewAdapter!!.notifyDataSetChanged()
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            actorList = savedInstanceState.getParcelableArrayList("list_Actor")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (actorList != null && !actorList!!.isEmpty())
            outState.putParcelableArrayList("list_Actor", actorList as ArrayList<out Parcelable>?)
    }
    class ActorAdapter(private val actorList: List<Actor>) : RecyclerView.Adapter<ActorAdapter.ViewHolder>() {

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var mFilm: TextView
            var mName: TextView

            init {
                mName = itemView.findViewById(R.id.actor_name)
                mFilm = itemView.findViewById(R.id.actor_film)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorAdapter.ViewHolder {

            val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_itemlist, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mName.text = actorList[position].name
            holder.mFilm.text = actorList[position].film
        }

        override fun getItemCount() = actorList.size
    }
}
