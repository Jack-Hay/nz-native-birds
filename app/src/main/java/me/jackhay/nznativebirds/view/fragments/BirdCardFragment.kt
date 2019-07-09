package me.jackhay.nznativebirds.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.jackhay.nznativebirds.R
import me.jackhay.nznativebirds.view.adapters.CardViewAdapter
import me.jackhay.nznativebirds.model.bird.Bird



class BirdCardFragment : Fragment() {
    private lateinit var birdList: kotlin.collections.ArrayList<Bird>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            @Suppress("UNCHECKED_CAST")
            birdList = arguments!!.getSerializable("birdList") as ArrayList<Bird>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)

        val mainRecyclerView = view.findViewById<RecyclerView>(R.id.mainRecyclerView)
        val mainLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mainRecyclerView.layoutManager = mainLayoutManager
        val mainAdapter = CardViewAdapter(context!!, birdList, activity!!)
        mainRecyclerView.adapter = mainAdapter
        return view

    }

    companion object {
        fun newInstance(birdList: ArrayList<Bird>): BirdCardFragment {
            val fragment = BirdCardFragment()
            val args = Bundle()
            args.putSerializable("birdList", birdList)
            fragment.arguments = args
            return fragment
        }
    }
}