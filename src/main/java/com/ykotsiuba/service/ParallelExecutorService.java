package com.ykotsiuba.service;

import com.ykotsiuba.entity.Article;
import com.ykotsiuba.entity.ArticleQueue;
import com.ykotsiuba.entity.ArticleStatistics;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.utils.FileSystemUtils;
import com.ykotsiuba.utils.ReflectionUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ParallelExecutorService {

    private ExecutorService executorService;

    private ArticleProducer producer;

    private ConcurrentParameterMap parameters;

    public ParallelExecutorService() {
        this.parameters = new ConcurrentParameterMap();;
        this.producer = new ArticleProducer(parameters);
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void run() {
        List<String> jsonFiles = FileSystemUtils.getJsonFiles("D:\\");
        CompletableFuture<?>[] futures = jsonFiles.stream()
                .map(file -> CompletableFuture.runAsync(() -> producer.read(file), executorService))
                .toArray(CompletableFuture[]::new);


        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        allOf.join();

        try {
            JAXBContext context = JAXBContext.newInstance(ConcurrentParameterMap.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(parameters, new File("D:\\public_message.xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

    }
}
