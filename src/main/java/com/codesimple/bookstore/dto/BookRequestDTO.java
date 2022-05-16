package com.codesimple.bookstore.dto;

import java.util.List;

public class BookRequestDTO {

    private Long id;
    private String name;
    private String desc;
    private Integer yearOfPublication;
    private String bookType;

    private List<BookEditionDTO> editions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public List<BookEditionDTO> getEditions() {
        return editions;
    }

    public void setEditions(List<BookEditionDTO> editions) {
        this.editions = editions;
    }
}
