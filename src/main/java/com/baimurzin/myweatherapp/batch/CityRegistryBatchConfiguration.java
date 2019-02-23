package com.baimurzin.myweatherapp.batch;

import com.baimurzin.myweatherapp.model.CityRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class CityRegistryBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<CityRegistry> reader() {
        return new FlatFileItemReaderBuilder<CityRegistry>()
                .name("personItemReader")
                .resource(new ClassPathResource("city.csv"))
                .delimited()
                .names(new String[]{"lat","lon","country","id","name"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CityRegistry>() {{
                    setTargetType(CityRegistry.class);
                }})
                .build();
    }
    @Bean
    public JdbcBatchItemWriter<CityRegistry> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CityRegistry>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO city_registry (city_id, city_name, country, lat, lon) " +
                        "VALUES (:id, :name, :country, :lat, :lon)")
                .dataSource(dataSource)
                .build();
    }


    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<CityRegistry> writer) {
        return stepBuilderFactory.get("loadData")
                .<CityRegistry, CityRegistry> chunk(500)
                .reader(reader())
//                .processor(processor())
                .writer(writer)
                .build();
    }
}
