package com.braggbay101.dao;

import java.util.List;

import com.braggbay101.dao.GenericDAO;
import com.braggbay101.domain.Category;





public interface CategoryDAO extends GenericDAO<Category, Integer> {
  
	List<Category> findAll();
	






}


