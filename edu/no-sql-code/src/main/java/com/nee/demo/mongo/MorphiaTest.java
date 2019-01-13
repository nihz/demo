package com.nee.demo.mongo;

import com.mongodb.MongoClient;
import com.nee.demo.mongo.entity.User;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

public class MorphiaTest {

    public static void main(String[] args) {
        final Morphia morphia = new Morphia();
        Datastore ds = morphia.createDatastore(new MongoClient("192.168.1.5", 27017), "nee-demo");

        User user = new User();
        user.setAge(18);
        user.setName("nee");

        Key<User> key = ds.save(user);

        System.out.println(key);
    }
}
