package momonyan.pokertools

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import net.nend.android.NendAdInterstitial

class MainActivity : AppCompatActivity() {

    private lateinit var mSectionsPagerAdapter: TabAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NendAdInterstitial.loadAd(
            applicationContext,
            getString(R.string.AdPopKey),
            resources.getInteger(R.integer.AdPopup)
        )
        setContentView(R.layout.activity_main)

        mSectionsPagerAdapter = TabAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))


        //クリック時の動作
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                NendAdInterstitial.showAd(this@MainActivity)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }
}
