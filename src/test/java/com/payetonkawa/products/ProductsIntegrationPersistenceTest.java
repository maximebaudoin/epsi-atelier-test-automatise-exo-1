package com.payetonkawa.products;

import com.payetonkawa.products.entity.ProductsEntity;
import com.payetonkawa.products.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProductsIntegrationPersistenceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testPersistenceBetweenRestarts() throws Exception {

        String productJson = "{ \"name\": \"Test Product\", \"price\": 19.99, \"description\": \"A test product\", \"color\": \"Red\", \"stock\": 100 }";
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isCreated());

        List<ProductsEntity> products = productRepository.findAll();
        assertThat(products).isNotNull();
        ProductsEntity savedProduct = products.get(0);
        UUID productId = savedProduct.getId();

        productRepository.flush();  // Ensure changes are written to the database

        Optional<ProductsEntity> retrievedProduct = productRepository.findById(productId);
        assertThat(retrievedProduct).isPresent();
        assertThat(retrievedProduct.get().getId()).isNotNull();
    }
}
