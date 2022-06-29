package com.example.blog

import generateAuthentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

/**
 * Work on Heroku
 * Open PowerShell in root folder or open Terminal in Android Studio
 * - For first time:
 *      + heroku login
 *      + heroku git:clone -a nguyenducthinh
 *      + cd nguyenducthinh
 * - For deploy changes:
 *      + git add .
 *      + git commit -am "commit-name"
 *      + git push heroku master
 * */

@RestController
@RequestMapping("/api")
class HomeController(private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun findAll() = Response(userRepository.findAll())
}

@RestController
@RequestMapping("/api/peoples")
class PeopleController(private val repository: PeopleRepository) {

    @GetMapping("/insert")
    fun insert(): Response<People> {
        if (repository.count() > 0) {
            return Response(message = "Closed")
        } else {
            repository.saveAll(listOf(
                    People(
                            username = "minhnt3",
                            email = "minhnt3@nal.vn",
                            firstname = "Minh",
                            lastname = "Nt3",
                            avatar = "/images/avatar-1.jpg",
                            country = "Vietnam",
                            phone = "+84384737103",
                            facebook = "facebook.com/ducthinhtrai",
                    ),
                    People(
                            username = "duynn",
                            email = "duynn@nal.vn",
                            firstname = "Ngọc Duy",
                            lastname = "Nguyễn",
                            avatar = "/images/avatar-1.jpg",
                            country = "Vietnam",
                            phone = "+84384737103",
                            facebook = "facebook.com/ducthinhtrai",
                    ),
                    People(
                            username = "lanltn",
                            email = "lanltn@nal.vn",
                            firstname = "Ngọc Lan",
                            lastname = "Lưu Thị",
                            avatar = "/images/avatar-1.jpg",
                            country = "Vietnam",
                            phone = "+84384737103",
                            facebook = "facebook.com/ducthinhtrai",
                    ),
                    People(
                            username = "duck_think",
                            email = "ducthinhtrai@gmail.com",
                            firstname = "Đức Thịnh",
                            lastname = "Nguyễn",
                            avatar = "/images/avatar-1.jpg",
                            country = "Vietnam",
                            phone = "+84384737103",
                            facebook = "facebook.com/ducthinhtrai",
                    ),
//                    People(
//                            username = "mySouthAfrica",
//                            email = "nelson123@gmail.com",
//                            firstname = "Nelson",
//                            lastname = "Mandela",
//                            avatar = "/images/nelson-mandela.jpg",
//                            country = "South Africa",
//                            phone = "+16112151566",
//                            facebook = null,
//                    ),
                    People(
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
                            username = "RonaldoFootball4ever",
                            email = "crisnaldo@gmail.com",
                            firstname = "Cristiano",
                            lastname = "Ronaldo",
                            avatar = "/images/Cristiano_Ronaldo.jpg",
                            country = "Portugal",
                            phone = "+459451233245",
                            facebook = "facebook.com/Cristiano",
                    ),
            ))
            return Response(message = "Success")
        }
    }

    @GetMapping("")
    fun getPeoples() = ResponseList(
            data = repository.findAll(),
            isLoadMore = false
    )

    @GetMapping("/{page}/{size}")
    fun getPeoples(
            @PathVariable page: Int,
            @PathVariable size: Int,
    ) = repository.findAll(PageRequest.of(page, size)).let {
        ResponseList(it.content, isLoadMore = page < it.totalPages - 1)
    }
}

@RestController
@RequestMapping("/api/product")
class ProductController(private val repository: ProductRepository) {

    @GetMapping("/getHome")
    fun getHome() = Response(Home(
            repository.findTop5ByOrderByAddedAtDesc(),
            repository.findTop5ByOrderByAddedAtDesc(),
            repository.findTop10ByOrderByAddedAtDesc(),
    ))

