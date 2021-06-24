package com.example.minifaceapp.utils;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.minifaceapp.model.PostType;
import com.example.minifaceapp.model.Status;
import com.example.minifaceapp.repositories.PostTypeRepository;
import com.example.minifaceapp.repositories.StatusRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DataLoader implements ApplicationRunner {

    private PostTypeRepository postTypeRepository;
    private StatusRepository statusRepostory;

    @Override
	public void run(ApplicationArguments args) {
        if (postTypeRepository.findById(1L).orElse(null) == null) {
        	postTypeRepository.save(new PostType(1L, "user"));
        }
        if (postTypeRepository.findById(2L).orElse(null) == null) {
        	postTypeRepository.save(new PostType(2L, "group"));
        }
        
        if (statusRepostory.findById(1L).orElse(null) == null) {
        	statusRepostory.save(new Status(1L, "Pending"));
        }
        if (statusRepostory.findById(2L).orElse(null) == null) {
        	statusRepostory.save(new Status(2L, "Accepted"));
        }
        if (statusRepostory.findById(3L).orElse(null) == null) {
        	statusRepostory.save(new Status(3L, "Rejected"));
        }
    }
}