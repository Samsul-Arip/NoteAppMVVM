package com.samsul.challengegdsc.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.samsul.challengegdsc.R
import com.samsul.challengegdsc.adapter.SectionPagerAdapter
import com.samsul.challengegdsc.data.entity.Note
import com.samsul.challengegdsc.databinding.ActivityDetailBinding
import com.samsul.challengegdsc.ui.fragment.AboutFragment
import com.samsul.challengegdsc.ui.fragment.DescriptionFragment
import com.samsul.challengegdsc.utils.ViewModelFactory
import com.samsul.challengegdsc.viewmodel.MainViewModel

class DetailActivity : AppCompatActivity() {
    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance(this)
        ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.title = "Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val idNote = intent.getIntExtra("id_note", 0)
        val bundle = Bundle()
        bundle.putInt("id_note", idNote)
        setViewPager2(bundle)
        viewModel.getAllNotes().observe(this) {
            for(i in it) {
                if(i.id == idNote) {
                    binding.tvTitle.text = i.title
                    binding.tvLocation.text = i.location
                }
            }
        }
    }

    private fun setViewPager2(bundle: Bundle) {
        val fragmentList = listOf(DescriptionFragment(), AboutFragment())
        val tabTitle = listOf(resources.getString(R.string.description), resources.getString(R.string.about))
        binding.viewPager2.adapter = SectionPagerAdapter(fragmentList, supportFragmentManager, lifecycle,bundle)

        TabLayoutMediator(binding.tabLayout, binding.viewPager2) {tab, position ->
            tab.text = tabTitle[position]
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}