    @GetMapping("/insert")
    fun insert(): Response<Product> {
        if (repository.count() > 0) {
            return Response(message = "Closed")
        }
        repository.deleteAll()
        repository.save(Product(
                name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
                price = 4600000000,
                image = "/images/VinFastPresident2021.jpg",
                thumbnail = "/images/thumbnails/VinFastPresident2021.jpg",
                rate = 4.5f,
                description = "Được thiết kế đúng chất \"Chủ tịch\" với ngoại hình bệ vệ, sang trọng.",
                shopId = 1,
                shopName = "President Đà Nẵng"))
        repository.save(Product(
                name = "Rolls-Royce Phantom - Đồ sộ và cổ điển nhưng trẻ trung và hiện đại",
                price = 46113000000,
                image = "/images/rolls-royce-phantom.jpg",
                thumbnail = "/images/thumbnails/rolls-royce-phantom.jpg",
                rate = 5f,
                description = "Is a full-sized luxury saloon debuting in 2017. It is the eighth and current generation of the Rolls-Royce Phantom, and the second launched by Rolls-Royce under BMW ownership.",
                shopId = 3,
                shopName = "Rolls-Royce Motor Cars"))
        repository.save(Product(
                name = "BMW XM - SUV plug-in 740 hp & 738 lb-ft",
                price = 5600000000,
                image = "/images/BMW-XM.jpeg",
                thumbnail = "/images/thumbnails/BMW-XM.jpeg",
                rate = 4f,
                description = "Extroverted. Expressionistic. A radically new concept that refuses to back down. Experience the BMW Concept XM: an electrified high-performance luxury vehicle unlike anything you've ever seen.",
                shopId = 4,
                shopName = "Citroën Vietnam"))
        repository.save(Product(
                name = "Mercedes-Benz VISION AVTR - Sensual Purity and Modern Luxury",
                price = 880000000,
                image = "/images/Mercedes-Benz-VISION-AVTR.jpg",
                thumbnail = "/images/thumbnails/Mercedes-Benz-VISION-AVTR.jpg",
                rate = 5f,
                description = "Partnering with the creators of Avatar, Mercedes-Benz designed the VISION AVTR to imagine a sustainable future for the automobile.",
                shopId = 5,
                shopName = "Mercedes Future"))
        repository.save(Product(
                name = "VinFast Lux SA2.0 - MẠNH MẼ & NĂNG ĐỘNG",
                price = 1835693000,
                image = "/images/VinFast-Lux-SA2.0.png",
                thumbnail = "/images/thumbnails/VinFast-Lux-SA2.0.png",
                rate = 3f,
                description = "Sở hữu một ngoại thất với tỉ lệ hoàn hảo, chiều dài cơ sở lớn, nắp capô mạnh mẽ hướng ra trước một cách vừa phải và rộng, tạo nên một chiếc xe hội tụ đầy đủ những thành tốt tuyệt vời nhất.",
                shopId = 2,
                shopName = "WorldCar"))
        repository.save(Product(
                name = "Bugatti Chiron Noire - SPORTIVE - ELEGANCE - LUXURY AND POWER",
                price = 69000000000,
                image = "/images/bugatti-chiron-noire.jpg",
                thumbnail = "/images/thumbnails/bugatti-chiron-noire.jpg",
                rate = 5f,
                description = "The story of BUGATTI’s La Voiture Noire is a renowned myth within the world of automotive. Created by Jean Bugatti, the black Type 57 SC Atlantic went missing at the beginning of the Second World War and was never seen again.",
                shopId = 7,
                shopName = "Automobiles Ettore Bugatti"))
        repository.save(Product(
                name = "Mercedes-Benz-EQG-G-Class-2021 - An icon embraces the future",
                price = 2200000000,
                image = "/images/Mercedes-Benz-EQG.jpg",
                thumbnail = "/images/thumbnails/Mercedes-Benz-EQG.jpg",
                rate = 5f,
                description = "No one can certainly tell where the future will take us. But one thing is certain: the G-Class will guide the way.",
                shopId = 5,
                shopName = "Mercedes Future"))
        repository.save(Product(
                name = "Porsche 911 Carrera S - Timeless design, contemporary interpretation",
                price = 7850000000,
                image = "/images/Porsche-911.jpg",
                thumbnail = "/images/thumbnails/Porsche-911.jpg",
                rate = 5f,
                description = "The harmony of tradition and modernity, the iconic flyline and the continuous light strip.",
                shopId = 6,
                shopName = "Porsche Vietnam"))
        repository.save(Product(
                name = "Porsche 959 - Siêu phẩm của thế kỷ 20",
                price = 4350000000,
                image = "/images/Porsche-959.jpg",
                thumbnail = "/images/thumbnails/Porsche-959.jpg",
                rate = 5f,
                description = "Đây là một chiếc Porsche 959 có một không hai đã được thiết kế đặc biệt cho một hoàng gia Qatar vào năm 1989. Giờ đây đã xuất hiện tại Porsche Vietnam.",
                shopId = 6,
                shopName = "Porsche Vietnam"))
        repository.save(Product(
                name = "Mercedes-Benz C 300 AMG - Dynamism is an attitude",
                price = 1499000000,
                image = "/images/MERCEDES-BENZ_C300.jpg",
                thumbnail = "/images/thumbnails/MERCEDES-BENZ_C300.jpg",
                rate = 4f,
                description = "As sensually pure as ever. As dynamic and progressive as never before.",
                shopId = 5,
                shopName = "Mercedes Future"))
        repository.save(Product(
                name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
                price = 4600000000,
                image = "/images/VinFastPresident2021.jpg",
                thumbnail = "/images/thumbnails/VinFastPresident2021.jpg",
                rate = 4.5f,
                description = "Được thiết kế đúng chất \"Chủ tịch\" với ngoại hình bệ vệ, sang trọng.",
                shopId = 1,
                shopName = "President Đà Nẵng"))
        repository.save(Product(
                name = "Rolls-Royce Phantom - Đồ sộ và cổ điển nhưng trẻ trung và hiện đại",
                price = 46113000000,
                image = "/images/rolls-royce-phantom.jpg",
                thumbnail = "/images/thumbnails/rolls-royce-phantom.jpg",
                rate = 5f,
                description = "Is a full-sized luxury saloon debuting in 2017. It is the eighth and current generation of the Rolls-Royce Phantom, and the second launched by Rolls-Royce under BMW ownership.",
                shopId = 3,
                shopName = "Rolls-Royce Motor Cars"))
        repository.save(Product(
                name = "BMW XM - SUV plug-in 740 hp & 738 lb-ft",
                price = 5600000000,
                image = "/images/BMW-XM.jpeg",
                thumbnail = "/images/thumbnails/BMW-XM.jpeg",
                rate = 4f,
                description = "Extroverted. Expressionistic. A radically new concept that refuses to back down. Experience the BMW Concept XM: an electrified high-performance luxury vehicle unlike anything you've ever seen.",
                shopId = 4,
                shopName = "Citroën Vietnam"))
        repository.save(Product(
                name = "Mercedes-Benz VISION AVTR - Sensual Purity and Modern Luxury",
                price = 880000000,
                image = "/images/Mercedes-Benz-VISION-AVTR.jpg",
                thumbnail = "/images/thumbnails/Mercedes-Benz-VISION-AVTR.jpg",
                rate = 5f,
                description = "Partnering with the creators of Avatar, Mercedes-Benz designed the VISION AVTR to imagine a sustainable future for the automobile.",
                shopId = 5,
                shopName = "Mercedes Future"))
        repository.save(Product(
                name = "VinFast Lux SA2.0 - MẠNH MẼ & NĂNG ĐỘNG",
                price = 1835693000,
                image = "/images/VinFast-Lux-SA2.0.png",
                thumbnail = "/images/thumbnails/VinFast-Lux-SA2.0.png",
                rate = 3f,
                description = "Sở hữu một ngoại thất với tỉ lệ hoàn hảo, chiều dài cơ sở lớn, nắp capô mạnh mẽ hướng ra trước một cách vừa phải và rộng, tạo nên một chiếc xe hội tụ đầy đủ những thành tốt tuyệt vời nhất.",
                shopId = 2,
                shopName = "WorldCar"))
        repository.save(Product(
                name = "Bugatti Chiron Noire - SPORTIVE - ELEGANCE - LUXURY AND POWER",
                price = 69000000000,
                image = "/images/bugatti-chiron-noire.jpg",
                thumbnail = "/images/thumbnails/bugatti-chiron-noire.jpg",
                rate = 5f,
                description = "The story of BUGATTI’s La Voiture Noire is a renowned myth within the world of automotive. Created by Jean Bugatti, the black Type 57 SC Atlantic went missing at the beginning of the Second World War and was never seen again.",
                shopId = 7,
                shopName = "Automobiles Ettore Bugatti"))
        repository.save(Product(
                name = "Mercedes-Benz-EQG-G-Class-2021 - An icon embraces the future",
                price = 2200000000,
                image = "/images/Mercedes-Benz-EQG.jpg",
                thumbnail = "/images/thumbnails/Mercedes-Benz-EQG.jpg",
                rate = 5f,
                description = "No one can certainly tell where the future will take us. But one thing is certain: the G-Class will guide the way.",
                shopId = 5,
                shopName = "Mercedes Future"))
        repository.save(Product(
                name = "Porsche 911 Carrera S - Timeless design, contemporary interpretation",
                price = 7850000000,
                image = "/images/Porsche-911.jpg",
                thumbnail = "/images/thumbnails/Porsche-911.jpg",
                rate = 5f,
                description = "The harmony of tradition and modernity, the iconic flyline and the continuous light strip.",
                shopId = 6,
                shopName = "Porsche Vietnam"))
        repository.save(Product(
                name = "Porsche 959 - Siêu phẩm của thế kỷ 20",
                price = 4350000000,
                image = "/images/Porsche-959.jpg",
                thumbnail = "/images/thumbnails/Porsche-959.jpg",
                rate = 5f,
                description = "Đây là một chiếc Porsche 959 có một không hai đã được thiết kế đặc biệt cho một hoàng gia Qatar vào năm 1989. Giờ đây đã xuất hiện tại Porsche Vietnam.",
                shopId = 6,
                shopName = "Porsche Vietnam"))
        repository.save(Product(
                name = "Mercedes-Benz-EQG-G-Class-2021 - Dynamism is an attitude",
                price = 1499000000,
                image = "/images/MERCEDES-BENZ_C300.jpg",
                thumbnail = "/images/thumbnails/MERCEDES-BENZ_C300.jpg",
                rate = 4f,
                description = "As sensually pure as ever. As dynamic and progressive as never before.",
                shopId = 5,
                shopName = "Mercedes Future"))
        repository.save(Product(
                name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
                price = 4600000000,
                image = "/images/VinFastPresident2021.jpg",
                thumbnail = "/images/thumbnails/VinFastPresident2021.jpg",
                rate = 4.5f,
                description = "Được thiết kế đúng chất \"Chủ tịch\" với ngoại hình bệ vệ, sang trọng.",
                shopId = 1,
                shopName = "President Đà Nẵng"))
        repository.save(Product(
                name = "Rolls-Royce Phantom - Đồ sộ và cổ điển nhưng trẻ trung và hiện đại",
                price = 46113000000,
                image = "/images/rolls-royce-phantom.jpg",
                thumbnail = "/images/thumbnails/rolls-royce-phantom.jpg",
                rate = 5f,
                description = "Is a full-sized luxury saloon debuting in 2017. It is the eighth and current generation of the Rolls-Royce Phantom, and the second launched by Rolls-Royce under BMW ownership.",
                shopId = 3,
                shopName = "Rolls-Royce Motor Cars"))
        repository.save(Product(
                name = "BMW XM - SUV plug-in 740 hp & 738 lb-ft",
                price = 5600000000,
                image = "/images/BMW-XM.jpeg",
                thumbnail = "/images/thumbnails/BMW-XM.jpeg",
                rate = 4f,
                description = "Extroverted. Expressionistic. A radically new concept that refuses to back down. Experience the BMW Concept XM: an electrified high-performance luxury vehicle unlike anything you've ever seen.",
                shopId = 4,
                shopName = "Citroën Vietnam"))
        return Response(message = "Success")
    }

