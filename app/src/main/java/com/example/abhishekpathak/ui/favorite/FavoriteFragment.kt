package com.example.abhishekpathak.ui.favorite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Post
import com.example.abhishekpathak.R
import com.example.abhishekpathak.ui.detail.DetailFragment
import com.example.abhishekpathak.ui.extensions.inflate
import com.example.abhishekpathak.ui.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment() {

    private lateinit var bundle: Bundle
    private lateinit var adapter: FavoriteAdapter

    private val viewModel: FavoriteViewModel by currentScope.viewModel(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_favorite)
    }

    @SuppressLint("WrongConstant")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        postListFavorite.layoutManager = mLayoutManager

        postListFavorite.setHasFixedSize(true)

        adapter = FavoriteAdapter(viewModel::onAnimeClicked)
        postListFavorite.adapter = adapter
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: FavoriteViewModel.UiModel) {

        containerProgressIndicator.visibility = if (model is FavoriteViewModel.UiModel.Loading) View.VISIBLE else View.GONE

        when (model) {
            is FavoriteViewModel.UiModel.Content -> {
                adapter.animes = model.posts as MutableList<Post>
            }
            is FavoriteViewModel.UiModel.Navigation -> {
                requireActivity().intent.putExtra(DetailFragment.POST, model.post.postId)

                bundle = Bundle()
                Navigation.addFragment(requireActivity().supportFragmentManager,
                    R.id.fragmentContainer, DetailFragment(bundle)
                )
            }
            FavoriteViewModel.UiModel.showUi -> {
                viewModel.showUi()
            }
        }
    }
}