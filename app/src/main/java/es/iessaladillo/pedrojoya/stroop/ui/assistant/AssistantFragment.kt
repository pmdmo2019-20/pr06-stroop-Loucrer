package es.iessaladillo.pedrojoya.stroop.ui.assistant

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import es.iessaladillo.pedrojoya.stroop.R
import es.iessaladillo.pedrojoya.stroop.base.OnToolbarAvailableListener
import es.iessaladillo.pedrojoya.stroop.data.models.Page
import kotlinx.android.synthetic.main.assistant_fragment.*

class AssistantFragment: Fragment(R.layout.assistant_fragment) {

    private lateinit var assistantAdapter: AssistantFragmentAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        setupAdapter()
        setupViewPager()
        setupTabLayout()
        setupToolbar()
    }

    private fun setupTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
    }


    private fun setupToolbar() {
        (requireActivity() as OnToolbarAvailableListener).onToolbarCreated(toolbar)
    }

    private fun setupViewPager() {
        viewPager.run {
            adapter = assistantAdapter
        }
    }

    private fun setupAdapter() {
        val pageList: ArrayList<Page> = arrayListOf()
        pageList.add(
            Page(
                R.drawable.logo,
                getString(R.string.assistant_stroopDescription),
                R.color.stroopOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_play_black_24dp,
                getString(R.string.assistant_playDescription),
                R.color.playOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_settings_black_24dp,
                getString(R.string.assistant_settingsDescription),
                R.color.settingsOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_ranking_black_24dp,
                getString(R.string.assistant_rankingDescription),
                R.color.rankingOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_assistant_black_24dp,
                getString(R.string.assistant_assistantDescription),
                R.color.assistantOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_player_black_24dp,
                getString(R.string.assistant_playerDescription),
                R.color.playerOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_about_black_24dp,
                getString(R.string.assistant_aboutDescription),
                R.color.aboutOption
            )
        )
        pageList.add(
            Page(
                R.drawable.ic_finish_black_24dp,
                getString(R.string.assistant_finishDescription),
                R.color.finishOption
            )
        )

        assistantAdapter = AssistantFragmentAdapter(pageList).also {
            it.onItemClickListener = {
                activity?.onBackPressed()
            }
        }
    }
}