package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.Map;



public class CountryIdMap {
	
	private Map<Integer,Country> map;

	public CountryIdMap() {
		map=new HashMap<>();
	}
	
	public Country get(int cCode){
		return map.get(cCode);
	}
	
	public Country put(Country c){
		Country old=map.get(c.getcCode());
		if(old==null){
			map.put(c.getcCode(),c);
			return c;
		}
		else
			return old;
	}


}
