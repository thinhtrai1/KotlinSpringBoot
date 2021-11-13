package com.example.blog

import toSlug
import java.time.LocalDateTime
import javax.persistence.*

class Response<T>(val data: T? = null, val message: String? = null)
class ResponseList<T>(val data: T? = null, val message: String? = null, val isLoadMore: Boolean)

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
@Table(name = "users")
class User(
        var token: String?,
        var username: String,
        var password: String,
        var firstname: String,
        var lastname: String,
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