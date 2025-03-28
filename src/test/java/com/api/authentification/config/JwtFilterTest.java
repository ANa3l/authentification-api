package com.api.authentification.config;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.api.authentification.controllers.TestController;

/**
 * Tests unitaires pour le filtre JWT (JwtFilter).
 * Vérifie que les requêtes sans token passent et que les tokens révoqués sont bien refusés.
 */
@WebMvcTest
@Import({JwtFilter.class, JwtUtil.class, TestSecurityConfig.class, TestController.class})
class JwtFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testRequestWithoutTokenShouldPass() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/any-endpoint"))
               .andExpect(status().isOk());
    }

    @Test
    void testRequestWithRevokedTokenShouldBeUnauthorized() throws Exception {
        String token = "fakeToken";
        JwtFilter.revokeToken(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/any-endpoint")
                .header("Authorization", "Bearer " + token))
               .andExpect(status().isUnauthorized());
    }
}
