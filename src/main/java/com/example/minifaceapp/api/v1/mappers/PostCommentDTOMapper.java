package com.example.minifaceapp.api.v1.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.minifaceapp.api.v1.dtos.PostCommentDTO;
import com.example.minifaceapp.model.PostComment;

@Mapper(componentModel = "spring", uses = FaceUserDTOMapper.class)
public interface PostCommentDTOMapper {

	PostCommentDTOMapper INSTANCE = Mappers.getMapper(PostCommentDTOMapper.class);
	
	@Mapping(target = "faceUserDTO", source = "faceUser")
	PostCommentDTO postCommentToPostCommentDTOMapper(PostComment postComment);
	
	@Mapping(target = "updateTime", ignore = true)
	@Mapping(target = "faceUser", source = "faceUserDTO")
	PostComment postCommentDTOToPostCommentMapper(PostCommentDTO postCommentDTO);
	
	List<PostCommentDTO> postCommentListToPostCommentDTOList(List<PostComment> list);
}
