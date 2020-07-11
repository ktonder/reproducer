package com.foo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ReproducerTest {
    @Test
    public void reproduce() {
        final MongoClient mongoClient = provideMongoCLient();
        final Datastore datastore = datastore(mongoClient);
        this.clearOldData(datastore);

        // Create new entities
        final List<MyEntity> entities = Arrays.asList(createEntity(), createEntity());
        final List<MyEntity> saved = datastore.save(entities);

        // Fail here
        Assert.assertThat(saved.get(0).getVersion(), CoreMatchers.notNullValue());

        // Expected
        saved.stream().peek(entity -> entity.setDescription("Updatable")).forEach(datastore::save);
    }

    private MyEntity createEntity() {
        final MyEntity entity = new MyEntity();
        entity.setDescription("Created");
        return entity;
    }

    private MongoClient provideMongoCLient() {
        final String connectionUrl = "mongodb://awesome:thenewawesomecompanyontopoftheworld@localhost:27017/awesome";
        final ConnectionString connString = new ConnectionString(connectionUrl);
        final MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connString)
            .retryWrites(true)
            .build();
        return MongoClients.create(settings);
    }

    private Datastore datastore(final MongoClient mongoClient) {

        final Datastore datastore = Morphia.createDatastore(mongoClient, "awesome");
        datastore.getMapper().mapPackage("com.foo");
        datastore.ensureIndexes();

        return datastore;
    }

    private void clearOldData(final Datastore datastore) {
        datastore.find(MyEntity.class).iterator().forEachRemaining(datastore::delete);
    }
}
