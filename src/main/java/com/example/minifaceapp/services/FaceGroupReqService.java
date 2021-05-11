package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.api.v1.dtos.FaceGroupReqDTO;

public interface FaceGroupReqService extends CrudService<FaceGroupReqDTO, Long>{

	List<Long> findAllByFaceUserId(Long id);

}
