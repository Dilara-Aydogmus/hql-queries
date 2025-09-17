package com.example.repository;

import com.example.data.BookStore;
import com.example.model.Book;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class BookRepository implements BookStore {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTableIfNotExists();
    }

    private final RowMapper<Book> bookMapper = new RowMapper<Book>() {
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            Long id = rs.getLong("id");
            String title = rs.getString("title");
            String author = rs.getString("author");
            return new Book(id, title, author);
        }
    };

    private void createTableIfNotExists() {
        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS books (" +
                        "id IDENTITY PRIMARY KEY, " +
                        "title VARCHAR(255) NOT NULL, " +
                        "author VARCHAR(255) NOT NULL)"
        );
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            // INSERT ve otomatik oluşturulan ID'yi KeyHolder ile alıyoruz
            final KeyHolder keyHolder = new GeneratedKeyHolder();

            PreparedStatementCreator psc = new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) {
                    try {
                        PreparedStatement ps = connection.prepareStatement(
                                "INSERT INTO books(title, author) VALUES (?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                        ps.setString(1, book.getTitle());
                        ps.setString(2, book.getAuthor());
                        return ps;
                    } catch (SQLException e) {
                        throw new RuntimeException("PreparedStatement oluşturulurken hata oluştu", e);
                    }
                }
            };

            // JdbcTemplate.update(PreparedStatementCreator, KeyHolder)
            jdbcTemplate.update(psc, keyHolder);

            Number generated = keyHolder.getKey();
            if (generated != null) {
                book.setId(generated.longValue());
            }
            return book;
        } else {
            // UPDATE
            jdbcTemplate.update(
                    "UPDATE books SET title = ?, author = ? WHERE id = ?",
                    book.getTitle(), book.getAuthor(), book.getId()
            );
            return book;
        }
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM books ORDER BY id", bookMapper);
    }

    @Override
    public Book findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT * FROM books WHERE id = ?",
                    bookMapper, id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }
}
