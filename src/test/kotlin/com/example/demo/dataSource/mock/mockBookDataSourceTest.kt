package com.example.demo.dataSource.mock

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class mockBookDataSourceTest (val bookrepo:BookRepository,val authorRepository: AuthorRepository ,val categoryRepository: CategoryRepository){

    @BeforeEach
    fun setUp() {
    }

    val mockBookDataSource=mockBookDataSource(bookrepo,authorRepository,categoryRepository)

    @Test
    fun `shoud return all books`(){

          val books=mockBookDataSource.getBooks()
        Assertions.assertThat(books.size).isGreaterThanOrEqualTo(3)

    }

    @Test
    fun `shoud return given book`(){

        val books=mockBookDataSource.getBooks()
        Assertions.assertThat(books.size).isGreaterThanOrEqualTo(3)
        Assertions.assertThat(books).anyMatch { it.bookName == "ramleela"}

    }


    @AfterEach
    fun tearDown() {
    }
}