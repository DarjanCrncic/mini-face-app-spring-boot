package com.example.minifaceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.minifaceapp.model.FaceFriendReq;
import com.example.minifaceapp.model.Status;

public interface FaceFriendReqRepository extends JpaRepository<FaceFriendReq, Long>{

	@Query("select distinct ffr.faceUserId from FaceFriendReq ffr where ffr.faceFriendId = ?1 and ffr.status = ?2")
	List<Long> getPendingFriendRequests(Long id, Status status);


}
