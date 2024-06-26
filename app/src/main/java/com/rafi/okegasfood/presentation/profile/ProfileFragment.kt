package com.rafi.okegasfood.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.rafi.okegasfood.R
import com.rafi.okegasfood.databinding.FragmentProfileBinding
import com.rafi.okegasfood.presentation.login.LoginActivity
import com.rafi.okegasfood.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupForm()
        showUserData()
        setClickListener()
        observeEditMode()
    }

    private fun setupForm() {
        binding.layoutForm.tilName.isVisible = true
        binding.layoutForm.etName.isEnabled = false
        binding.layoutForm.tilEmail.isVisible = true
        binding.layoutForm.etEmail.isEnabled = false
        binding.btnChangeProfile.isEnabled = false
    }

    private fun showUserData() {
        profileViewModel.getCurrentUser()?.let {
            binding.layoutForm.etName.setText(it.fullName)
            binding.layoutForm.etEmail.setText(it.email)
            binding.ivProfileImg.load(it.photoUrl) {
                crossfade(true)
                placeholder(R.drawable.iv_user)
                error(R.drawable.iv_user)
            }
        }
    }

    private fun setClickListener() {
        binding.ibEditHeaderProfile.setOnClickListener {
            profileViewModel.changeEditMode()
        }
        binding.btnChangeProfile.setOnClickListener {
            if (checkNameValidation()) {
                changeProfileData()
            }
        }
        binding.tvLogout.setOnClickListener {
            doLogOut()
        }
    }

    private fun changeProfileData() {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        profileViewModel.updateFullName(fullName)
    }

    private fun checkNameValidation(): Boolean {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        return if (fullName.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }

    private fun doLogOut() {
        val dialog =
            AlertDialog.Builder(requireContext()).setMessage(getString(R.string.text_want_to_logout))
                .setPositiveButton(
                    "Yes",
                ) { dialog, id ->
                    profileViewModel.doLogout()
                    navigateToLogin()
                }
                .setNegativeButton(
                    "No",
                ) { dialog, id ->
                }.create()
        dialog.show()
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun observeEditMode() {
        profileViewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.layoutForm.tilName.isVisible = true
            binding.layoutForm.etName.isEnabled = it
            binding.btnChangeProfile.isEnabled = it
        }
        observeData()
    }

    private fun observeData() {
        profileViewModel.changeProfileResult.observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_change_profile_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    binding.layoutForm.etName.isEnabled = false
                    binding.layoutForm.etEmail.isEnabled = false
                    binding.btnChangeProfile.isEnabled = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.btnChangeProfile.isVisible = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_change_profile_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                    binding.layoutForm.etName.isEnabled = false
                    binding.layoutForm.etEmail.isEnabled = false
                    binding.btnChangeProfile.isEnabled = false
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.btnChangeProfile.isVisible = false
                },
            )
        }
    }
}
