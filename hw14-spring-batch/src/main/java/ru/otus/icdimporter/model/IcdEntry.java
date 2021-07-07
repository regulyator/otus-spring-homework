package ru.otus.icdimporter.model;

import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="entry")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@Builder
public class IcdEntry {
    @XmlElement(name="ID")
    private Long id;
    @XmlElement(name="REC_CODE")
    private String recCode;
    @XmlElement(name="MKB_NAME")
    private String mbkName;
    @XmlElement(name="ACTUAL")
    private Integer actual;
    @XmlElement(name="ID_PARENT")
    private Long idParent;
    @XmlElement(name="MKB_CODE")
    private String mkbCode;
}
