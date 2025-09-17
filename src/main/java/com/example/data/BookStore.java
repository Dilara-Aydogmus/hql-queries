package com.example.data;

import com.example.model.Book;
//farklı paketlerde olduklarından Book'u kullanabilmek için import et

import java.util.List;

public interface BookStore {
    Book save(Book book);
    List<Book> findAll();
    Book findById(Long id);
}
