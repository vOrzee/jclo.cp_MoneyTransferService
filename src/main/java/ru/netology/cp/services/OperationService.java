package ru.netology.cp.services;

import org.springframework.stereotype.Service;
import ru.netology.cp.exceptions.InvalidDataException;
import ru.netology.cp.exceptions.TransferException;
import ru.netology.cp.models.OperationRequest;
import ru.netology.cp.utils.TransactionLogger;

import java.util.Random;

@Service
public class OperationService {

    public String confirmOperation(OperationRequest request) {

        if (request.getOperationId() == null || request.getOperationId().isEmpty()) {
            TransactionLogger.logOperation(request, "Operation ID cannot be null or empty");
            throw new InvalidDataException("Operation ID cannot be null or empty");
        }

        if (new Random().nextInt(100) < 70) {
            TransactionLogger.logOperation(request, "Успешно");
            return request.getOperationId();
        } else {
            TransactionLogger.logOperation(request, "Error during confirmation process");
            throw new TransferException("Error during confirmation process");
        }
    }
}
