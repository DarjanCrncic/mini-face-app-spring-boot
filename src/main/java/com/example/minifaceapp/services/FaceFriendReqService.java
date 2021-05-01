package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.api.v1.dtos.FaceFriendReqDTO;

public interface FaceFriendReqService extends CrudService<FaceFriendReqDTO, Long>{

	List<Long> findAllByFaceFriendId(Long id);

}
