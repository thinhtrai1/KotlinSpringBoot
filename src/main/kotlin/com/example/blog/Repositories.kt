package com.example.blog

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.Optional
import kotlin.math.absoluteValue
import kotlin.random.Random

class UserRepository {
    fun save(user: User): User {
        users.add(user.apply {
            id = users.size.toLong() + 1
        })
        return user
    }

    fun findAll() = users
    fun findByUsername(username: String): User? = users.firstOrNull { it.username == username }
    fun existsByUsername(username: String): Boolean = users.any { it.username == username }
    fun existsByEmail(email: String): Boolean = users.any { it.email == email }
}

class ProductRepository {
    fun findAllByNameContainingIgnoreCase(name: String): Iterable<Product> = products.filter {
        it.name.contains(name, ignoreCase = true)
    }

    fun findAll(pageable: Pageable): Page<Product> = products.getPage(pageable)
    fun findById(id: Long): Optional<User> = Optional.ofNullable(users.firstOrNull { it.id == id })
}

class PeopleRepository {
    fun findAll() = peoples
    fun findAll(pageable: Pageable): Page<People> = peoples.getPage(pageable)
}

private fun <T> List<T>.getPage(pageable: Pageable): PageImpl<T> {
    val from = pageable.pageNumber * pageable.pageSize
    val to = from + pageable.pageSize
    return PageImpl(
        if (to > size) drop(from) else subList(from, to),
        pageable,
        size.toLong(),
    )
}

private fun randomId(): Long {
    return Random.nextLong().absoluteValue
}

