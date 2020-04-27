package repository;

import domain.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryProiectRepository<ID,E extends Entity<ID>> implements IRepository<ID, E> {
    private Map<ID,E> entities;

    public InMemoryProiectRepository() {
        entities = new HashMap<>();
    }

    @Override
    public E save(E entity){
        if(entity == null)
            throw new IllegalArgumentException("The Entity cannot be null");
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

