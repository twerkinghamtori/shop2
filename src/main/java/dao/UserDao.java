package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dao.mapper.SaleMapper;
import dao.mapper.UserMapper;
import logic.User;

@Repository
public class UserDao {
	@Autowired
	private SqlSessionTemplate template; 
	
	private Map<String, Object> param = new HashMap<String, Object>();
	private final Class<UserMapper> cls = UserMapper.class;	
	
	public void userinsert(User user) {
		template.getMapper(cls).userinsert(user);
	}
	
	public List<User> selectUserAll() {
		param.clear();
		return template.getMapper(cls).select(param);
	}

	public User selectUserOne(String userid) {
		param.clear();
		param.put("userid", userid);
		return template.selectOne("dao.mapper.UserMapper.select",param);
	}

	public void userUpdate(User user) {
		template.getMapper(cls).update(user);
	}

	public void userDelete(String userid) {
		param.clear();
		param.put("userid", userid);
		template.getMapper(cls).delete(param);
	}

	public void changePass(String chgpass, String userid) {
		param.clear();
		param.put("password", chgpass);
		param.put("userid", userid);
		template.getMapper(cls).changePass(param);
	}	
	
	public List<User> list(String[] idchks) {
		param.clear();
		param.put("idchks", idchks);
		return template.getMapper(cls).select(param);
	}

	public String searchId(String email, String phoneno) {
		param.clear();
		param.put("email", email);
		param.put("phoneno", phoneno);		
		return template.getMapper(cls).searchId(param);
	}

	public String searchPw(String userid, String email, String phoneno) {
		param.clear();
		param.put("userid", userid);
		param.put("email", email);
		param.put("phoneno", phoneno);		
		return template.getMapper(cls).searchPw(param);
	}

	public List<User> selectUserPhoneno(String phoneno) {
		param.clear();
		param.put("phoneno", phoneno);
		return template.getMapper(cls).selectUserPhoneno(phoneno);
	}

}
