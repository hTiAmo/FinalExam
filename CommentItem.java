package com.swufe.finalexam;

public class CommentItem {
    private int id;
    private String Comment;

    public CommentItem() {
        this.Comment = "";
    }

    public CommentItem(String Comment) {
        this.Comment = Comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String Comment) {
        this.Comment = Comment;
    }
}
