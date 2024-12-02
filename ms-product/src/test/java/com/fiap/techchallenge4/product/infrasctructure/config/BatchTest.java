package com.fiap.techchallenge4.product.infrasctructure.config;

import com.fiap.techchallenge4.product.core.model.Product;
import com.fiap.techchallenge4.product.infrasctructure.batch.processor.ProductProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@Configuration
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BatchTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ProductProcessor processor;

    @Autowired
    private FlatFileItemReader<Product> reader;

    @Autowired
    private ItemWriter<Product> writer;

    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Test
    public void testBatchProcessing() throws Exception {
        // Configuração dos parâmetros do job
        String filePath = "src/test/resources/products.csv";  // Caminho do arquivo
        System.out.println("Arquivo de entrada: " + filePath); // Debugging

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("pathWithFileName", filePath)
                .toJobParameters();

        // Executando o job (passando o nome do step)
        JobExecution jobExecution = this.jobLauncherTestUtils.launchStep("step", jobParameters);

        // Verificando o status da execução do job
        assertEquals("COMPLETED", jobExecution.getStatus().toString());

        // Verificando os dados no banco após a execução
        List<Map<String, Object>> products = jdbcTemplate.queryForList("SELECT * FROM product ORDER BY name");
        assertEquals("Product Test 1", products.get(0).get("name"));
    }


    @Test
    public void testProcessor() throws Exception {
        Product product = new Product();
        product.setSkuId(1L);
        product.setName("Product-1");
        product.setDescription("Description for Product-1");
        product.setPrice(BigDecimal.valueOf(982.82));
        product.setStock(198);

        Product processedProduct = processor.process(product);

        assertNotNull(processedProduct);
        assertEquals("Product-1", processedProduct.getName());
    }
}
