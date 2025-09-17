package com.example;

import com.example.model.Book;
import com.example.service.BookManagerService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class App {
    public static void main(String[] args) {
        // Spring context
        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(Config.class)) {

            BookManagerService manager = ctx.getBean(BookManagerService.class);

            System.out.println("=== Başlangıç: mevcut kayıtlar ===");
            List<Book> initial = manager.list();
            System.out.println("Kayıt sayısı: " + initial.size());
            printList(initial);

            System.out.println("\n=== Yeni kitaplar ekleniyor ===");
            Book book1 = manager.add("1984", "George Orwell");
            Book book2 = manager.add("Brave New World", "Aldous Huxley");
            Book book3 = manager.add("Clean Code", "Robert C. Martin");

            System.out.println("Eklendi:");
            System.out.println("  " + book1);
            System.out.println("  " + book2);
            System.out.println("  " + book3);

            System.out.println("\n=== Tüm kayıtlar (servis üzerinden) ===");
            printList(manager.list());

            System.out.println("\n=== ID ile arama (servis üzerinden) ===");
            Book found = manager.get(book1.getId());
            if (found != null) {
                System.out.println("Bulundu: " + found);
            } else {
                System.out.println("ID=" + book1.getId() + " bulunamadı.");
            }

            System.out.println("\n=== Güncelleme örneği (sadece başlık güncelleniyor) ===");
            boolean updated = manager.updateTitle(book1.getId(), "Nineteen Eighty-Four");
            System.out.println("Güncelleme başarılı mı? " + updated);
            System.out.println("Güncel kayıt: " + manager.get(book1.getId()));

            System.out.println("\n=== Silme örneği ===");
            manager.delete(book2.getId());
            System.out.println("ID=" + book2.getId() + " silindi.");

            System.out.println("\n=== Son liste ===");
            printList(manager.list());

        }
    }

    private static void printList(List<Book> list) {
        if (list == null || list.isEmpty()) {
            System.out.println("  (kayıt yok)");
            return;
        }
        for (Book b : list) {
            System.out.println("  " + b);
        }
    }
}
