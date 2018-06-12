package com.feng.authserver.config;

import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import javax.sql.DataSource;

@Configuration
public class JooqConfig {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private DataSource dataSource;

    @Bean
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configration());
    }

    private DefaultConfiguration configration() {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(connectionProvider());
        configuration.set(new DefaultExecuteListenerProvider(jooqExceptionTranslator()));
        //不知道为什么yml里边配置不生效，在这里再次声明方言类型
        configuration.setSQLDialect(SQLDialect.MYSQL);
        return configuration;
    }

    @Bean
    public DefaultExecuteListener jooqExceptionTranslator() {
        return new DefaultExecuteListener() {
            @Override
            public void exception(ExecuteContext ctx) {

                SQLDialect dialect = ctx.configuration().dialect();

                SQLExceptionTranslator translator = (dialect != null)
                        ? new SQLErrorCodeSQLExceptionTranslator(dialect.name())
                        : new SQLStateSQLExceptionTranslator();
                //noinspection SqlNoDataSourceInspection
                ctx.exception(translator.translate("Access database using jOOQ", ctx.sql(), ctx.sqlException()));
            }
        };
    }

}
