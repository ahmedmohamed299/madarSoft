package com.android.madarsoft.presentation.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.android.madarsoft.R
import com.android.madarsoft.databinding.FragmentInputBinding
import com.android.madarsoft.presentation.UiState
import com.android.madarsoft.utils.Gender
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InputFragment : Fragment() {

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    private val viewModel: InputViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInputBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            validateAndSave()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Idle -> {
                            binding.progressBar.visibility = View.GONE
                        }
                        is UiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.btnSave.isEnabled = false
                        }
                        is UiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSave.isEnabled = true
                            clearFields()
                            viewModel.resetState()
                            findNavController().navigate(R.id.action_inputFragment_to_displayFragment)
                        }
                        is UiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.btnSave.isEnabled = true
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    private fun validateAndSave() {
        val name = binding.etName.text.toString().trim()
        val ageStr = binding.etAge.text.toString().trim()
        val jobTitle = binding.etJobTitle.text.toString().trim()
        
        var isValid = true

        if (name.isEmpty()) {
            binding.tilName.error = "Name is required"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (ageStr.isEmpty() || ageStr.toIntOrNull() == null || ageStr.toInt() <= 0) {
            binding.tilAge.error = "Valid positive age is required"
            isValid = false
        } else {
            binding.tilAge.error = null
        }

        if (jobTitle.isEmpty()) {
            binding.tilJobTitle.error = "Job Title is required"
            isValid = false
        } else {
            binding.tilJobTitle.error = null
        }

        val checkedRadioButtonId = binding.rgGender.checkedRadioButtonId
        val gender = when (checkedRadioButtonId) {
            R.id.rbMale -> {
                Gender.MALE.displayName
            }
            R.id.rbFemale -> {
                Gender.FEMALE.displayName
            }
            else -> {
                ""
            }
        }

        if (gender.isEmpty()) {
            Toast.makeText(requireContext(), "Please select a gender", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        if (isValid) {
            viewModel.saveUser(name, ageStr, jobTitle, gender)
        }
    }

    private fun clearFields() {
        binding.etName.text?.clear()
        binding.etAge.text?.clear()
        binding.etJobTitle.text?.clear()
        binding.rgGender.clearCheck()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
