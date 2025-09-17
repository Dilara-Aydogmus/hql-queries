package com.example.service;

import com.example.data.BookStore;
import com.example.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookManager {

    private final BookStore store;

    public BookManager(BookStore store) {
        this.store = store;
    }

    // Yeni kitap ekleme servisi
    public Book add(String title, String author) {
        return store.save(new Book(null, title, author));
    }

    // Kaydetme güncelleme servisi
    public Book save(Book book) {
        return store.save(book);
    }

    // listeleme servisi
    public List<Book> list() {
        return store.findAll();
    }

    // ID ile getirme servisi
    public Book get(Long id) {
        return store.findById(id);
    }

    // Sadece başlığı güncelleme servisi
    public boolean updateTitle(Long id, String newTitle) {
        Book b = store.findById(id);
        if (b == null)
            return false;

        b.setTitle(newTitle);
        store.save(b);
        return true;
    }

    // Silme servisi
    public void delete(Long id) {
        store.deleteById(id);
    }
}
