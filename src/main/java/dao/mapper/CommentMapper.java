package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import logic.Comment;

public interface CommentMapper {
	@Select("select ifnull(max(seq), 0) from comment where num=#{value}")
	int getMaxSeq(Integer num);

	@Insert("insert into comment (num, seq, writer, pass, content, regdate) values (#{num}, #{seq}, #{writer}, #{pass}, #{content}, now())")
	void insert(Map<String, Object> param);

	@Select("select * from comment where num=#{value}")
	List<Comment> getCommList(Integer num);

	@Delete("delete from comment where num=#{num} and seq=#{seq}")
	void delComment(Map<String, Object> param);

	@Select("select * from comment where num=#{num} and seq=#{seq}")
	Comment selectComment(Map<String, Object> param);
}
