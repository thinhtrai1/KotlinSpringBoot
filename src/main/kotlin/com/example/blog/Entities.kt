package com.example.blog

import javax.persistence.*

class Response<T>(val data: T? = null, val message: String? = null)
class ResponseList<T>(val data: T? = null, val message: String? = null, val isLoadMore: Boolean)

class UserResponse(val id: Long, val username: String, val email: String, val firstName: String, val lastName: String, val token: String)

@Entity
@Table(name = "users")
class User(
        val username: String,
        val password: String,
        val email: String,
        val firstname: String,
        val lastname: String,
        val role: String,
        @Id @GeneratedValue var id: Long? = null
)

@Entity
@Table(name = "people")
class People(
        @Id @GeneratedValue var id: Long? = null,
        val username: String,
        val email: String,
        val firstname: String,
        val lastname: String,
        val avatar: String,
        val country: String,
        val phone: String,
        val facebook: String?
)

@Entity
@Table(name = "product")
class Product(
        val name: String,
        val price: Long,
        val image: String,
        val thumbnail: String,
        val rate: Float,
        val description: String,
        val shopId: Int,
        val shopName: String,
        val addedAt: Long = System.currentTimeMillis(),
        @Id @GeneratedValue var id: Long? = null
)

class Home(
        val hot: Iterable<Product>,
        val new: Iterable<Product>,
        val forYou: Iterable<Product>,
)