package com.braggbay101.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbay101.domain.Category;
import com.braggbay101.dto.CategoryDTO;
import com.braggbay101.dto.CategorySearchDTO;
import com.braggbay101.dto.CategoryPageDTO;
import com.braggbay101.dto.CategoryConvertCriteriaDTO;
import com.braggbay101.service.GenericService;
import com.braggbay101.dto.common.RequestDTO;
import com.braggbay101.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CategoryService extends GenericService<Category, Integer> {

	List<Category> findAll();

	ResultDTO addCategory(CategoryDTO categoryDTO, RequestDTO requestDTO);

	ResultDTO updateCategory(CategoryDTO categoryDTO, RequestDTO requestDTO);

    Page<Category> getAllCategorys(Pageable pageable);

    Page<Category> getAllCategorys(Specification<Category> spec, Pageable pageable);

	ResponseEntity<CategoryPageDTO> getCategorys(CategorySearchDTO categorySearchDTO);
	
	List<CategoryDTO> convertCategorysToCategoryDTOs(List<Category> categorys, CategoryConvertCriteriaDTO convertCriteria);

	CategoryDTO getCategoryDTOById(Integer categoryId);







}





