package com.idn.muslimmediaapp.ui

import android.graphics.Bitmap
import android.os.Build.VERSION.SDK_INT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.idn.muslimmediaapp.R
import com.idn.muslimmediaapp.databinding.ActivityDetailBinding
import com.idn.muslimmediaapp.model.network.ArticleItem
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null //to check if _binding is not null variable, to avoid NullPointerException error
    private val binding get() = _binding as ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)

        setSupportActionBar(binding.toolbarDetail)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.news_detail)
        }

        setContentView(binding.root)

        @Suppress("DEPRECATION") val newsData = when {
            SDK_INT >= 33 -> intent.getParcelableExtra(NEWS_DATA, ArticleItem::class.java)
            else -> intent.getParcelableExtra(NEWS_DATA)
        }

        binding.apply {
            tvDetailTitle.text = newsData?.title
            tvDetailAuthor.text = newsData?.author
            tvDetailPublishAt.text = publishedAt
            Picasso.get().load(newsData?.urlToImage).into(ivDetailImage)
        }
    }

    private fun setWebView(data : ArticleItem?){
        var loadingFinished = true
        var redirect = false

        binding.wvDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                if( !loadingFinished){
                    redirect = true
                }
                loadingFinished = false
                view?.loadUrl(request?.url.toString())

                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                loadingFinished = false
                //for progressbar visibility
                binding.loadingView.root.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (!redirect){
                    loadingFinished = true
                }

                if(loadingFinished && !redirect){
                }

                if(loadingFinished && !redirect){
                    binding.loadingView.root.visibility = View.GONE
                } else {
                    redirect = false
                }
            }
        }

        data?.url?.let { binding.wvDetail.loadUrl(it) }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    val newsDate = intent.getStringExtra(EXTRA_DATA_DATE)
    val newsTime = intent.getStringExtra(EXTRA_DATA_TIME)
    val publishedAt = newsDate + newsTime

    companion object {
        const val NEWS_DATA = "data"
        const val EXTRA_DATA_DATE = "date"
        const val EXTRA_DATA_TIME = "time"
    }
}