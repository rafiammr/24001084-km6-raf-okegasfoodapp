package com.rafi.okegasfood.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.databinding.FragmentHomeBinding
import com.rafi.okegasfood.presentation.home.homeadapter.CategoryAdapter
import com.rafi.okegasfood.presentation.home.homeadapter.MenuAdapter
import com.rafi.okegasfood.presentation.home.homeadapter.OnItemClickedListener

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var adapterMenu: MenuAdapter? = null
    private var adapterCategory: CategoryAdapter? = null
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterCategory = CategoryAdapter()
        setListCategory()
        observeGridMode()
        setClickAction()
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner){isUsingGridMode ->
            bindMenuList(isUsingGridMode)
            setIcon(isUsingGridMode)
        }
    }

    private fun setListCategory() {
        binding.rvCategory.apply {
            adapter = this@HomeFragment.adapterCategory
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        adapterCategory?.submitData(viewModel.getCategoryList())
    }

    private fun setClickAction() {
        binding.layoutHeaderMenu.ivIconGridList.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.layoutHeaderMenu.ivIconGridList.setImageResource(if (usingGridMode) R.drawable.ic_grid else R.drawable.ic_list)

    }

    private fun bindMenuList(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenuAdapter.MODE_GRID  else MenuAdapter.MODE_LIST
        adapterMenu = MenuAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {

                }
            }
        )
        binding.rvMenu.apply {
            adapter = this@HomeFragment.adapterMenu
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        adapterMenu?.submitData(viewModel.getMenuList())
    }

}