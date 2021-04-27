package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.model.FaceUser;

public interface CrudService<T, ID> {
	List<FaceUser> findAll();
	
	T findById(ID id);
	
	T save(T object);
	
	void delete(T object);
	
	void deleteById(ID id);
}
