package com.example.repository;

import com.example.model.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class BookDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Book> findAll(String author) {
        return em.createQuery(
                        "SELECT b FROM Book b WHERE b.author = :author", Book.class)
                .setParameter("author", author)
                .getResultList();
    }

    public Book findById(Long id) {
        return em.createQuery(
                        "SELECT b FROM Book b WHERE b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Book insert(Book book) {
        return em.merge(book);
    }
}