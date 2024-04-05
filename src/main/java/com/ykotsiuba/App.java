package com.ykotsiuba;

import com.ykotsiuba.entity.Article;
import com.ykotsiuba.service.ReadArticleServiceImpl;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        ReadArticleServiceImpl service = new ReadArticleServiceImpl();

        List<Article> read = service.read("D:\\1.json");

        System.out.println( read);
    }
}
