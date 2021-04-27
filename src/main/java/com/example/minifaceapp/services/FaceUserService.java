package com.example.minifaceapp.services;

import com.example.minifaceapp.model.FaceUser;

public interface FaceUserService extends CrudService<FaceUser, Long>{

	FaceUser findByUsername(String username);

	FaceUser findByEmail(String email);

}
