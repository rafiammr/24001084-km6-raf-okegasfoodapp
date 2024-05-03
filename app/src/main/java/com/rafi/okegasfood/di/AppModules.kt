package com.rafi.okegasfood.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.rafi.okegasfood.data.datasource.AuthDataSource
import com.rafi.okegasfood.data.datasource.FirebaseAuthDataSource
import com.rafi.okegasfood.data.datasource.cart.CartDataSource
import com.rafi.okegasfood.data.datasource.cart.CartDatabaseDataSource
import com.rafi.okegasfood.data.datasource.category.CategoryApiDataSource
import com.rafi.okegasfood.data.datasource.category.CategoryDataSource
import com.rafi.okegasfood.data.datasource.menu.MenuApiDataSource
import com.rafi.okegasfood.data.datasource.menu.MenuDataSource
import com.rafi.okegasfood.data.datasource.user.UserDataSource
import com.rafi.okegasfood.data.datasource.user.UserDataSourceImpl
import com.rafi.okegasfood.data.repository.CartRepository
import com.rafi.okegasfood.data.repository.CartRepositoryImpl
import com.rafi.okegasfood.data.repository.CategoryRepository
import com.rafi.okegasfood.data.repository.CategoryRepositoryImpl
import com.rafi.okegasfood.data.repository.MenuRepository
import com.rafi.okegasfood.data.repository.MenuRepositoryImpl
import com.rafi.okegasfood.data.repository.UserModeRepository
import com.rafi.okegasfood.data.repository.UserModeRepositoryImpl
import com.rafi.okegasfood.data.repository.UserRepository
import com.rafi.okegasfood.data.repository.UserRepositoryImpl
import com.rafi.okegasfood.data.source.firebase.FirebaseService
import com.rafi.okegasfood.data.source.firebase.FirebaseServiceImpl
import com.rafi.okegasfood.data.source.local.database.AppDatabase
import com.rafi.okegasfood.data.source.local.database.dao.CartDao
import com.rafi.okegasfood.data.source.local.pref.UserPreference
import com.rafi.okegasfood.data.source.local.pref.UserPreferenceImpl
import com.rafi.okegasfood.data.source.network.services.OkeGasFoodApiService
import com.rafi.okegasfood.presentation.cart.CartViewModel
import com.rafi.okegasfood.presentation.checkout.CheckoutViewModel
import com.rafi.okegasfood.presentation.detailmenu.DetailMenuViewModel
import com.rafi.okegasfood.presentation.home.HomeViewModel
import com.rafi.okegasfood.presentation.login.LoginViewModel
import com.rafi.okegasfood.presentation.main.MainViewModel
import com.rafi.okegasfood.presentation.profile.ProfileViewModel
import com.rafi.okegasfood.presentation.register.RegisterViewModel
import com.rafi.okegasfood.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {
    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val firebaseModule =
        module {
            single<FirebaseAuth> { FirebaseAuth.getInstance() }
            single<FirebaseService> { FirebaseServiceImpl(get()) }
        }

    private val networkModule =
        module {
            single<OkeGasFoodApiService> { OkeGasFoodApiService.invoke() }
        }

    private val datasource =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<CategoryDataSource> { CategoryApiDataSource(get()) }
            single<MenuDataSource> { MenuApiDataSource(get()) }
            single<UserDataSource> { UserDataSourceImpl(get()) }
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
        }

    private val repository =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<UserModeRepository> { UserModeRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
            single<MenuRepository> { MenuRepositoryImpl(get(), get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::ProfileViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModel { params ->
                DetailMenuViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
            viewModelOf(::MainViewModel)
        }

    val modules = listOf<Module>(firebaseModule, networkModule, localModule, datasource, repository, viewModelModule)
}
