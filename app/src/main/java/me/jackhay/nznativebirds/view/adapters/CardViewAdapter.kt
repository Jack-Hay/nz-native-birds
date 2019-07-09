package me.jackhay.nznativebirds.view.adapters

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import me.jackhay.nznativebirds.model.bird.Bird
import me.jackhay.nznativebirds.R
import me.jackhay.nznativebirds.view.fragments.BirdInfoFragment


class CardViewAdapter(
    private val context: Context,
    private val birdDataList: ArrayList<Bird>,
    private val activity: FragmentActivity
) : RecyclerView.Adapter<CardViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bird_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerImageView.setImageResource(
            context.resources.getIdentifier(
                birdDataList[position].imageName.toLowerCase(),
                "drawable", context.packageName
            )
        )
        holder.fullName.text = birdDataList[position].fullName
        holder.constraintLayout.setOnClickListener {
            val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, BirdInfoFragment.newInstance(birdDataList[position]))
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return birdDataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var recyclerImageView: ImageView = itemView.findViewById(R.id.recyclerImageView)
        internal var fullName: TextView = itemView.findViewById(R.id.birdNameTextView)
        internal var constraintLayout: ConstraintLayout = itemView.findViewById(R.id.cardView_linear_layout)
    }
}