package com.example.abhishekpathak.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.abhishekpathak.R
import com.example.abhishekpathak.ui.extensions.inflate
import com.example.domain.User
import kotlinx.android.synthetic.main.fragment_detail.editUser
import kotlinx.android.synthetic.main.fragment_detail.edt_update_name
import kotlinx.android.synthetic.main.fragment_detail.edt_update_phone
import kotlinx.android.synthetic.main.fragment_detail.edt_update_username
import kotlinx.android.synthetic.main.fragment_detail.edt_update_website
import kotlinx.android.synthetic.main.fragment_detail.ivFavorite
import kotlinx.android.synthetic.main.fragment_detail.titleDetailTextView
import kotlinx.android.synthetic.main.fragment_detail.tvDetailDescription
import kotlinx.android.synthetic.main.fragment_detail.tvUserEmail
import kotlinx.android.synthetic.main.fragment_detail.tvUserName
import kotlinx.android.synthetic.main.fragment_detail.tvUserPhone
import kotlinx.android.synthetic.main.fragment_detail.tvUserWebsite
import kotlinx.android.synthetic.main.fragment_detail.updateEditedUser
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment(var bundle: Bundle) : Fragment() {

    companion object {
        private val CLASS_TAG = DetailFragment::class.java.simpleName
        const val POST = "DetailFragment:post"
        const val USER_ID = "DetailFragment:user_id"
        const val USER_NAME = "DetailFragment:user_name"
        const val USER_EMAIL = "DetailFragment:user_email"
        const val USER_PHONE = "DetailFragment:user_phone"
        const val USER_WEBSITE = "DetailFragment:user_website"
    }

    private lateinit var id: String
    private var userId = -1
    private var name = ""
    private var email = ""
    private var phone = ""
    private var website = ""

    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        id = requireActivity().intent.getIntExtra(POST, -1).toString()
        if (!bundle.isEmpty) {
            bundle = activity?.intent?.extras!!
            userId = bundle.getInt(USER_ID)
            name = bundle.getString(USER_NAME).orEmpty()
            email = bundle.getString(USER_EMAIL).orEmpty()
            phone = bundle.getString(USER_PHONE).orEmpty()
            website = bundle.getString(USER_WEBSITE).orEmpty()
        }
        parametersOf(id.toDouble())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initViews(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.model.observe(this, Observer(::updateUi))

        setListeners()

        viewModel.updateReadStatus()
    }

    private fun updateUi(model: DetailViewModel.UiModel) = with(model.post) {
        id = model.post.postId.toString()

        titleDetailTextView.text = model.post.title
        tvDetailDescription.text = model.post.body

        val icon = if (favorite) R.drawable.ic_favorite else R.drawable.ic_favorite_outline
        ivFavorite.setImageDrawable(activity?.applicationContext?.let { getDrawable(it, icon) })

        tvUserName.text = name
        tvUserEmail.text = email
        tvUserPhone.text = phone
        tvUserWebsite.text = website
    }

    private fun setListeners() {
        ivFavorite.setOnClickListener { viewModel.onFavoriteClicked() }
        editUser.setOnClickListener {
            tvUserName.visibility = View.GONE
            tvUserEmail.visibility = View.GONE
            tvUserPhone.visibility = View.GONE
            tvUserWebsite.visibility = View.GONE

            edt_update_name.visibility = View.VISIBLE
            edt_update_username.visibility = View.VISIBLE
            edt_update_phone.visibility = View.VISIBLE
            edt_update_website.visibility = View.VISIBLE
            updateEditedUser.visibility = View.VISIBLE
            editUser.visibility = View.GONE
        }

        updateEditedUser.setOnClickListener {
            tvUserName.visibility = View.VISIBLE
            tvUserEmail.visibility = View.VISIBLE
            tvUserPhone.visibility = View.VISIBLE
            tvUserWebsite.visibility = View.VISIBLE

            edt_update_name.visibility = View.GONE
            edt_update_username.visibility = View.GONE
            edt_update_phone.visibility = View.GONE
            edt_update_website.visibility = View.GONE
            updateEditedUser.visibility = View.GONE
            editUser.visibility = View.VISIBLE

            tvUserName.text = edt_update_name.text.toString()
            tvUserEmail.text = edt_update_username.text.toString()
            tvUserPhone.text = edt_update_phone.text.toString()
            tvUserWebsite.text = edt_update_website.text.toString()

            viewModel.updateUserDetails(
                User(
                    userId,
                    edt_update_name.text.toString(),
                    edt_update_username.text.toString(),
                    edt_update_phone.text.toString(),
                    edt_update_website.text.toString()
                )
            )

            Toast.makeText(activity, "User details updated successfully...", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initViews(container: ViewGroup?): View? {
        return container?.inflate(R.layout.fragment_detail)
    }
}
