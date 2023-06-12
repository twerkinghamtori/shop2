package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.User;

public interface UserMapper {
	@Insert("insert into usersecurity (userid, password, username, phoneno, postcode, address, email, birthday) values (#{userid}, #{password}, #{username}, #{phoneno}, #{postcode}, #{address}, #{email}, #{birthday})")
	void userinsert(User user);

	@Select({"<script> ",
		"select * from usersecurity ",
		"<if test='userid != null'> ",
		"where userid=#{userid} ",
		"</if>",
		"<if test='idchks != null'>",
		"where userid in ",
		"<foreach collection='idchks' item='idchk' separator=',' open='(' close=')'>#{idchk}</foreach>",
		"</if>",
		"</script> "
	})
	List<User> select(Map<String, Object> param);

	@Update("update usersecurity set username=#{username}, phoneno=#{phoneno}, postcode=#{postcode}, address=#{address}, email=#{email}, birthday=#{birthday}  where userid=#{userid}")
	void update(User user);

	@Delete("delete from usersecurity where userid=#{userid}")
	void delete(Map<String, Object> param);

	@Update("update usersecurity set password=#{password} where userid=#{userid}")
	void changePass(Map<String, Object> param);

	@Select("select userid from usersecurity where email=#{email} and phoneno=#{phoneno}")
	String searchId(Map<String, Object> param);

	@Select("select password from usersecurity where userid=#{userid} and email=#{email} and phoneno=#{phoneno}")
	String searchPw(Map<String, Object> param);

	@Select("select * from usersecurity where phoneno=#{phoneno}")
	List<User> selectUserPhoneno(String phoneno);

//	@Select({"<script>",
//		"select * from useraccount ",
//		"<foreach collection='idchks' item='idchk' separator=',' open='where userid in (' close=')'>#{idchk}</foreach>",
//		"</script>"
//	})
//	List<User> list(Map<String, Object> param);

}
