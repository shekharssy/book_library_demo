package com.example.demo.dataSource

import com.example.demo.model.Book
import com.example.demo.model.Author
import com.example.demo.model.Category
import org.springframework.data.domain.Page
import java.util.*

interface bookDataSource{
    fun getBooks(): Collection<Book>
    fun getBook(bookid: Long):Book
    fun deleteBook(bookid: Long):String
    fun createBook(book:Book):Book
    fun getAuthors(): Collection<Author>
    fun getbookbyauthor(genre: String, aname:String): Collection<Book>
    fun getBookspagable(startpage: Int, pagesize: Int): Page<Book>
     fun getcategory(): Collection<Category>
     fun getauthorbyid(authorid: Long): Author
}