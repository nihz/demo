package com.nee.demo.mongo.framework;

import com.nee.demo.mongo.framework.utils.GenericsUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.Serializable;
import java.util.List;

public abstract class BaseDaoSupport<T extends Serializable, PK extends Serializable> {

    private MongoTemplate mongoTemplate;
    private EntityOperation op;

    public BaseDaoSupport() {
        Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass(), 0);
        op = new EntityOperation<>(entityClass);
    }

    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected abstract String getPKColumn();

    protected List<T> query() {
        return null;
    }


}
