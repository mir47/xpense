package com.xpense.android.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import com.xpense.android.R
import com.xpense.android.ui.theme.XpenseTheme

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setHasOptionsMenu(true)
            setContent {
                XpenseTheme {
                    AboutScreen()
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.about_menu, menu)
        // check if the activity resolves
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share).isVisible = false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) shareAbout()
        return super.onOptionsItemSelected(item)
    }

    private fun shareAbout() = startActivity(getShareIntent())

    private fun getShareIntent() = ShareCompat.IntentBuilder(requireActivity())
        .setText(APP_STORE_LINK)
        .setType("text/plain")
        .intent

    companion object {
        const val APP_STORE_LINK = "https://play.google.com/store/apps/details?id=com.xpense.android"
    }
}
