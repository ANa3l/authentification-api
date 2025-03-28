package com.api.authentification.config;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest // Simule un environnement WebMVC sans démarrer tout Spring
@Import({JwtFilter.class, JwtUtil.class}) // Injecte tes composants de config
class JwtFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void testRequestWithoutTokenShouldPass() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/any-endpoint"))
               .andExpect(status().isOk()); // Suppose que /any-endpoint est public
    }

    @Test
    void testRequestWithRevokedTokenShouldBeUnauthorized() throws Exception {
        String token = "fakeToken";
        JwtFilter.revokeToken(token);

        mockMvc.perform(MockMvcRequestBuilders.get("/any-endpoint")
                .header("Authorization", "Bearer " + token))
               .andExpect(status().isUnauthorized());
    }

    // Tu peux ajouter un test avec un vrai token simulé si tu mocks jwtUtil.validateToken/token extraction.
}
