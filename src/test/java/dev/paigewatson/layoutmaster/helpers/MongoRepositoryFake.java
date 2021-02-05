package dev.paigewatson.layoutmaster.helpers;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MongoRepositoryFake<T> implements MongoRepository<T, String>
{
    public T savedEntity;
    public List<T> returnedValues = Collections.emptyList();

    @Override
    public <S extends T> S save(S entity)
    {
        savedEntity = entity;
        return entity;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> entities)
    {
        return null;
    }

    @Override
    public Optional<T> findById(String s)
    {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s)
    {
        return false;
    }

    @Override
    public List<T> findAll()
    {
        return returnedValues;
    }

    @Override
    public Iterable<T> findAllById(Iterable<String> strings)
    {
        return null;
    }

    @Override
    public long count()
    {
        return 0;
    }

    @Override
    public void deleteById(String s)
    {

    }

    @Override
    public void delete(T entity)
    {

    }

    @Override
    public void deleteAll(Iterable<? extends T> entities)
    {

    }

    @Override
    public void deleteAll()
    {

    }

    @Override
    public List<T> findAll(Sort sort)
    {
        return null;
    }

    @Override
    public Page<T> findAll(Pageable pageable)
    {
        return null;
    }

    @Override
    public <S extends T> S insert(S entity)
    {
        this.savedEntity = entity;
        return entity;
    }

    @Override
    public <S extends T> List<S> insert(Iterable<S> entities)
    {
        return null;
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example)
    {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example)
    {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort)
    {
        return null;
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable)
    {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example)
    {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example)
    {
        return false;
    }

    public void setReturnedValuesList(List returnedValues)
    {
        this.returnedValues = returnedValues;
    }

    public T savedEntity()
    {
        return savedEntity;
    }
}
