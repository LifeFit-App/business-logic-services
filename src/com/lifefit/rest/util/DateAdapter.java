package com.lifefit.rest.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	 
	 // the desired format
	 private String pattern = "yyyy-MM-dd";
	
	 @Override
	 public String marshal(Date date) throws Exception {
		 return new SimpleDateFormat(pattern).format(date);
	 }
	
	 @Override
	 public Date unmarshal(String dateString) throws Exception {
		 return new SimpleDateFormat(pattern).parse(dateString);
	 }
}
