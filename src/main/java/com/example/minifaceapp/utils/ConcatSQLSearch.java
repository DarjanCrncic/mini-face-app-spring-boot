package com.example.minifaceapp.utils;

import java.util.ArrayList;

import com.example.minifaceapp.api.v1.dtos.ReportDTO;
import com.example.minifaceapp.api.v1.dtos.SearchDTO;

public class ConcatSQLSearch {

	public static String concatenate(String originalQuery, SearchDTO searchDTO, String[] caseAll) {

		String wordPosition = searchDTO.getWordPosition();
		ArrayList<String> words = searchDTO.getSearchWords();
		ArrayList<String> filters = searchDTO.getSearchParams();
		String logicalOperand = searchDTO.getLogicalOperand();

		StringBuilder str = new StringBuilder();

		str.append("SELECT * FROM( ");
		str.append(originalQuery);
		str.append(") WHERE ");

		for (int i = 0; i < filters.size(); i++) {
			if (filters.get(i).toString().equals("all")) {
				str.append("(");
				for (int j = 0; j < caseAll.length; j++) {
					if (wordPosition.equals("back")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "' ");
					} else if (wordPosition.equals("front")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '" + words.get(i).toString().toUpperCase() + "%' ");
					} else {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "%' ");
					}
					if (j < caseAll.length - 1) {
						str.append("OR ");
					}
				}
				str.append(")");
			} else {
				if (wordPosition.equals("back")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%"
							+ words.get(i).toString().toUpperCase() + "' ");
				} else if (wordPosition.equals("front")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '" + words.get(i).toString().toUpperCase()
							+ "%' ");
				} else {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%"
							+ words.get(i).toString().toUpperCase() + "%' ");
				}
			}

			if (i < filters.size() - 1) {
				str.append(logicalOperand.toUpperCase() + " ");
			}
		}

		return str.toString();
	}

	public static String createSQLQueryAddition(String filter, SearchDTO searchDTO, String[] caseAll) {

		String wordPosition = searchDTO.getWordPosition();
		ArrayList<String> words = searchDTO.getSearchWords();
		ArrayList<String> filters = searchDTO.getSearchParams();
		String logicalOperand = searchDTO.getLogicalOperand();

		StringBuilder str = new StringBuilder();
		str.append(filter + " (");
		for (int i = 0; i < filters.size(); i++) {
			if (filters.get(i).toString().contains("+")) {
				String newFilter = "(" + filters.get(i).toString().replaceAll("\\+", " || ' ' || ") + ")";
				filters.remove(i);
				filters.add(i, newFilter);
			}
			if (filters.get(i).toString().equals("all")) {
				str.append("(");
				for (int j = 0; j < caseAll.length; j++) {
					if (wordPosition.equals("back")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "' ");
					} else if (wordPosition.equals("front")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '" + words.get(i).toString().toUpperCase() + "%' ");
					} else {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "%' ");
					}
					if (j < caseAll.length - 1) {
						str.append("OR ");
					}
				}
				str.append(")");
			} else {
				if (wordPosition.equals("back")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%"
							+ words.get(i).toString().toUpperCase() + "' ");
				} else if (wordPosition.equals("front")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '" + words.get(i).toString().toUpperCase()
							+ "%' ");
				} else {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%"
							+ words.get(i).toString().toUpperCase() + "%' ");
				}
			}

			if (i < filters.size() - 1) {
				str.append(logicalOperand.toUpperCase() + " ");
			}
		}
		str.append(")");
		return str.toString();
	}

	public static String generateReportQuery(ReportDTO reportDTO) {
		StringBuilder str = new StringBuilder();

		if (!reportDTO.getTitleKeyword().isEmpty() && !reportDTO.getTitleKeyword().isBlank()) {
			str.append(" AND ");
			str.append("UPPER(fp.title) LIKE '%" + reportDTO.getTitleKeyword().toUpperCase() + "%'");
		}

		for (int i = 0; i < reportDTO.getCommentOperations().size(); i++) {
			str.append(" AND comment_counter.value ");
			str.append(reportDTO.getCommentOperations().get(i) + " " + reportDTO.getCommentNumbers().get(i));
		}

		for (int i = 0; i < reportDTO.getLikeOperations().size(); i++) {
			str.append(" AND counter.value ");
			str.append(reportDTO.getLikeOperations().get(i) + " " + reportDTO.getLikeNumbers().get(i));
		}

		str.append(" AND ");
		str.append("(fp.creation_time BETWEEN to_date('" + reportDTO.getStartDate() + "', 'MM/DD/YYYY') AND to_date('"
				+ reportDTO.getEndDate() + "', 'MM/DD/YYYY'))");

		return str.toString();
	}

}
