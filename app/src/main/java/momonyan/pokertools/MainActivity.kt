package momonyan.pokertools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                AlertDialog.Builder(this)
                    .setTitle("Webページを開きます")
                    .setMessage("プライバシーポリシーのページを開きます\nよろしいですか？")
                    .setPositiveButton("OK") { _, _ ->
                        val uri = Uri.parse(getString(R.string.privacy))
                        val i = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(i)
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
                return true
            }
            R.id.menu2 -> {
                AlertDialog.Builder(this)
                    .setTitle("Webページを開きます")
                    .setMessage("意見感想のページを開きます\nよろしいですか？")
                    .setPositiveButton("OK") { _, _ ->
                        val uri = Uri.parse(getString(R.string.enquete))
                        val i = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(i)
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
                return true
            }
            R.id.menu0 -> {
            AlertDialog.Builder(this)
                .setTitle("Webページを開きます")
                .setMessage("幻想乃桜工房のHPを開きます\nよろしいですか？")
                .setPositiveButton("OK") { _, _ ->
                    val uri = Uri.parse(getString(R.string.fantasyBlossomURL))
                    val i = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(i)
                }
                .setNegativeButton("キャンセル", null)
                .show()
            return true
            }
            R.id.menuReview -> {
                AlertDialog.Builder(this)
                    .setTitle("プレイストアを開きます")
                    .setMessage("ぜひレビューをお願いします！！")
                    .setPositiveButton("OK") { _, _ ->
                        val uri = Uri.parse(getString(R.string.reviewURL))
                        val i = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(i)
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
