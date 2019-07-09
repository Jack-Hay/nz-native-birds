package me.jackhay.nznativebirds.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.main.*
import me.jackhay.nznativebirds.R
import me.jackhay.nznativebirds.model.bird.Bird
import me.jackhay.nznativebirds.model.bird.BirdDatabaseDto
import me.jackhay.nznativebirds.model.bird.ImageDownloader
import me.jackhay.nznativebirds.model.bird.JsonParser
import me.jackhay.nznativebirds.view.fragments.BirdCardFragment
import me.jackhay.nznativebirds.view.fragments.BirdInfoFragment
import me.jackhay.nznativebirds.view.fragments.BirdListFragment
import me.xdrop.fuzzywuzzy.FuzzySearch
import java.net.URL


open class MainActivity : AppCompatActivity() {
    private lateinit var birdsDto: BirdDatabaseDto
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var birdList: ArrayList<Bird>
    private lateinit var searchView: MaterialSearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        birdsDto = JsonParser(this).getBirdsFromJson()!!
        for (bird in birdsDto.birds) {
            bird.setFullName()
        }

        birdList = ArrayList(birdsDto.birds.sortedBy { it.fullName })
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }

        if (savedInstanceState == null) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, BirdCardFragment.newInstance(birdList)).commit()
            navigationView.setCheckedItem(R.id.home)
        }

        populateSearchView()
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
            R.id.birdListOption -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, BirdListFragment.newInstance(birdList)).addToBackStack(null).commit()
            }
            R.id.home -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragmentContainer, BirdCardFragment.newInstance(birdList)).addToBackStack(null).commit()
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else if (searchView.isSearchOpen) {
            searchView.closeSearch()
        } else {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager!!.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        ImageDownloader(findViewById(R.id.navImageView))
            .execute(URL("https://www.doc.govt.nz/contentassets/260747c7b563443c8c28b119d90675f0/doc-logo-horizontal-white-background-390.jpg"))

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            R.id.menu_doc -> {
                //Implicit intent
                val url = resources.getString(R.string.doc_website)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun search(query: String): List<String> {
        val birdNames = birdList.map { it.fullName }
        val searchResults = FuzzySearch.extractAll(query, birdNames)
        return searchResults.sortedWith(compareBy { -it.score }).map { it.string }
    }

    private fun populateSearchView() {
        searchView = findViewById(R.id.search_view)!!
        searchView.setEllipsize(true)
        searchView.visibility = View.GONE
        searchView.setHint("Albatross")
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                for (bird in birdList) {
                    if (bird.fullName == query) {
                        val fragmentTransaction = supportFragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.fragmentContainer, BirdInfoFragment.newInstance(bird))
                            .addToBackStack(null)
                            .commit()
                        return false
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchView.setSuggestions(search(newText).toTypedArray())
                searchView.showSuggestions()
                return false
            }
        })
        searchView.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                searchView.visibility = View.VISIBLE
                toolbar.visibility = View.GONE
            }

            override fun onSearchViewClosed() {
                searchView.visibility = View.GONE
                toolbar.visibility = View.VISIBLE
            }
        })
    }
}