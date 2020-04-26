
package com.covid19.match.pepipost.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fileName",
    "fileContent"
})
public class Attachment {

    /**
     * Name of the file to be attached. e.g. abc.pdf or mydoc.xls
     * 
     */
    @JsonProperty("fileName")
    @JsonPropertyDescription("Name of the file to be attached. e.g. abc.pdf or mydoc.xls")
    private String fileName;
    /**
     * base64 encoded value of the attached file. e.g if you want to attach a xyz.pdf file, then first convert the file content in base64 and pass the same in this parameter.
     * 
     */
    @JsonProperty("fileContent")
    @JsonPropertyDescription("base64 encoded value of the attached file. e.g if you want to attach a xyz.pdf file, then first convert the file content in base64 and pass the same in this parameter.")
    private String fileContent;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * Name of the file to be attached. e.g. abc.pdf or mydoc.xls
     * 
     */
    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    /**
     * Name of the file to be attached. e.g. abc.pdf or mydoc.xls
     * 
     */
    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * base64 encoded value of the attached file. e.g if you want to attach a xyz.pdf file, then first convert the file content in base64 and pass the same in this parameter.
     * 
     */
    @JsonProperty("fileContent")
    public String getFileContent() {
        return fileContent;
    }

    /**
     * base64 encoded value of the attached file. e.g if you want to attach a xyz.pdf file, then first convert the file content in base64 and pass the same in this parameter.
     * 
     */
    @JsonProperty("fileContent")
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
