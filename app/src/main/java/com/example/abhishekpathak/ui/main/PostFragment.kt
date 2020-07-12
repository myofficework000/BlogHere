package com.example.abhishekpathak.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Post
import com.example.domain.User
import com.example.abhishekpathak.R
import com.example.abhishekpathak.ui.detail.DetailFragment
import com.example.abhishekpathak.ui.extensions.inflate
import com.example.abhishekpathak.ui.navigation.Navigation
import com.example.abhishekpathak.ui.utils.SwipeToDeleteCallback
import kotlinx.android.synthetic.main.fragment_post.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel


class PostFragment(val type:Int) : Fragment() {


    private lateinit var bundle: Bundle
    private var userId = -1
    private var userName = ""
    private var userEmail = ""
    private var userPhone = ""
    private var userWebsite = ""
    private lateinit var adapter: PostAdapter
    private lateinit var usersList: MutableList<User>
    private val viewModel: PostViewModel by currentScope.viewModel(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_post)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       if(type==2){
           layout_new_post.visibility = View.VISIBLE
           postFragment.visibility = View.GONE
           //supportActionBar?.title = "Create Post"
       }else
       {
           layout_new_post.visibility = View.GONE
           postFragment.visibility = View.VISIBLE
       }
        val mLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        postList.layoutManager = mLayoutManager

        postList.setHasFixedSize(true)

        adapter = PostAdapter(requireActivity().applicationContext, viewModel::onPostClicked)
        postList.adapter = adapter

        viewModel.model.observe(this, Observer(::updateUi))

        deleteAllPostFb.setOnClickListener {
            viewModel.onDeleteAllPostsClicked()
            adapter.clearPostsList()
        }

        btn_create_post.setOnClickListener {
            viewModel.onAddNewPost(
                Post(
                1,
                1,
                edt_title.text.toString(),
                edt_description.text.toString(),
                    false,
                    false
            )
            )
            layout_new_post.visibility = View.GONE
            postFragment.visibility = View.VISIBLE
            adapter.addIntoPostsList()
        }



        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter, viewModel))
        itemTouchHelper.attachToRecyclerView(postList)
    }

    private fun updateUi(model: PostViewModel.UiModel) {

        containerProgressIndicator.visibility =
            if (model is PostViewModel.UiModel.Loading) VISIBLE else GONE

        when (model) {
            is PostViewModel.UiModel.Content -> {
                adapter.posts = model.posts
            }
            is PostViewModel.UiModel.UserContent -> {
                usersList = model.users
                Log.d("USER", "Lista de Usuarios: $usersList")

                for (pos in 0..usersList.size ) {
                    if (pos< adapter.posts.size && pos< usersList.size && adapter.posts[pos].userId == usersList[pos].userId) {
                        Log.d("USER", "Usuario: "+ viewModel.findUser(adapter.posts[pos].userId))
                    }
                }
            }
            is PostViewModel.UiModel.Navigation -> {
                viewModel.updateReadStatus(model.post)
                requireActivity().intent.putExtra(DetailFragment.POST, model.post.postId)

                if (usersList.isNotEmpty()) {
                    usersList.forEach {
                        if (model.post.userId == it.userId) {
                            userId=it.userId
                            userName = it.name
                            userEmail = it.email
                            userPhone = it.phone
                            userWebsite = it.website
                        }
                    }

                    bundle = Bundle()
                    bundle.putInt(DetailFragment.USER_ID,userId)
                    bundle.putString(DetailFragment.USER_NAME, userName)
                    bundle.putString(DetailFragment.USER_EMAIL, userEmail)
                    bundle.putString(DetailFragment.USER_PHONE, userPhone)
                    bundle.putString(DetailFragment.USER_WEBSITE, userWebsite)
                    requireActivity().intent.putExtras(bundle)
                }

                Navigation.addFragment(
                    requireActivity().supportFragmentManager,
                    R.id.fragmentContainer,
                    DetailFragment(bundle)
                )
            }
            is PostViewModel.UiModel.ReadStatus -> {
                viewModel.updateReadStatus(model.post)
            }
            PostViewModel.UiModel.showUi -> {
                viewModel.showUi()
            }
        }
    }
}
