package com.example.minifaceapp.services;

import java.util.List;

import com.example.minifaceapp.model.FaceFriendReq;

public interface FaceFriendReqService extends CrudService<FaceFriendReq, Long>{

	List<Long> findAllByFaceFriendId(Long id);

}
