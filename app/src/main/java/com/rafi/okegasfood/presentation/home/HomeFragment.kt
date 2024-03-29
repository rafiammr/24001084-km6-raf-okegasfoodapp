package com.rafi.okegasfood.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.category.DummyCategoryDataSource
import com.rafi.okegasfood.data.datasource.menu.DummyMenuDataSource
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.CategoryRepositoryImpl
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.repository.MenuRepositoryImpl
import com.rafi.okegasfood.data.model.Category
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.source.local.pref.UserPreference
import com.rafi.okegasfood.data.source.local.pref.UserPreferenceImpl
import com.rafi.okegasfood.databinding.FragmentHomeBinding
import com.rafi.okegasfood.presentation.detailmenu.DetailMenuActivity
import com.rafi.okegasfood.presentation.home.homeadapter.CategoryAdapter
import com.rafi.okegasfood.presentation.home.homeadapter.MenuAdapter
import com.rafi.okegasfood.presentation.home.homeadapter.OnItemClickedListener
import com.rafi.okegasfood.utils.GenericViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var userPreference: UserPreference

    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenuDataSource()
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)

        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {

        }
    }

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(
            object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    DetailMenuActivity.startActivity(requireContext(), item)
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        userPreference = UserPreferenceImpl(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindListCategory(viewModel.getCategories())
        observeGridMode()
        setClickAction()
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            userPreference.setUsingGridMode(isUsingGridMode)
            bindListMenu(isUsingGridMode)
            setIcon(isUsingGridMode)
        }
    }

    private fun setClickAction() {
        binding.layoutHeaderMenu.ivIconGridList.setOnClickListener {
            viewModel.changeListMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.layoutHeaderMenu.ivIconGridList.setImageResource(if (usingGridMode) R.drawable.ic_grid else R.drawable.ic_list)
    }

    private fun bindListCategory(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        categoryAdapter.submitData(data)
    }

    private fun bindListMenu(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        menuAdapter.listMode = listMode
        binding.rvMenu.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
        menuAdapter.submitData(viewModel.getMenu())
    }
}

