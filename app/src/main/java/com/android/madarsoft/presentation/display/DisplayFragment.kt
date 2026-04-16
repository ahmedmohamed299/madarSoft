package com.android.madarsoft.presentation.display

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.android.madarsoft.databinding.FragmentDisplayBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DisplayFragment : Fragment() {

    private var _binding: FragmentDisplayBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DisplayViewModel by viewModels()
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDisplayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeUsers()
    }

    private fun setupRecyclerView() {
        adapter = UsersAdapter()
        binding.rvUsers.adapter = adapter
    }

    private fun observeUsers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.users.collect { userList ->
                    if (userList.isEmpty()) {
                        binding.tvEmptyState.visibility = View.VISIBLE
                        binding.rvUsers.visibility = View.GONE
                    } else {
                        binding.tvEmptyState.visibility = View.GONE
                        binding.rvUsers.visibility = View.VISIBLE
                        adapter.submitList(userList)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
