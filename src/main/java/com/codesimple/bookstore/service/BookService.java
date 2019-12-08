package com.codesimple.bookstore.service;

import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.repo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Get
    public List<Book> getBooks(Set<Integer> yop, String bookType) {

        List<Book> bookList = new ArrayList<>();

        if(yop == null){
            bookRepository.findAll()
                    .forEach(book -> bookList.add(book));
        } else {
             return bookRepository.findAllByYearOfPublicationInAndBookType(yop, bookType);
        }

        return bookList;
    }

    // Create
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Single resource
    public Book getBookById(Integer bookId) {
        return bookRepository.findOne(bookId);
    }

    // Update
    public Book updateBook(Book incomingBook) {
        return bookRepository.save(incomingBook);
    }

    // Delete
    public String deleteById(Integer bookId) {
        bookRepository.delete(bookId);

        return "Deleted Successfully";
    }
}
