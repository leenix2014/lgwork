package com.lagou.blog.controller;

import com.lagou.blog.pojo.Article;
import com.lagou.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @RequestMapping("/")
    public String index(Integer page, Map<String, Object> model){
        if (page == null){
            page = 0;
        }
        PageRequest req = PageRequest.of(page, 2);
        Page<Article> pageContent = articleRepository.findAll(req);
        model.put("articles", pageContent.getContent());
        int prePage = page - 1;
        if(prePage < 0){
            prePage = 0;
        }
        int lastPage = pageContent.getTotalPages() - 1;
        if(lastPage<0){
            lastPage =0;
        }
        int nextPage = page + 1;
        if(nextPage>lastPage){
            nextPage = lastPage;
        }
        model.put("prePage", prePage);
        model.put("nextPage", nextPage);
        model.put("lastPage", lastPage);
        return "client/index";
    }
}
