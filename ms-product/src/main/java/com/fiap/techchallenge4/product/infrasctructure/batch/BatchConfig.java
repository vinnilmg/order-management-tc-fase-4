package com.fiap.techchallenge4.product.infrasctructure.batch;

import com.fiap.techchallenge4.product.infrasctructure.batch.processor.ProductProcessor;
import com.fiap.techchallenge4.product.core.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@AllArgsConstructor
public class BatchConfig {

    @Qualifier("transactionManager")
    @Autowired
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Step step(ItemReader<Product> reader, ItemProcessor<Product, Product> processor, ItemWriter<Product> writer, JobRepository jobRepository) {
        return new StepBuilder("step", jobRepository)
                .<Product, Product>chunk(200, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @Bean
    public ItemProcessor<Product, Product> processor() {
        return new ProductProcessor();
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Product> productItemReader(@Value("#{jobParameters['pathWithFileName']}") String filePath) {
        FlatFileItemReader<Product> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));
        reader.setLinesToSkip(1);

        DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setDelimiter(";");
        tokenizer.setStrict(false);
        tokenizer.setNames("sku_id","name", "description", "price", "stock");

        BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Product.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public ItemWriter<Product> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>()
                .dataSource(dataSource)
                .sql("INSERT INTO product (sku_id, name, description, price, stock, created_date_time) " +
                        "VALUES (:skuId, :name, :description, :price, :stock, :createdDateTime)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }
}
