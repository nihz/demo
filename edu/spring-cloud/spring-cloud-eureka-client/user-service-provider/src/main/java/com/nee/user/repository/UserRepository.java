package com.nee.user.repository;

import com.nee.user.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private ConcurrentMap<Long, User>  repository = new ConcurrentHashMap<>();

    private static final AtomicLong idGenerator = new AtomicLong();

    public Collection<User> findAll() {
        return this.repository.values();
    }

    public boolean save(User user) {

        Long id = idGenerator.incrementAndGet();
        user.setId(id);

        return repository.putIfAbsent(id, user) == null;
    }
}
