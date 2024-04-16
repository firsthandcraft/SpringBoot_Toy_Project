package board.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration // Configuration이 지정되어 있는 클래스를 자바 기반의 설정파일로 인식.
@PropertySource("classpath:/application.properties")
//@PropertySource: 해당 클래스에서 참조할 properties 파일의 위치를 지정
//@RequiredArgsConstructor : 이후 럼복 설치 하면 @autowired지우소 사용해보기 
public class DatabaseConfiguration  {
	@Autowired
	//@Autowired : 빈(Bean)으로 등록된 인스턴스(이하 객체)를 클래스에 주입하는 데 사용
	//@Autowired 이외에도 @Resource, @Inject 등이 존재
	private ApplicationContext applicationContext;
	/*ApplicationContext는 스프링 컨테이너(Spring Container) 중 하나
	컨테이너는 사전적 의미로 무언가를 담는 용기 또는 그릇을 의미함,
	스프링 컨테이너는 빈(Bean)의 생성과 사용, 관계, 생명 주기 등을 관리.*/
	
	
	@Bean// Configuration의 메서드에만 지정이 가능,
	// @Bean = 객체  Bean이 지정된 객체는 컨테이너에 의해 관리되는 빈(Bean)으로 등록
	@ConfigurationProperties(prefix = "spring.datasource.hikari")// (application.properties)에서 읽어올 설정 정보의 prefix 지정
	public HikariConfig hikariConfig() {
		return new HikariConfig();
		//커넥션 풀 라이브러리인 HikariCP 객체를 생성
	}
//	@Bean
//	public DataSource dataSource() {
//		// DataSource = DB와 관계된 커넥션 정보를 담고 있으며, 빈으로 등록하여 인자로 넘겨준다.
//		return new HikariDataSource(hikariConfig());
//		//데이터 소스 객체생성 
//		// 순수 JDBC는 SQL을 실행할 때 마다 커넥션을 맺고 끚는 I/O작업을 진행하는데,
//		// 이러한 작업은 상당한 리소스를 잡아 먹게된다.
//		// 이러한 문제의 해결 방법으로 커넥션 풀이 등장. 커넥션 풀은 커넥션 객체를 생성해두고,
//		// DB에 접근하는 사용자에게 미리 생성해 둔 커넥션을 제공 했다가 다시 돌려받는 방법이다.
//		// DataSource는 커넥션 풀을 지원하기 위한 인터페이스다.
//	}  
	@Bean
	@ConfigurationProperties(prefix="mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfig(){//마이바티스에 관련 설정을 가져온다.
		return new org.apache.ibatis.session.Configuration(); 
	}
	
	
	@Bean
	public DataSource dataSource() throws Exception {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		//System.out.println(dataSource.toString());
		return dataSource;
		/*앞에서 만든 히카리 CP 설파일을 이용해서 데이터 베이스와 연결하는 데이더 소스를 만든다. 여기서는 데이서 소스가 정상적으로 
		 * 생성되었는지 확인하기 위해 데이터 소스를 출력했다. */
		//HikariDataSource (HikariPool-3)이 콘솔에 나온다. 
	}
	
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mapper/**/sql-*.xml"));
		sqlSessionFactoryBean.setConfiguration(mybatisConfig());
		
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}