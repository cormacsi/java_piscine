package edu.school21.repositories;

import edu.school21.exceptions.NotDeletedSubEntityException;
import edu.school21.exceptions.NotSavedSubEntityException;
import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {

    private DataSource dataSource;

    private ProductsRepository productsRepository;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "iPhone15Pro", 999.0),
            new Product(2L, "iPhone15", 799.0),
            new Product(3L, "iPhone14", 699.0),
            new Product(4L, "MacBook Air 15-inch", 1299.0),
            new Product(5L, "MacBook Air 13-inch", 1099.0),
            new Product(6L, "MacBook Pro 16-inch", 2499.0),
            new Product(7L, "MacBook Pro 14-inch", 1999.0),
            new Product(8L, "MacBook Pro 13-inch", 1299.0)
    );

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(5L, "MacBook Air 13-inch", 1099.0);

    final Product EXPECTED_UPDATED_PRODUCT = new Product(8L, "MacBook Pro 13-inch Sale", 1200.0);

    final Product EXPECTED_UPDATED_PRODUCT_NULL_PRICE = new Product(8L, "MacBook Pro 13-inch Sale", null);

    final Product EXPECTED_NOT_UPDATED_PRODUCT = new Product(12L, "MacBook", 1399.0);

    final Product EXPECTED_SAVED_PRODUCT = new Product(14L, "AirPods (3rd generation)", 169.0);

    final Product EXPECTED_SAVED_PRODUCT_NO_ID = new Product(null, "AirPods (3rd generation)", 169.0);

    final Product EXPECTED_SAVED_PRODUCT_NULL_PRICE = new Product(null, "AirPods (3rd generation)", null);

    final Product EXPECTED_NO_SAVED_PRODUCT = new Product(4L, "MacBook Air 15-inch", 1299.0);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void findAllTest() {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void findByIdTrueTest() {
        Optional<Product> product = productsRepository.findById(EXPECTED_FIND_BY_ID_PRODUCT.getId());
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.get());
    }

    @Test
    void findByIdNullTest() {
        Assertions.assertFalse(productsRepository.findById(null).isPresent());
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @ValueSource(longs = {0, 14, 23, 33, 545, 2351})
    void findByIdFalseTest(Long id) {
        Assertions.assertFalse(productsRepository.findById(id).isPresent());
    }

    @Test
    void updateTrueTest() {
        try {
            productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        } catch (NotSavedSubEntityException e) {
            Assertions.fail();
        }
        Optional<Product> product = productsRepository.findById(EXPECTED_UPDATED_PRODUCT.getId());
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, product.get());
    }

    @Test
    void updateTrueNullPriceTest() {
        try {
            productsRepository.update(EXPECTED_UPDATED_PRODUCT_NULL_PRICE);
        } catch (NotSavedSubEntityException e) {
            Assertions.fail();
        }
        Optional<Product> product = productsRepository.findById(EXPECTED_UPDATED_PRODUCT_NULL_PRICE.getId());
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT_NULL_PRICE, product.get());
    }

    @Test
    void updateFalseTest() {
        Assertions.assertThrows(NotSavedSubEntityException.class, () -> productsRepository.update(EXPECTED_NOT_UPDATED_PRODUCT));
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @ValueSource(longs = {2, 4, 5, 6, 8})
    void deleteTest(Long id) {
        try {
            productsRepository.delete(id);
        } catch (NotDeletedSubEntityException e) {
            Assertions.fail();
        }
        Assertions.assertFalse(productsRepository.findById(id).isPresent());
    }

    @ParameterizedTest(name = "№ {index}. [{arguments}]")
    @ValueSource(longs = {0, 20, 40, 100})
    void notDeleteTest(Long id) {
        Assertions.assertThrows(NotDeletedSubEntityException.class, () -> productsRepository.delete(id));
    }

    @Test
    void saveWithIdTest() {
        try {
            productsRepository.save(EXPECTED_SAVED_PRODUCT);
        } catch (NotSavedSubEntityException e) {
            Assertions.fail();
        }
        Optional<Product> product = productsRepository.findById(EXPECTED_SAVED_PRODUCT.getId());
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(product.get(), EXPECTED_SAVED_PRODUCT);
    }

    @Test
    void saveWithoutIdTest() {
        try {
            productsRepository.save(EXPECTED_SAVED_PRODUCT_NO_ID);
        } catch (NotSavedSubEntityException e) {
            Assertions.fail();
        }
        Optional<Product> product = productsRepository.findById(EXPECTED_SAVED_PRODUCT_NO_ID.getId());
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(product.get(), EXPECTED_SAVED_PRODUCT_NO_ID);
    }

    @Test
    void noSaveTest() {
        Assertions.assertThrows(NotSavedSubEntityException.class, () -> productsRepository.save(EXPECTED_NO_SAVED_PRODUCT));
    }

    @Test
    void saveNullPriceTest() {
        try {
            productsRepository.save(EXPECTED_SAVED_PRODUCT_NULL_PRICE);
        } catch (NotSavedSubEntityException e) {
            Assertions.fail();
        }
        Optional<Product> product = productsRepository.findById(EXPECTED_SAVED_PRODUCT_NULL_PRICE.getId());
        Assertions.assertTrue(product.isPresent());
        Assertions.assertEquals(product.get(), EXPECTED_SAVED_PRODUCT_NULL_PRICE);
    }
}
