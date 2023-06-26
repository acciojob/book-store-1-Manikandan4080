package com.driver;

import java.util.ArrayList;
import java.util.Collections;
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
        bookList.add(book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    // getBookById()
    @GetMapping("get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        for(Book book: bookList){
            if(book.getId()==id) return new ResponseEntity<>(book,HttpStatus.ACCEPTED);
        }

        return new ResponseEntity("Not found",HttpStatus.NO_CONTENT);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    // deleteBookById()
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable int id){
        int todelete = -1;
        for(int i=0;i<bookList.size();i++){
            if(bookList.get(i).getId()==id){
                todelete = i;
                break;
            }
        }

        if(todelete<0) return new ResponseEntity("Book not found",HttpStatus.NOT_FOUND);

        return new ResponseEntity<>("SUCCESS",HttpStatus.ACCEPTED);
    }



    // get request /get-all-books
    // getAllBooks()
    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        ArrayList<Book> allBooks = new ArrayList<>(bookList);
        return new ResponseEntity(allBooks,HttpStatus.OK);
    }

    // delete request /delete-all-books
    // deleteAllBooks()
    @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks(){
        bookList.clear();
        return new ResponseEntity<>("SUCCESS",HttpStatus.NO_CONTENT);
    }

    // get request /get-books-by-author
    // pass author name as request param
    // getBooksByAuthor()
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam String name){
        for(Book book: bookList){
            if(book.getName().equals(name)){
                return new ResponseEntity<>(book,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("NOT FOUND",HttpStatus.NOT_FOUND);
    }

    // get request /get-books-by-genre
    // pass genre name as request param
    // getBooksByGenre()
    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam String genre){
        for(Book book: bookList){
            if(book.getGenre().equals(genre)){
                return new ResponseEntity<>(book,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("NOT FOUND",HttpStatus.NOT_FOUND);
    }
}