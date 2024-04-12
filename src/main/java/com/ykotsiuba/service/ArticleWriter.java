package com.ykotsiuba.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.StatisticsItem;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.File;
import java.util.List;

public class ArticleWriter {
    private final ConcurrentParameterMap parameterMap;

    public ArticleWriter(ConcurrentParameterMap parameterMap) {
        this.parameterMap = parameterMap;
    }

    public void write(String parameter) {
        String name = String.format("statistics_by_%s.xml", parameter);
        List<StatisticsItem> statisticsItems = mapStatistics();
        writeXML(statisticsItems, "articles.xml");
    }

    private void writeXML(List<StatisticsItem> items, String fileName) {
        try {
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

            File outFile = new File(fileName);
            xmlMapper.writeValue(outFile, items);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<StatisticsItem> mapStatistics() {
        return parameterMap.getStatistics()
                .entrySet()
                .stream()
                .map(entry -> StatisticsItem.builder()
                        .value(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .toList();
    }
}
