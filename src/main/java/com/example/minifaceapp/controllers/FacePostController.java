package com.example.minifaceapp.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;
import com.example.minifaceapp.api.v1.dtos.FacePostSearchDTO;
import com.example.minifaceapp.api.v1.dtos.FaceUserDTO;
import com.example.minifaceapp.api.v1.dtos.ReportDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;
import com.example.minifaceapp.security.CustomUserDetails;
import com.example.minifaceapp.services.FacePostService;
import com.example.minifaceapp.services.FaceUserService;
import com.example.minifaceapp.services.PostTypeService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
@RequestMapping("/posts")
public class FacePostController {
	
	private final FaceUserService faceUserService;
	private final PostTypeService postTypeService;
	private final FacePostService facePostService;
	private final JdbcTemplate jdbcTemplate;

	public FacePostController(FaceUserService faceUserService, FacePostService facePostService, PostTypeService postTypeService, JdbcTemplate jdbcTemplate) {
		this.faceUserService = faceUserService;
		this.facePostService = facePostService;
		this.postTypeService = postTypeService;
		this.jdbcTemplate = jdbcTemplate;
	}

	@PostMapping(value = "/new/user", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FacePostDTO createNewUserPost(@RequestBody FacePostDTO facePostDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		facePostDTO.setCreator(faceUserService.findById(userDetails.getId()));
		facePostDTO.setType(postTypeService.findById(1L));
		return facePostService.save(facePostDTO);
	}
	
	@PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<FacePostSearchDTO> searchAllPosts(@RequestBody SearchDTO searchDTO, @AuthenticationPrincipal CustomUserDetails userDetails) {
		return facePostService.searchVissiblePosts(searchDTO, userDetails.getId());
	}
	
	@DeleteMapping(value = "/delete/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deletePost(@PathVariable Long id) {
		facePostService.deleteById(id);
	}
	
	@PostMapping(value = "edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FacePostDTO editPost(@PathVariable Long id, @RequestBody FacePostDTO facePostDTO) {
		return facePostService.edit(id, facePostDTO);
	}
	
	@PostMapping(value = "/new/group/{groupId}", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public FacePostDTO createNewGroupPost(@RequestBody FacePostDTO facePostDTO, @PathVariable Long groupId, @AuthenticationPrincipal CustomUserDetails userDetails) {
		facePostDTO.setCreator(faceUserService.findById(userDetails.getId()));
		facePostDTO.setType(postTypeService.findById(2L));
		facePostDTO = facePostService.save(facePostDTO);
		return facePostService.saveGroup(facePostDTO, groupId);
	}
	
	@DeleteMapping(value = "/delete/group/{groupId}/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteGroupPost(@PathVariable Long groupId, @PathVariable Long id) {
		facePostService.deleteGroupPostById(groupId, id);
	}
	
	@PostMapping(value = "/report", consumes = MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Object generatePdfReportOnPosts(@RequestBody ReportDTO reportDTO, @AuthenticationPrincipal CustomUserDetails userDetails, HttpServletResponse response) throws JRException, IOException, SQLException {
	
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=" + "report.pdf");

		System.out.println(reportDTO);
		String pdf = exportPDF(reportDTO, userDetails.getId());
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("data", pdf);
		return returnMap;
	}
		
	private String exportPDF(ReportDTO reportDTO, Long id) throws IOException{
		
		try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()){
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			JasperDesign jasperDesign = JRXmlLoader.load("D:\\eclipse-spring\\minifaceapp\\src\\main\\resources\\PostReport.jrxml");
			JasperReport report = JasperCompileManager.compileReport(jasperDesign);
			
			FaceUserDTO faceUserDTO = faceUserService.findById(id);
			String query = facePostService.generateReportQuery(reportDTO);
			
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("user_id", Integer.valueOf(faceUserDTO.getId().toString()));
			parameters.put("username", faceUserDTO.getUsername());
			parameters.put("user_full_name", faceUserDTO.getName() + " " + faceUserDTO.getSurname());
			parameters.put("placeholder", query);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			
			byte[] pdf = outStream.toByteArray();
			String base64Pdf = Base64.getEncoder().encodeToString(pdf);
			return base64Pdf;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
