package com.adel.documentviewer.repository;

import com.adel.documentviewer.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    Optional<Document> findByNumber(String number);
}
