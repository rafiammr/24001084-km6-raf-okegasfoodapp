package com.rafi.okegasfood.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.AuthDataSource
import com.rafi.okegasfood.data.datasource.FirebaseAuthDataSource
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.data.repository.UserRepositoryImpl
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import com.rafi.okegasfood.data.source.firebase.FirebaseServiceImpl
import com.rafi.okegasfood.databinding.FragmentProfileBinding
import com.rafi.okegasfood.presentation.login.LoginActivity
import com.rafi.okegasfood.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(ProfileViewModel(r))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showUserData()
        observeEditMode()
        setClickListener()
//        observeProfileData()
    }

    private fun showUserData() {
        viewModel.getCurrentUser()?.let {
            binding.nameEditText.setText(it.fullName)
            binding.emailEditText.setText(it.email)
            binding.passwordEditText.isVisible = false
            binding.ivProfileImg.load(it.photoUrl) {
                crossfade(true)
                placeholder(R.drawable.iv_user)
                error(R.drawable.iv_user)
            }
        }
    }

    /*    private fun observeProfileData(email: String, password: String, fullName: String) {
            viewModel.profileData(email, password, fullName).observe(viewLifecycleOwner) {
                binding.nameEditText.setText(fullName)
                binding.emailEditText.setText(email)
                binding.passwordEditText.setText(password)
            }
        }*/

    private fun setClickListener() {
        binding.ibEditHeaderProfile.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.tvLogout.setOnClickListener {
            doLogOut()
        }
    }

    private fun doLogOut() {
        val dialog = AlertDialog.Builder(requireContext()).setMessage("Do you want to logout ?")
            .setPositiveButton(
                "Yes"
            ) { dialog, id ->
                viewModel.doLogout()
                navigateToLogin()
            }
            .setNegativeButton(
                "No"
            ) { dialog, id ->
                //no-op , do nothing
            }.create()
        dialog.show()

    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
            binding.passwordEditText.isEnabled = it
        }
    }
}