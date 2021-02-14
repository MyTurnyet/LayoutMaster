package dev.paigewatson.layoutmaster.data.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig
{

    @Value("${spring.data.mongodb.uri}")
    String connectionString;

    @Value("${spring.data.mongodb.database}")
    private String mongoDatabaseName;

    @Bean
    public MongoClient mongo()
    {
        final ConnectionString connectionString = new ConnectionString(this.connectionString);
        final MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(clientSettings);
    }

    @Bean
    public MongoTemplate template() throws Exception
    {
        return new MongoTemplate(mongo(), mongoDatabaseName);
    }
}
