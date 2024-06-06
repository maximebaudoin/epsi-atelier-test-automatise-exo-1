package com.payetonkawa.products;

import com.payetonkawa.products.entity.ProductsEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ProductIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private UUID productId;

    private void createTestProduct() {
        if (this.productId == null) {
            ProductsEntity product = new ProductsEntity();
            product.setName("Mon produit test");
            product.setPrice(12.5F);

            ResponseEntity<ProductsEntity> postResponse = restTemplate.postForEntity("/products", product, ProductsEntity.class);
            assertThat(postResponse.getStatusCodeValue()).isEqualTo(201);
            ProductsEntity createdProduct = postResponse.getBody();
            assertThat(createdProduct).isNotNull();
            assertThat(createdProduct.getId()).isNotNull();

            this.productId = createdProduct.getId();
        }
    }

    @Test
    public void testCreateProduct() {
        ProductsEntity product = new ProductsEntity();
        product.setName("Mon produit test");
        product.setPrice(12.5F);

        ResponseEntity<ProductsEntity> postResponse = restTemplate.postForEntity("/products", product, ProductsEntity.class);
        assertThat(postResponse.getStatusCodeValue()).isEqualTo(201);
        ProductsEntity createdProduct = postResponse.getBody();
        assertThat(createdProduct).isNotNull();
        assertThat(createdProduct.getId()).isNotNull();

        this.productId = createdProduct.getId();
    }

    @Test
    public void testReadProduct() {
        createTestProduct();
        ResponseEntity<ProductsEntity> getResponse = restTemplate.getForEntity("/products/" + productId, ProductsEntity.class);
        assertThat(getResponse.getStatusCodeValue()).isEqualTo(200);
        ProductsEntity fetchedProduct = getResponse.getBody();
        assertThat(fetchedProduct).isNotNull();
        assertThat(fetchedProduct.getId()).isEqualTo(productId);
    }

    @Test
    public void testUpdateProduct() {
        createTestProduct();
        ProductsEntity productToUpdate = restTemplate.getForObject("/products/" + productId, ProductsEntity.class);
        assertThat(productToUpdate).isNotNull();

        productToUpdate.setName("Updated Product");
        productToUpdate.setPrice(25F);
        HttpEntity<ProductsEntity> requestUpdate = new HttpEntity<>(productToUpdate);

        ResponseEntity<ProductsEntity> updateResponse = restTemplate.exchange("/products/" + productId, HttpMethod.PUT, requestUpdate, ProductsEntity.class);
        assertThat(updateResponse.getStatusCodeValue()).isEqualTo(200);
        ProductsEntity updatedProduct = updateResponse.getBody();
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Updated Product");
        assertThat(updatedProduct.getPrice()).isEqualTo(25F);
    }

    @Test
    public void testDeleteProduct() {
        createTestProduct();
        restTemplate.delete("/products/" + productId);

        ResponseEntity<ProductsEntity> deleteResponse = restTemplate.getForEntity("/products/" + productId, ProductsEntity.class);
        assertThat(deleteResponse.getStatusCodeValue()).isEqualTo(404);
    }
}