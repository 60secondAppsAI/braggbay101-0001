package com.braggbay101.dao;

import java.util.List;

import com.braggbay101.dao.GenericDAO;
import com.braggbay101.domain.OrderItem;





public interface OrderItemDAO extends GenericDAO<OrderItem, Integer> {
  
	List<OrderItem> findAll();
	






}


