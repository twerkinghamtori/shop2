package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.BoardMapper;
import logic.Board;

@Repository
public class BoardDao {
	@Autowired
	private SqlSessionTemplate template;
	
	private Map<String, Object> param = new HashMap<String, Object>();
	private final Class<BoardMapper> cls = BoardMapper.class;		
	
	public void insert(Board board) {
		template.getMapper(cls).insert(board);
	}

	public int maxnum() {
		return template.getMapper(cls).maxNum();
	}

	public int count(String boardid, String searchType, String searchContent) {
		param.clear();
		param.put("boardid", boardid);
		if(!searchType.equals("")) {
		   param.put("searchType", searchType);
		   param.put("searchContent", searchContent);
		}
		return template.getMapper(cls).count(param);
	}

	public List<Board> list(Integer pageNum, int limit, String boardid, String searchType, String searchContent) {
		param.clear();		
		param.put("s", (pageNum-1)*limit);
		param.put("e", limit);
		param.put("boardid", boardid);
		if(!searchType.equals("")) {
		param.put("searchType", searchType);
		param.put("searchContent", "%" + searchContent + "%");
		}
		return template.getMapper(cls).select(param);
	}

	public Board selectOne(int num) {
		param.clear();
		param.put("num", num);
		return template.selectOne("dao.mapper.BoardMapper.select", param);
	}

	public void addReadCnt(int num) {
		param.clear();
		param.put("num", num);
		template.getMapper(cls).addReadCnt(param);		
	}

	public void insertReply(Board b) {
		template.getMapper(cls).insert(b);
	}

	public void grpStepAdd(Board b) {
		param.clear();
		param.put("grp", b.getGrp());
		param.put("grpstep", b.getGrpstep());
		template.getMapper(cls).updateGrpStep(param);
	}

	public void delete(Integer num) {
		param.clear();
		param.put("num", num);
		template.getMapper(cls).delete(param);
	}

	public List<Map<String, Object>> graph1(String id) {
		param.clear();
		param.put("id", id);
		return template.getMapper(cls).graph1(param);
	}

	public List<Map<String, Object>> graph2(String id) {
		param.clear();
		param.put("id", id);
		return template.getMapper(cls).graph2(param);
	}

}
