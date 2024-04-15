package com.ykotsiuba.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ykotsiuba.entity.ArticleStatistics;
import com.ykotsiuba.entity.ConcurrentParameterMap;
import com.ykotsiuba.entity.RuntimeParameters;
import com.ykotsiuba.entity.StatisticsItem;
import com.ykotsiuba.service.ArticleWriter;

import java.io.File;
import java.util.List;

public class ArticleWriterImpl implements ArticleWriter {
    private final ConcurrentParameterMap parameterMap;

    private final RuntimeParameters parameters;

    public ArticleWriterImpl(ConcurrentParameterMap parameterMap, RuntimeParameters parameters) {
        this.parameterMap = parameterMap;
        this.parameters = parameters;
    }

    public void write() {
        String name = String.format("statistics_by_%s.xml", parameters.getParameterName());
        List<StatisticsItem> statisticsItems = mapStatistics();
        ArticleStatistics statistics = new ArticleStatistics();
        statistics.setStatistics(statisticsItems);
        writeXML(statistics, name);
    }

    private void writeXML(ArticleStatistics statistics, String fileName) {
        try {
            ObjectMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

            File outFile = new File(fileName);
            xmlMapper.writeValue(outFile, statistics);
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
