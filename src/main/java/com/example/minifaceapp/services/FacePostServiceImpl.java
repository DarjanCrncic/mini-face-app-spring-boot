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

import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.ReportDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.api.v1.mappers.FacePostDTOMapper;
import com.example.minifaceapp.api.v1.mappers.FacePostDTORowMapper;
import com.example.minifaceapp.model.FaceGroup;
import com.example.minifaceapp.model.FacePost;
import com.example.minifaceapp.model.FaceUser;
import com.example.minifaceapp.repositories.FaceGroupRepository;
import com.example.minifaceapp.repositories.FacePostRepository;
import com.example.minifaceapp.utils.ConcatSQLSearch;
import com.example.minifaceapp.utils.QueryHolder;
import com.example.minifaceapp.utils.WordDocument;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Service
public class FacePostServiceImpl implements FacePostService {

	private FacePostRepository facePostRepository;
	private FacePostDTOMapper facePostDTOMapper;
	private JdbcTemplate jdbcTemplate;
	private FaceGroupRepository faceGroupRepository;
	private JavaMailSender emailSender;
	private TaskExecutor taskExecutor;
	private QueryHolder queryHolder;

	public FacePostServiceImpl(FacePostRepository facePostRepository, FacePostDTOMapper facePostDTOMapper,
			JdbcTemplate jdbcTemplate, FaceGroupRepository faceGroupRepository, JavaMailSender emailSender,
			TaskExecutor taskExecutor, QueryHolder queryHolder) {
		this.facePostRepository = facePostRepository;
		this.facePostDTOMapper = facePostDTOMapper;
		this.jdbcTemplate = jdbcTemplate;
		this.faceGroupRepository = faceGroupRepository;
		this.emailSender = emailSender;
		this.taskExecutor = taskExecutor;
		this.queryHolder = queryHolder;
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
		
		String query = queryHolder.getQueries().get("vissible-posts") + placeholder + " order by fp.creation_time DESC " +		
				" OFFSET " + (searchDTO.getPageNumber() - 1) * searchDTO.getRowNumber() + " ROWS FETCH NEXT " + (searchDTO.getRowNumber() + 1) + " ROWS ONLY";
		
		return jdbcTemplate.query(query, new Object[] { id, id }, new int[] { Types.INTEGER, Types.INTEGER }, new FacePostDTORowMapper());
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
	public FacePostDTO saveGroupPost(FacePostDTO facePostDTO, Long groupId) {
		FaceGroup faceGroup = faceGroupRepository.findById(groupId).orElse(null);
		faceGroup.getPosts().add(facePostDTOMapper.facePostDTOToFacePostMapper(facePostDTO));
		faceGroupRepository.save(faceGroup);
		for (FaceUser user : faceGroup.getMembers()) {
			if(user.isNotify() && user.getId() != facePostDTO.getCreator().getId()) {
				taskExecutor.execute(new Runnable() {
					@Override
					public void run() {
						sendEmailNotification("minifaceapp@gmail.com", "New group post for " + user.getName(), facePostDTO.getTitle() + ":\n" + facePostDTO.getBody());
					}
				});
			}
		}

		return facePostDTO;
	}

	private void sendEmailNotification(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("minifaceapp@gmail.com");
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}

	@Override
	public String exportPDF(ReportDTO reportDTO, FaceUserDTO faceUserDTO) throws IOException {

		try (ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				Connection connection = jdbcTemplate.getDataSource().getConnection();) {

			JasperDesign jasperDesign = JRXmlLoader.load(ResourceUtils.getFile("classpath:PostReport.jrxml"));
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

	@Override
	public String exportWord(Long id) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
			WordDocument.createDocumentDocx4j(baos, this.findById(id));
			byte[] doc = baos.toByteArray();
			return Base64.getEncoder().encodeToString(doc);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
