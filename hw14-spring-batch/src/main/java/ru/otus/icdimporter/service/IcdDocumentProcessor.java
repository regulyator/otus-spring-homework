package ru.otus.icdimporter.service;

import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.domain.mongo.IcdDocument;

public interface IcdDocumentProcessor {
    IcdDocument processIcd(IcdEntry icdEntry);
}
