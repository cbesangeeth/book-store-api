package com.codesimple.bookstore.service;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.common.BadRequestException;
import com.codesimple.bookstore.common.Error;
import com.codesimple.bookstore.data.BookData;
import com.codesimple.bookstore.dto.*;
import com.codesimple.bookstore.entity.Author;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.entity.BookAuthor;
import com.codesimple.bookstore.entity.BookEdition;
import com.codesimple.bookstore.repo.BookAuthorRepository;
import com.codesimple.bookstore.repo.BookEditionRepository;
import com.codesimple.bookstore.repo.BookRepository;
import com.codesimple.bookstore.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookAuthorRepository bookAuthorRepository;

    @Autowired
    private BookValidator bookValidator;
    @Autowired
    private BookEditionRepository bookEditionRepository;

    // Get
    public List<Book> getBooks(Set<Integer> yop, String bookType) {

        List<Book> bookList = new ArrayList<>();

        if (yop == null) {
            bookRepository.findAll()
                    .forEach(book -> bookList.add(book));
        } else {
            return bookRepository.findAllByYearOfPublicationInAndBookType(yop, bookType);
        }

        return bookList;
    }

    // Create
    public Book createBook(BookRequestDTO bookDTO) {

        // validation
        List<Error> errors = bookValidator.validateCreateBookRequest(bookDTO);

        // if not success
        if(errors.size() > 0){
            throw new BadRequestException("bad request", errors);
        }

        // if success
        Book book = new Book();
        book.setName(bookDTO.getName());
        book.setBookType(bookDTO.getBookType());
        book.setDesc(bookDTO.getDesc());
        book.setYearOfPublication(bookDTO.getYearOfPublication());
        bookRepository.save(book);

        // populate edition
        if(!Objects.isNull(bookDTO.getEditions())) {
            bookDTO.getEditions().forEach(bookEditionDTO -> {
                BookEdition bookEdition = new BookEdition();
                bookEdition.setBook(book);
                bookEdition.setIsbn(bookEditionDTO.getIsbn());
                bookEdition.setDescription(bookEditionDTO.getDesc());
                bookEdition.setPageSize(bookEditionDTO.getPageSize());
                bookEdition.setPrice(bookEditionDTO.getPrice());
                bookEditionRepository.save(bookEdition);
            });
        }
        return book;
    }

    // Single resource
    public BookDTO getBookById(Long bookId, boolean authorData) {

        Book book;
        List<BookAuthor> bookAuthors = null;

        book = bookRepository.findOne(bookId);

        if (authorData) {
            bookAuthors = bookAuthorRepository.findAllByBookId(bookId);
        }

        BookDTO bookDTO = new BookDTO();

        // set book details
        bookDTO.setId(book.getId());
        bookDTO.setName(book.getName());
        bookDTO.setDesc(book.getDesc());
        bookDTO.setYearOfPublication(book.getYearOfPublication());
        bookDTO.setBookType(book.getBookType());

        // get author details
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        if (bookAuthors != null) {
            for (BookAuthor bookAuthor : bookAuthors) {
                Author author = bookAuthor.getAuthor();

                AuthorDTO authorDTO = new AuthorDTO();
                authorDTO.setId(author.getId());
                authorDTO.setName(author.getName());
                authorDTO.setGender(author.getGender());

                authorDTOList.add(authorDTO);
            }

            // set author details
            bookDTO.setAuthors(authorDTOList);
        }
        return bookDTO;
    }

    // Update
    public Book updateBook(Book incomingBook) {
        return bookRepository.save(incomingBook);
    }

    // Delete
    public String deleteById(Long bookId) {
        bookRepository.delete(bookId);

        return "Deleted Successfully";
    }

    // raw query - get books
    public APIResponse getBooksByRawQuery(Set<Integer> yop) {

        APIResponse apiResponse = new APIResponse();

        // db call
        List<Book> bookList = bookRepository.findAllByYearOfPublicationIn(yop);

        // set data
        BookData bookData = new BookData();
        bookData.setBooks(bookList);

        // set api response
        apiResponse.setData(bookData);

        return apiResponse;
    }

    public APIResponse getCaughtException(Integer yop) {

        int result = 100/yop;

        APIResponse response = new APIResponse();
        response.setData(result);

        return response;
    }

    public APIResponse getBooksByQueryDsl(Integer year) {
        APIResponse apiResponse = new APIResponse();

        // repo to get the result
        // List<Book> bookList = bookRepository.getAllBooksByQuerDsl(year);

        List<BookQueryDslDTO> bookQueryDslDTOS = bookRepository.getAllBooksByQuerDslDto(year);

        apiResponse.setData(bookQueryDslDTOS);

        //return
        return apiResponse;
    }

    public APIResponse bulkService(BulkBooksRequestDTO bulkBooksRequestDTO) {

        List<Book> booksEntity = new ArrayList<>();
//        bulkBooksRequestDTO.getBooks().forEach(each -> {
//            Book book = new Book();
//
//            book.setName(each.getName());
//            book.setDesc(each.getDesc());
//            book.setBookType(each.getBookType());
//            book.setYearOfPublication(each.getYearOfPublication());
//
//            booksEntity.add(book);
//
//        });

        for (int i = 0; i < 100000; i++) {
            Book book = new Book();

            book.setName("book-"+i);
            book.setDesc("book-"+i);
            book.setBookType("book-"+i);
            book.setYearOfPublication(2022);

            booksEntity.add(book);
        }

        bookRepository.save(booksEntity);

        return new APIResponse();
    }
}
