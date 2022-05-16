package com.codesimple.bookstore.repo;

import com.codesimple.bookstore.entity.BookEdition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookEditionRepository extends CrudRepository<BookEdition, Long> {
}
