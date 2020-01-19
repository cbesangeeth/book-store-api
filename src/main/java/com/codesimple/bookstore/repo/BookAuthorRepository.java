package com.codesimple.bookstore.repo;

import com.codesimple.bookstore.entity.BookAuthor;
import org.springframework.data.repository.CrudRepository;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long> {

}
