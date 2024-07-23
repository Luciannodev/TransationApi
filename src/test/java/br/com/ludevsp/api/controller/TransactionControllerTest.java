package br.com.ludevsp.api.controller;

import br.com.ludevsp.api.controllers.TransactionController;
import br.com.ludevsp.api.dto.TransactionQuery;
import br.com.ludevsp.api.dto.TransactionResponse;
import br.com.ludevsp.domain.enums.ResponseCode;
import br.com.ludevsp.domain.entities.Transaction;
import br.com.ludevsp.domain.intefaces.usecase.TransactionUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionUseCase transactionUseCase;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        objectMapper = new ObjectMapper();
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

    @Test
    public void testGetTransaction() throws Exception {
        TransactionQuery transactionQuery = new TransactionQuery();
        Transaction transaction = new Transaction(){};
        transaction.setTotalAmount(new BigDecimal(100));
        TransactionResponse transactionResponse = new TransactionResponse(transaction);
        List<TransactionResponse> expectedTransactions = Arrays.asList(transactionResponse);

        when(transactionUseCase.getAllTransactions(any(TransactionQuery.class))).thenReturn(List.of(transaction));

        mockMvc.perform(get("/get_transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionQuery)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedTransactions)));
    }
}