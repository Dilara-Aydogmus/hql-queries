package com.example;

import com.example.model.Book;
import com.example.repository.BookDAO;
import com.example.service.BookManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemoRunner implements CommandLineRunner {
    private final BookManagerService manager;

    @Autowired
    private BookDAO bookDAO;

    public DemoRunner(BookManagerService manager) {
        this.manager = manager;
    }

    @Override
    public void run(String... args) {

        if (manager.list().isEmpty()) {
            manager.add("1984", "George Orwell");
            manager.add("Brave New World", "Aldous Huxley");
            manager.add("Clean Code", "Robert C. Martin");
        }
        List<Book> all = manager.list();
        System.out.println("All: " + all);

        Book first = all.get(0);
        manager.update(first.getId(), "Nineteen Eighty-Four", "George Orwell");
        System.out.println("After update: " + manager.find(first.getId()));

        System.out.println("HQL searchByTitle('new'): " + manager.searchByTitle("new"));
        System.out.println("HQL findByAuthorExact('George Orwell'): " + manager.findByAuthorExact("George Orwell"));
        System.out.println("HQL findBooksWithLongTitles(10): " + manager.findBooksWithLongTitles(10));


        if (manager.list().size() > 1) {
            Long idToDelete = manager.list().get(0).getId();
            manager.delete(idToDelete);
        }
        System.out.println("After delete: " + manager.list());

        System.out.println("HQL ORNEGI");
        bookDAO.findAll("George Orwell").stream().forEach(o ->
                System.out.println("KITAP:" + o.toString()));

        Book book = new Book();
        book.setAuthor("Edgar Allan Poe");
        book = bookDAO.insert(book);

        Book book2 = bookDAO.findById(book.getId());
        System.out.println("BOOK2" + book2.toString());

    }


}
