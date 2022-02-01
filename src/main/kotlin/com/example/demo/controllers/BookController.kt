package com.example.demo.controllers

import com.example.demo.Services.BookService
import com.example.demo.dataSource.mock.AuthorRepository
import com.example.demo.dataSource.mock.BookRepository
import com.example.demo.model.Book
import com.example.demo.model.Author
import com.example.demo.model.Category
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable

import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.System.getLogger
import java.util.*
import kotlin.NoSuchElementException


@RestController


@RequestMapping("/api")
class BookController(

    private val bookService: BookService,
    private val articleRepository: AuthorRepository,
    private val bookrepo: BookRepository
){

    private val logger = LoggerFactory.getLogger(javaClass)


    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> =
            ResponseEntity(e.message,HttpStatus.NOT_FOUND)


    @ExceptionHandler(Exception::class)
    fun handlenotadded(e:Exception):ResponseEntity<String> =
        ResponseEntity(e.message,HttpStatus.NOT_ACCEPTABLE)


    @GetMapping("/books")
    fun getBooks():Collection<Book> = bookService.serv_getbooks()

//    @GetMapping("/books")
//    fun getBooks():String = "ok"

    @GetMapping("/books/pagination/{startpage}/{pagesize}")
    fun getBooksbypagination(@PathVariable startpage:Int,@PathVariable pagesize:Int):Page<Book> = bookService.serv_getbooksbypagination(startpage,pagesize)

    @GetMapping("/books/author/{aname}/{genre}")
    fun getBookbyauthor(@PathVariable genre: String,@PathVariable aname:String):Collection<Book> = bookService.serv_getbookbyauthor(genre,aname)


    @GetMapping("/books/{bookId}")
    fun getBook(@PathVariable bookId:Long):Book = bookService.serv_getbook(bookId)


    @PostMapping("/books")
    @CachePut(value = arrayOf("Book"), key = "#book.bookId")
    fun addbook(@RequestBody book:Book):Book = bookService.serv_addbook(book)

    @CacheEvict(value = arrayOf("Book"), allEntries = true)
    @DeleteMapping("/books/{bookid}")
    fun deletebank(@PathVariable bookid: Long):String= bookService.serv_deletebook(bookid)



    //for author
    @GetMapping("/author")
    fun getauthor():Collection<Author> = bookService.serv_getauthors()

    @GetMapping("/author/{authorId}")
    fun getauthorbyid(@PathVariable authorId:Long): Author = bookService.serv_getauthorsbyid(authorId)


    @GetMapping("/category")
    fun getcategory():Collection<Category> = bookService.serv_getcategories()



}