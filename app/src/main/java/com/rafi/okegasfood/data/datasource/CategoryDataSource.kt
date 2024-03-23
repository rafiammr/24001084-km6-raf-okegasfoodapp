package com.rafi.okegasfood.data.datasource

import com.rafi.okegasfood.data.model.Category

interface CategoryDataSource {
    fun getCategoryData(): List<Category>
}

class CategoryDataSourceImpl() : CategoryDataSource {
    override fun getCategoryData(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://i.pinimg.com/564x/86/fb/60/86fb60ce155d83690c0467c580934d98.jpg",
                name = "Nasi"
            ),
            Category(
                imgUrl = "https://i.pinimg.com/564x/2c/bf/9b/2cbf9bccdbd9c21769c03392dd695852.jpg",
                name = "Mie"
            ),
            Category(
                imgUrl = "https://i.pinimg.com/564x/36/32/42/3632425b55e32c164661a2547e5c861f.jpg",
                name = "Snack"
            ),
            Category(
                imgUrl = "https://i.pinimg.com/564x/a9/9f/d5/a99fd5e32d71ffdb6448409c96019c21.jpg",
                name = "Fast food"
            ),
            Category(
                imgUrl = "https://i.pinimg.com/564x/f7/6c/93/f76c93a3a23c2666e107ada4c4f33aec.jpg",
                name = "Bakso & soto"
            ),
            Category(
                imgUrl = "https://i.pinimg.com/564x/64/b4/63/64b463e8a33d4036166461102cc93206.jpg",
                name = "Seafood"
            ),
            Category(
                imgUrl = "https://i.pinimg.com/564x/26/67/f8/2667f88df9c8413faccbbf0e8fd64106.jpg",
                name = "Minuman"
            )
        )
    }

}