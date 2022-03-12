package com.samsul.challengegdsc.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.samsul.challengegdsc.R
import com.samsul.challengegdsc.adapter.MainAdapter
import com.samsul.challengegdsc.data.entity.Note
import com.samsul.challengegdsc.databinding.ActivityHomeBinding
import com.samsul.challengegdsc.utils.ViewModelFactory
import com.samsul.challengegdsc.viewmodel.MainViewModel

class HomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private lateinit var mainAdapter: MainAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "My Note"

        mainAdapter = MainAdapter(this)

        viewModel.getAllNotes().observe(this){
            if(it!=null) {
                mainAdapter.setListAdapter(it)
            }
        }

        binding.rvNote.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mainAdapter
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        mainAdapter.setOnItemClickListener(object : MainAdapter.ItemClickListener {
            override fun onItemClicked(note: Note) {
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra("id_note", note.id)
                startActivity(intent)
            }

            override fun onEditClicked(note: Note) {
                val intent = Intent(applicationContext, AddActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                intent.putExtra("id_item", note.id)
                intent.putExtra("toolbar", "Edit Note")
                startActivity(intent)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                filter(text!!)
                searchView.clearFocus()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText!!)
                return true
            }

        })

        return true
    }

    private fun filter(searchText: String) {
        val search = "%$searchText%"
        viewModel.getSearchResult(search).observe(this) {
            if(it!=null) {
                mainAdapter.setListAdapter(it)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.exit -> {
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }

}