package com.sails.software.interview.builder.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

//@Configuration
public class SchemaConfig implements BeanPostProcessor {
    @Value("${db.schema}")
    private String dbSchema;

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Object postProcessAfterInitialization(Object bean,
                                                 String beanName) throws BeansException {
        if(bean instanceof DataSource dataSource) {
            try(Connection con = dataSource.getConnection();
                Statement stat = con.createStatement()) {
                logger.info("Executing schema creation script for schema: "+dbSchema);
                stat.execute("create schema if not exists "+dbSchema);
            } catch (SQLException e) {
                throw new RuntimeException(
                        "Exception in creating schema: "+e.getMessage());
            }
        }
        return bean;
    }
}
