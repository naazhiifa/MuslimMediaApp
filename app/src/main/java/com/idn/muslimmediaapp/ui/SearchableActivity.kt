package com.idn.muslimmediaapp.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.idn.muslimmediaapp.NewsViewModel
import com.idn.muslimmediaapp.R
import com.idn.muslimmediaapp.adapter.NewsAdapter
import com.idn.muslimmediaapp.databinding.ActivitySearchableBinding

class SearchableActivity : AppCompatActivity() {
    private var _binding: ActivitySearchableBinding? = null
    private val binding get() = _binding as ActivitySearchableBinding

    private var _searchViewModel: NewsViewModel? = null
    private val searchViewModel get() = _searchViewModel as NewsViewModel

    private var querySearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySearchableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _searchViewModel = ViewModelProvider(this)[NewsViewModel::class.java]
        binding.loadingView.root.visibility = View.VISIBLE

        handleIntent(intent)

        searchViewModel.searchNews.observe(this){
            binding.apply {
                if (it.articles?.size == 0){
                    tvNoNews.text = getString(R.string.no_news_text)
                    tvNoNews.visibility = View.VISIBLE
                } else {
                    rvSearchResult.apply {
                        val menuAdapter = NewsAdapter()
                        menuAdapter.setData(it.articles)
                        adapter = menuAdapter
                        layoutManager = LinearLayoutManager(this@SearchableActivity)
                        visibility = View.VISIBLE
                    }
                }
            }
            binding.loadingView.root.visibility = View.GONE
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntent(intent)
        // handle intent function
    }

    fun handleIntent(intent: Intent){
        if (Intent.ACTION_SEARCH == intent.action){
            intent.getStringExtra(SearchManager.QUERY)
                ?.also { query ->
                    querySearch = query
                    binding.apply {
                        rvSearchResult.visibility = View.GONE
                        loadingView.root.visibility = View.VISIBLE
                        tvNoNews.visibility = View.INVISIBLE
                        searchView.setQuery("",false)
                        searchView.clearFocus()
                    }
                    doMySearch(query)
                }
        }
    }

    fun doMySearch(q : String){
        searchViewModel.searchNews(q)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}