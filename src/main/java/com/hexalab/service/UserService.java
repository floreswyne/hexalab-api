package com.hexalab.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.entity.UserEntity;
import com.hexalab.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public UserEntity save(UserEntity user) {
		generateAccountWhenSaving(user);
		return userRepository.save(user);
	}
	
	@Transactional
	public List<UserEntity> saveAll(List<UserEntity> users) {
		return userRepository.saveAll(users);
	}
	
	public Optional<UserEntity> findById(UUID id) {
		return userRepository.findById(id);
	}
	
	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}
	
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public boolean existsByPhone(String phone) {
		return userRepository.existsByPhone(phone);
	}
	
	public boolean existsByCpfCnpj(String cpfCnpj) {
		return userRepository.existsByCpfCnpj(cpfCnpj);
	}
	
	private void generateAccountWhenSaving(UserEntity user) {
		user.getAccount().setAccountNumber("0001");
		user.getAccount().setAgency("000001");
		user.getAccount().setBalance(new BigDecimal("0.0"));
	}
	
}
