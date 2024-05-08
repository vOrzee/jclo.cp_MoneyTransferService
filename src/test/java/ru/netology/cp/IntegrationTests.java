package ru.netology.cp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.cp.models.Amount;
import ru.netology.cp.models.SuccessResponse;
import ru.netology.cp.models.TransferRequest;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private final GenericContainer<?> cp = new GenericContainer<>("cp").withExposedPorts(5500);


    @Test
    void loadBodyDev() {
        // Создание HttpHeaders
        TransferRequest request = new TransferRequest(
                "4237650234658140",
                "01/34",
                "899",
                "8365734985692435",
                new Amount(500000, "RUR")
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создание HttpEntity
        HttpEntity<TransferRequest> entity = new HttpEntity<>(request, headers);

        // Отправка POST-запроса
        ResponseEntity<SuccessResponse> response = restTemplate.postForEntity(
                "http://localhost:" + cp.getMappedPort(5500) + "/transfer",
                entity,
                SuccessResponse.class
        );

        // Получение тела ответа
        SuccessResponse responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(36, responseBody.getOperationId().length());
    }

    @Test
    void loadBodyDevTransferException() {
        // Создание HttpHeaders
        TransferRequest request = new TransferRequest(
                "4237650234658140",
                "01/34",
                "900",
                "8365734985692435",
                new Amount(500000, "RUR")
        );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Создание HttpEntity
        HttpEntity<TransferRequest> entity = new HttpEntity<>(request, headers);

        // Отправка POST-запроса
        ResponseEntity<SuccessResponse> response = restTemplate.postForEntity(
                "http://localhost:" + cp.getMappedPort(5500) + "/transfer",
                entity,
                SuccessResponse.class
        );

        // Получение тела ответа
        SuccessResponse responseBody = response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(responseBody.getOperationId());
    }
}
