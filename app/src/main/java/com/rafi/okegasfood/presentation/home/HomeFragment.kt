package com.rafi.okegasfood.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.rafi.okegasfood.R
import com.rafi.okegasfood.data.datasource.category.CategoryApiDataSource
import com.rafi.okegasfood.data.datasource.category.CategoryDataSource
import com.rafi.okegasfood.data.datasource.menu.MenuApiDataSource
import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.datasource.user.UserDataSource
import com.rafi.okegasfood.data.datasource.user.UserDataSourceImpl
import com.rafi.okegasfood.data.model.Category
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.CategoryRepositoryImpl
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.repository.MenuRepositoryImpl
import com.rafi.okegasfood.data.repository.UserModeRepository
import com.rafi.okegasfood.data.repository.UserModeRepositoryImpl
import com.rafi.okegasfood.data.source.local.pref.UserPreference
import com.rafi.okegasfood.data.source.local.pref.UserPreferenceImpl
import com.rafi.okegasfood.data.source.network.services.OkeGasFoodApiService
import com.rafi.okegasfood.databinding.FragmentHomeBinding
import com.rafi.okegasfood.presentation.detailmenu.DetailMenuActivity
import com.rafi.okegasfood.presentation.home.homeadapter.CategoryAdapter
import com.rafi.okegasfood.presentation.home.homeadapter.MenuAdapter
import com.rafi.okegasfood.presentation.home.homeadapter.OnItemClickedListener
import com.rafi.okegasfood.utils.GenericViewModelFactory
import com.rafi.okegasfood.utils.proceedWhen

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var mySharedPreferences: UserPreferenceImpl


    private val viewModel: HomeViewModel by viewModels {
        val service = OkeGasFoodApiService.invoke()
        val userPreference: UserPreference = UserPreferenceImpl(requireContext())
        val userDataSource: UserDataSource = UserDataSourceImpl(userPreference)
        val userRepository: UserModeRepository = UserModeRepositoryImpl(userDataSource)
        val menuDataSource: MenuDataSource = MenuApiDataSource(service)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource: CategoryDataSource = CategoryApiDataSource(service)
        val categoryRepository: CategoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(
            HomeViewModel(
                categoryRepository,
                menuRepository,
                userRepository
            )
        )
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuData(it.nameCategory)
        }
    }

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(object : OnItemClickedListener<Menu> {
            override fun onItemClicked(item: Menu) {
                DetailMenuActivity.startActivity(requireContext(), item)
            }
        })
    }

    private fun applyUiMode() {
        val isUsingGridMode = viewModel.isUsingGridMode()
        if (isUsingGridMode) {
            setupListMenu(isUsingGrid = true)
        } else {
            setupListMenu(isUsingGrid = false)
        }
        setIcon(isUsingGridMode)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mySharedPreferences = UserPreferenceImpl(requireContext())
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyUiMode()
        setupListCategory()
        getCategoryData()
        getMenuData(null)
        setClickAction()
    }

    private fun getMenuData(categoryName: String? = null) {
        Log.d("HomeFragment", "getSlugData: Slug received: $categoryName")
        viewModel.getMenu(categoryName).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutStateHomeMenu.root.isVisible = false
                    binding.layoutStateHomeMenu.pbLoading.isVisible = false
                    binding.layoutStateHomeMenu.tvError.isVisible = false
                    binding.rvMenu.isVisible = true
                    Log.d("HomeFragment", "getMenuData: Menu received: ${it.payload}")
                    it.payload?.let { data -> bindListMenu(data) }
                },
                doOnError = {
                    binding.layoutStateHomeMenu.root.isVisible = true
                    binding.layoutStateHomeMenu.pbLoading.isVisible = false
                    binding.layoutStateHomeMenu.tvError.isVisible = true
                    binding.layoutStateHomeMenu.tvError.text = it.exception?.message.orEmpty()
                    binding.rvMenu.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateHomeMenu.root.isVisible = true
                    binding.layoutStateHomeMenu.pbLoading.isVisible = false
                    binding.layoutStateHomeMenu.tvError.isVisible = true
                    binding.layoutStateHomeMenu.tvError.text = getString(R.string.text_no_data)
                    binding.rvMenu.isVisible = false
                }
            )
        }
    }

    private fun getCategoryData() {
        Log.d("HomeFragment", "getCategoryData: Fetching categories")
        viewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnLoading = {
                    binding.layoutStateHomeCategory.root.isVisible = true
                    binding.layoutStateHomeCategory.pbLoading.isVisible = true
                    binding.layoutStateHomeCategory.tvError.isVisible = false
                    binding.rvCategory.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutStateHomeCategory.root.isVisible = false
                    binding.layoutStateHomeCategory.pbLoading.isVisible = false
                    binding.layoutStateHomeCategory.tvError.isVisible = false
                    binding.rvCategory.isVisible = true
                    it.payload?.let { data -> bindListCategory(data) }
                },
                doOnError = {
                    binding.layoutStateHomeCategory.root.isVisible = true
                    binding.layoutStateHomeCategory.pbLoading.isVisible = false
                    binding.layoutStateHomeCategory.tvError.isVisible = true
                    binding.layoutStateHomeCategory.tvError.text = it.exception?.message.orEmpty()
                    binding.rvCategory.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutStateHomeCategory.root.isVisible = true
                    binding.layoutStateHomeCategory.pbLoading.isVisible = false
                    binding.layoutStateHomeCategory.tvError.isVisible = true
                    binding.layoutStateHomeCategory.tvError.text = getString(R.string.text_no_data)
                    binding.rvCategory.isVisible = false
                }
            )
        }
    }

    private fun setupListMenu(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        menuAdapter.listMode = listMode
        binding.rvMenu.apply {
            adapter = menuAdapter
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGrid) 2 else 1)
        }
    }

    private fun setupListCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun setClickAction() {
        binding.layoutHeaderMenu.ivIconGridList.setOnClickListener {
            viewModel.changeListGridMode()
            applyUiMode()
        }
    }

    private fun setIcon(usingGridMode: Boolean) {
        binding.layoutHeaderMenu.ivIconGridList.setImageResource(if (usingGridMode) R.drawable.ic_grid else R.drawable.ic_list)
    }

    private fun bindListCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun bindListMenu(data: List<Menu>) {
        menuAdapter.submitData(data)
    }
}

