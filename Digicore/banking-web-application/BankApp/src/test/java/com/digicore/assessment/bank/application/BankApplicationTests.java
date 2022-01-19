package com.digicore.assessment.bank.application;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BankApplicationTests {
    @Autowired
    MockMvc mockMvc;
    static Map<String, String> payload = new HashMap<>();
    @Test
    void TestCreateAndLoginEndpoint() throws Exception {

        BankService.initiateAccounts();
        payload.put("accountName", "Adewale");
        payload.put("accountPassword", "@123@");
        payload.put("initialDeposit", "6000");
        String accountNumber = (mockMvc.perform(MockMvcRequestBuilders
                        .post("/create_account")
                        .content(new Gson().toJson(payload)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString().substring(24, 34)); /*The use of substring is to get the account number out of the response*/
        payload.put("accountNumber", accountNumber);
        String token = (mockMvc.perform(MockMvcRequestBuilders
                        .post("/login")
                        .content(new Gson().toJson(payload)).contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString());
        payload.put("token",token);
    }

}
