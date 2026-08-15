package com.SchoolManagementSystem.system.service;

import java.util.List;

public interface CrudService<D,  ID> {
    //TODO should remove it
    D save(D dto);

    D update(ID id, D dto);

    D getById(ID id);

    List<D> getAll();

    void delete(ID id);
}