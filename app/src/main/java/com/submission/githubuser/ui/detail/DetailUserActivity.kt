package com.submission.githubuser.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.submission.githubuser.R
import com.submission.githubuser.data.remote.response.DetailUserResponse
import com.submission.githubuser.databinding.ActivityDetailUserBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(KEY_USER)
        val id = intent.getIntExtra(KEY_ID, 0)
        val url = intent.getStringExtra(KEY_URL)
        val bundle = Bundle()
        bundle.putString(KEY_USER, username)

        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val userLogin = intent.getStringExtra(KEY_USER)
        binding.detailName.text = userLogin

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = userLogin.toString()

        val viewPager: ViewPager2 = findViewById(R.id.view_pager_detail)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.detail_tabs)
        TabLayoutMediator(tabs, viewPager) { tabs, position ->
            tabs.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        if (userLogin != null) {
            showLoading(true)
            viewModel.getUserDetails(userLogin)
            showLoading(false)
        }

        viewModel.detailUser.observe(this) {
                detailUser -> setDetailUser(detailUser)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.tbFav.isChecked = true
                        _isChecked = true
                    } else {
                        binding.tbFav.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.tbFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                if (username != null) {
                    if (url != null) {
                        viewModel.insertFavorite(username, id, url)
                    }
                }
            } else {
                viewModel.deleteFavorite(id)
            }
            binding.tbFav.isChecked = _isChecked
        }
    }

    private fun setDetailUser(Name: DetailUserResponse) {
        Glide.with(this@DetailUserActivity)
            .load(Name.avatarUrl)
            .into(binding.detailAvatar)
        binding.detailName.text = Name.login
        binding.detailUsername.text = Name.name
        binding.detailFollowers.text = Name.followers.toString()
        binding.detailFollowings.text = Name.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_USER = "key_user"
        const val KEY_ID = "key_id"
        const val KEY_URL = "key_url"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.followings
        )
    }
}