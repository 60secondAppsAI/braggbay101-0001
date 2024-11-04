package com.braggbay101.dao;

import java.util.List;

import com.braggbay101.dao.GenericDAO;
import com.braggbay101.domain.User;

import java.util.Optional;




public interface UserDAO extends GenericDAO<User, Integer> {
  
	List<User> findAll();
	






}


