package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.Board;

public interface BoardMapper {
	String select = "select num, writer, pass, title, content, file1 fileurl, regdate, readcnt, grp, grplevel, grpstep, boardid from board";

	@Select("select ifnull(max(num), 0) from board")
	int maxNum();
	
	@Insert("insert into board values (#{num}, #{writer}, #{pass}, #{title}, #{content}, #{fileurl}, #{boardid}, now(), 0, #{grp}, #{grplevel}, #{grpstep})")
	void insert(Board board);

	@Select({"<script>",
		"select count(*) from board where boardid=#{boardid} ",
		"<if test='searchType!=null '>",
		" and ${searchType} like '%${searchContent}%' ",
		"</if>",
		"</script>"
	})
	int count(Map<String, Object> param);

	@Select({"<script>",
		select,
		"<if test='num != null'> where num=#{num} </if>",
		"<if test='boardid != null'> where boardid=#{boardid} </if>",
		"<if test='searchType != null '> and ${searchType} like #{searchContent} </if>",
		"<if test='e != null'> order by grp desc, grpstep asc limit #{s}, #{e} </if>",
		"</script>"
	})
	List<Board> select(Map<String, Object> param);

	@Update("update board set readcnt=readcnt+1 where num=#{num}")
	void addReadCnt(Map<String, Object> param);

	@Update("update board set grpstep=grpstep+1 where grp=#{grp} and grpstep>#{grpstep}")
	void updateGrpStep(Map<String, Object> param);

	@Delete("delete from board where num=#{num}")
	void delete(Map<String, Object> param);

	@Select("select writer, count(*) cnt from board where boardid=#{id} group by writer order by 2 desc limit 0,8")
	List<Map<String, Object>> graph1(Map<String, Object> param);
}
