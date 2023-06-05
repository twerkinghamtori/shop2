package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import dao.mapper.SaleMapper;
import logic.Sale;

@Repository
public class SaleDao {
	@Autowired
	private SqlSessionTemplate template; 
	
	private Map<String, Object> param = new HashMap<String, Object>();
	private final Class<SaleMapper> cls = SaleMapper.class;	

	public int getMaxSaleId() {
		return template.getMapper(cls).getMaxSaleId();
	}

	public void insert(Sale sale) {
		template.getMapper(cls).insert(sale);
	}

	public List<Sale> selectSaleList(String userid) {
		param.clear();
		param.put("userid", userid);
		return template.getMapper(cls).select(param);
	}

}
