package com.hexalab.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexalab.entity.AccountEntity;
import com.hexalab.entity.UserEntity;
import com.hexalab.exceptions.UserAlreadyExistsException;
import com.hexalab.exceptions.UserNotFoundException;
import com.hexalab.repository.AccountRepository;
import com.hexalab.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AccountRepository accountRepository;

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

	public UserEntity findByAccountNumberAndAgency(String accountNumber, String agency) {
		return userRepository.findByAccountNumberAndAgency(accountNumber, agency)
				.orElseThrow(() -> new UserNotFoundException(
						"User with Account number: " + accountNumber + " and Agency: " + agency + " cannot be found!"));
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
		AccountEntity lastAccount = accountRepository.findFirstByOrderByIdDesc().orElse(new AccountEntity());
		
		if (lastAccount.getId() == null) {
			lastAccount.setAccountNumber("0000");
			lastAccount.setAgency("000001");
		}
		
		Integer lastAccountNumber = Integer.valueOf(lastAccount.getAccountNumber());
		Integer lastAgency = Integer.valueOf(lastAccount.getAgency());
		
		if (lastAccountNumber >= 9999) {
			lastAgency++;
			lastAccountNumber = 1;
		} else {
			lastAccountNumber++;
		}
		
		String newAccountNumber = String.format("%04d", lastAccountNumber);
		String newAgency = String.format("%06d", lastAgency);
		
		user.getAccount().setAccountNumber(newAccountNumber);
		user.getAccount().setAgency(newAgency);
		user.getAccount().setBalance(new BigDecimal("0.0"));
		
		accountRepository.save(user.getAccount());
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
