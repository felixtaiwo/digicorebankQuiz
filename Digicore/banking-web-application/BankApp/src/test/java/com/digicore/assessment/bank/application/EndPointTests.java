package com.digicore.assessment.bank.application;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class EndPointTests extends BankApplicationTests{
    @Test
    public void testDeposit() throws Exception {
        payload.put("amount", "50000");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/deposit")
                        .content(new Gson().toJson(payload)).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + payload.get("token"))
                )
                .andExpect(status().isOk());
    }
    @Test
    public void testWithdrwaw() throws Exception {
        payload.put("withdrawnAmount", "1000");
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/withdrawal")
                        .content(new Gson().toJson(payload)).contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + payload.get("token"))
                )
                .andExpect(status().isOk());
    }
    @Test
    public void testAccountInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account_info/" + payload.get("accountNumber")).header("Authorization", "Bearer " + payload.get("token")))
                .andExpect(status().isOk());

    }
    @Test
    public void testAccountStatement() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/account_statement/" + payload.get("accountNumber")).header("Authorization", "Bearer " + payload.get("token")))
                .andExpect(status().isOk());
    }
}
