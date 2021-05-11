package com.example.minifaceapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.minifaceapp.model.FaceGroupReq;
import com.example.minifaceapp.model.Status;

public interface FaceGroupReqRepository extends JpaRepository<FaceGroupReq, Long>{

	@Query("select distinct fgr.groupId from FaceGroupReq fgr where fgr.userId = ?1 and fgr.status = ?2")
	List<Long> getPendingGroupRequests(Long id, Status status);

}
