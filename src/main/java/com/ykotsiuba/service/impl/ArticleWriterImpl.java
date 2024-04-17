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
import java.util.TreeSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The ArticleWriterImpl class implements the ArticleWriter interface
 * and provides functionality to write article statistics to an XML file.
 */
public class ArticleWriterImpl implements ArticleWriter {
    private final ConcurrentParameterMap parameterMap;

    private final RuntimeParameters parameters;

    public ArticleWriterImpl(ConcurrentParameterMap parameterMap, RuntimeParameters parameters) {
        this.parameterMap = parameterMap;
        this.parameters = parameters;
    }

    /**
     * Writes article statistics to an XML file.
     * The XML file name is generated based on the parameter name from runtime parameters.
     * Statistics are obtained from the parameter map and mapped to StatisticsItem objects,
     * which are then written to the XML file using Jackson's XML serialization.
     */
    public void write() {
        String name = String.format("statistics_by_%s.xml", parameters.getParameterName());
        List<StatisticsItem> statisticsItems = mapStatistics();
        ArticleStatistics statistics = new ArticleStatistics();
        statistics.setStatistics(statisticsItems);
        writeXML(statistics, name);
    }

    /**
     * Writes the given ArticleStatistics object to an XML file with the specified file name.
     *
     * @param statistics The article statistics to write.
     * @param fileName   The name of the XML file.
     */
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

    /**
     * Maps article statistics from the parameter map to StatisticsItem objects.
     *
     * @return A list of StatisticsItem objects representing article statistics.
     */
    private List<StatisticsItem> mapStatistics() {
        return parameterMap.getStatistics()
                .entrySet()
                .stream()
                .map(entry -> StatisticsItem.builder()
                        .value(entry.getKey())
                        .count(entry.getValue())
                        .build())
                .sorted()
                .toList();
    }
}
