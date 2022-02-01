package com.example.demo.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*


    @Entity
    data class Book (
        var noOfPages:Int=100,
        var isBnNo:Boolean=true,
        var addedAt: LocalDateTime = LocalDateTime.now(),
        var bookName: String="wings of fire",

//        @JsonIgnore
        @ManyToMany(fetch = FetchType.EAGER,cascade = (arrayOf(CascadeType.PERSIST,CascadeType.MERGE)))
        var category: MutableCollection<Category> = arrayListOf(),
        @Lob
        @Column(name = "pdfcolumn", columnDefinition = "BLOB")
        var pdf: ByteArray="ayz".toByteArray(),
        @ManyToOne(fetch = FetchType.EAGER,cascade = arrayOf(CascadeType.PERSIST))
        var author: Author=Author("seeta",1),
//        var slug: String = bookName.toSlug(),

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var bookId: Long? = null):Serializable


    @Entity
    class Author(
        var authorName: String="chetan bhagat",
//        var description: String? = null,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var authorId: Long? = null) :Serializable


    @Entity
    class Category(
        @Id var id: Int?=1,
        var genre:String="horror",
//        @JsonIgnore
//        @ManyToMany(cascade = (arrayOf(CascadeType.PERSIST,CascadeType.MERGE)))
//        var jonarbooks: MutableCollection<Book> = arrayListOf()
    ) :Serializable