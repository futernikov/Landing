package com.example.Landing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
@ComponentScan("com.example.Landing")
@EnableJpaRepositories(basePackages = "com.example.Landing.repo")
@PropertySource("classpath:testApplication.properties")

@EnableTransactionManagement
public class LandingApplicationTest {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource(){

        InputStream is = LandingApplicationTest.class.getResourceAsStream("/testApplication.properties");
        Properties props = new Properties();
        try {
            props.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("!!!" + props);
       return DataSourceBuilder.create()
               .username(props.getProperty("spring.datasource.username"))
               .password(props.getProperty("spring.datasource.password"))
               .url(props.getProperty("spring.datasource.url"))
               .build();
    }

}
