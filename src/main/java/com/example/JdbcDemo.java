package com.example;

import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        try {
            // H2 veritabanına bağlan
            Connection conn = DriverManager.getConnection(
                    "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "");

            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE books(id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), author VARCHAR(255))");

            PreparedStatement ps = conn.prepareStatement("INSERT INTO books(title, author) VALUES (?, ?)");
            ps.setString(1, "Clean Code");
            ps.setString(2, "Robert C. Martin");
            ps.executeUpdate();

            ps.setString(1, "Effective Java");
            ps.setString(2, "Joshua Bloch");
            ps.executeUpdate();

            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " +
                        rs.getString("title") + " by " +
                        rs.getString("author"));
            }

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
