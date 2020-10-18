package com.foo;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import org.junit.Test;


public class ReproducerTest {
    @Test
    public void reproduce() {
        Datastore datastore = Morphia.createDatastore(MongoClients.create(), "SaasGalaxy");
        datastore.getMapper().map(MyEntity.class);
        
        datastore.find(MyEntity.class).forEach(o -> {
            System.out.println(o);
        });
    }

}
