
package com.bjut.service.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bjut.entity.User;
import com.bjut.repository.UserDao;
import com.bjut.service.ServiceException;
import com.bjut.service.account.ShiroDbRealm.ShiroUser;

import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

/**
 * 用户管理类.
 * 
 * @author yangkaiwen
 */

@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;

	private Clock clock = Clock.DEFAULT;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}
	
	public User getUser(Long id) {
		return userDao.findOne(id);
	}
	@Transactional(readOnly = true)
	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	
	

	

	
	
	
	public void registerUser(User user) {
		entryptPassword(user);
		user.setRoles("user");
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
	}
	
	public String addUser(User user) {
		
		if(userDao.findByLoginName(user.getLoginName())!=null){
			return "账号已经占用"; 
		}
		entryptPassword(user);
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
		return ""; 
	}
    
	public User registerRestUser(User user) {
	
		entryptRestPassword(user);
		user.setRoles("appUser");
		user.setRegisterDate(clock.getCurrentDate());

		return userDao.save(user);
	}
	

	
	
	
	
	
	public void updateUser(User user) {
		
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}
	
	public String updateUserPassword(long id,String password,String plainPassword){
		User user = this.getUser(id);
		byte[] hashPassword = Digests.sha1(password.getBytes(), Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
		String oldPassword = Encodes.encodeHex(hashPassword);
     	if(oldPassword.equals(user.getPassword())){
			user.setPlainPassword(plainPassword);
			if (StringUtils.isNotBlank(user.getPlainPassword())) {
				entryptPassword(user);
			}

			userDao.save(user);
			return "";
		}else{
			//user = userDao.findOne(user.getId());
			return"旧密码不正确！";
		}
	}
	   
	public User updateRestUser(User user) {
		
		return userDao.save(user);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 设定Rest类型的安全的密码
	 */
	private void entryptRestPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPassword().getBytes(), null, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	
	public void setClock(Clock clock) {
		this.clock = clock;
	}
}
