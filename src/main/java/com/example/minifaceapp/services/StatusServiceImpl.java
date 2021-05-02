package com.example.minifaceapp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.minifaceapp.model.Status;
import com.example.minifaceapp.repositories.StatusRepository;

@Service
public class StatusServiceImpl implements StatusService{
	
	private final StatusRepository statusRepository;
	
	public StatusServiceImpl(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	@Override
	public List<Status> findAll() {
		return statusRepository.findAll();
	}

	@Override
	public Status findById(Long id) {
		return statusRepository.findById(id).orElse(null);
	}

	@Override
	public Status save(Status status) {
		return statusRepository.save(status);
	}

	@Override
	public void delete(Status status) {
		statusRepository.delete(status);
	}

	@Override
	public void deleteById(Long id) {
		statusRepository.deleteById(id);
	}

}
