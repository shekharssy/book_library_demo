package com.example.demo.Services

import com.example.demo.dataSource.bookDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BookServiceTest {

    private val bookDataSource:bookDataSource= mockk(relaxed = true)
    private val bookService:BookService= BookService(bookDataSource)

    @Test
    fun `book service layer test for data modal` (){

        val banks=bookService.serv_getbooks()

        verify(exactly = 1) { bookDataSource.getBooks() }

    }

}