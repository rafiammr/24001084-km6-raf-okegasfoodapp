package com.rafi.okegasfood.data.repository

import app.cash.turbine.test
import com.rafi.okegasfood.data.datasource.cart.CartDataSource
import com.rafi.okegasfood.data.model.Cart
import com.rafi.okegasfood.data.model.Menu
import com.rafi.okegasfood.data.model.PriceItem
import com.rafi.okegasfood.data.source.local.database.entity.CartEntity
import com.rafi.okegasfood.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class CartRepositoryImplTest {
    @MockK
    lateinit var ds: CartDataSource
    private lateinit var repo: CartRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = CartRepositoryImpl(ds)
    }

    @Test
    fun getUserCartData_success() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 1,
                itemNotes = "asdsadasdasd",
            )
        val entity2 =
            CartEntity(
                id = 1,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 1,
                itemNotes = "asdsadasdasd",
            )
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2202)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(16000.0, actualData.payload?.second)
            }
        }
    }

    @Test
    fun getUserCartData_loading() {
        val mockFlow = flow<List<CartEntity>> { delay(2000) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(1100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun getUserCartData_error() {
        every { ds.getAllCarts() } returns
            flow {
                throw IllegalStateException("Mock Error")
            }
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2202)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getUserCartData_empty() {
        val mockFlow = flow<List<CartEntity>> { emit(emptyList()) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(2301)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
            }
        }
    }

    @Test
    fun getCheckoutData_success() {
        val entity1 =
            CartEntity(
                id = 1,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 1,
                itemNotes = "asdsadasdasd",
            )
        val entity2 =
            CartEntity(
                id = 2,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 1,
                itemNotes = "asdsadasdasd",
            )
        val mockPriceList = listOf(PriceItem("asdasdasd", 8000.0), PriceItem("asdasdasd", 8000.0))
        val mockList = listOf(entity1, entity2)
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2202)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(mockList.size, actualData.payload?.first?.size)
                assertEquals(mockPriceList, actualData.payload?.second)
                assertEquals(16000.0, actualData.payload?.third)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_loading() {
        val mockFlow = flow<List<CartEntity>> { delay(2000) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getUserCartData().map {
                delay(100)
                it
            }.test {
                delay(1100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
            }
        }
    }

    @Test
    fun getCheckoutData_empty() {
        val mockList = listOf<CartEntity>()
        val mockFlow = flow { emit(mockList) }
        every { ds.getAllCarts() } returns mockFlow
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2202)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Empty)
                assertEquals(true, actualData.payload?.first?.isEmpty())
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun getCheckoutData_error() {
        every { ds.getAllCarts() } returns
            flow {
                throw IllegalStateException("Mock Error")
            }
        runTest {
            repo.getCheckoutData().map {
                delay(100)
                it
            }.test {
                delay(2210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                verify { ds.getAllCarts() }
            }
        }
    }

    @Test
    fun createCart_success() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, "sadsdasda")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2220)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_success_note_null() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3)
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2220)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Success)
                    assertEquals(true, actualData.payload)
                    coVerify { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun createCart_loading() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } coAnswers {
            delay(2000)
            1
        }
        runTest {
            repo.createCart(mockMenu, 3, "sadsdasda")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2110)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Loading)
                }
        }
    }

    @Test
    fun createCart_error_processing() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns "123"
        coEvery { ds.insertCart(any()) } throws IllegalStateException("Insert Cart Error")
        runTest {
            repo.createCart(mockMenu, 3, "sadsdasda")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    assertTrue((actualData as ResultWrapper.Error).exception?.message?.contains("Insert Cart Error") == true)
                }
        }
    }

    @Test
    fun createCart_error_no_product_id() {
        val mockMenu = mockk<Menu>(relaxed = true)
        every { mockMenu.id } returns null
        coEvery { ds.insertCart(any()) } returns 1
        runTest {
            repo.createCart(mockMenu, 3, "sadsdasda")
                .map {
                    delay(100)
                    it
                }.test {
                    delay(2210)
                    val actualData = expectMostRecentItem()
                    assertTrue(actualData is ResultWrapper.Error)
                    coVerify(atLeast = 0) { ds.insertCart(any()) }
                }
        }
    }

    @Test
    fun decreaseCart_when_quantity_more_than_0() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 2,
                itemNotes = "asdsadasdasd",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 0) { ds.deleteCart(any()) }
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun decreaseCart_when_quantity_less_than_1() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 1,
                itemNotes = "asdsadasdasd",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.decreaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
                coVerify(atLeast = 0) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun increaseCart() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 1,
                itemNotes = "asdsadasdasd",
            )
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.increaseCart(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun setCartNotes() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 3,
                itemNotes = "asdsadasdasd",
            )
        coEvery { ds.updateCart(any()) } returns 1
        runTest {
            repo.setCartNotes(mockCart).map {
                delay(100)
                it
            }.test {
                delay(210)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.updateCart(any()) }
            }
        }
    }

    @Test
    fun deleteCart() {
        val mockCart =
            Cart(
                id = 2,
                menuId = "asdasdasd",
                menuImgUrl = "asdasdasd",
                menuPrice = 8000.0,
                menuName = "asdasdasd",
                itemQuantity = 3,
                itemNotes = "asdsadasdasd",
            )
        coEvery { ds.deleteCart(any()) } returns 1
        runTest {
            repo.deleteCart(mockCart).test {
                delay(100)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                assertEquals(true, actualData.payload)
                coVerify(atLeast = 1) { ds.deleteCart(any()) }
            }
        }
    }

    @Test
    fun deleteAllCart() {
        coEvery { ds.deleteAll() } returns Unit // Mocking the deleteAll function to return Unit
        runTest {
            val flow = flow { emit(repo.deleteAllCart()) }
            flow.test {
                delay(100) // Delay to allow the flow to emit
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify(atLeast = 1) { ds.deleteAll() }
                cancelAndIgnoreRemainingEvents() // Cancel the test to ignore remaining events
            }
        }
    }
}
