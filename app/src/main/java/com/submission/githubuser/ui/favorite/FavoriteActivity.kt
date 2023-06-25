package com.submission.githubuser.ui.favorite

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.githubuser.ItemsItem
import com.submission.githubuser.data.local.entity.UserEntity
import com.submission.githubuser.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        val layoutManager = LinearLayoutManager(this)
        binding?.rvFavUser?.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvFavUser?.addItemDecoration(itemDecoration)

        viewModel.getFavoriteUser()?.observe(this) { users: List<UserEntity>? ->
            val items = arrayListOf<ItemsItem>()
            users?.map {
                val item = ItemsItem(login = it.login, id = it.id, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            binding?.rvFavUser?.adapter = FavoriteUserAdapter(items)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBarFav?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}