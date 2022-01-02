package com.codesimple.bookstore.dto;

import java.util.List;

public class BulkBooksRequestDTO {

    private List<BookDTO> books;

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
