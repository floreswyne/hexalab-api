package com.hexalab.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.entity.UserEntity;
import com.hexalab.exceptions.UserAlreadyExistsException;
import com.hexalab.exceptions.UserNotFoundException;
import com.hexalab.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public UserEntity save(UserEntity user) {
		this.prepareUserToSave(user);
		return userRepository.save(user);
	}

	@Transactional
	public List<UserEntity> saveAll(List<UserEntity> users) {
		users.forEach(this::prepareUserToSave);
		return userRepository.saveAll(users);
	}

	public UserEntity findById(UUID id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User with ID: " + id.toString() + " cannot be found!"));
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

	private void generateAccountBeforeSaving(UserEntity user) {
		user.getAccount().setAccountNumber("0001");
		user.getAccount().setAgency("000001");
		user.getAccount().setBalance(new BigDecimal("0.0"));
	}

	private void prepareUserToSave(UserEntity user) {
		if (existsByEmail(user.getEmail())) {
			throw new UserAlreadyExistsException("This e-mail: " + user.getEmail() + " is already in use");
		}

		if (existsByPhone(user.getPhone())) {
			throw new UserAlreadyExistsException("This phone: " + user.getPhone() + " is already in use");
		}

		if (existsByCpfCnpj(user.getCpfCnpj())) {
			throw new UserAlreadyExistsException("The CPF/CNPJ: " + user.getCpfCnpj() + " is already in use");
		}

		generateAccountBeforeSaving(user);
	}

}
