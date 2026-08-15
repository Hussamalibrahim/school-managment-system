package com.SchoolManagementSystem.System.service.impl;

import com.SchoolManagementSystem.System.service.CrudService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
public abstract class BaseCrudServiceImpl<E, D> implements CrudService<D, Long> {

    protected final JpaRepository<E, Long> repository;

    protected abstract D toDto(E entity);

    protected abstract E toEntity(D dto);

    @Override
    public D save(D dto) {
        E entity = toEntity(dto);

        log.info("Before Saving entity: {}", entity);
        entity = repository.save(entity);
        log.info("After Saving entity: {}", entity);
        return toDto(entity);
    }

    @Override
    public D update(Long id, D dto) {

        E entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        E updatedEntity = toEntity(dto);

        setId(updatedEntity, id);

        updatedEntity = repository.save(updatedEntity);
        return toDto(updatedEntity);
    }

    @Override
    public D getById(Long id) {
        return repository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<D> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    private void setId(E entity, Long id) {
        try {
            var field = entity.getClass().getSuperclass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, id);
        } catch (Exception e) {
            throw new RuntimeException("User to set ID", e);
        }
    }
}