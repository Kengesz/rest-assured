package com.example.restassured.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Task {
    private int id;
    private String title;
    private String body;
    private int userId;

    @JsonCreator
    public Task(@JsonProperty("id") int id,
                @JsonProperty("title") String title,
                @JsonProperty("body") String body,
                @JsonProperty("userId") int userId) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
    }
}
