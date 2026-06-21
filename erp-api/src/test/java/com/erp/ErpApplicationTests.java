package com.erp;

import com.erp.infrastructure.persistence.jpa.repository.OrderJpaRepository;
import com.erp.infrastructure.persistence.jpa.repository.ProductJpaRepository;
import com.erp.infrastructure.persistence.mongo.repository.CatalogMongoRepository;
import com.erp.infrastructure.persistence.mongo.repository.ProductInCatalogMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.jpa.hibernate.ddl-auto=none",
    "spring.data.mongodb.uri=mongodb://localhost:27017/test",
    "spring.data.redis.host=localhost",
    "spring.autoconfigure.exclude=" +
        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
        "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration," +
        "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
        "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration"
})
class ErpApplicationTests {

    // DB autoconfiguration is excluded above so the context loads without live
    // databases. The persistence adapters still need their Spring Data repository
    // beans, so we provide mocks for them.
    @MockitoBean
    private OrderJpaRepository orderJpaRepository;
    @MockitoBean
    private ProductJpaRepository productJpaRepository;
    @MockitoBean
    private CatalogMongoRepository catalogMongoRepository;
    @MockitoBean
    private ProductInCatalogMongoRepository productInCatalogMongoRepository;

    @Test
    void contextLoads() {
    }

}
