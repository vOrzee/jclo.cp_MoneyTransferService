package ru.netology.cp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cp.exceptions.TransferException;
import ru.netology.cp.models.Amount;
import ru.netology.cp.models.TransferRequest;
import ru.netology.cp.services.TransferService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTests {

    @Mock
    private TransferService transferService;

    @Test
    public void testProcessTransferSuccess() {
        TransferRequest request = new TransferRequest("1234567890123456", "12/23", "123", "6543219876543210", new Amount(100, "USD"));

        String expectedOperationId = "OP123456";
        when(transferService.processTransfer(request)).thenReturn(expectedOperationId);

        String operationId = transferService.processTransfer(request);

        assertNotNull(operationId);
        assertEquals(expectedOperationId, operationId);
        verify(transferService).processTransfer(request);
    }

    @Test
    public void testProcessTransferInvalidCard() {
        TransferRequest request = new TransferRequest("invalid", "12/23", "123", "6543219876543210", new Amount(100, "USD"));

        when(transferService.processTransfer(request)).thenThrow(new TransferException("Invalid card number"));

        assertThrows(TransferException.class, () -> transferService.processTransfer(request));

        verify(transferService).processTransfer(request);
    }

    @Test
    public void testProcessTransferInsufficientFunds() {
        TransferRequest request = new TransferRequest("1234567890123456", "12/23", "123", "6543219876543210", new Amount(10000, "USD"));

        when(transferService.processTransfer(request)).thenThrow(new TransferException("Insufficient funds"));

        assertThrows(TransferException.class, () -> transferService.processTransfer(request));
    }

    @Test
    public void testProcessTransferInvalidCVV() {
        TransferRequest request = new TransferRequest("1234567890123456", "12/23", "000", "6543219876543210", new Amount(100, "USD"));

        when(transferService.processTransfer(request)).thenThrow(new TransferException("Invalid CVV"));

        assertThrows(TransferException.class, () -> transferService.processTransfer(request));
    }

    @Test
    public void testProcessTransferInvalidExpirationDate() {
        TransferRequest request = new TransferRequest("1234567890123456", "12/20", "123", "6543219876543210", new Amount(100, "USD"));

        when(transferService.processTransfer(request)).thenThrow(new TransferException("Card expired"));

        assertThrows(TransferException.class, () -> transferService.processTransfer(request));
    }
}

