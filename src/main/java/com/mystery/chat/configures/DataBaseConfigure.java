package com.mystery.chat.configures;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;

/**
 * @author shouchen
 * @date 2022/11/30
 */
@Configuration
public class DataBaseConfigure {
    @Value("${spring.datasource.url}")
    private String jdbcURL;

    @Bean
    public DataSource dataSource() {
        SQLiteConfig config = new SQLiteConfig();
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        config.setJournalMode(SQLiteConfig.JournalMode.TRUNCATE);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl(jdbcURL);
        return dataSource;
    }
}
