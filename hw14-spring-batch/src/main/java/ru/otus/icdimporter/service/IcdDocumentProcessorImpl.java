package ru.otus.icdimporter.service;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Service;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.domain.mongo.IcdDocument;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class IcdDocumentProcessorImpl implements IcdDocumentProcessor {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;
    private final Map<Long, String> parentRecCodesCash = new HashMap<>(1000);

    @Autowired
    public IcdDocumentProcessorImpl(NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }

    @Override
    public IcdDocument processIcd(@NonNull IcdEntry icdEntry) {
        if (Objects.nonNull(icdEntry.getIdParent())) {
            String parentRecCode = parentRecCodesCash.computeIfAbsent(icdEntry.getIdParent(), this::getParentRecCode);

            return IcdDocument.builder()
                    .mbkName(icdEntry.getMbkName())
                    .mkbCode(icdEntry.getMkbCode())
                    .recCode(icdEntry.getRecCode())
                    .parentIcdRecCode(parentRecCode)
                    .actual(Objects.nonNull(icdEntry.getActual()) && icdEntry.getActual() == 1)
                    .build();
        } else {
            return IcdDocument.builder()
                    .mbkName(icdEntry.getMbkName())
                    .mkbCode(icdEntry.getMkbCode())
                    .recCode(icdEntry.getRecCode())
                    .actual(Objects.nonNull(icdEntry.getActual()) && icdEntry.getActual() == 1)
                    .build();
        }
    }

    private String getParentRecCode(Long idParent) {
        return namedParameterJdbcOperations.queryForObject("SELECT rec_code from icd where id = :id",
                Collections.singletonMap("id", idParent), String.class);
    }
}
