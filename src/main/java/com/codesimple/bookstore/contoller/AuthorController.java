package com.codesimple.bookstore.contoller;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    public AuthorService authorService;

    @GetMapping(value = "/authors")
    private APIResponse getAuthors(Pageable pageable){

        APIResponse apiResponse = authorService.getAuthors(pageable);

        return apiResponse;
    }
}
