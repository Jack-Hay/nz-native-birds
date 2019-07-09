package me.jackhay.nznativebirds.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import me.jackhay.nznativebirds.model.bird.Bird
import me.jackhay.nznativebirds.R

class BirdInfoFragment : Fragment() {
    private lateinit var bird: Bird

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            bird = arguments!!.getSerializable("bird") as Bird
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_bird_info, container, false)

        val imageView = view.findViewById<ImageView>(R.id.birdImageBirdInfoView)
        imageView.setImageResource(
            resources.getIdentifier(
                bird.imageName.toLowerCase(),
                "drawable", context!!.packageName
            )
        )
        imageView.setOnLongClickListener {
            Toast.makeText(context!!, bird.imageCredit, Toast.LENGTH_LONG).show()
            true
        }

        populateTextFields(view)

        return view
    }

    private fun populateTextFields(view: View) {
        val fullName = view.findViewById<TextView>(R.id.fullNameBirdInfoView)
        fullName.text = String.format(resources.getString(R.string.full_name), bird.fullName)

        val maoriName = view.findViewById<TextView>(R.id.maoriNameBirdInfoView)
        if (bird.maoriName != "") {
            maoriName.text = String.format(resources.getString(R.string.maori_name), bird.maoriName)
        } else {
            maoriName.visibility = View.GONE
        }

        val englishName = view.findViewById<TextView>(R.id.englishNameBirdInfoView)
        if (bird.englishName != "") {
            englishName.text = String.format(resources.getString(R.string.english_name), bird.englishName)
        } else {
            englishName.visibility = View.GONE
        }

        val description = view.findViewById<TextView>(R.id.descriptionBirdInfoView)
        description.text = String.format(resources.getString(R.string.description), bird.description)
    }

    companion object {
        fun newInstance(bird: Bird): BirdInfoFragment {
            val fragment = BirdInfoFragment()
            val args = Bundle()
            args.putSerializable("bird", bird)
            fragment.arguments = args
            return fragment
        }
    }
}