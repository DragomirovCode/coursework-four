package com.example.service;

import com.example.entity.News;
import com.example.repository.NewsRepository;
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

    public void deleteById(int id){
        newsRepository.deleteById(id);
    }

    public void update(int id, News updateNews){
        updateNews.setId(id);
        newsRepository.save(updateNews);
    }
}
