package com.example.minifaceapp.services;

import com.example.minifaceapp.model.PostLike;

public interface PostLikeService extends CrudService<PostLike, Long> {

	int getPostLikeCount(Long id);

}
