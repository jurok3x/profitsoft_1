package com.ykotsiuba;

import com.ykotsiuba.entity.Article;
import com.ykotsiuba.entity.ArticleStatistics;
import com.ykotsiuba.entity.StatisticsItem;
import com.ykotsiuba.service.ArticleComponentsFactory;
import com.ykotsiuba.service.Executor;
import com.ykotsiuba.service.impl.DefaultArticleComponentsFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.ykotsiuba.configuration.TestFieldsReader.read;
import static com.ykotsiuba.configuration.TestFilesGenerator.clean;
import static com.ykotsiuba.configuration.TestFilesGenerator.writeJson;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private static final Integer ARTICLE_COUNT = 5;
    private static final Integer YEAR = 2024;

    private Executor service;

    private static final String PATH = "src/test/resources/data";

    private static final String NAME = "year";

    @BeforeEach
    void setUp() {
        writeJson(prepareArticles());
        String[] args = {PATH, NAME};
        ArticleComponentsFactory factory = DefaultArticleComponentsFactory.getInstance(args);
        service = factory.create(Executor.class);
    }

    @AfterEach
    void tearDown() {
        clean();
        String fileName = String.format("statistics_by_%s.xml", NAME);
        File file = new File(fileName);
        if(file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testApp() {
        service.run();

        String expectedFileName = String.format("statistics_by_%s.xml", NAME);

        ArticleStatistics statistics = read(expectedFileName);

        assertNotNull(statistics);
        assertFalse(statistics.getStatistics().isEmpty());

        StatisticsItem item = statistics.getStatistics().get(0);
        assertEquals(ARTICLE_COUNT, item.getCount());
        assertEquals(YEAR.toString(), item.getValue());
    }

    private Article[] prepareArticles() {
        Article[] articles = new Article[ARTICLE_COUNT];
        for (int i = 0; i < ARTICLE_COUNT; i++) {
            articles[i] =  Article.builder()
                    .title("Title")
                    .author("John Doe")
                    .year(YEAR)
                    .journal("Applied Physics")
                    .field("Physics")
                    .build();
        }
        return articles;
    }
}
