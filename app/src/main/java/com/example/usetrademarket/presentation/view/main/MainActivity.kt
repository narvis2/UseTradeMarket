package com.example.usetrademarket.presentation.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.usetrademarket.R
import com.example.usetrademarket.data.preference.AppPreferenceManager
import com.example.usetrademarket.databinding.ActivityMainBinding
import com.example.usetrademarket.presentation.base.BaseActivity
import com.example.usetrademarket.presentation.utils.MenuChangeEventBus
import com.example.usetrademarket.presentation.utils.extensions.toGone
import com.example.usetrademarket.presentation.utils.extensions.toVisible
import com.example.usetrademarket.presentation.view.home.HomeFragment
import com.example.usetrademarket.presentation.view.like.MyProductFavoriteFragment
import com.example.usetrademarket.presentation.view.myinquiry.MyInquiryActivity
import com.example.usetrademarket.presentation.view.profile.MyProfileFragment
import com.example.usetrademarket.presentation.view.search.ProductSearchActivity
import com.example.usetrademarket.presentation.view.signin.SigninActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()

    private val prefs : AppPreferenceManager by inject()

    private val menuChangeEventBus : MenuChangeEventBus by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.vm = viewModel

        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        observeData()
        initNavigationListener()
        initBottomNavigation()
        initSearchView()

        val header = binding.navigationView.getHeaderView(0)
        val userNameTextView = header.findViewById<TextView>(R.id.userNameTextView)
        val userEmailTextView = header.findViewById<TextView>(R.id.userEmailTextView)
        userNameTextView.setText(prefs.getUserName())
        userEmailTextView.setText(prefs.getUserEmail())

    }

    private fun initSearchView() = with(binding) {
        searchView.maxWidth = Int.MAX_VALUE
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                //엔터 누를때 호출
                override fun onQueryTextSubmit(keyword: String?): Boolean {
                    keyword?.let {
                        startActivity(
                            ProductSearchActivity.newIntent(this@MainActivity, keyword)
                        )
                    } ?: showToast("검색 키워드를 입력해주세요.")
                    Log.d("MainActivity", "query -> $keyword")
                    return true
                }

                // 검색할때마다 반응
                override fun onQueryTextChange(p0: String?): Boolean {
                    return true
                }
            }
        )
    }

    private fun initNavigationListener() = with(binding) {
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_message -> {
                    startActivity(MyInquiryActivity.newIntent(this@MainActivity))
                }
                R.id.menu_logout -> {
                    prefs.removeToken()
                    prefs.removeRefreshToken()
                    startActivity(SigninActivity.newIntent(this@MainActivity))
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }
    private fun initBottomNavigation() = with(binding) {
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
                    binding.toolbar.toVisible()
                    true
                }
                R.id.menu_like -> {
                    showFragment(MyProductFavoriteFragment.newInstance(), MyProductFavoriteFragment.TAG)
                    binding.toolbar.toGone()
                    true
                }
                R.id.menu_my -> {
                    showFragment(MyProfileFragment.newInstance(), MyProfileFragment.TAG)
                    binding.toolbar.toGone()
                    true
                }
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)
        supportFragmentManager.fragments.forEach { fm ->
            supportFragmentManager.beginTransaction().hide(fm).commitAllowingStateLoss()
        }
        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    override fun onBackPressed() {
        if (!binding.searchView.isIconified) {
            binding.searchView.isIconified = true
        } else {
            super.onBackPressed()
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            menuChangeEventBus.mainTabMenuFlow.collect {
                goToTab(it)
            }
        }
    }

    companion object{
        fun newIntent(activity: Activity) = Intent(activity, MainActivity::class.java)
    }

    // 어디에서든 탭 변경가능하게 하기위한 함수
    fun goToTab(mainTabMenu: MainTabMenu) {
        binding.bottomNav.selectedItemId = mainTabMenu.menuId
    }
}
enum class MainTabMenu(@IdRes val menuId: Int) {
    HOME(R.id.menu_home),
    LIKE(R.id.menu_like),
    MY(R.id.menu_my)
}