package com.braggbay101.dao;

import java.util.List;

import com.braggbay101.dao.GenericDAO;
import com.braggbay101.domain.Order;





public interface OrderDAO extends GenericDAO<Order, Integer> {
  
	List<Order> findAll();
	






}


