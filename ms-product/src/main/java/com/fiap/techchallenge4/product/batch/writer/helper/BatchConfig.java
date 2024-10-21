package com.fiap.techchallenge4.product.batch.writer.helper;

import com.fiap.techchallenge4.product.repository.model.Product;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Autowired
    @Qualifier("transactionManager")
    private PlatformTransactionManager transactionManager;

    @Bean
    public Job job(JobRepository jobRepository, Step step) {
        return new JobBuilder("job", jobRepository).start(step).build();
    }

    @Bean
    public Step step(ItemReader<Product> reader, ItemWriter<Product> writer, JobRepository jobRepository) {
        return new StepBuilder("step", jobRepository)
                .<Product, Product>chunk(200, transactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }


    @Bean
    public ItemReader<Product> reader() {
        return new FlatFileItemReaderBuilder<Product>().name("reader")
                .resource(new FileSystemResource("/home/lobaoff/Desktop/repositories/order-management-tc-fase-4/ms-product/src/main/resources/imports/pending/produtos.csv"))
                .comments("--")
                .delimited()
                .delimiter(";")
                .names("name", "description", "price", "estoque")
                .targetType(Product.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public ItemWriter<Product> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Product>().dataSource(dataSource).sql(""" 
                        INSERT INTO product  (name, description, price, estoque)
                VALUES (:name, :description, :price, :estoque);
               
                """)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }
}