private val users = mutableListOf<User>()
private val products = listOf(
    Product(
        id = randomId(),
        name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
        price = 4600000000,
        image = "/images/VinFastPresident2021.jpg",
        thumbnail = "/images/thumbnails/VinFastPresident2021.jpg",
        rate = 4.5f,
        description = "Được thiết kế đúng chất \"Chủ tịch\" với ngoại hình bệ vệ, sang trọng.",
        shopId = 1,
        shopName = "President Đà Nẵng"),
    Product(
        id = randomId(),
        name = "Rolls-Royce Phantom - Đồ sộ và cổ điển nhưng trẻ trung và hiện đại",
        price = 46113000000,
        image = "/images/rolls-royce-phantom.jpg",
        thumbnail = "/images/thumbnails/rolls-royce-phantom.jpg",
        rate = 5f,
        description = "Is a full-sized luxury saloon debuting in 2017. It is the eighth and current generation of the Rolls-Royce Phantom, and the second launched by Rolls-Royce under BMW ownership.",
        shopId = 3,
        shopName = "Rolls-Royce Motor Cars"),
    Product(
        id = randomId(),
        name = "BMW XM - SUV plug-in 740 hp & 738 lb-ft",
        price = 5600000000,
        image = "/images/BMW-XM.jpeg",
        thumbnail = "/images/thumbnails/BMW-XM.jpeg",
        rate = 4f,
        description = "Extroverted. Expressionistic. A radically new concept that refuses to back down. Experience the BMW Concept XM: an electrified high-performance luxury vehicle unlike anything you've ever seen.",
        shopId = 4,
        shopName = "Citroën Vietnam"),
    Product(
        id = randomId(),
        name = "Mercedes-Benz VISION AVTR - Sensual Purity and Modern Luxury",
        price = 880000000,
        image = "/images/Mercedes-Benz-VISION-AVTR.jpg",
        thumbnail = "/images/thumbnails/Mercedes-Benz-VISION-AVTR.jpg",
        rate = 5f,
        description = "Partnering with the creators of Avatar, Mercedes-Benz designed the VISION AVTR to imagine a sustainable future for the automobile.",
        shopId = 5,
        shopName = "Mercedes Future"),
    Product(
        id = randomId(),
        name = "VinFast Lux SA2.0 - MẠNH MẼ & NĂNG ĐỘNG",
        price = 1835693000,
        image = "/images/VinFast-Lux-SA2.0.png",
        thumbnail = "/images/thumbnails/VinFast-Lux-SA2.0.png",
        rate = 3f,
        description = "Sở hữu một ngoại thất với tỉ lệ hoàn hảo, chiều dài cơ sở lớn, nắp capô mạnh mẽ hướng ra trước một cách vừa phải và rộng, tạo nên một chiếc xe hội tụ đầy đủ những thành tốt tuyệt vời nhất.",
        shopId = 2,
        shopName = "WorldCar"),
    Product(
        id = randomId(),
        name = "Bugatti Chiron Noire - SPORTIVE - ELEGANCE - LUXURY AND POWER",
        price = 69000000000,
        image = "/images/bugatti-chiron-noire.jpg",
        thumbnail = "/images/thumbnails/bugatti-chiron-noire.jpg",
        rate = 5f,
        description = "The story of BUGATTI’s La Voiture Noire is a renowned myth within the world of automotive. Created by Jean Bugatti, the black Type 57 SC Atlantic went missing at the beginning of the Second World War and was never seen again.",
        shopId = 7,
        shopName = "Automobiles Ettore Bugatti"),
    Product(
        id = randomId(),
        name = "Mercedes-Benz-EQG-G-Class-2021 - An icon embraces the future",
        price = 2200000000,
        image = "/images/Mercedes-Benz-EQG.jpg",
        thumbnail = "/images/thumbnails/Mercedes-Benz-EQG.jpg",
        rate = 5f,
        description = "No one can certainly tell where the future will take us. But one thing is certain: the G-Class will guide the way.",
        shopId = 5,
        shopName = "Mercedes Future"),
    Product(
        id = randomId(),
        name = "Porsche 911 Carrera S - Timeless design, contemporary interpretation",
        price = 7850000000,
        image = "/images/Porsche-911.jpg",
        thumbnail = "/images/thumbnails/Porsche-911.jpg",
        rate = 5f,
        description = "The harmony of tradition and modernity, the iconic flyline and the continuous light strip.",
        shopId = 6,
        shopName = "Porsche Vietnam"),
    Product(
        id = randomId(),
        name = "Porsche 959 - Siêu phẩm của thế kỷ 20",
        price = 4350000000,
        image = "/images/Porsche-959.jpg",
        thumbnail = "/images/thumbnails/Porsche-959.jpg",
        rate = 5f,
        description = "Đây là một chiếc Porsche 959 có một không hai đã được thiết kế đặc biệt cho một hoàng gia Qatar vào năm 1989. Giờ đây đã xuất hiện tại Porsche Vietnam.",
        shopId = 6,
        shopName = "Porsche Vietnam"),
    Product(
        id = randomId(),
        name = "Mercedes-Benz C 300 AMG - Dynamism is an attitude",
        price = 1499000000,
        image = "/images/MERCEDES-BENZ_C300.jpg",
        thumbnail = "/images/thumbnails/MERCEDES-BENZ_C300.jpg",
        rate = 4f,
        description = "As sensually pure as ever. As dynamic and progressive as never before.",
        shopId = 5,
        shopName = "Mercedes Future"),
    Product(
        id = randomId(),
        name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
        price = 4600000000,
        image = "/images/VinFastPresident2021.jpg",
        thumbnail = "/images/thumbnails/VinFastPresident2021.jpg",
        rate = 4.5f,
        description = "Được thiết kế đúng chất \"Chủ tịch\" với ngoại hình bệ vệ, sang trọng.",
        shopId = 1,
        shopName = "President Đà Nẵng"),
    Product(
        id = randomId(),
        name = "Rolls-Royce Phantom - Đồ sộ và cổ điển nhưng trẻ trung và hiện đại",
        price = 46113000000,
        image = "/images/rolls-royce-phantom.jpg",
        thumbnail = "/images/thumbnails/rolls-royce-phantom.jpg",
        rate = 5f,
        description = "Is a full-sized luxury saloon debuting in 2017. It is the eighth and current generation of the Rolls-Royce Phantom, and the second launched by Rolls-Royce under BMW ownership.",
        shopId = 3,
        shopName = "Rolls-Royce Motor Cars"),
    Product(
        id = randomId(),
        name = "BMW XM - SUV plug-in 740 hp & 738 lb-ft",
        price = 5600000000,
        image = "/images/BMW-XM.jpeg",
        thumbnail = "/images/thumbnails/BMW-XM.jpeg",
        rate = 4f,
        description = "Extroverted. Expressionistic. A radically new concept that refuses to back down. Experience the BMW Concept XM: an electrified high-performance luxury vehicle unlike anything you've ever seen.",
        shopId = 4,
        shopName = "Citroën Vietnam"),
    Product(
        id = randomId(),
        name = "Mercedes-Benz VISION AVTR - Sensual Purity and Modern Luxury",
        price = 880000000,
        image = "/images/Mercedes-Benz-VISION-AVTR.jpg",
        thumbnail = "/images/thumbnails/Mercedes-Benz-VISION-AVTR.jpg",
        rate = 5f,
        description = "Partnering with the creators of Avatar, Mercedes-Benz designed the VISION AVTR to imagine a sustainable future for the automobile.",
        shopId = 5,
        shopName = "Mercedes Future"),
    Product(
        id = randomId(),
        name = "VinFast Lux SA2.0 - MẠNH MẼ & NĂNG ĐỘNG",
        price = 1835693000,
        image = "/images/VinFast-Lux-SA2.0.png",
        thumbnail = "/images/thumbnails/VinFast-Lux-SA2.0.png",
        rate = 3f,
        description = "Sở hữu một ngoại thất với tỉ lệ hoàn hảo, chiều dài cơ sở lớn, nắp capô mạnh mẽ hướng ra trước một cách vừa phải và rộng, tạo nên một chiếc xe hội tụ đầy đủ những thành tốt tuyệt vời nhất.",
        shopId = 2,
        shopName = "WorldCar"),
    Product(
        id = randomId(),
        name = "Bugatti Chiron Noire - SPORTIVE - ELEGANCE - LUXURY AND POWER",
        price = 69000000000,
        image = "/images/bugatti-chiron-noire.jpg",
        thumbnail = "/images/thumbnails/bugatti-chiron-noire.jpg",
        rate = 5f,
        description = "The story of BUGATTI’s La Voiture Noire is a renowned myth within the world of automotive. Created by Jean Bugatti, the black Type 57 SC Atlantic went missing at the beginning of the Second World War and was never seen again.",
        shopId = 7,
        shopName = "Automobiles Ettore Bugatti"),
    Product(
        id = randomId(),
        name = "Mercedes-Benz-EQG-G-Class-2021 - An icon embraces the future",
        price = 2200000000,
        image = "/images/Mercedes-Benz-EQG.jpg",
        thumbnail = "/images/thumbnails/Mercedes-Benz-EQG.jpg",
        rate = 5f,
        description = "No one can certainly tell where the future will take us. But one thing is certain: the G-Class will guide the way.",
        shopId = 5,
        shopName = "Mercedes Future"),
    Product(
        id = randomId(),
        name = "Porsche 911 Carrera S - Timeless design, contemporary interpretation",
        price = 7850000000,
        image = "/images/Porsche-911.jpg",
        thumbnail = "/images/thumbnails/Porsche-911.jpg",
        rate = 5f,
        description = "The harmony of tradition and modernity, the iconic flyline and the continuous light strip.",
        shopId = 6,
        shopName = "Porsche Vietnam"),
    Product(
        id = randomId(),
        name = "Porsche 959 - Siêu phẩm của thế kỷ 20",
        price = 4350000000,
        image = "/images/Porsche-959.jpg",
        thumbnail = "/images/thumbnails/Porsche-959.jpg",
        rate = 5f,
        description = "Đây là một chiếc Porsche 959 có một không hai đã được thiết kế đặc biệt cho một hoàng gia Qatar vào năm 1989. Giờ đây đã xuất hiện tại Porsche Vietnam.",
        shopId = 6,
        shopName = "Porsche Vietnam"),
    Product(
        id = randomId(),
        name = "Mercedes-Benz-EQG-G-Class-2021 - Dynamism is an attitude",
        price = 1499000000,
        image = "/images/MERCEDES-BENZ_C300.jpg",
        thumbnail = "/images/thumbnails/MERCEDES-BENZ_C300.jpg",
        rate = 4f,
        description = "As sensually pure as ever. As dynamic and progressive as never before.",
        shopId = 5,
        shopName = "Mercedes Future"),
    Product(
        id = randomId(),
        name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
        price = 4600000000,
        image = "/images/VinFastPresident2021.jpg",
        thumbnail = "/images/thumbnails/VinFastPresident2021.jpg",
        rate = 4.5f,
        description = "Được thiết kế đúng chất \"Chủ tịch\" với ngoại hình bệ vệ, sang trọng.",
        shopId = 1,
        shopName = "President Đà Nẵng"),
    Product(
        id = randomId(),
        name = "Rolls-Royce Phantom - Đồ sộ và cổ điển nhưng trẻ trung và hiện đại",
        price = 46113000000,
        image = "/images/rolls-royce-phantom.jpg",
        thumbnail = "/images/thumbnails/rolls-royce-phantom.jpg",
        rate = 5f,
        description = "Is a full-sized luxury saloon debuting in 2017. It is the eighth and current generation of the Rolls-Royce Phantom, and the second launched by Rolls-Royce under BMW ownership.",
        shopId = 3,
        shopName = "Rolls-Royce Motor Cars"),
    Product(
        id = randomId(),
        name = "BMW XM - SUV plug-in 740 hp & 738 lb-ft",
        price = 5600000000,
        image = "/images/BMW-XM.jpeg",
        thumbnail = "/images/thumbnails/BMW-XM.jpeg",
        rate = 4f,
        description = "Extroverted. Expressionistic. A radically new concept that refuses to back down. Experience the BMW Concept XM: an electrified high-performance luxury vehicle unlike anything you've ever seen.",
        shopId = 4,
        shopName = "Citroën Vietnam"),
)
private val peoples = listOf(
//    People(
//id = randomId(),
//        username = "minhnt3",
//        email = "minhnt3@nal.vn",
//        firstname = "Minh",
//        lastname = "Nt3",
//        avatar = "/images/avatar-1.jpg",
//        country = "Vietnam",
//        phone = "+84384737103",
//        facebook = "facebook.com/ducthinhtrai",
//    ),
//    People(
//id = randomId(),
//        username = "duynn",
//        email = "duynn@nal.vn",
//        firstname = "Ngọc Duy",
//        lastname = "Nguyễn",
//        avatar = "/images/avatar-1.jpg",
//        country = "Vietnam",
//        phone = "+84384737103",
//        facebook = "facebook.com/ducthinhtrai",
//    ),
//    People(
//id = randomId(),
//        username = "lanltn",
//        email = "lanltn@nal.vn",
//        firstname = "Ngọc Lan",
//        lastname = "Lưu Thị",
//        avatar = "/images/avatar-1.jpg",
//        country = "Vietnam",
//        phone = "+84384737103",
//        facebook = "facebook.com/ducthinhtrai",
//    ),
//    People(
//id = randomId(),
//        username = "duck_think",
//        email = "ducthinhtrai@gmail.com",
//        firstname = "Đức Thịnh",
//        lastname = "Nguyễn",
//        avatar = "/images/avatar-1.jpg",
//        country = "Vietnam",
//        phone = "+84384737103",
//        facebook = "facebook.com/ducthinhtrai",
//    ),
    People(
        id = randomId(),
        username = "mySouthAfrica",
        email = "nelson123@gmail.com",
        firstname = "Nelson",
        lastname = "Mandela",
        avatar = "/images/nelson-mandela.jpg",
        country = "South Africa",
        phone = "+16112151566",
        facebook = null,
    ),
    People(
        id = randomId(),
        username = "trinhdamdang",
        email = "trinh89@gmail.com",
        firstname = "Ngọc Trinh",
        lastname = "Trần Thị",
        avatar = "/images/ngoc-trinh.jpg",
        country = "Vietnam",
        phone = "+84451212323",
        facebook = "facebook.com/ngoctrinhfashion89",
    ),
    People(
        id = randomId(),
        username = "MicheleMoniqueReis",
        email = "hanhan@gmail.com",
        firstname = "Gia Hân",
        lastname = "Lý",
        avatar = "/images/ly-gia-han.jpg",
        country = "Hong Kong",
        phone = "+451651561212",
        facebook = null,
    ),
    People(
        id = randomId(),
        username = "joe_vjpprodeptrajkuteno1",
        email = "joe_biden@gmail.com",
        firstname = "Joe",
        lastname = "Biden",
        avatar = "/images/joe-biden.jpg",
        country = "United States",
        phone = "+151261561248",
        facebook = "facebook.com/joebiden",
    ),
    People(
        id = randomId(),
        username = "jisoo_Blackpink",
        email = "jisoo_bp@gmail.com",
        firstname = "Jisoo",
        lastname = "Jisoo",
        avatar = "/images/Jisoo.jpg",
        country = "Korea",
        phone = "+599156566628",
        facebook = "facebook.com/BLACKPINK.JISOO",
    ),
    People(
        id = randomId(),
        username = "yangyang",
        email = "yangyangboy@gmail.com",
        firstname = "Dương",
        lastname = "Dương",
        avatar = "/images/duong-duong.jpg",
        country = "China",
        phone = "+84516521451",
        facebook = "facebook.com/%E6%9D%A8%E6%B4%8B-Yang-Yang-287844248260916",
    ),
    People(
        id = randomId(),
        username = "trump_ducky",
        email = "trump-donald-duck@gmail.com",
        firstname = "Donald",
        lastname = "Trump",
        avatar = "/images/donald-trump.jpg",
        country = "United States",
        phone = "+451515515661",
        facebook = "facebook.com/DonaldTrump",
    ),
    People(
        id = randomId(),
        username = "dilraba_dilmurat",
        email = "rabamurat@gmail.com",
        firstname = "Nhiệt Ba",
        lastname = "Địch Lệ",
        avatar = "/images/dich-le-nhiet-ba.jpg",
        country = "China",
        phone = "+4894624862",
        facebook = "facebook.com/standilireba",
    ),
    People(
        id = randomId(),
        username = "RonaldoFootball4ever",
        email = "crisnaldo@gmail.com",
        firstname = "Cristiano",
        lastname = "Ronaldo",
        avatar = "/images/Cristiano_Ronaldo.jpg",
        country = "Portugal",
        phone = "+459451233245",
        facebook = "facebook.com/Cristiano",
    ),
)