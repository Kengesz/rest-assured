package com.example.restassured.tests;

import com.example.restassured.models.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.assertj.core.api.Assertions.assertThat;

class TaskTests extends RestAssuredTestBase {

    private final String POSTS_ENDPOINT = "/posts";

    @Test
    @DisplayName("Create task")
    void createTask() {
        Task expectedTask = Task.builder()
                .title("TaskTitle")
                .body("TaskBody")
                .userId(1)
                .build();

        Task actualTask = given()
                .contentType("application/json")
                .body(expectedTask)
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(201)
                .extract().as(Task.class);

        assertThat(actualTask.getId()).isPositive();
        assertThat(actualTask).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedTask);
    }

    @Test
    @DisplayName("Create task with missing fields")
    void createTaskWithMissingFields() {
        Task expectedTask = Task.builder().build();

        given()
                .contentType("application/json")
                .body(expectedTask)
                .when()
                .post(POSTS_ENDPOINT)
                .then()
                .assertThat()
                .statusCode(400);
    }

    @Test
    @DisplayName("Get task")
    void getTask() {
        Task expectedTask = Task.builder()
                .id(99)
                .title("temporibus sit alias delectus eligendi possimus magni")
                .body("quo deleniti praesentium dicta non quod\naut est molestias\nmolestias et officia quis nihil\nitaque dolorem quia")
                .userId(10)
                .build();

        Task actualTask = given()
                .contentType("application/json")
                .when()
                .get(POSTS_ENDPOINT + "/" + expectedTask.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract().as(Task.class);

        assertThat(actualTask).usingRecursiveComparison()
                .isEqualTo(expectedTask);
    }

    @Test
    @DisplayName("Get nonexistent task")
    void getNonExistentTask() {
        given()
                .when()
                .get(POSTS_ENDPOINT + "/-1")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @DisplayName("Update task")
    void updateTask() {
        Task expectedTask = Task.builder()
                .id(1)
                .title("UpdatedTitle")
                .body("UpdatedBody")
                .userId(2)
                .build();

        Task actualTask = given()
                .contentType("application/json")
                .body(expectedTask)
                .when()
                .put(POSTS_ENDPOINT + "/" + expectedTask.getId())
                .then()
                .assertThat()
                .statusCode(200)
                .extract().as(Task.class);

        assertThat(actualTask).usingRecursiveComparison()
                .isEqualTo(expectedTask);
    }

    @Test
    @DisplayName("Update nonexistent task")
    void updateNonExistentTask() {
        Task expectedTask = Task.builder()
                .id(-1)
                .title("UpdatedTitle")
                .body("UpdatedBody")
                .userId(2)
                .build();

        given()
                .contentType("application/json")
                .body(expectedTask)
                .when()
                .put(POSTS_ENDPOINT + "/" + expectedTask.getId())
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @DisplayName("Delete task")
    void deleteTask() {
        given()
                .when()
                .delete(POSTS_ENDPOINT + "/3")
                .then()
                .assertThat()
                .statusCode(200);

        given()
                .when()
                .get(POSTS_ENDPOINT + "/3")
                .then()
                .assertThat()
                .statusCode(404);
    }

    @Test
    @DisplayName("Delete nonexistent task")
    void deleteNonExistentTask() {
        given()
                .when()
                .delete(POSTS_ENDPOINT + "/-1")
                .then()
                .assertThat()
                .statusCode(404);
    }
}
