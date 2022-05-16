package com.codesimple.bookstore.contoller;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.dto.BookDTO;
import com.codesimple.bookstore.dto.BookRequestDTO;
import com.codesimple.bookstore.dto.BulkBooksRequestDTO;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/books")
    public List<Book> getBooks(
            @RequestParam(value = "yearOfPublications", required = false) Set<Integer> yop,
            @RequestParam(value = "bookType", required = false) String bookType) {
        return bookService.getBooks(yop, bookType);
    }

    @RequestMapping(value = "/books", method = RequestMethod.POST)
    public Book createBook(@RequestBody BookRequestDTO bookDTO) {

        return bookService.createBook(bookDTO);
    }

    @RequestMapping(value = "/books/{id}")
    public BookDTO getBookById(
            @PathVariable("id") Long bookId,
            @RequestParam(value = "authorData", required = false) boolean authorData
    ){
        return bookService.getBookById(bookId, authorData);
    }

    @RequestMapping(value = "/books", method = RequestMethod.PUT)
    public Book updateBook(@RequestBody Book incomingBook) {
        return bookService.updateBook(incomingBook);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    public String deleteBookById(@PathVariable Long bookId) {
        return bookService.deleteById(bookId);
    }

    @GetMapping("/raw/books")
    public APIResponse getBooksByRawQuery(@RequestParam(value = "yop") Set<Integer> yop){
        return bookService.getBooksByRawQuery(yop);
    }

    @GetMapping("/caughtException")
    public APIResponse getCaughtException(@RequestParam(value = "number") Integer yop){
        return bookService.getCaughtException(yop);
    }

    @GetMapping("/queryDsl/books")
    public APIResponse getBooksByQueryDsl(@RequestParam(value ="year") Integer year){
        return bookService.getBooksByQueryDsl(year);
    }

    @PostMapping("/bulkBooks")
    public APIResponse bulkBooks(@RequestBody BulkBooksRequestDTO bulkBooksRequestDTO){
        return bookService.bulkService(bulkBooksRequestDTO);
    }

}
