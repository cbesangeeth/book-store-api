package com.codesimple.bookstore.contoller;

import com.codesimple.bookstore.common.APIResponse;
import com.codesimple.bookstore.dto.RequestMeta;
import com.codesimple.bookstore.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    public AuthorService authorService;

    @Autowired
    private RequestMeta requestMeta;

    @GetMapping(value = "/authors")
    private APIResponse getAuthors(@SortDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){

        System.out.println(requestMeta.getEmailId());
        APIResponse apiResponse = authorService.getAuthors(pageable);

        return apiResponse;
    }

    @GetMapping(value = "/authorsWithNamed")
    private APIResponse getAuthorsWithNamedQuery(Pageable pageable){

        APIResponse apiResponse = authorService.getAuthorsWithNamedQuery(pageable);

        return apiResponse;
    }
}
