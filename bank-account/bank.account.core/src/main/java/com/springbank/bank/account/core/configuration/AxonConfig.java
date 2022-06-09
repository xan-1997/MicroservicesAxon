package com.springbank.bank.account.core.configuration;

import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.extensions.mongo.DefaultMongoTemplate;
import org.axonframework.extensions.mongo.MongoTemplate;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoFactory;
import org.axonframework.extensions.mongo.eventsourcing.eventstore.MongoSettingsFactory;
import org.axonframework.extensions.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.json.JacksonSerializer;
import org.axonframework.spring.config.AxonConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;

@Configuration
public class AxonConfig {
    @Value("${spring.data.mongodb.host:127.0.0.1}")
    private String mongoHost;
    @Value("${spring.data.mongodb.port:27017}")
    private int mongoPort;
    @Value("${spring.data.mongodb.database:bank}")
    private String mongoDatabase;

    @Bean
    public MongoClient mongo() {
        var mongoFactory = new MongoFactory();
        var mongoSettingsFactory = new MongoSettingsFactory();
        mongoSettingsFactory.setMongoAddresses(Collections.singletonList(new ServerAddress(mongoHost, mongoPort)));
        mongoFactory.setMongoClientSettings(mongoSettingsFactory.createMongoClientSettings());

        return mongoFactory.createMongo();
    }
//
    @Bean
    public MongoTemplate axonMongoTemplate() {
        return DefaultMongoTemplate.builder()
                .mongoDatabase(mongo(), mongoDatabase)
                .build();
    }

    @Bean
    public TokenStore tokenStore(Serializer serializer) {
        return MongoTokenStore.builder()
                .mongoTemplate(axonMongoTemplate())
                .serializer(serializer)
                .build();
    }

    @Bean
    public EventStorageEngine storageEngine(MongoClient mongoClient) {
        return MongoEventStorageEngine.builder()
                .mongoTemplate(DefaultMongoTemplate
                        .builder()
                        .mongoDatabase(mongoClient)
                        .build())
//                .eventSerializer(
//                        JacksonSerializer.builder()
//                                .defaultTyping()
//                                .build()
//                )
//                .snapshotSerializer(
//                        JacksonSerializer.builder()
//                                .defaultTyping()
//                                .build()
//                )
                .build();
    }

    @Bean
    public EmbeddedEventStore eventStore(EventStorageEngine storageEngine, AxonConfiguration configuration){
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(configuration.messageMonitor(EventStore.class, "eventStore")).build();
    }

// for solve xstream.security.ForbiddenClassException
    @Bean
    public XStream xStream() throws IOException {
        XStream xStream = new XStream(new StaxDriver());
//        xStream.addPermission(NoTypePermission.NONE);
        xStream.allowTypesByWildcard(new String[] {
                "com.springbank.bank.account.**",
                "com.springbank.user.**",
                "org.axonframework.**",
                "com.thoughtworks.xstream.**"
        });
        return xStream;
    }


//    @Bean
//    XStream xstream(){
//        XStream xstream = new XStream();
//        // clear out existing permissions and set own ones
//        xstream.addPermission(NoTypePermission.NONE);
//        // allow any type from the same package
//        xstream.allowTypesByWildcard(new String[] {
//                "com.ourpackages.**",
//                "org.axonframework.**",
//                "java.**",
//                "com.thoughtworks.xstream.**"
//        });
//
//        return xstream;
//    }
//
//    @Bean
//    @Primary
//    public Serializer serializer(@Qualifier("xStream") XStream xstream) {
//        return XStreamSerializer.builder().xStream(xstream).build();
//    }
}
