package ru.netology.cp.utils;

import ru.netology.cp.models.OperationRequest;
import ru.netology.cp.models.TransferRequest;

import java.io.IOException;
import java.util.logging.*;

public class TransactionLogger {
    private static final Logger logger = Logger.getLogger(TransactionLogger.class.getName());
    private static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("transactions.log", true);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void logTransaction(TransferRequest request, String commission, String result) {
        logger.info(String.format("Date/Time: %tc, Card From: %s, Card To: %s, Amount: %s %s, Commission: %s, Result: %s",
                System.currentTimeMillis(),
                request.getCardFromNumber(),
                request.getCardToNumber(),
                request.getAmount().getValue(),
                request.getAmount().getCurrency(),
                commission,
                result));
    }

    public static void logOperation(OperationRequest request, String result) {
        logger.info(String.format("Date/Time: %tc, Operation ID: %s, Confirmation Code: %s, Result: %s",
                System.currentTimeMillis(),
                request.getOperationId(),
                request.getCode(),
                result));
    }
}
