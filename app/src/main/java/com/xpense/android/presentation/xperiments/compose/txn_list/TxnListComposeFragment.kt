package com.xpense.android.presentation.xperiments.compose.txn_list

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
class TxnListComposeFragment : Fragment() {

    private val viewModel by viewModels<TxnListComposeViewModel> {
        TxnListComposeViewModel.TxnListComposeViewModelFactory(
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
                    TxnListScreen(findNavController(), viewModel)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getTxns()
    }
}
