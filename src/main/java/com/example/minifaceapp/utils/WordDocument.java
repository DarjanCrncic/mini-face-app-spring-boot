package com.example.minifaceapp.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.model.fields.merge.MailMerger.OutputField;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.util.ResourceUtils;

import com.example.minifaceapp.api.v1.dtos.FacePostDTO;

public class WordDocument {
	public static void createDocumentDocx4j(ByteArrayOutputStream baos, FacePostDTO facePost) {
		try {
			//File file = new File("D:\\\\eclipse-spring\\\\minifaceapp\\\\src\\\\main\\\\resources\\\\postTemplate.docx");
			File file = ResourceUtils.getFile("classpath:postTemplate.docx");
			WordprocessingMLPackage wordPackage = WordprocessingMLPackage.load(file);
		
			MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
			
			Map<DataFieldName, String> variables = new HashMap<>();
			variables.put(new DataFieldName("title"), facePost.getTitle());
			variables.put(new DataFieldName("body"), facePost.getBody());
			variables.put(new DataFieldName("name"), facePost.getCreator().getName() + " " + facePost.getCreator().getSurname());
			variables.put(new DataFieldName("id"), Long.toString(facePost.getId()));
			variables.put(new DataFieldName("creation_time"), facePost.getCreationTime().toString());
			variables.put(new DataFieldName("likes"), Integer.toString(facePost.getLikes().size()));
			variables.put(new DataFieldName("comments"), Integer.toString(facePost.getComments().size()));

			MailMerger.setMERGEFIELDInOutput(OutputField.KEEP_MERGEFIELD);
			MailMerger.performMerge(wordPackage, variables, true);
			
			for(int i=0; i<facePost.getComments().size(); i++) {
				mainDocumentPart.addStyledParagraphOfText("CommentTitle" , facePost.getComments().get(i).getFaceUser().getName() + " " +  
						facePost.getComments().get(i).getFaceUser().getSurname());
				mainDocumentPart.addStyledParagraphOfText("CommentBody", facePost.getComments().get(i).getBody());
				mainDocumentPart.addStyledParagraphOfText("CommentTime", facePost.getComments().get(i).getCreationTime().toString());
			}
			
			wordPackage.save(baos);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
