package com.bjut.service;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;


public class SpecificationFindUtil {

	
	
	/**
	 * 创建分页请求.
	 */
	public static PageRequest buildPageRequest(int pageNumber, int pagzSize,String sortColoum) {
		Sort sort = null;
		//if ("auto".equals(sortType)) {
			//sort = new Sort(Direction.DESC, "id");
		//} else if ("title".equals(sortType)) {
			//sort = new Sort(Direction.ASC, "title");
		//}
        if(sortColoum!=null||!"".equals(sortColoum)){
        	sort = new  Sort(Direction.DESC, sortColoum);
        }
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}
	
	/**
	 * 创建动态查询条件组合.
	 */
	public static Specification buildSpecification(Map<String, Object> searchParams,
			Class clazz) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification spec = DynamicSpecifications.bySearchFilter(filters.values(), clazz);
		return spec;
	}
}
