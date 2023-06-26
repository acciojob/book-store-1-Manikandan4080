package com.driver;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer bookId) {
        Book book = findBook(bookId);
        if(book == null) {
            throw new RuntimeException("Book not found!");
        } else {
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }
    }

    private Book findBook(Integer bookId) {
        for(Book book : bookList) {
            if(book.getId() == bookId) {
                return book;
            }
        }
        return null;
    }
    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable Integer bookId) {
        if(deleteBook(bookId)) {
            return new ResponseEntity<>("Book removed Successfully", HttpStatus.FOUND);
        } else {
            throw new RuntimeException("Book not found!");
        }
    }

    private Boolean deleteBook(Integer bookId) {
        int idx = -1;
        for(int i = 0; i < bookList.size(); i++) {
            if(bookList.get(i).getId() == bookId) {
                idx = i;
            }
        }
        if(idx != -1) {
            bookList.remove(idx);
            return true;
        }
        return false;
    }
    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> allBooks = getBookList();
        return new ResponseEntity<>(allBooks, HttpStatus.FOUND);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks() {
        bookList.clear();
        return new ResponseEntity<>("All books deleted Successfully", HttpStatus.CREATED);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity<Book> getBooksByAuthor(@RequestParam String authorName) {
        Book book = getBooksByAuthorName(authorName);
        if(book == null) {
            throw new RuntimeException("Book not found!");
        } else {
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }
    }

    private Book getBooksByAuthorName(String authorName) {
        for(Book book : bookList) {
            if(book.getAuthor().equals(authorName)) {
                return book;
            }
        }
        return null;
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-author")
    public ResponseEntity<Book> getBooksByGenre(@RequestParam String genreName) {
        Book book = getBooksByGenreName(genreName);
        if(book == null) {
            throw new RuntimeException("Book not found!");
        } else {
            return new ResponseEntity<>(book, HttpStatus.FOUND);
        }
    }

    private Book getBooksByGenreName(String genreName) {
        for(Book book : bookList) {
            if(book.getGenre().equals(genreName)) {
                return book;
            }
        }
        return null;
    }
}