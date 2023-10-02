package com.example.tugasgithubuser.UI
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.tugasgithubuser.Adapter.SectionPagerAdapter
import com.example.tugasgithubuser.R
import com.example.tugasgithubuser.ViewModel.DetailViewModel
import com.example.tugasgithubuser.ViewModel.ViewModelFactory
import com.example.tugasgithubuser.database.UserEntity
import com.example.tugasgithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Detail : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = intent.getStringExtra("USERNAME")
        val avatar = intent.getStringExtra("AVATAR")

        detailViewModel = obtainDetailViewModel(this@Detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (username != null) {
            val sectionPagerAdapter = SectionPagerAdapter(this, username)
            val viewPager: ViewPager2 = findViewById(R.id.view_page)
            viewPager.adapter = sectionPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabLayout)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }
        if (username != null) {
            detailViewModel.getDetailUser(username)
        }

        detailViewModel.userDetail.observe(this) {
            if (it != null) {
                Glide.with(this@Detail)
                    .load(it.avatarUrl)
                    .centerCrop()
                    .into(binding.circleImageView)
                binding.txtUsername.text = it.name
                binding.txtName.text = it.username
                binding.totalFollower.text = it.followersCount
                binding.totalFollowing.text = it.followingCount
                binding.btnFavorit.contentDescription = it.isFavorite.toString()

                binding.apply {
                    if (!it.isFavorite) {
                        btnFavorit.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@Detail, R.drawable.baseline_favorite_border_24
                            )
                        )
                    } else {
                        btnFavorit.setImageDrawable(
                            ContextCompat.getDrawable(
                                this@Detail, R.drawable.baseline_favorite_24
                            )
                        )
                    }
                }
            }
        }

        detailViewModel.loading.observe(this) {
            showLoading(it)
        }

        binding.apply {
            btnFavorit.setOnClickListener {
                val userFavorite = UserEntity(
                    name = txtUsername.text.toString(),
                    username = txtName.text.toString(),
                    avatarUrl = avatar.toString(),
                    isFavorite = true,
                    followersCount = totalFollower.text.toString(),
                    followingCount = totalFollowing.text.toString()
                )


                val currentIcon = btnFavorit.contentDescription
                if (currentIcon.equals("true")) {
                    btnFavorit.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@Detail, R.drawable.baseline_favorite_border_24
                        )
                    )
                    detailViewModel.deleteUserFavorite(userFavorite)
                    btnFavorit.contentDescription = "false"
                } else {
                    btnFavorit.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@Detail, R.drawable.baseline_favorite_24
                        )
                    )
                    detailViewModel.insertUserFavorite(userFavorite)
                    btnFavorit.contentDescription = "true"
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                progressBarDetail.visibility = View.VISIBLE
            } else {
                progressBarDetail.visibility = View.GONE
            }
        }
    }

    private fun obtainDetailViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(this@Detail, factory)[DetailViewModel::class.java]
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower_fragment,
            R.string.following_fragment
        )
    }
}
