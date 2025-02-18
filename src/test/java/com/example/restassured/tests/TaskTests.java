package com.example.restassured.tests;

import com.example.restassured.models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

class TaskTests extends RestAssuredTestBase {

    @Test
    @DisplayName("Create task")
    void createTask() {
        Task expectedTask = Task.builder()
                .title("TaskTitle")
                .body("TaskBody")
                .userId(1).build();

        Task actualTask = given()
                .contentType("application/json")
                .body(expectedTask)
                .when()
                .post("/posts")
                .then()
                .assertThat()
                .statusCode(201)
                .extract().as(Task.class);

        assertThat(actualTask.getId()).isPositive();
        assertThat(actualTask).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedTask);
    }
}
