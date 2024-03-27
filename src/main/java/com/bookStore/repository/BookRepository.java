package com.bookStore.repository;

import com.bookStore.entity.MyBookList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer>  {
    List<Book> findByName (String name);

    @Query("SELECT b FROM Book b WHERE b.soldQuantity > 0")
    List<Book> findSoldBooks();
}
