package self.adragon.aviaroute_web.ui

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import self.adragon.aviaroute_web.R
import self.adragon.aviaroute_web.ui.adapters.ViewPagerAdapter
import self.adragon.aviaroute_web.ui.fragments.Documents
import self.adragon.aviaroute_web.ui.fragments.Profile
import self.adragon.aviaroute_web.ui.fragments.Search

private const val APP_PREFERENCES = "aviaroute_setting"

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout

    private lateinit var setting: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.mainViewPager2)
        tabLayout = findViewById(R.id.tabLayout)

        val fragments: List<Fragment> = listOf(Search(), Documents(), Profile())
        viewPager2.adapter = ViewPagerAdapter(fragments, this)
        viewPager2.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, viewPager2) { tab, i ->
            tab.text = when (i) {
                0 -> "Поиск билета"
                1 -> "Документы"
                2 -> "Личный кабинет"
                else -> "Ошибка"
            }
        }.attach()
        requestAllPerms()

        setting = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
    }


    private fun requestAllPerms() {
        val perm = Manifest.permission.POST_NOTIFICATIONS
        val perm2 = Manifest.permission.READ_EXTERNAL_STORAGE
        requestPermissions(arrayOf(perm, perm2), 1)
    }
}