package io.syncscribe.emailservice.datasource.models;

import io.syncscribe.emailservice.datasource.converters.StringMapConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.Map;

@Getter
@Setter
@ToString
@Entity
@Table(name = "emails")
public class EmailModel {
    @Id
    private String id;
    private String recipient;
    @Convert(converter = StringMapConverter.class)
    private Map<String, Object> params;
    private String template;
    private Boolean success;
    private String createdBy;
    private OffsetDateTime createdAt;
}
