package com.nee.demo.mongo.framework;

/**
 * Created by Tom on 2018/9/5.
 */
public class EntityOperation<T> {
    public Class<T> entityClass = null;

    public EntityOperation(Class<T> entityClass){
        this.entityClass = entityClass;
    }
}
