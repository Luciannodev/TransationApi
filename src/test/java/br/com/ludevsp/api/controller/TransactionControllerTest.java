package br.com.ludevsp.api.controller;

import br.com.ludevsp.api.controllers.TransactionController;
import br.com.ludevsp.domain.dto.ResponseCode;
import br.com.ludevsp.domain.entities.Transaction;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionUseCase transactionUseCase;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setResponseCode(ResponseCode.APROVADA);
        transaction.setResponseCode("APROVADA");

        when(transactionUseCase.execute(any())).thenReturn(transaction);

        mockMvc.perform(post("/create_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"account\":\"1\",\"totalAmount\":100,\"mcc\":\"5811\",\"merchant\":\"PADARIADOZESAOPAULOBR\"}"))
                .andExpect(status().isOk());
    }
}