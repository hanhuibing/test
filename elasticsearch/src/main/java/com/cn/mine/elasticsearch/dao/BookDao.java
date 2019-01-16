package com.cn.mine.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.cn.mine.elasticsearch.entity.Book;

public interface BookDao extends ElasticsearchRepository<Book,String>{

}
