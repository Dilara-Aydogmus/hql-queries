package com.example.repository;

import com.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Book> searchByTitle(@Param("q") String q);

    @Query("SELECT b FROM Book b WHERE b.author = :author")
    List<Book> findByAuthorExact(@Param("author") String author);

    @Query("SELECT b FROM Book b WHERE LENGTH(b.title) > :len")
    List<Book> findBooksWithLongTitles(@Param("len") int len);







}
