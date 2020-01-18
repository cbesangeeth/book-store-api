package com.codesimple.bookstore.contoller;

import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @RequestMapping(value = "/books/{id}")
    public Book getBookById(@PathVariable("id") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @RequestMapping(value = "/books", method = RequestMethod.PUT)
    public Book updateBook(@RequestBody Book incomingBook) {
        return bookService.updateBook(incomingBook);
    }

    @RequestMapping(value = "/books/{bookId}", method = RequestMethod.DELETE)
    public String deleteBookById(@PathVariable Integer bookId) {
        return bookService.deleteById(bookId);
    }

    @GetMapping("/raw/books")
    public List<Book> getBooksByRawQuery(@RequestParam(value = "yop") Set<Integer> yop){
        return bookService.getBooksByRawQuery(yop);
    }
}
