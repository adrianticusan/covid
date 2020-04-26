
package com.covid19.match.pepipost.model;

import java.util.HashMap;
import java.util.List;
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
    "attributes",
    "recipient",
    "recipient_cc",
    "x-apiheader",
    "x-apiheader_cc",
    "recipient_bcc",
    "x-headers"
})
public class Personalization {

    /**
     * A collection of key/value pairs following the pattern "NAME":"John". All are assumed to be strings. These attributes will apply to the html content of the body of your email, sender name and subject parameters. The total collective size of your attributes may not exceed 10,000 bytes per personalization object.
     * 
     * Each attribute can be used in your HTML as [%NAME%] for NAME , [%AGE%] for AGE. Spaces are not allowed within the attribute name, but special character underscore can be used.
     * 
     */
    @JsonProperty("attributes")
    @JsonPropertyDescription("A collection of key/value pairs following the pattern \"NAME\":\"John\". All are assumed to be strings. These attributes will apply to the html content of the body of your email, sender name and subject parameters. The total collective size of your attributes may not exceed 10,000 bytes per personalization object.\n\nEach attribute can be used in your HTML as [%NAME%] for NAME , [%AGE%] for AGE. Spaces are not allowed within the attribute name, but special character underscore can be used.")
    private Attributes attributes;
    /**
     * An array of recipients email address. Each object within this array must always contain the email address of a recipient.
     * (Required)
     * 
     */
    @JsonProperty("recipient")
    @JsonPropertyDescription("An array of recipients email address. Each object within this array must always contain the email address of a recipient.")
    private String recipient;
    /**
     * An array of recipients who will receive a copy of your email i.e. recipients who will be in CC of your emails. Each object within this array must always contain the email address of the recipient.
     * 
     */
    @JsonProperty("recipient_cc")
    @JsonPropertyDescription("An array of recipients who will receive a copy of your email i.e. recipients who will be in CC of your emails. Each object within this array must always contain the email address of the recipient.")
    private List<String> recipientCc = null;
    /**
     * Values that are specific to this personalization that will be carried along with the email and its activity data. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.
     * 
     */
    @JsonProperty("x-apiheader")
    @JsonPropertyDescription("Values that are specific to this personalization that will be carried along with the email and its activity data. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.")
    private String xApiheader;
    /**
     * Values that are specific to this personalization that will be carried along with the email and its activity data. This data is going to be mapped with their respective email address defined in the parameter recipient_cc. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.
     * 
     */
    @JsonProperty("x-apiheader_cc")
    @JsonPropertyDescription("Values that are specific to this personalization that will be carried along with the email and its activity data. This data is going to be mapped with their respective email address defined in the parameter recipient_cc. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.")
    private String xApiheaderCc;
    /**
     * An array of recipients who will receive a copy of your email i.e. recipients who will be in BCC of your emails. Each object within this array must always contain the email address of the recipient.
     * 
     */
    @JsonProperty("recipient_bcc")
    @JsonPropertyDescription("An array of recipients who will receive a copy of your email i.e. recipients who will be in BCC of your emails. Each object within this array must always contain the email address of the recipient.")
    private List<String> recipientBcc = null;
    /**
     * This parameters allows you to add custom headers to the mail.
     * 
     */
    @JsonProperty("x-headers")
    @JsonPropertyDescription("This parameters allows you to add custom headers to the mail.")
    private XHeaders xHeaders;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * A collection of key/value pairs following the pattern "NAME":"John". All are assumed to be strings. These attributes will apply to the html content of the body of your email, sender name and subject parameters. The total collective size of your attributes may not exceed 10,000 bytes per personalization object.
     * 
     * Each attribute can be used in your HTML as [%NAME%] for NAME , [%AGE%] for AGE. Spaces are not allowed within the attribute name, but special character underscore can be used.
     * 
     */
    @JsonProperty("attributes")
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * A collection of key/value pairs following the pattern "NAME":"John". All are assumed to be strings. These attributes will apply to the html content of the body of your email, sender name and subject parameters. The total collective size of your attributes may not exceed 10,000 bytes per personalization object.
     * 
     * Each attribute can be used in your HTML as [%NAME%] for NAME , [%AGE%] for AGE. Spaces are not allowed within the attribute name, but special character underscore can be used.
     * 
     */
    @JsonProperty("attributes")
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * An array of recipients email address. Each object within this array must always contain the email address of a recipient.
     * (Required)
     * 
     */
    @JsonProperty("recipient")
    public String getRecipient() {
        return recipient;
    }

    /**
     * An array of recipients email address. Each object within this array must always contain the email address of a recipient.
     * (Required)
     * 
     */
    @JsonProperty("recipient")
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * An array of recipients who will receive a copy of your email i.e. recipients who will be in CC of your emails. Each object within this array must always contain the email address of the recipient.
     * 
     */
    @JsonProperty("recipient_cc")
    public List<String> getRecipientCc() {
        return recipientCc;
    }

    /**
     * An array of recipients who will receive a copy of your email i.e. recipients who will be in CC of your emails. Each object within this array must always contain the email address of the recipient.
     * 
     */
    @JsonProperty("recipient_cc")
    public void setRecipientCc(List<String> recipientCc) {
        this.recipientCc = recipientCc;
    }

    /**
     * Values that are specific to this personalization that will be carried along with the email and its activity data. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.
     * 
     */
    @JsonProperty("x-apiheader")
    public String getXApiheader() {
        return xApiheader;
    }

    /**
     * Values that are specific to this personalization that will be carried along with the email and its activity data. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.
     * 
     */
    @JsonProperty("x-apiheader")
    public void setXApiheader(String xApiheader) {
        this.xApiheader = xApiheader;
    }

    /**
     * Values that are specific to this personalization that will be carried along with the email and its activity data. This data is going to be mapped with their respective email address defined in the parameter recipient_cc. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.
     * 
     */
    @JsonProperty("x-apiheader_cc")
    public String getXApiheaderCc() {
        return xApiheaderCc;
    }

    /**
     * Values that are specific to this personalization that will be carried along with the email and its activity data. This data is going to be mapped with their respective email address defined in the parameter recipient_cc. It is recommended to pass Unique string in this parameter. Example: You can pass InvoiceID, TransactionID, MessageID in this parameter. These will get associated with their respective email messages and will be returned back to you in reports and over webhooks. Each of the X-APIHEADER value should not exceed 256 characters.
     * 
     */
    @JsonProperty("x-apiheader_cc")
    public void setXApiheaderCc(String xApiheaderCc) {
        this.xApiheaderCc = xApiheaderCc;
    }

    /**
     * An array of recipients who will receive a copy of your email i.e. recipients who will be in BCC of your emails. Each object within this array must always contain the email address of the recipient.
     * 
     */
    @JsonProperty("recipient_bcc")
    public List<String> getRecipientBcc() {
        return recipientBcc;
    }

    /**
     * An array of recipients who will receive a copy of your email i.e. recipients who will be in BCC of your emails. Each object within this array must always contain the email address of the recipient.
     * 
     */
    @JsonProperty("recipient_bcc")
    public void setRecipientBcc(List<String> recipientBcc) {
        this.recipientBcc = recipientBcc;
    }

    /**
     * This parameters allows you to add custom headers to the mail.
     * 
     */
    @JsonProperty("x-headers")
    public XHeaders getXHeaders() {
        return xHeaders;
    }

    /**
     * This parameters allows you to add custom headers to the mail.
     * 
     */
    @JsonProperty("x-headers")
    public void setXHeaders(XHeaders xHeaders) {
        this.xHeaders = xHeaders;
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
