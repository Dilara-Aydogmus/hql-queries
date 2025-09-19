package com.example.service;

import com.example.model.Book;
import com.example.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookManagerService {
    private final BookRepository repo;

    public BookManagerService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> list() {
        return repo.findAll();
    }

    public Book find(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Book add(String title, String author) {
        Book book = new Book(title, author);
        return repo.save(book);
    }

    public Book update(Long id, String title, String author) {
        Book book = repo.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            repo.save(book);
            return book;
        }
        return null;
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Book> searchByTitle(String q) {
        return repo.searchByTitle(q);
    }

    public List<Book> findByAuthorExact(String author) {
        return repo.findByAuthorExact(author);
    }

    public List<Book> findBooksWithLongTitles(int len) {
        return repo.findBooksWithLongTitles(len);
    }
}
