package ru.otus.icdimporter.service;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.domain.mongo.IcdDocument;

import java.util.*;
import java.util.stream.Collectors;

public class CustomIcdMongoWriter implements ItemWriter<IcdEntry> {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final MongoItemWriter<IcdDocument> icdDocumentMongoItemWriter;

    public CustomIcdMongoWriter(NamedParameterJdbcOperations namedParameterJdbcOperations,
                                MongoItemWriter<IcdDocument> icdDocumentMongoItemWriter) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
        this.icdDocumentMongoItemWriter = icdDocumentMongoItemWriter;
    }

    @Override
    public void write(List<? extends IcdEntry> items) throws Exception {
        Map<Long, String> idParentRecCodesMap = fillIdParentRecCodesMap(items);
        List<IcdDocument> icdDocuments = items.stream()
                .map(icdEntry -> convertToDocument(icdEntry, idParentRecCodesMap))
                .collect(Collectors.toList());
        icdDocumentMongoItemWriter.write(icdDocuments);
    }

    private Map<Long, String> fillIdParentRecCodesMap(List<? extends IcdEntry> items) {
        HashMap<Long, String> tmpMap = new HashMap<>(items.size(), 1.0f);
        namedParameterJdbcOperations.query("SELECT id, rec_code from icd where id in (:parentIds)",
                Collections.singletonMap("parentIds", items.stream()
                        .map(IcdEntry::getIdParent)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())), (rs, rowNum) -> tmpMap.put(rs.getLong("id"), rs.getString("rec_code")));
        return tmpMap;
    }

    private IcdDocument convertToDocument(IcdEntry icdEntry, Map<Long, String> idParentRecCodesMap) {
        return IcdDocument.builder()
                .mbkName(icdEntry.getMbkName())
                .mkbCode(icdEntry.getMkbCode())
                .recCode(icdEntry.getRecCode())
                .parentIcdRecCode(Objects.nonNull(icdEntry.getIdParent()) ?
                        idParentRecCodesMap.get(icdEntry.getIdParent()) : null)
                .actual(Objects.nonNull(icdEntry.getActual()) && icdEntry.getActual() == 1)
                .build();
    }
}
