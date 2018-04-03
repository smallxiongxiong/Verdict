package com.law.verdict.parse.db.model;

import java.util.Date;

public class Judgement {
    private Integer id;

    private String docId;

    private String title;

    private Date pubDate;

    private String tailContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId == null ? null : docId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getTailContent() {
        return tailContent;
    }

    public void setTailContent(String tailContent) {
        this.tailContent = tailContent == null ? null : tailContent.trim();
    }

	@Override
	public String toString() {
		return "Judgement [id=" + id + ", docId=" + docId + ", title=" + title + ", pubDate=" + pubDate
				+ ", tailContent=" + tailContent + "]";
	}
    
    
}