package com.amir.batch.demo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import com.amir.batch.demo.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

  @Autowired
  private StepBuilderFactory stepBuilderFactory;

  @Autowired
  private JobBuilderFactory jobBuilderFactory;

  @Bean
  public Job job(ItemProcessor<User, User> itemProcessor, ItemWriter<User> itemWriter) {
    Step step = stepBuilderFactory.get("load_example_step_name").<User, User>chunk(2)
        .reader(itemReader()).processor(itemProcessor).writer(itemWriter).build();

    return jobBuilderFactory.get("load_example_job_name").incrementer(new RunIdIncrementer())
        // can be added more than one step.
        .start(step).build();
  }

  @Bean
  public FlatFileItemReader<User> itemReader() {
    FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
    flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
    flatFileItemReader.setName("CSV-reader");
    flatFileItemReader.setLinesToSkip(1);
    flatFileItemReader.setLineMapper(lineMapper());
    return flatFileItemReader;
  }

  private LineMapper<User> lineMapper() {
    DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setDelimiter(",");
    lineTokenizer.setStrict(false);
    lineTokenizer.setNames("id", "name", "dept", "salary");

    BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(User.class);

    defaultLineMapper.setLineTokenizer(lineTokenizer);
    defaultLineMapper.setFieldSetMapper(fieldSetMapper);
    return defaultLineMapper;
  }
}
