package com.example.blog

import toSlug
import java.time.LocalDateTime
import javax.persistence.*

//@Entity
//class Article(
//        var title: String,
//        var headline: String,
//        var content: String,
//        @ManyToOne var author: User,
//        var slug: String = title.toSlug(),
//        var addedAt: LocalDateTime = LocalDateTime.now(),
//        @Id @GeneratedValue var id: Long? = null
//)

@Entity
class User(
        var login: String,
        var firstname: String,
        var lastname: String,
        var description: String? = null,
        @Id @GeneratedValue var id: Long? = null
)

@Entity
@Table(name = "product")
class Product(
        val name: String,
        val price: Long,
        val thumbnail: String,
        val rate: Float,
        val shopId: Int,
        val shopName: String,
        var addedAt: LocalDateTime = LocalDateTime.now(),
        @Id @GeneratedValue var id: Long? = null
)

class Home(
        val hot: Iterable<Product>,
        val new: Iterable<Product>,
        val forYou: Iterable<Product>,
)