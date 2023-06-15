package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;

public class CountScheduler {
	private int cnt;
	/*
	 * cron
	 * 	 1. 특정시간, 주기적으로 프로그램을 수행하는 프로세스. 유닉스(멀티유저 기반 os) 기반.
	 * 	 2. 스프링에서는 cron기능을 Scheduler라고 함 (리눅스 에서는 crontab)
	 * cron 식
	 * 	"0/5 * * * * ?"
	 * 	 초 분 시 일 월 요일 (연도)
	 */
	//5초에 한번씩 실행
//	@Scheduled(cron="0/5 * * * * ?")
//	public void execute1() {
//		System.out.println("cnt : " + cnt++);
//	}
	
	//6월 14일 15시 10분에 메서드 실행
	@Scheduled(cron="0 8 15 14 JUN ?")
	public void execute2() {
		System.out.println("15:07 잠온다");
	}
	
	/*
	 * 1. 평일 아침 10시에 환율정보를 조회해서 db에 등록
	 * 2. exchange 테이블 생성
	 */
	//cron="0 0 10 ? * MON-FRI"
	@Scheduled(cron="10 0 10 * * ?")
	public void exchange() {
		System.out.println("환율 스케줄러 실행");
		Document doc = null;
		List<List<String>> trlist = new ArrayList<>();
		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
		String exdate = null;
		try {
			doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr");
			exdate = doc.select("p.table-unit").html().substring(8); //조회기준일	
			for(Element tr : trs) {
				List<String> tdlist = new ArrayList<String>();
				Elements tds = tr.select("td");
				for(Element td : tds) {
					tdlist.add(td.html());
				}
				if(tdlist.size()>0) trlist.add(tdlist);			
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		PreparedStatement pstmt = null;
		Connection con = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/gdudb", "gdu", "1234");	
			pstmt = con.prepareStatement("insert into exchange (code, name, primeamt, sellamt, buyamt, exdate) values (?,?,?,?,?,?)");
			for (List<String> tdlist : trlist) {
		        String code = tdlist.get(0);
		        String name = tdlist.get(1);
		        Float primeamt = Float.parseFloat(tdlist.get(4).replace(",", ""));
		        Float sellamt = Float.parseFloat(tdlist.get(2).replace(",", ""));
		        Float buyamt = Float.parseFloat(tdlist.get(3).replace(",", ""));		        
		        pstmt.setString(1, code);
		        pstmt.setString(2, name);
		        pstmt.setFloat(3, primeamt);
		        pstmt.setFloat(4, sellamt);
		        pstmt.setFloat(5, buyamt);
		        pstmt.setString(6, exdate);
		        pstmt.executeUpdate();
		    }
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
}
