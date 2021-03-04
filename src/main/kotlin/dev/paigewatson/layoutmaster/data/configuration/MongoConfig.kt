package dev.paigewatson.layoutmaster.data.configuration

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoConfig {
    @Value("\${spring.data.mongodb.uri:}")
    lateinit var connectionString: String

    @Value("\${spring.data.mongodb.database:}")
    lateinit var mongoDatabaseName: String

    @Bean
    fun mongo(): MongoClient {
        val connectionString = ConnectionString(connectionString)
        val clientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(clientSettings)
    }

    @Bean
    @Throws(Exception::class)
    fun template(): MongoTemplate {
        return MongoTemplate(mongo(), mongoDatabaseName)
    }
}
