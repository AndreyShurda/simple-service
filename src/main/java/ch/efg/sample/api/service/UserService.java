package ch.efg.sample.api.service;

import ch.efg.sample.api.model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static lombok.Lombok.checkNotNull;

@Repository
@Transactional(readOnly = true)
public class UserService implements IUserService<User, String> {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public List<User> findAll() {
        return em.createQuery("Select u from User u").getResultList();
    }

    @Transactional
    @Override
    public List<User> findById(String s) {
        return em.createQuery("Select u from User u where id=" + s).getResultList();
    }

    @Transactional
    @Override
    public <S extends User> S save(S var1) {
        if (var1.getId() == null) {
            em.persist(var1);
        } else {
            em.merge(var1);
        }

        return var1;
    }

    @Transactional
    @Override
    public <S extends User> List<S> saveAll(Iterable<S> var1) {
        checkNotNull(var1, "The given Iterable of entities not be null!");
        List<S> result = new ArrayList<>();

        for (S entity : var1) {
            result.add(save(entity));
        }

        return result;
    }

    @Transactional
    @Override
    public User delete(String var1) {
        checkNotNull(var1, "Id must not be null");

        User user = em.find(User.class, var1);

        em.remove(user);

        return user;
    }

    @Transactional
    @Override
    public Map<String, List<User>> findAllGroupByGroupId() {
        return findAll().stream()
                .collect(Collectors.groupingBy(User::getGroupId));
    }
}
