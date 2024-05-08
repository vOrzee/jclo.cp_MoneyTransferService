package ru.netology.cp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cp.exceptions.InvalidDataException;
import ru.netology.cp.exceptions.TransferException;
import ru.netology.cp.models.OperationRequest;
import ru.netology.cp.services.OperationService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OperationServiceTests {

    @Mock
    private OperationService operationService;

    @Test
    public void testConfirmOperationSuccess() {
        OperationRequest request = new OperationRequest("OP123456", "confirmationCode123");

        when(operationService.confirmOperation(request)).thenReturn("SuccessOperationId");

        String result = operationService.confirmOperation(request);

        assertEquals("SuccessOperationId", result);
        verify(operationService).confirmOperation(request);
    }

    @Test
    public void testConfirmOperationFailure() {
        OperationRequest request = new OperationRequest("OP123456", "wrongCode");

        when(operationService.confirmOperation(request)).thenThrow(new TransferException("Invalid confirmation code"));

        assertThrows(TransferException.class, () -> operationService.confirmOperation(request));
        verify(operationService).confirmOperation(request);
    }

    @Test
    public void testConfirmOperationInvalidOperationId() {
        OperationRequest request = new OperationRequest("invalidId", "confirmationCode123");

        when(operationService.confirmOperation(request)).thenThrow(new InvalidDataException("Invalid operation ID"));

        assertThrows(InvalidDataException.class, () -> operationService.confirmOperation(request));
        verify(operationService).confirmOperation(request);
    }
}


