package com.mystery.chat.configures;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author shouchen
 * @date 2022/11/30
 */
@Configuration
public class DataBaseConfigure {
    @Value("${spring.datasource.url}")
    private String jdbcURL;

    @Bean
    public DataSource dataSource() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        config.setJournalMode(SQLiteConfig.JournalMode.TRUNCATE);
        SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl(jdbcURL);
        checkDatabase(dataSource.getConnection());
        return dataSource;
    }

    private void checkDatabase(Connection connection) throws SQLException {
        try {
            connection.setAutoCommit(false);
            // TODO: 2022/12/31
        } finally {
            connection.close();
        }
    }
}
