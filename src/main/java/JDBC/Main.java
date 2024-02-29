package JDBC;

import com.tms.JPA.User;
import com.tms.books.model.Author;
import com.tms.books.repository.AuthorRepository;
import com.tms.books.repository.BookRepository;
import com.tms.books.repository.PageRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
/*        PageRepository pageRepository = new PageRepository();
        System.out.println(pageRepository.getAllPages());*/

      /*  BookRepository bookRepository = new BookRepository();
        System.out.println(bookRepository.getBookById(2).getPages());
        System.out.println(bookRepository.getBookById(2).getAuthors());*/

        AuthorRepository authorRepository = new AuthorRepository();
        for(Author a : authorRepository.getAllAuthors()){
            System.out.println(a);
            System.out.println(a.getBooks());
        }
    }
}
