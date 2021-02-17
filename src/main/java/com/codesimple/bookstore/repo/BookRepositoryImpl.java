package com.codesimple.bookstore.repo;

import com.codesimple.bookstore.dto.BookQueryDslDTO;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.entity.QBook;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepositoryCustom{

    @PersistenceContext
    EntityManager em;

    public static QBook qBook = QBook.book;

    @Override
    public List<Book> getAllBooksByQuerDsl(Integer year) {

        // query dsl
        JPAQuery<Book> jpaQuery = new JPAQuery<>(em);

        // select id, bookType from book where year_of_publication = year;

        // Method 1 : using tuple
       /* List<Tuple> tuples = jpaQuery
                .select(qBook.bookType,qBook.id)
                .from(qBook)
                .where(qBook.yearOfPublication.eq(year))
                .fetch();

        List<Book> books = new ArrayList<>();

        for(Tuple eachTuple: tuples){
            Book book = new Book();
            book.setId(eachTuple.get(qBook.id));
            book.setBookType(eachTuple.get(qBook.bookType));

            books.add(book);
        }*/

        //Method 2: Using Projection

        QBean<Book> bookQBean = Projections.bean(Book.class,
                qBook.id,
                qBook.bookType
        );

        List<Book> books =  jpaQuery
                .from(qBook)
                .where(qBook.yearOfPublication.eq(year))
                .select(bookQBean)
                .fetch();

        //return
        return books;
    }

    @Override
    public List<BookQueryDslDTO> getAllBooksByQuerDslDto(Integer year) {

        // query dsl
        JPAQuery<BookQueryDslDTO> jpaQuery = new JPAQuery<>(em);

        QBean<BookQueryDslDTO> dslDTOQBean = Projections.bean(BookQueryDslDTO.class,
                qBook.id,
                qBook.bookType.as("type")
        );

        List<BookQueryDslDTO> books = jpaQuery
                .select(dslDTOQBean)
                .from(qBook)
                .where(qBook.yearOfPublication.eq(year))
                .fetch();

        return books;
    }

}
