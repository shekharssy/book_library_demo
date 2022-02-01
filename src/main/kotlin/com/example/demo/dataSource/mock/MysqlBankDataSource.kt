package com.example.demo.dataSource.mock

import com.example.demo.dataSource.bookDataSource
import com.example.demo.model.Author
import com.example.demo.model.Book
import com.example.demo.model.Category
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException


@Repository
interface AuthorRepository : JpaRepository<Author, Long>

@Repository
interface BookRepository: JpaRepository<Book, Long>

@Repository
interface CategoryRepository: JpaRepository<Category, Int>


@Repository
class mockBookDataSource(val bookrepo:BookRepository,val authorRepository: AuthorRepository ,val categoryRepository: CategoryRepository):bookDataSource{

    private val logger = LoggerFactory.getLogger(javaClass)
//    val books=bookrepo.findAll();

//    val genre= mapOf( "horror" to 1,"comedy" to 2, "rommance" to 3 ,"thriller" to 4)

    override fun getBooks(): Collection<Book> {
        val books=bookrepo.findAll()
        if (books.size>=0) {
            return books
        }
        else
        throw NoSuchElementException("book repository is empty")
        return emptyList()
    }
    @Transactional
    @Cacheable(cacheNames = arrayOf("bookbyid"), key = "#bookid")
    override fun getBook(bookid: Long): Book {
        logger.info("retrieveed from db",bookid)
        val currbook=bookrepo.findByIdOrNull(bookid)?:
        throw NoSuchElementException("Could not find a book with these details")
        return currbook
    }

    @CacheEvict(cacheNames = arrayOf("bookbyid"), key = "#bookid")
    override fun deleteBook(bookid: Long): String {
        try {
            val currbook = bookrepo.deleteById(bookid)
        }
        catch (e:Exception) {
            throw Exception("book with these details does not exist:/n $e")
        }
        return "book with id $bookid deleted sucessfully"
    }

    @Transactional
    @CachePut(cacheNames = arrayOf("bookbyid"), key = "#book.bookId")
    override fun createBook(book: Book): Book {
        logger.info("retrieve from db",book.bookId)
        val author1 = authorRepository.findByIdOrNull(book.author.authorId)?:
        authorRepository.save(book.author)
        for (cat in book.category) {
            val caegory = categoryRepository.findByIdOrNull(cat.id)?:
            categoryRepository.save(cat)
        }

        val book=bookrepo.findByIdOrNull(book.bookId)?:
        bookrepo.save(book)

        val boo= bookrepo.findByIdOrNull(book.bookId)?:
        throw NoSuchElementException("book could not be saved properly pease retry")

        return boo

        }

    override fun getAuthors(): Collection<Author> {
         var author=authorRepository.findAll()
        if (author.size<=0) {
            throw NoSuchElementException("Could not find any author or no author exists")
        }
        return author
    }

    override fun getbookbyauthor(jona: String, aname:String): Collection<Book> {

        var books=bookrepo.findAll().filter { it.author.authorName==aname && it.category.any { it.genre==jona } }

        return books
    }

    override fun getBookspagable(startpage: Int, pagesize: Int): Page<Book> {
        var x: PageRequest = PageRequest.of(startpage,pagesize, Sort.by("bookName"))
        return bookrepo.findAll(x)
    }

    override fun getcategory(): Collection<Category> {
        return categoryRepository.findAll()
    }

    @Cacheable(value = arrayOf("author"), key="#authorid.toString()")
    override fun getauthorbyid(authorid: Long): Author {
        logger.info("retrieved from db",authorid)
        val author=  authorRepository.findByIdOrNull(authorid)?:
        throw NoSuchElementException("no author exists with the given id")
        return author
    }

}