package io.syncscribe.documentservice.datasource.converters;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.syncscribe.documentservice.contracts.DocumentVisitor;
import jakarta.persistence.AttributeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentVisitorStringListConverter implements AttributeConverter<List<DocumentVisitor>, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<DocumentVisitor> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            log.warn("Failed to convert list of DocumentVisitor to string", e);
        }
        return null;
    }

    @Override
    public List<DocumentVisitor> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        try {
            return objectMapper.readValue(dbData,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, DocumentVisitor.class));
        } catch (JsonProcessingException e) {
            log.warn("Failed to convert string to list of DocumentVisitor", e);
        }
        return null;
    }
}
