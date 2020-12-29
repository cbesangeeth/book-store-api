package com.codesimple.bookstore.service;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.entity.Author;
import com.codesimple.bookstore.repo.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public APIResponse getAuthors(Pageable pageable) {
        APIResponse apiResponse = new APIResponse();

        // make db call to get authors
        Page<Author> authorPage = authorRepository.findAll(pageable);

        apiResponse.setData(authorPage);
        return apiResponse;
    }
}
