package com.braggbay101.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.braggbay101.dao.GenericDAO;
import com.braggbay101.service.GenericService;
import com.braggbay101.service.impl.GenericServiceImpl;
import com.braggbay101.dao.UserDAO;
import com.braggbay101.domain.User;
import com.braggbay101.dto.UserDTO;
import com.braggbay101.dto.UserSearchDTO;
import com.braggbay101.dto.UserPageDTO;
import com.braggbay101.dto.UserConvertCriteriaDTO;
import com.braggbay101.dto.common.RequestDTO;
import com.braggbay101.dto.common.ResultDTO;
import com.braggbay101.service.UserService;
import com.braggbay101.util.ControllerUtils;





@Service
public class UserServiceImpl extends GenericServiceImpl<User, Integer> implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserDAO userDao;

	


	@Override
	public GenericDAO<User, Integer> getDAO() {
		return (GenericDAO<User, Integer>) userDao;
	}
	
	public List<User> findAll () {
		List<User> users = userDao.findAll();
		
		return users;	
		
	}

	public ResultDTO addUser(UserDTO userDTO, RequestDTO requestDTO) {

		User user = new User();

		user.setUserId(userDTO.getUserId());


		user.setUsername(userDTO.getUsername());


		user.setEmail(userDTO.getEmail());


		user.setPassword(userDTO.getPassword());


		user.setAddress(userDTO.getAddress());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		user = userDao.save(user);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<User> getAllUsers(Pageable pageable) {
		return userDao.findAll(pageable);
	}

	public Page<User> getAllUsers(Specification<User> spec, Pageable pageable) {
		return userDao.findAll(spec, pageable);
	}

	public ResponseEntity<UserPageDTO> getUsers(UserSearchDTO userSearchDTO) {
	
			Integer userId = userSearchDTO.getUserId(); 
 			String username = userSearchDTO.getUsername(); 
 			String email = userSearchDTO.getEmail(); 
 			String password = userSearchDTO.getPassword(); 
 			String address = userSearchDTO.getAddress(); 
 			String sortBy = userSearchDTO.getSortBy();
			String sortOrder = userSearchDTO.getSortOrder();
			String searchQuery = userSearchDTO.getSearchQuery();
			Integer page = userSearchDTO.getPage();
			Integer size = userSearchDTO.getSize();

	        Specification<User> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, userId, "userId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, username, "username"); 
			
			spec = ControllerUtils.andIfNecessary(spec, email, "email"); 
			
			spec = ControllerUtils.andIfNecessary(spec, password, "password"); 
			
			spec = ControllerUtils.andIfNecessary(spec, address, "address"); 
			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("username")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("email")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("password")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("address")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<User> users = this.getAllUsers(spec, pageable);
		
		//System.out.println(String.valueOf(users.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(users.getTotalPages()));
		
		List<User> usersList = users.getContent();
		
		UserConvertCriteriaDTO convertCriteria = new UserConvertCriteriaDTO();
		List<UserDTO> userDTOs = this.convertUsersToUserDTOs(usersList,convertCriteria);
		
		UserPageDTO userPageDTO = new UserPageDTO();
		userPageDTO.setUsers(userDTOs);
		userPageDTO.setTotalElements(users.getTotalElements());
		return ResponseEntity.ok(userPageDTO);
	}

	public List<UserDTO> convertUsersToUserDTOs(List<User> users, UserConvertCriteriaDTO convertCriteria) {
		
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		
		for (User user : users) {
			userDTOs.add(convertUserToUserDTO(user,convertCriteria));
		}
		
		return userDTOs;

	}
	
	public UserDTO convertUserToUserDTO(User user, UserConvertCriteriaDTO convertCriteria) {
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setUserId(user.getUserId());

	
		userDTO.setUsername(user.getUsername());

	
		userDTO.setEmail(user.getEmail());

	
		userDTO.setPassword(user.getPassword());

	
		userDTO.setAddress(user.getAddress());

	

		
		return userDTO;
	}

	public ResultDTO updateUser(UserDTO userDTO, RequestDTO requestDTO) {
		
		User user = userDao.getById(userDTO.getUserId());

		user.setUserId(ControllerUtils.setValue(user.getUserId(), userDTO.getUserId()));

		user.setUsername(ControllerUtils.setValue(user.getUsername(), userDTO.getUsername()));

		user.setEmail(ControllerUtils.setValue(user.getEmail(), userDTO.getEmail()));

		user.setPassword(ControllerUtils.setValue(user.getPassword(), userDTO.getPassword()));

		user.setAddress(ControllerUtils.setValue(user.getAddress(), userDTO.getAddress()));



        user = userDao.save(user);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public UserDTO getUserDTOById(Integer userId) {
	
		User user = userDao.getById(userId);
			
		
		UserConvertCriteriaDTO convertCriteria = new UserConvertCriteriaDTO();
		return(this.convertUserToUserDTO(user,convertCriteria));
	}







}
