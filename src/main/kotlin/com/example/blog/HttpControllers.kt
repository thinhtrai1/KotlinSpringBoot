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

@RestController
@RequestMapping("/api")
class HomeController(private val repository: ProductRepository, private val userRepository: UserRepository) {

    @GetMapping("/users")
    fun findAll() = Response(userRepository.findAll())
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
//        return Response(message = "Deprecated")
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
                name = "VinFast VF e34 - Ô tô điện thông minh đầu tiên của Việt Nam",
                price = 1835693000,
                image = "/images/VinFast-VFe34-gray.jpg",
                thumbnail = "/images/thumbnails/VinFast-VFe34-gray.jpg",
                rate = 4.5f,
                description = "Trợ lý ảo thông minh cùng các tiện tích trực tuyến, hỗ trợ an ninh an toàn vượt trội cùng sự thân thiện tuyệt đối với môi trường.",
                shopId = 3,
                shopName = "VinFast Shop"))
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
                name = "VinFast VF e34 - Ô tô điện thông minh đầu tiên của Việt Nam",
                price = 1835693000,
                image = "/images/VinFast-VFe34-gray.jpg",
                thumbnail = "/images/thumbnails/VinFast-VFe34-gray.jpg",
                rate = 4.5f,
                description = "Trợ lý ảo thông minh cùng các tiện tích trực tuyến, hỗ trợ an ninh an toàn vượt trội cùng sự thân thiện tuyệt đối với môi trường.",
                shopId = 3,
                shopName = "VinFast Shop"))
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
                        password,
                        user.firstname,
                        user.lastname,
                        generateAuthentication(username)
                )
        )
    }
}