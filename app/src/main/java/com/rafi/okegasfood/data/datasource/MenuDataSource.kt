package com.rafi.okegasfood.data.datasource

import com.rafi.okegasfood.data.model.Menu

interface MenuDataSource {
    fun getMenuData(): List<Menu>
}

class MenuDataSourceImpl() : MenuDataSource {
    override fun getMenuData(): List<Menu> {
        return mutableListOf(
            Menu(
                imgUrl = "https://i.pinimg.com/736x/30/77/26/3077267be56b9dbab34d865b86e89948.jpg",
                name = "Dimsum Mix",
                price = 20000.00,
                shortDesc = "Dimsum Mix adalah paduan sempurna dari berbagai dimsum yang menggoda selera. Dari pangsit goreng yang renyah hingga siomay yang lembut, setiap suapan menawarkan cita rasa autentik Asia. Disajikan dengan saus spesial, sajian ini memikat dengan tekstur yang beragam dan perpaduan rasa yang sempurna. Dengan aroma rempah yang menggugah selera, Dimsum Mix adalah pilihan tak terbantahkan bagi para pencinta kuliner Asia.",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/69/30/35/6930350561e6a3188d664f5af29e81b1.jpg",
                name = "Kue pukis",
                price = 15000.00,
                shortDesc = "Nikmati kelezatan kue pukis yang lezat dan segar! Dibuat dengan olahan segar dan bahan-bahan berkualitas, setiap gigitan mempersembahkan kombinasi rasa manis dan harum yang memikat. Sajikan sebagai camilan istimewa atau hidangan penutup yang sempurna untuk menambahkan sentuhan istimewa pada momen Anda. Segera dapatkan kue pukis ini sebelum kehabisan!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/2c/44/32/2c443267b13619fa7abc8b44c4951161.jpg",
                name = "Martabak manis",
                price = 23000.00,
                shortDesc = "Rasakan kenikmatan Martabak Manis yang lezat dan menggugah selera! Dengan paduan antara telur, gula, dan susu yang melimpah, setiap gigitan menghadirkan cita rasa manis yang memanjakan lidah. Tersedia dalam berbagai varian topping mulai dari cokelat, keju, hingga kacang, menjadikannya pilihan camilan atau hidangan penutup yang sempurna untuk dinikmati kapan pun Anda inginkan. Segera nikmati kelezatan Martabak Manis ini dan buat hari Anda menjadi lebih istimewa!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/be/41/3a/be413a27aaac08b15b6dfaef474d39d5.jpg",
                name = "Martabak telur",
                price = 30000.00,
                shortDesc = "Nikmati kelezatan Martabak Telur yang lezat dan menggugah selera! Dibuat dengan telur yang berlimpah, ditambah dengan campuran daging dan rempah-rempah pilihan, setiap gigitan membawa Anda dalam perjalanan rasa yang autentik. Dengan tekstur renyah di luar dan lembut di dalam, sajian ini adalah pilihan sempurna untuk sarapan atau camilan kapan pun Anda inginkan. Segera rasakan cita rasa Martabak Telur yang menggugah selera ini sekarang juga!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/6b/7e/ef/6b7eef2ea0d9f9abe3166f20b85499c8.jpg",
                name = "Takoyaki",
                price = 13000.00,
                shortDesc = "Nikmati sensasi Takoyaki, sajian Jepang yang lezat dan unik! Dengan balutan tepung yang renyah di luar dan isian lembut di dalam, setiap bola Takoyaki menghadirkan kombinasi cita rasa gurih dan manis yang memikat. Disajikan dengan saus khas Jepang dan taburan bonito flakes yang bergerak-gerak, Takoyaki adalah camilan atau hidangan penutup yang sempurna untuk dinikmati bersama keluarga dan teman-teman. Segera nikmati kelezatan Takoyaki ini dan rasakan sensasi kuliner Jepang yang autentik!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/e6/a4/2c/e6a42cad211c1b45e81c2a0381764a9d.jpg",
                name = "Corndog",
                price = 10000.00,
                shortDesc = "Rasakan kelezatan Corndog yang unik dan menggugah selera! Dengan sosis yang dibalut dengan adonan jagung yang renyah dan gurih, setiap gigitan merupakan perpaduan cita rasa yang sempurna. Tersedia dalam berbagai varian topping dan saus, Corndog adalah pilihan camilan yang cocok untuk dinikmati kapan pun Anda inginkan. Segera nikmati sensasi Corndog ini dan buat hari Anda menjadi lebih istimewa!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/32/a4/29/32a4291c2da7db49ea10400207aad4c7.jpg",
                name = "Topokki",
                price = 15000.00,
                shortDesc = "Rasakan kelezatan Topokki, hidangan Korea yang lezat dan menggugah selera! Terbuat dari kue beras yang kenyal, direndam dalam saus pedas manis yang khas Korea, setiap suapan mempersembahkan perpaduan cita rasa yang menggoda. Dengan tambahan bahan tambahan seperti telur, keju, atau seafood, Topokki adalah camilan atau hidangan utama yang sempurna untuk dinikmati bersama teman-teman atau keluarga. Segera nikmati Topokki ini dan rasakan sensasi kuliner Korea yang autentik!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Menu(
                imgUrl = "https://i.pinimg.com/564x/cb/ab/36/cbab3668111326924649e6214dae92e1.jpg",
                name = "Pempek",
                price = 25000.00,
                shortDesc = "Rasakan kelezatan Pempek, hidangan khas Palembang yang menggugah selera! Terbuat dari campuran ikan segar dan tepung sagu yang dibentuk dengan teliti, setiap gigitan mempersembahkan cita rasa gurih yang khas. Disajikan dengan saus cuko yang pedas dan asam, Pempek adalah paduan sempurna antara rasa dan tekstur yang membuat lidah bergoyang. Cocok dinikmati sebagai camilan atau hidangan utama, Pempek adalah pilihan yang tak tertandingi untuk memuaskan hasrat kuliner Anda. Segera nikmati kelezatan Pempek ini dan buat hari Anda lebih istimewa!",
                location = "Jl. BSD Green Office Park Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                urlLocation = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
        )
    }
}