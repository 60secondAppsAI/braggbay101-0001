package com.braggbay101.dao;

import java.util.List;

import com.braggbay101.dao.GenericDAO;
import com.braggbay101.domain.Product;





public interface ProductDAO extends GenericDAO<Product, Integer> {
  
	List<Product> findAll();
	






}


