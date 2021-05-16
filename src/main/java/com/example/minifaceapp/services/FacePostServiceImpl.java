package com.example.minifaceapp.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.ReportDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.api.v1.mappers.FacePostDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FacePostDTORowMapper;
import com.example.minifaceapp.model.FaceGroup;
import com.example.minifaceapp.model.FacePost;
import com.example.minifaceapp.repositories.FaceGroupRepository;
import com.example.minifaceapp.repositories.FacePostRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class FacePostServiceImpl implements FacePostService {

	FacePostRepository facePostRepository;
	FacePostDTOMapper facePostDTOMapper;
	JdbcTemplate jdbcTemplate;
	FaceGroupRepository faceGroupRepository;

	public FacePostServiceImpl(FacePostRepository facePostRepository, FacePostDTOMapper facePostDTOMapper,
			JdbcTemplate jdbcTemplate, FaceGroupRepository faceGroupRepository) {
		this.facePostRepository = facePostRepository;
		this.facePostDTOMapper = facePostDTOMapper;
		this.jdbcTemplate = jdbcTemplate;
		this.faceGroupRepository = faceGroupRepository;
	}

	@Override
	public List<FacePostDTO> findAll() {
		return new ArrayList<>();
	}

	@Override
	public FacePostDTO findById(Long id) {
		return facePostDTOMapper.facePostToFacePostDTOMapper(facePostRepository.findById(id).orElse(null));
	}

	@Override
	public FacePostDTO save(FacePostDTO facePostDTO) {
		FacePost saved = facePostRepository.save(facePostDTOMapper.facePostDTOToFacePostMapper(facePostDTO));
		return facePostDTOMapper.facePostToFacePostDTOMapper(saved);
	}

	@Override
	public void delete(FacePostDTO facePostDTO) {
		facePostRepository.delete(facePostDTOMapper.facePostDTOToFacePostMapper(facePostDTO));
	}

	@Override
	public void deleteById(Long id) {
		FacePost facePost = facePostRepository.findById(id).orElse(null);
		if (facePost.getType().getId() == 1)
			facePostRepository.deleteById(id);

	}

	@Override
	public void deleteGroupPostById(Long groupId, Long id) {
		FaceGroup group = faceGroupRepository.findById(groupId).orElse(null);
		group.getPosts().remove(facePostRepository.findById(id).orElse(null));
		faceGroupRepository.save(group);
	}

	@Override
	public List<FacePostSearchDTO> searchVissiblePosts(SearchDTO searchDTO, Long faceUserId) {
		String[] caseAll = { "FP.TITLE", "FP.BODY", "FP.TYPE", "(FU.NAME || ' ' || FU.SURNAME)" };
		String placeholder = "";
		if (!searchDTO.getSearchWords().get(0).isBlank()) {
			placeholder = ConcatSQLSearch.createSQLQueryAddition("and", searchDTO, caseAll);
		}
		String id = Long.toString(faceUserId);

		String query = "select fp.id, fp.title, fp.body, fu.name, fu.surname, fu.image, fp.type, fp.creator_id, fp.creation_time, counter.value as likes from face_post fp "
				+ "inner join (select fp.id, count(distinct liker_id) as value from post_like pl right outer join face_post fp on fp.id = pl.post_id group by fp.id) counter on counter.id = fp.id "
				+ "inner join face_user fu on fp.creator_id = fu.id and "
				+ "((fp.creator_id in (select friend_user_id from face_friend where face_user_id = ?)) or fp.creator_id = ?) and fp.type = 1 "
				+ placeholder + " order by fp.creation_time DESC" + " OFFSET "
				+ (searchDTO.getPageNumber() - 1) * searchDTO.getRowNumber() + " ROWS FETCH NEXT "
				+ (searchDTO.getRowNumber() + 1) + " ROWS ONLY";

		return jdbcTemplate.query(query, new Object[] { id, id }, new int[] { Types.INTEGER, Types.INTEGER },
				new FacePostDTORowMapper());
	}

	@Override
	public FacePostDTO edit(Long id, FacePostDTO facePostDTO) {
		FacePost facePost = facePostRepository.findById(id).orElse(null);
		if (facePost != null) {
			facePost.setBody(facePostDTO.getBody());
			facePost.setTitle(facePostDTO.getTitle());
			facePostRepository.save(facePost);
		}

		return this.findById(id);
	}

	@Override
	public FacePostDTO saveGroup(FacePostDTO facePostDTO, Long groupId) {
		FaceGroup faceGroup = faceGroupRepository.findById(groupId).orElse(null);
		faceGroup.getPosts().add(facePostDTOMapper.facePostDTOToFacePostMapper(facePostDTO));
		faceGroupRepository.save(faceGroup);
		return facePostDTO;
	}

	@Override
	public String exportPDF(ReportDTO reportDTO, FaceUserDTO faceUserDTO) throws IOException {

		try (ByteArrayOutputStream outStream = new ByteArrayOutputStream(); Connection connection = jdbcTemplate.getDataSource().getConnection();) {
		
			JasperDesign jasperDesign = JRXmlLoader.load("D:\\eclipse-spring\\minifaceapp\\src\\main\\resources\\PostReport.jrxml");
			JasperReport report = JasperCompileManager.compileReport(jasperDesign);

			String query = ConcatSQLSearch.generateReportQuery(reportDTO);

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("user_id", Integer.valueOf(faceUserDTO.getId().toString()));
			parameters.put("username", faceUserDTO.getUsername());
			parameters.put("user_full_name", faceUserDTO.getName() + " " + faceUserDTO.getSurname());
			parameters.put("placeholder", query);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);

			byte[] pdf = outStream.toByteArray();
			return Base64.getEncoder().encodeToString(pdf);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
