package ru.netology.cp.services;

import org.springframework.stereotype.Service;
import ru.netology.cp.exceptions.InvalidDataException;
import ru.netology.cp.exceptions.TransferException;
import ru.netology.cp.models.TransferRequest;
import ru.netology.cp.utils.TransactionLogger;

import java.util.UUID;

@Service
public class TransferService {

    public String processTransfer(TransferRequest request) throws InvalidDataException, TransferException {
        if (!validateRequest(request)) {
            TransactionLogger.logTransaction(request,String.valueOf(request.getAmount().getValue()*0.01),"Данные не прошли валидацию");
            throw new InvalidDataException("Input data validation failed");
        }
        return performTransfer(request);
    }

    private boolean validateRequest(TransferRequest request) {
        return request.getCardFromNumber() != null && request.getCardToNumber() != null;
    }

    private String performTransfer(TransferRequest request) throws TransferException {

        if (Integer.parseInt(request.getCardFromCVV()) < 900) {
            TransactionLogger.logTransaction(request,String.valueOf(request.getAmount().getValue()*0.01),"Пока всё норм");
            return UUID.randomUUID().toString();
        } else {
            TransactionLogger.logTransaction(request,String.valueOf(request.getAmount().getValue()*0.01),"Что-то пошло не так");
            throw new TransferException("Transfer failed due to an internal error");
        }
    }
}
