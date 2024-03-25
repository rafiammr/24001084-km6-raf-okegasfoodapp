package com.rafi.okegasfood.data.datasource.category

import com.rafi.okegasfood.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategories(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_rice.jpg?raw=true",
                name = "Nasi"
            ),
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_noodle.jpg?raw=true",
                name = "Mie"
            ),
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_snack.jpg?raw=true",
                name = "Snack"
            ),
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_fastfood.jpg?raw=true",
                name = "Fast food"
            ),
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_meatball.jpg?raw=true",
                name = "Bakso & soto"
            ),
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_seafood.jpg?raw=true",
                name = "Seafood"
            ),
            Category(
                imgUrl = "https://github.com/rafiammr/okegasfood-assets/blob/main/category_img/img_category_beverages.jpg?raw=true",
                name = "Minuman"
            )
        )
    }

}