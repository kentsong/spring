package com.kentsong.web.support.collection;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class RequestParams {
	private Map<String, String[]> paramMap = new HashMap<String, String[]>();
	private Map<String, List<FileItem>> paramFileMap;

	public void put(String key, String[] value) {
		this.paramMap.put(key, value);
	}

	public String[] get(String key) {
		if (this.paramMap.containsKey(key)) {
			return (String[]) this.paramMap.get(key);
		}
		String[] param = new String[1];
		param[0] = "";
		this.paramMap.put(key, param);

		return param;
	}

	public FileItem getFileParameter(String paramName) {
		return paramFileMap.get(paramName).get(0);
	}

	public List<FileItem> getFileParameters(String paramName) {
		return paramFileMap.get(paramName);
	}

	public Map<String, List<FileItem>> getParamFileMap() {
		return paramFileMap;
	}

	public void setParamFileMap(Map<String, List<FileItem>> paramFileMap) {
		this.paramFileMap = paramFileMap;
	}

	public String getParameter(String paramName) {
		return get(paramName)[0];
	}

	public String[] getParameters(String paramName) {
		return get(paramName);
	}

	private int parseInt(String source, String paramName) {
		int result = 0;
		if (StringUtils.isBlank(source)) {
			return result;
		}

		try {
			result = Integer.parseInt(source);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(paramName + "： 格式錯誤，必須為int");
		}
		return result;
	}

	public int getIntParameter(String paramName) {
		String strParam = getParameter(paramName);
		return parseInt(strParam, paramName);
	}

	public int[] getIntParameters(String paramName) {
		String[] strParams = getParameters(paramName);
		int[] intParams = new int[strParams.length];
		for (int i = 0; i < strParams.length; i++) {
			intParams[i] = parseInt(strParams[i], paramName);
		}

		return intParams;
	}

	private float parseFloat(String source, String paramName) {
		float result = 0.0F;
		try {
			result = Float.parseFloat(source);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(paramName + "： 格式錯誤，必須為float");
		}
		return result;
	}

	public float getFloatParameter(String paramName) {
		String strParam = getParameter(paramName);
		return parseFloat(strParam, paramName);
	}

	public float[] getFloatParameters(String paramName) {
		String[] strParams = getParameters(paramName);
		float[] floatParams = new float[strParams.length];
		for (int i = 0; i < strParams.length; i++) {
			floatParams[i] = parseFloat(strParams[i], paramName);
		}

		return floatParams;
	}
	
	private long parseLong(String source, String paramName){
		long result = 0L;
		try {
			result = Long.parseLong(source);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(paramName + "： 格式錯誤，必須為long");
		}
		return result;
	}
	
	public long getLongParameter(String paramName){
		String strParam = getParameter(paramName);
		return parseLong(strParam, paramName);
	}
	
	public long[] getLongParameters(String paramName) {
		String[] strParams = getParameters(paramName);
		long[] floatParams = new long[strParams.length];
		for (int i = 0; i < strParams.length; i++) {
			floatParams[i] = parseLong(strParams[i], paramName);
		}

		return floatParams;
	}

	private Date parseDate(String source, String paramName, String pattern) {
		if (StringUtils.isBlank(source)) {
			throw new RuntimeException("日期參數不可為空，參數:" + paramName + " is required");
		}

		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		try {
			if (source.length() > 8) {
				return LocalDateTime.parse(source, formatter).toDate();
			}
			return LocalDate.parse(source, formatter).toDate();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("日期格式錯誤, 輸入:" + source + ", 格式必須為:" + pattern);
		}

	}

	public Date getDateParameter(String paramName) {
		String strParam = getParameter(paramName);
		return parseDate(strParam, paramName, "yyyy/MM/dd");
	}

	public Date getDateParameter(String paramName, String pattern) {
		String strParam = getParameter(paramName);
		return parseDate(strParam, paramName, pattern);
	}

	public Date[] getDateParameters(String paramName) {
		return getDateParameters(paramName, "yyyy/MM/dd");
	}

	public Date[] getDateParameters(String paramName, String pattern) {
		String[] strParams = getParameters(paramName);
		Date[] dateParams = new Date[strParams.length];
		for (int i = 0; i < strParams.length; i++) {
			dateParams[i] = parseDate(strParams[i], paramName, pattern);
		}
		return dateParams;
	}

	public static void main(String[] args) {
		RequestParams paramMap = new RequestParams();
		paramMap.put("testParam", new String[] { "1987/11/08" });

		System.out.println(paramMap.getDateParameter("testParam"));
	}

}