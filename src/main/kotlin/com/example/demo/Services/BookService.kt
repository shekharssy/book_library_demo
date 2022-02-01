package com.example.demo.Services

import com.example.demo.dataSource.bookDataSource
import com.example.demo.model.Author
import com.example.demo.model.Book
import com.example.demo.model.Category
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*


@Service
class BookService(private val bookDataSource: bookDataSource){
    fun serv_getbooks():Collection<Book> = bookDataSource.getBooks()
    fun serv_getbook(bookId: Long):Book = bookDataSource.getBook(bookId)
    fun serv_addbook(book:Book):Book = bookDataSource.createBook(book)
    fun serv_deletebook(bookId: Long):String = bookDataSource.deleteBook(bookId)
    fun serv_getauthors(): Collection<Author> =bookDataSource.getAuthors()
    fun serv_getbookbyauthor(genre: String, aname:String): Collection<Book> = bookDataSource.getbookbyauthor(genre,aname)
    fun serv_getbooksbypagination(startpage: Int, pagesize: Int): Page<Book> =bookDataSource.getBookspagable(startpage,pagesize)
    fun serv_getcategories(): Collection<Category> = bookDataSource.getcategory()
    fun serv_getauthorsbyid(authorid: Long): Author =bookDataSource.getauthorbyid(authorid)
}