    @GetMapping("")
    fun findAll(
            @RequestParam("search") search: String? = null,
            @RequestParam("id") id: Long? = null
    ) = if (search != null) {
        Response(repository.findAllByNameContainingIgnoreCase(search))
    } else if (id != null) {
        repository.findById(id).run {
            if (isPresent) {
                Response(get())
            } else {
                throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
            }
        }
    } else {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "Missing RequestParam")
    }

    @GetMapping("/{page}")
    fun getForYou(@PathVariable page: Int) = repository.findAll(PageRequest.of(page, 10)).run {
        ResponseList(content, isLoadMore = page < totalPages - 1)
    }
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository, private val passwordEncoder: PasswordEncoder) {

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @PostMapping("/register")
    fun register(
            @Param("username") username: String,
            @Param("password") password: String,
            @Param("email") email: String,
            @Param("firstname") firstname: String,
            @Param("lastname") lastname: String
    ): Response<UserResponse> {
        if (repository.existsByUsername(username)) {
            return Response(null, "Username is already taken!")
        }
        if (repository.existsByEmail(email)) {
            return Response(null, "Email is already taken!")
        }
        val user = repository.save(User(username, passwordEncoder.encode(password), email, firstname, lastname, "USER"))
        return Response(
                UserResponse(
                        user.id!!,
                        username,
                        email,
                        firstname,
                        lastname,
                        generateAuthentication(username)
                )
        )
    }

    @PostMapping("/login")
    fun login(@Param("username") username: String, @Param("password") password: String): Response<UserResponse> {
        val auth = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))
        SecurityContextHolder.getContext().authentication = auth
        val user = (auth.principal as CustomUserDetail).user
        return Response(
                UserResponse(
                        user.id!!,
                        username,
                        user.email,
                        user.firstname,
                        user.lastname,
                        generateAuthentication(username)
                )
        )
    }
}