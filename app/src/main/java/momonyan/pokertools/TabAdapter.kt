package momonyan.pokertools

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val setFragment: Fragment
        when (position) {
            0 -> setFragment = Tab1StrengthList()
            1 -> setFragment = Tab2WordList()
            2 -> setFragment = Tab3BetMode()
            else -> error("TabError")
        }
        return setFragment
    }

    override fun getCount(): Int {
        return 3
    }
}