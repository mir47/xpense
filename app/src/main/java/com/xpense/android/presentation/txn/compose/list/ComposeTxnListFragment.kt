package com.xpense.android.presentation.txn.compose.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.xpense.android.XpenseApplication
import com.xpense.android.domain.use_case.get_txns.GetTxnsUseCase
import com.xpense.android.presentation.ui.theme.XpenseTheme

@ExperimentalMaterialApi
class ComposeTxnListFragment : Fragment() {

    private val vm by viewModels<ComposeTxnListViewModel> {
        ComposeTxnListViewModel.ComposeTxnListViewModelFactory(
            GetTxnsUseCase(
                (requireContext().applicationContext as XpenseApplication).txnRepository
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                XpenseTheme {
                    TxnListScreen(findNavController(), vm)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        vm.getTxns()
    }
}
