package me.jackhay.nznativebirds.view.adapters

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import me.jackhay.nznativebirds.model.bird.Bird
import me.jackhay.nznativebirds.R
import me.jackhay.nznativebirds.view.fragments.BirdInfoFragment

class BirdListAdapter(
    private val birds: ArrayList<Bird>,
    private val activity: FragmentActivity
) :
    RecyclerView.Adapter<BirdListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bird_text_row_view, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.birdName.text = birds[position].fullName
        holder.description.text = birds[position].description
        holder.relativeLayout.setOnClickListener {
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, BirdInfoFragment.newInstance(birds[position]))
                .addToBackStack(null)
                .commit()
        }

    }

    override fun getItemCount() = birds.size

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var birdName: TextView = itemView.findViewById(R.id.recyclerRowBirdName)
        internal var description: TextView = itemView.findViewById(R.id.recyclerRowBirdDescription)
        internal var relativeLayout: RelativeLayout = itemView.findViewById(R.id.relLayoutListView)
    }
}