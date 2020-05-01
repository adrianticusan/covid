
package com.covid19.match.external.pepipost.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "attachments",
    "content",
    "ampcontent",
    "from",
    "personalizations",
    "replyToId",
    "settings",
    "subject",
    "tags",
    "templateId"
})
public class Request {

    @JsonProperty("attachments")
    private List<Attachment> attachments = null;
    /**
     * The HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.
     * 
     */
    @JsonProperty("content")
    @JsonPropertyDescription("The HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.")
    private String content;
    /**
     * The AMP HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.
     * 
     * Make sure you also add HTML in content parameter which will be rendered in case AMP HTML doesn't work on a given client.
     * 
     */
    @JsonProperty("ampcontent")
    @JsonPropertyDescription("The AMP HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.\n\nMake sure you also add HTML in content parameter which will be rendered in case AMP HTML doesn't work on a given client.")
    private String ampcontent;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("from")
    private From from;
    /**
     * An array of recipients and their metadata. Each object within personalizations can be thought of as an envelope - it defines who should receive an individual message and how that message should be handled.
     * (Required)
     * 
     */
    @JsonProperty("personalizations")
    @JsonDeserialize(as = java.util.LinkedHashSet.class)
    @JsonPropertyDescription("An array of recipients and their metadata. Each object within personalizations can be thought of as an envelope - it defines who should receive an individual message and how that message should be handled.")
    private Set<Personalization> personalizations = null;
    /**
     * Email address which need to be used as Reply-To
     * 
     */
    @JsonProperty("replyToId")
    @JsonPropertyDescription("Email address which need to be used as Reply-To")
    private String replyToId;
    @JsonProperty("settings")
    private Settings settings;
    /**
     * The subject line for your email. You can also use attributes in subject like [% NAME %], [% AGE %] and these will be replaced with the respective value for the recipient.
     * (Required)
     * 
     */
    @JsonProperty("subject")
    @JsonPropertyDescription("The subject line for your email. You can also use attributes in subject like [% NAME %], [% AGE %] and these will be replaced with the respective value for the recipient.")
    private String subject;
    /**
     * Tags can help organize your email analytics by enabling you to “tag” emails by type or broad topic. You can define your own custom tags like "OTP Emails", "Order Confirmation emails" etc.
     * 
     * Any space before and after the tag will be ignored. For instance: " New Tag " will be considered "New Tag".
     * 
     */
    @JsonProperty("tags")
    @JsonPropertyDescription("Tags can help organize your email analytics by enabling you to \u201ctag\u201d emails by type or broad topic. You can define your own custom tags like \"OTP Emails\", \"Order Confirmation emails\" etc.\n\nAny space before and after the tag will be ignored. For instance: \" New Tag \" will be considered \"New Tag\".")
    private String tags;
    /**
     * You can upload the HTML content in your Pepipost account and can just pass the TemplateID here. This will save the data transfer in API call in case you are going to repeatedly use same HTML template for all your emails.
     * 
     */
    @JsonProperty("templateId")
    @JsonPropertyDescription("You can upload the HTML content in your Pepipost account and can just pass the TemplateID here. This will save the data transfer in API call in case you are going to repeatedly use same HTML template for all your emails.")
    private Integer templateId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attachments")
    public List<Attachment> getAttachments() {
        return attachments;
    }

    @JsonProperty("attachments")
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    /**
     * The HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.
     * 
     */
    @JsonProperty("content")
    public String getContent() {
        return content;
    }

    /**
     * The HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.
     * 
     */
    @JsonProperty("content")
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * The AMP HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.
     * 
     * Make sure you also add HTML in content parameter which will be rendered in case AMP HTML doesn't work on a given client.
     * 
     */
    @JsonProperty("ampcontent")
    public String getAmpcontent() {
        return ampcontent;
    }

    /**
     * The AMP HTML content to be sent in your email. You can use attributes to display dynamic values such as name, account number, etc. for ex. [% NAME %] for NAME , [% AGE %] for AGE etc.
     * 
     * Make sure you also add HTML in content parameter which will be rendered in case AMP HTML doesn't work on a given client.
     * 
     */
    @JsonProperty("ampcontent")
    public void setAmpcontent(String ampcontent) {
        this.ampcontent = ampcontent;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("from")
    public From getFrom() {
        return from;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("from")
    public void setFrom(From from) {
        this.from = from;
    }

    /**
     * An array of recipients and their metadata. Each object within personalizations can be thought of as an envelope - it defines who should receive an individual message and how that message should be handled.
     * (Required)
     * 
     */
    @JsonProperty("personalizations")
    public Set<Personalization> getPersonalizations() {
        return personalizations;
    }

    /**
     * An array of recipients and their metadata. Each object within personalizations can be thought of as an envelope - it defines who should receive an individual message and how that message should be handled.
     * (Required)
     * 
     */
    @JsonProperty("personalizations")
    public void setPersonalizations(Set<Personalization> personalizations) {
        this.personalizations = personalizations;
    }

    /**
     * Email address which need to be used as Reply-To
     * 
     */
    @JsonProperty("replyToId")
    public String getReplyToId() {
        return replyToId;
    }

    /**
     * Email address which need to be used as Reply-To
     * 
     */
    @JsonProperty("replyToId")
    public void setReplyToId(String replyToId) {
        this.replyToId = replyToId;
    }

    @JsonProperty("settings")
    public Settings getSettings() {
        return settings;
    }

    @JsonProperty("settings")
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * The subject line for your email. You can also use attributes in subject like [% NAME %], [% AGE %] and these will be replaced with the respective value for the recipient.
     * (Required)
     * 
     */
    @JsonProperty("subject")
    public String getSubject() {
        return subject;
    }

    /**
     * The subject line for your email. You can also use attributes in subject like [% NAME %], [% AGE %] and these will be replaced with the respective value for the recipient.
     * (Required)
     * 
     */
    @JsonProperty("subject")
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Tags can help organize your email analytics by enabling you to “tag” emails by type or broad topic. You can define your own custom tags like "OTP Emails", "Order Confirmation emails" etc.
     * 
     * Any space before and after the tag will be ignored. For instance: " New Tag " will be considered "New Tag".
     * 
     */
    @JsonProperty("tags")
    public String getTags() {
        return tags;
    }

    /**
     * Tags can help organize your email analytics by enabling you to “tag” emails by type or broad topic. You can define your own custom tags like "OTP Emails", "Order Confirmation emails" etc.
     * 
     * Any space before and after the tag will be ignored. For instance: " New Tag " will be considered "New Tag".
     * 
     */
    @JsonProperty("tags")
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * You can upload the HTML content in your Pepipost account and can just pass the TemplateID here. This will save the data transfer in API call in case you are going to repeatedly use same HTML template for all your emails.
     * 
     */
    @JsonProperty("templateId")
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * You can upload the HTML content in your Pepipost account and can just pass the TemplateID here. This will save the data transfer in API call in case you are going to repeatedly use same HTML template for all your emails.
     * 
     */
    @JsonProperty("templateId")
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
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
