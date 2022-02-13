package com.netguru.codereview.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.netguru.codereview.shoplist.R
import com.netguru.codereview.ui.model.ResultState

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        layoutInflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()

        viewModel.getShopList()
    }

    private fun observe() {
        viewModel.shopListsLiveData.observe(viewLifecycleOwner) { result ->
            val progressBar = view?.findViewById<ProgressBar>(R.id.progress_bar)
            val latestIcon = view?.findViewById<ImageView>(R.id.latest_list_icon)

            result?.apply {
                when(this) {
                    is ResultState.Success -> {
                        this.data?.map { shotList ->
                            Log.i("LOG_TAG", "${
                                shotList?.listName
                            } : ${
                                shotList?.items?.map {
                                    it?.name
                                }
                            }"
                            )
                        }

                        latestIcon?.load(this.data?.lastOrNull()?.iconUrl)

                        progressBar?.isVisible = false

                        Log.i("LOG_TAG", "Yes, Is it done already?")


                        // Display the list in recyclerview
                        // adapter.submitList(shopLists)
                    }
                    is ResultState.Failed -> {
                        Toast.makeText(context, "some error Happened, ${this.error}",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        viewModel.events().observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

}
