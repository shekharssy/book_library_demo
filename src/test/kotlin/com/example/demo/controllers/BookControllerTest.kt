package com.example.demo.controllers


import com.example.demo.dataSource.mock.AuthorRepository
import com.example.demo.model.Author
import com.example.demo.model.Book
import com.example.demo.model.Category
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*
import java.time.LocalDateTime

@SpringBootTest
@AutoConfigureMockMvc
internal class BookControllerTest @Autowired constructor(
        val mockmvc:MockMvc,
        val Objmpr:ObjectMapper,
        val articleRepository: AuthorRepository
)
{
    val baseurl="/api/banks"
//    @Nested
//    @DisplayName("GET api/banks")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class getBanks{
//        @Test
//        fun `shoul run all urls`(){
//            //fiven
//
//            mockmvc.get(baseurl)
//                    .andDo { print() }
//                    .andExpect { status { isOk() }
//                        content { contentType(MediaType.APPLICATION_JSON) }
//                        jsonPath("$[0].accountNo"){value("1234")}
//                    }
//
//        }
//    }
//
//
//    @Nested
//    @DisplayName("Get /api.banks/accountNo")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class getBank{
//        @Test
//        fun `should return bank with given account no`() {
//            //fiven
//            val accountNo = "1234"
//            mockmvc.get("$baseurl/$accountNo")
//                    .andDo { print() }
//                    .andExpect {
//                        status { isOk() }
//                        content { contentType(MediaType.APPLICATION_JSON) }
//                        jsonPath("$.trust") { value("1.1") }
//                    }
//
//
//        }
//
//        @Test
//        fun `should return not found status` (){
//            val accountNo="does_not_exist"
//
//            mockmvc.get("$baseurl/$accountNo")
//                    .andDo { print() }
//                    .andExpect {
//                        status { isNotFound() }
//                    }
//
//
//        }
//
//
//    }
//
//    @Nested
//    @DisplayName("POST /api/banks")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class PostNewBank{
//            @Test
//             fun `should add new bank` (){
//
//                val newBank=Bank("2234", 8.2,2)
////                var abc=Objmpr.writeValueAsString(newBank)
//
//                 val performPost=mockmvc.post(baseurl){
//                     contentType=MediaType.APPLICATION_JSON
//                    content=Objmpr.writeValueAsString(newBank)
//                 }
//                         performPost
//                         .andDo { print() }
//                         .andExpect { status { isCreated()}
//                         content { contentType(MediaType.APPLICATION_JSON) }
//                         }
//
//             }
//
//    }
//
//
//    @Nested
//    @DisplayName("PATCH /api/banks")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class PatchExistingBank{
//        @Test
//        fun `should update an existing bank` (){
//
////            val newBank=Bank("7163",9.5,8)
//            val updateBank=Bank("1234",1.2,3)
//
//            val performPatch=mockmvc.patch(baseurl){
//                contentType=MediaType.APPLICATION_JSON
//                content=Objmpr.writeValueAsString(updateBank)
//            }
//            performPatch
//                    .andDo { print() }
//                    .andExpect { status { isOk() }
//                    }
//
//        }
//
//    }
//
//    @Nested
//    @DisplayName("Delete /api/banks")
//    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
//    inner class  DeleteExistingBank{
//    //inner body
//
//        @Test
//        fun `should delete given bank` (){
//               //fiven
//
//
//            val delBank=Bank("1234",1.2,3)
//
//            val del=mockmvc.delete(baseurl){
//                contentType= MediaType.APPLICATION_JSON
//                content=Objmpr.writeValueAsString(delBank)
//            }
//                .andDo { print() }
//                .andExpect { status { isOk() } }
//
//
//        }
//
//    }





    //* books controller test starts

    val baseurl1="/api/books"

    @Nested
    @DisplayName("GET /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class getbooks{
        //inner body

        @Test
        fun `should return all books` (){
            //fiven

            mockmvc.get(baseurl1)
                .andDo { print() }
                .andExpect { status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }


        }

        @Test
        fun `should return book with given id` (){
            //fiven

            val bookid=6

            mockmvc.get("$baseurl1/$bookid")
                .andDo { print() }
                .andExpect { status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.bookId") { value(6)
                        jsonPath("$.author.authorName") { value("shekhar")}
                    }
                }


        }

        @Test
        fun `should return book by author` (){
            //fiven

            val bookid=6
            val authorname:String="shekhar"

            mockmvc.get("$baseurl1/author/$bookid/$authorname")
                .andDo { print() }
                .andExpect { status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].bookId") { value(6)
                        jsonPath("$[0].author.authorName") { value("shekhar")}
                    }
                }


        }


    }


    @Nested
    @DisplayName("POST /api/books")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class postbooktodb() {
        //inner body

        @Test
        fun `should add a books to db`() {
            val author= Author("ramkrishna",1)
            val category=Category(1,"horor")
            var bookpdfbyte="book to convert to byte"
            var bookbyte=bookpdfbyte.toByteArray()
            val book= Book(100,true, LocalDateTime.now(),"ramleela", arrayListOf(), bookbyte,author,3)
            articleRepository.save(author)
//            articleRepository.save(book)
            mockmvc.post("/api/article").andDo { print() }
                .andExpect { status { isCreated() } }
        }
    }


}