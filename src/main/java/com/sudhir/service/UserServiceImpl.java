package com.sudhir.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sudhir.entity.CityEntity;
import com.sudhir.entity.CountryEntity;
import com.sudhir.entity.StateEntity;
import com.sudhir.entity.UserAccountEntity;
import com.sudhir.model.UserAccount;
import com.sudhir.repository.CityRepository;
import com.sudhir.repository.CountryRepository;
import com.sudhir.repository.StateRepository;
import com.sudhir.repository.UserAccountRepository;
import com.sudhir.util.EmailUtils;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private UserAccountRepository userAccRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Override
	public String loginCheck(String email, String pwd) {
		UserAccountEntity entity= userAccRepo.findByEmailAndPassword(email, pwd);
		if(entity==null) {
			return "Invalid Credentials";
		}else if(entity.getStatus().equals("LOCKED")) {
			return "Your Account is locked";
		}else {
			return "VALID";
		}
		
	}

	@Override
	public Map<Integer, String> loadCountries() {
		Map<Integer, String> countryMap = new HashMap<>();
		List<CountryEntity> entitylist = countryRepo.findAll();
		entitylist.forEach(entity -> {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		});
		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStatesByCountryId(Integer countryId) {

		Map<Integer, String> stateMap = new HashMap<>();
		List<StateEntity> entitylist = stateRepo.findByCountryId(countryId);
		entitylist.forEach(entity -> {
			stateMap.put(entity.getStateId(), entity.getStateName());
		});
		return stateMap;

	}

	@Override
	public Map<Integer, String> loadCitiesByStateId(Integer stateId) {
		Map<Integer, String> cityMap = new HashMap<>();
		List<CityEntity> entitylist = cityRepo.findByStateId(stateId);
		entitylist.forEach(entity -> {
			cityMap.put(entity.getCityId(), entity.getCityName());
		});
		return cityMap;
	}

	@Override
	public boolean isUniqueEmail(String email) {
		UserAccountEntity userAccountEntity = userAccRepo.findByEmail(email);
		return userAccountEntity != null ? false : true;
	}

	@Override
	public String generateTempPwd() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	@Override
	public boolean saveUserAccount(UserAccount userAccount) {
		userAccount.setStatus("LOCKED");
		userAccount.setPassword(generateTempPwd());
		UserAccountEntity userAccountEntity=new UserAccountEntity();
		BeanUtils.copyProperties(userAccount, userAccountEntity);
		UserAccountEntity saveEntity = userAccRepo.save(userAccountEntity);
		if(saveEntity.getUserId() !=null) {
			String to=userAccount.getEmail();
			String subject="Registeration Successfully";
			String body=getRegSuccMailBody(userAccount);
			sendRegSuccEmail(to, subject, body);
			return true;
		}
		return  false;
	}

	@Override
	public String getRegSuccMailBody(UserAccount userAccount) {
		String fileName = "UnlockAccountMailBodyTemplate.txt";
		List<String> replacedLines = null;
		String mailBody = null;
		try {
			Path path = Paths.get(fileName, "");
			Stream<String> lines = Files.lines(path);

			replacedLines = lines.map(line -> line.replace("{FNAME}", userAccount.getFirstName())
								 .replace("{LNAME}", userAccount.getLastName())
								 .replace("{TEMP-PWD}", userAccount.getPassword())
								 .replace("{EMAIL}", userAccount.getEmail()))
								 .collect(Collectors.toList());

			mailBody = String.join("", replacedLines);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;
	}

	@Override
	public boolean sendRegSuccEmail(String to, String subject, String body) {
		
		return emailUtils.sendEmail(to,subject,body);
	}

	@Override
	public boolean isTempPwdValid(String email, String tempPwd) {
		UserAccountEntity entity=userAccRepo.findByEmailAndPassword(email, tempPwd);
		return entity !=null ? true : false;
	}

	@Override
	public boolean unlockAccount(String email, String password) {
		UserAccountEntity entity=userAccRepo.findByEmail(email);
		entity.setStatus("UNLOCKED");
		entity.setPassword(password);
		UserAccountEntity saveEntity=userAccRepo.save(entity);
		return saveEntity.getUserId()!=null?true:false;
	}

	@Override
	public String recoverPassword(String email) {
		UserAccountEntity entity=userAccRepo.findByEmail(email);
		if(entity !=null) {
			UserAccount account=new UserAccount();
			BeanUtils.copyProperties(entity, account);
			String body=getRecoverPwdEmailBody(account);
			String to=account.getEmail();
			String subject="Password Recovery ";
			return sendPwdToEmail(to, subject, body);
		}else {
			return "FAIL";
		}
		
	}

	@Override
	public String getRecoverPwdEmailBody(UserAccount userAccount) {
		String fileName = "RecoverPasswordMailBodyTemplate.txt";
		List<String> replacedLines = null;
		String mailBody = null;
		try {
			Path path = Paths.get(fileName, "");
			Stream<String> lines = Files.lines(path);

			replacedLines = lines.map(line -> line.replace("{FNAME}", userAccount.getFirstName())
								 .replace("{LNAME}", userAccount.getLastName())
								 .replace("{PWD}", userAccount.getPassword()))
								 .collect(Collectors.toList());

			mailBody = String.join("", replacedLines);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mailBody;

	}

	@Override
	public String sendPwdToEmail(String to, String subject, String body) {
		boolean isSent=emailUtils.sendEmail(to, subject, body);
		if(isSent) {
			return "SUCCESS";
		}
		return "FAILED";
	}

}
