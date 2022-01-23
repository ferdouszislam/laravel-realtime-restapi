package com.example.laravel_echo_realtime_app;

import org.json.JSONException;
import org.json.JSONObject;

public class Message {
    private String id, author, content, createdAt, updatedAt;

    public Message() {
    }

    public Message(String id, String author, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Message parseFromJson(JSONObject messageJson) throws JSONException {

        return new Message(
                messageJson.getString("id"),
                messageJson.getString("author"),
                messageJson.getString("content"),
                messageJson.getString("created_at"),
                messageJson.getString("updated_at")
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
