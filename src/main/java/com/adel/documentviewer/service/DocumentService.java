package com.adel.documentviewer.service;

import com.adel.documentviewer.model.Document;
import com.adel.documentviewer.repository.DocumentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocumentService {
    private final DocumentRepository documentRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    {
        objectMapper.setDateFormat(new SimpleDateFormat("dd.MM.yyyy"));
        objectMapper.registerModule(new JavaTimeModule());
    }

    public List<Document> getAllDocuments(){
        return documentRepository.findAll();
    }

    public void deleteDocument(Long id) {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isEmpty()) {
            log.error("Document with id {} does not exist!",id);
            throw new RuntimeException();
        }
        else {
            documentRepository.deleteById(id);
            log.info("Deleted document {} from database", document.get());
        }
    }

    public void saveDocument(Document document) {
        if (documentRepository.findByNumber(document.getNumber()).isPresent()) {
            log.error("Document with number {} is already exists!",document.getNumber());
            throw new RuntimeException();
        }
        else {
            documentRepository.save(document);
            log.info("Saved document {} to database", document);
        }
    }

    public Map<String, Object> parseDocumentToMap(Document document) {
        return objectMapper.convertValue(document,new TypeReference<>() {});
    }

    public void saveDocumentsToFile(List<Document> selected, File file) {
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter writer = new BufferedWriter(fileWriter)) {

            for (Document document : selected) {
                Map<String, Object> fields = parseDocumentToMap(document);
                for (Map.Entry<String,Object> f : fields.entrySet())
                    writer.write(f.getKey()+": "+f.getValue()+"\n");
                writer.write("\n");
            }
            log.info("Saved documents to {} directory", file.getAbsolutePath());
        } catch (IOException e) {
            log.error("Error saving documents to {} directory",file.getAbsolutePath());
        }
    }

    public void loadDocumentsFromFile(File file) {
        try (FileReader fileReader = new FileReader(file);
             BufferedReader reader = new BufferedReader(fileReader)) {

            Map<String,Object> fields = new HashMap<>();
            String line;

            while((line = reader.readLine())!=null){
                while(!line.isBlank()) {
                    String[] parts = line.split(": ", 2);
                    if (parts.length < 2)
                        throw new IOException();
                    fields.put(parts[0], parts[1]);
                    line = reader.readLine();
                }
                if(!fields.isEmpty()) {
                    Document document = objectMapper.convertValue(fields, Document.class);
                    saveDocument(document);
                    fields.clear();
                }
            }

            log.info("Loaded documents from {} directory", file.getAbsolutePath());
        } catch (Exception e) {
            log.error("Error loading documents from {} directory",file.getAbsolutePath());
        }
    }
}
