/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2016 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package tk.mybatis.springboot.model;

import java.sql.Date;

import javax.persistence.Column;

public class Judgement extends BaseEntity {
    
    private String docId;

    private String title;

    private Date pubDate;
    
    private String head;
    
    private String head2;
    
    private String facts;
    
    private String cause;
    
    private String judgeResult;
    
    private String tailContent;

	public String getDocId() {
		return docId;
	}
	@Column(name = "doc_id")
	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public String getHead2() {
		return head2;
	}

	public void setHead2(String head2) {
		this.head2 = head2;
	}

	public String getFacts() {
		return facts;
	}

	public void setFacts(String facts) {
		this.facts = facts;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getJudgeResult() {
		return judgeResult;
	}

	public void setJudgeResult(String judgeResult) {
		this.judgeResult = judgeResult;
	}

	public String getTailContent() {
		return tailContent;
	}

	public void setTailContent(String tailContent) {
		this.tailContent = tailContent;
	}

    
}