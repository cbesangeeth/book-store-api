package com.codesimple.bookstore.repo;

import com.codesimple.bookstore.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {

    List<Book> findAllByYearOfPublicationInAndBookType(Set<Integer> yop, String bookType);

}
