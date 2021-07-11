package com.codesimple.bookstore.contoller;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.dto.BookDTO;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.service.BookService;
import com.codesimple.bookstore.util.BooksPDFExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping("/books:export")
    public APIResponse exportBooks() throws IOException {

        List<Book> list = bookService.getBooks(null, null);

        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        final HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        BooksPDFExporter exporter = new BooksPDFExporter(list);
        exporter.export(response);
        return null;
    }

}
