package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

    @Bean
    fun databaseInitializer(userRepository: UserRepository,
                            productRepository: ProductRepository) = ApplicationRunner {

//        val smaldini = userRepository.save(User("smaldini", "Stéphane", "Maldini"))
        productRepository.save(Product(
                name = "VinFast President 2021 - Động cơ V8 - Khi người Việt vươn tầm xe sang",
                price = 4600000000,
                thumbnail = "https://ducthinhtrai.000webhostapp.com/images/VinFastPresident2021.jpg",
                rate = 4.5f,
                shopId = 1,
                shopName = "President Đà Nẵng"))
        productRepository.save(Product(
                name = "VinFast Lux SA2.0 - MẠNH MẼ & NĂNG ĐỘNG",
                price = 1835693000,
                thumbnail = "https://ducthinhtrai.000webhostapp.com/images/VinFast-Lux-SA2.0.png",
                rate = 5f,
                shopId = 2,
                shopName = "WorldCar"))
    }
}