package com.bookStore.service;

import com.bookStore.entity.News;
import com.bookStore.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    public News getNewsById(int id){
        return newsRepository.findById(id).get();
    }

    public List<News> getAllNews(){
        return newsRepository.findAll();
    }

    public List<News> getNewsByTitle(String title){
        return newsRepository.findByTitle(title);
    }

    public News save(News news){
        return newsRepository.save(news);
    }
}
