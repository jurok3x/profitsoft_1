package com.ykotsiuba.service;

import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.utils.FileSystemUtils;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.util.List;
import java.util.concurrent.*;

public class ParallelExecutorService {

    private ExecutorService executorService;

    private ArticleProducer producer;

    private ConcurrentParameterMap parameterMap;

    private RuntimeParameters parameters;

    public ParallelExecutorService(RuntimeParameters parameters) {
        this.parameters = parameters;
        this.parameterMap = new ConcurrentParameterMap();;
        this.producer = new ArticleProducer(parameterMap, parameters);
        this.executorService = Executors.newFixedThreadPool(4);
    }

    public void run() {
        List<String> jsonFiles = FileSystemUtils.getJsonFiles(parameters.getFolderPath());
        CompletableFuture<?>[] futures = jsonFiles.stream()
                .map(file -> CompletableFuture.runAsync(() -> producer.read(file), executorService))
                .toArray(CompletableFuture[]::new);


        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);

        allOf.join();

        try {
            JAXBContext context = JAXBContext.newInstance(ConcurrentParameterMap.class);
            Marshaller mar = context.createMarshaller();
            mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            mar.marshal(parameterMap, new File("F:\\public_message.xml"));
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();
    }
}
