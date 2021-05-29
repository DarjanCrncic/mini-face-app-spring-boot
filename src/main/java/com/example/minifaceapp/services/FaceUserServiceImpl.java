package com.example.minifaceapp.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FaceUserDTORowMapper;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceUserRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;
import com.example.minifaceapp.utils.QueryHolder;

@Service
public class FaceUserServiceImpl implements FaceUserService {

	private final FaceUserRepository faceUserRepository;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private FaceUserDTOMapper faceUserDTOMapper;
	private QueryHolder queryHolder;

	public FaceUserServiceImpl(FaceUserRepository faceUserRepository, JdbcTemplate jdbcTemplate,
			FaceUserDTOMapper faceUserDTOMapper, QueryHolder queryHolder) {
		this.faceUserRepository = faceUserRepository;
		this.jdbcTemplate = jdbcTemplate;
		this.faceUserDTOMapper = faceUserDTOMapper;
		this.queryHolder = queryHolder;
	}

	@Override
	@Transactional
	public FaceUserDTO save(FaceUserDTO faceUserDTO) {
		FaceUser faceUser = faceUserRepository.findById(faceUserDTO.getId()).orElse(null);
		if (faceUser != null) {
			faceUserDTO.setUsername(faceUser.getUsername());
			faceUserDTO.setEmail(faceUser.getEmail());
			faceUserDTOMapper.updateFaceUserFromDTO(faceUserDTO, faceUser);
			faceUser = faceUserRepository.save(faceUser);
		}
		return faceUserDTOMapper.faceUserToFaceUserDTOMapper(faceUser);
	}

	@Override
	public List<FaceUserDTO> findAll() {
		List<FaceUser> users = faceUserRepository.findAll();
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(users);
	}

	@Override
	public FaceUserDTO findById(Long id) {
		return faceUserDTOMapper.faceUserToFaceUserDTOMapper(faceUserRepository.findById(id).orElse(null));
	}

	@Override
	public FaceUser findByUsername(String username) {
		return faceUserRepository.findByUsername(username);
	}

	@Override
	public List<FaceUserDTO> searchFaceUsers(SearchDTO searchDTO, Long faceUserId) {
		String[] caseAll = { "FU.SURNAME", "FU.NAME" };

		String placeholder = "";

		if (!searchDTO.getSearchWords().get(0).isBlank()) {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll);
		}
		String id = Long.toString(faceUserId);

		String query = queryHolder.getQueries().get("search-users")
				+ placeholder + " order by upper(name), upper(surname) ";

		return jdbcTemplate.query(query, new Object[] { id, id, id },
				new int[] { Types.INTEGER, Types.INTEGER, Types.INTEGER }, new FaceUserDTORowMapper());

	}

	@Override
	public List<FaceUserDTO> findFriends(FaceUser faceUser) {
		List<FaceUser> users = faceUser.getFriends();
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(users);
	}

	@Override
	public List<FaceUserDTO> findByIdIn(List<Long> ids) {
		List<FaceUser> users = faceUserRepository.findByIdIn(ids);
		return faceUserDTOMapper.faceUserListToFaceUserDTOList(users);
	}

	@Override
	public void saveUserOnRegister(@Valid FaceUser faceUser) {
		faceUserRepository.save(faceUser);
	}

	@Override
	public void delete(FaceUserDTO object) {
	}

	@Override
	public void deleteById(Long id) {
	}

	@Override
	public FaceUserDTO saveImage(MultipartFile multipartFile, Long id) {
		  try {
	            FaceUser faceUser = faceUserRepository.findById(id).get();

	            Byte[] byteObjects = new Byte[multipartFile.getBytes().length];

	            int i = 0;

	            for (byte b : multipartFile.getBytes()){
	                byteObjects[i++] = b;
	            }

	            faceUser.setImage(byteObjects);
	            faceUserRepository.save(faceUser);
	            
	       } catch (IOException e) {
	            e.printStackTrace();
	       }
		  return null;
	}

	// for getting raw image
	@Override
	public void getImage(Long id, HttpServletResponse response) throws IOException {
		FaceUser faceUser = faceUserRepository.findById(id).get();

        if (faceUser.getImage() != null) {
            byte[] byteArray = new byte[faceUser.getImage().length];
            int i = 0;

            for (Byte wrappedByte : faceUser.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
	}

	// for getting image as base64 String 
	@Override
	public String getImageAsString(Long id) {
		Byte[] imageBytes = faceUserRepository.findById(id).get().getImage();
		
		if(imageBytes != null) {
			byte[] bytes = new byte[imageBytes.length];
			
			int j=0;
			for(Byte b: imageBytes)
			    bytes[j++] = b.byteValue();
			
			return Base64.getEncoder().encodeToString(bytes);
		}
		return null;
	}

	@Override
	public FaceUserDTO switchNotify(boolean notify, Long id) {
		FaceUser faceUser = faceUserRepository.findById(id).orElse(null);
		faceUser.setNotify(notify);
		return faceUserDTOMapper.faceUserToFaceUserDTOMapper(faceUserRepository.save(faceUser));
	}

	
}
