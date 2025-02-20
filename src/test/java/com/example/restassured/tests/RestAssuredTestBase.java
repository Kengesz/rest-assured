package com.example.restassured.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class RestAssuredTestBase {

    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }
}
