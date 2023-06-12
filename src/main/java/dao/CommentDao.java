package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.CommentMapper;
import logic.Comment;

@Repository
public class CommentDao {
	@Autowired
	private SqlSessionTemplate template;
	
	private Map<String, Object> param = new HashMap<String, Object>();
	private final Class<CommentMapper> cls = CommentMapper.class;
	
	public int getMaxSeq(Integer num) {
		return template.getMapper(cls).getMaxSeq(num);
	}

	public void addComment(Integer num, int maxSeq, String writer, String pass, String content) {
		param.clear();
		param.put("num", num);
		param.put("seq", maxSeq);
		param.put("writer", writer);
		param.put("pass", pass);
		param.put("content", content);	
		template.getMapper(cls).insert(param);
	}

	public List<Comment> getCommList(Integer num) {
		return template.getMapper(cls).getCommList(num);
	}

	public void delComment(int num, int seq) {
		param.clear();
		param.put("num", num);
		param.put("seq", seq);
		template.getMapper(cls).delComment(param);
	}

	public void addComment(Comment comm) {
		template.getMapper(cls).insert(param);
	}

	public Comment selectComment(int num, int seq) {
		param.clear();
		param.put("num", num);
		param.put("seq", seq);
		return template.getMapper(cls).selectComment(param);
	}	
}
