package com.bookStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookStore.entity.MyBookList;

import java.util.List;

@Repository
public interface MyBookRepository extends JpaRepository<MyBookList,Integer>{
    List<MyBookList> findByCurrentUserId (int userId);
    List<MyBookList> findByCurrentUserIdAndNameContainingIgnoreCase (int userId, String keyword);
}
