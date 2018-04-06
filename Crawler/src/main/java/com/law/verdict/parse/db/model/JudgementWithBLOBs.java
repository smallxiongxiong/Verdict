package com.law.verdict.parse.db.model;

public class JudgementWithBLOBs extends Judgement {
    private String title;

    private String head;

    private String head2;

    private String facts;

    private String cause;

    private String judgeResult;

    private String tailContent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head == null ? null : head.trim();
    }

    public String getHead2() {
        return head2;
    }

    public void setHead2(String head2) {
        this.head2 = head2 == null ? null : head2.trim();
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts == null ? null : facts.trim();
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause == null ? null : cause.trim();
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult == null ? null : judgeResult.trim();
    }

    public String getTailContent() {
        return tailContent;
    }

    public void setTailContent(String tailContent) {
        this.tailContent = tailContent == null ? null : tailContent.trim();
    }
}