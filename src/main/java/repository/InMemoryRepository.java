package repository;

import domain.Entity;
import validator.ValidationException;
import validator.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryRepository<ID,E extends Entity<ID>> implements CrudRepository<ID, E> {
    private Map<ID,E> entities;
    private Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public E save(E entity) throws ValidationException {
        if(entity == null)
            throw new IllegalArgumentException("The Entity cannot be null");
        validator.validate(entity);
        return entities.putIfAbsent(entity.getId(), entity);
    }

    @Override
    public E delete(ID id) {
        if(id == null)
            throw new IllegalArgumentException("The ID  cannot be null");
        return entities.remove(id);
    }

    @Override
    public E update(E entity){
        if(entity == null )
            throw new IllegalArgumentException("The Entity cannot be null");
        //
        validator.validate(entity);
        //
        E oldEntity = entities.get(entity.getId());
        if(oldEntity == null)
            return entity;
        else{
            entities.put(entity.getId(),entity);
            return null;
        }
    }

    @Override
    public E findOne(ID id) {
        if(id == null)
            throw new IllegalArgumentException("The ID cannot be null");
        return entities.get(id);
    }

    @Override
    public Iterable<E> findAll() {
        List<E> list= new ArrayList<>();
        for (ID key:entities.keySet()) {
            list.add(entities.get(key));
        }
        return list;
    }
}

