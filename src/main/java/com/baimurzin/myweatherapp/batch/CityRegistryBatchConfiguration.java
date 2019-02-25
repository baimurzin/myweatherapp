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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

/**
 * The Batch Configuration which used to populate table
 * when application is started.
 *
 * @author Vladislav Baimurzin
 */
@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class CityRegistryBatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    @Value("${app.batch.csvCitiesFile}")
    private String resourceCsvFile;

    /**
     * The bean define the input. The reader method creates
     * an ItemReade. It looks for passed csv file and
     * parses it line by line.
     *
     *  @return reader for further processing
     */
    @Bean
    public FlatFileItemReader<CityRegistry> reader() {
        return new FlatFileItemReaderBuilder<CityRegistry>()
                .name("personItemReader")
                .resource(new ClassPathResource(resourceCsvFile))
                .delimited()
                .names(new String[]{"lat","lon","country","id","name"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CityRegistry>() {{
                    setTargetType(CityRegistry.class);
                }})
                .build();
    }

    /**
     * The bean defines an ItemWriter. This one works with datasource
     * to run sql statements to insert a single {@link CityRegistry}
     *
     * @param dataSource Datasource will be automatically injected
     * @return an ItemWriter
     */
    @Bean
    public JdbcBatchItemWriter<CityRegistry> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CityRegistry>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO city_registry (city_id, city_name, country, lat, lon) " +
                        "VALUES (:id, :name, :country, :lat, :lon)")
                .dataSource(dataSource)
                .build();
    }

    /**
     * The job importUserJob. Which will be registered to run on app start
     *
     * @param listener customer listener to log data and handle events
     * @param step1 The step which will be run in scope of this job
     * @return Job bean
     */
    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    /**
     * Step which uses reader and writer to populate the table
     *
     * @param writer writer defined above
     * @return the step used in the job
     */
    @Bean
    public Step step1(JdbcBatchItemWriter<CityRegistry> writer) {
        return stepBuilderFactory.get("loadData")
                .<CityRegistry, CityRegistry> chunk(3000)
                .reader(reader())
                .writer(writer)
                .build();
    }
}
