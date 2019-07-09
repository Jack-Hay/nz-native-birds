package me.jackhay.nznativebirds.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.jackhay.nznativebirds.model.bird.Bird
import me.jackhay.nznativebirds.R
import me.jackhay.nznativebirds.view.adapters.BirdListAdapter

class BirdListFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var birdList: ArrayList<Bird>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            @Suppress("UNCHECKED_CAST")
            birdList = arguments!!.getSerializable("birdList") as ArrayList<Bird>
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bird_list, container, false)

        viewManager = LinearLayoutManager(context)
        viewAdapter = BirdListAdapter(birdList, activity!!)

        recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        return view
    }

    companion object {
        fun newInstance(birdList: ArrayList<Bird>): BirdListFragment {
            val fragment = BirdListFragment()
            val args = Bundle()
            args.putSerializable("birdList", birdList)
            fragment.arguments = args
            return fragment
        }
    }
}