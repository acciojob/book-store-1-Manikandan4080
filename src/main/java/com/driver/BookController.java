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
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        Book book = getBook(id);
        if(book == null)throw new RuntimeException("Book Not Found");

        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }
    private Book getBook(int id){
        for (Book book : bookList){
            if(book.getId() == id) return book;
        }
        return null;
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity<String> deleteBookById(@PathVariable int id){
        Book book = getBook(id);
        if(book == null) throw new RuntimeException("Book Not Found");
        bookList.remove(book);
        return new ResponseEntity<>("Book Removed Successfully!!", HttpStatus.FOUND);
    }

    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks(){
        return new ResponseEntity<>(getBookList(), HttpStatus.FOUND);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity<String> deleteAllBooks(){
        bookList.clear();
        return new ResponseEntity<>("All Books Cleared", HttpStatus.FOUND);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/books/get-books-by-author")
    public ResponseEntity<Book> getBooksByAuthor(@RequestParam String authorName){
        Book book = getBookByAuthorName(authorName);
        if(book == null) throw new RuntimeException("Book Not Found");
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }
    private Book getBookByAuthorName(String name){
        for(Book book : bookList){
            if(book.getAuthor().equals(name))
                return book;
        }
        return null;
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity<Book> getBooksByGenre(@RequestParam String genre){
        Book book = getByGenre(genre);
        if(book == null) throw new RuntimeException("Book Not Found");
        return new ResponseEntity<>(book, HttpStatus.FOUND);
    }
    private Book getByGenre(String genre){
        for(Book book : bookList){
            if(book.getGenre().equals(genre))
                return book;
        }
        return null;
    }
}
