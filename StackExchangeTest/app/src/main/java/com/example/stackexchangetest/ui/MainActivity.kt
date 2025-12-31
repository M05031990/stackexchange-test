package com.example.stackexchangetest.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.stackexchangetest.R
import com.example.stackexchangetest.databinding.ActivityMainBinding
import com.example.stackexchangetest.ui.users.UserAdapter
import com.example.stackexchangetest.ui.users.UserViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: UserViewModel by viewModels()

    private lateinit var adapter: UserAdapter

    private var isAddingNewItems = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        adapter = UserAdapter()

        bind()
        observers()
        viewModel.searchUsers("")
    }

    private fun observers(){
        viewModel.usersState.observe(this) { response ->
            response?.let {
                adapter.submitList(response)
                isAddingNewItems = false
                binding.progressBar.visibility = View.GONE
            }

        }

        viewModel.error.observe(this){error ->
            Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG)
                .setBackgroundTint(Color.RED)
                .setTextColor(Color.WHITE)
                .show()
        }
    }

    private fun bind() = with(binding){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        val searchText = searchView.findViewById<EditText>(
            androidx.appcompat.R.id.search_src_text
        )
        searchText.hint = "Search users"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUsers(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)

                val layoutManager = rv.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItem == totalItemCount - 1) {
                    // Reached bottom
                    if (!isAddingNewItems) {
                        progressBar.visibility = View.VISIBLE
                        isAddingNewItems = true
                        viewModel.searchUsers(
                            searchView.query.toString(),
                            isNextPage = true
                        )
                    }
                }
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                binding.searchView.setQuery("", false)
                viewModel.searchUsers("")
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}