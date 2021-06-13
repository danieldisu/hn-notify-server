package com.danieldisu.hnnotify.framework.datasource

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.net.URI
import javax.sql.DataSource


@Configuration
@Profile("prod")
class HerokuDatasourceConfiguration {

    @Bean
    fun getHerokuDatasource(): DataSource {
        val dbUri = URI(System.getenv("DATABASE_URL"))

        val username = dbUri.userInfo.split(":").toTypedArray()[0]
        val password = dbUri.userInfo.split(":").toTypedArray()[1]
        val jdbcCompatibleDBUrl =
            "jdbc:postgresql://" + dbUri.host + ':' + dbUri.port + dbUri.path + "?sslmode=require"

        return DataSourceBuilder.create()
            .url(jdbcCompatibleDBUrl)
            .username(username)
            .password(password)
            .build()
    }

}
