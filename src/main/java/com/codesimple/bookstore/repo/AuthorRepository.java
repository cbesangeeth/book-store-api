package com.codesimple.bookstore.repo;

import com.codesimple.bookstore.entity.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository  extends CrudRepository<Author, Long> {

}
