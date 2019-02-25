package lk.ijse.dep.app.main;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("file:${user.dir}/resources/application.properties")
public class HibernateConfig {

    @Autowired
    private Environment env;

    @Bean(name="sessionFactory")
    public LocalSessionFactoryBean sessionFactoryBean(DataSource ds){
        LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
        lsfb.setDataSource(ds);
        lsfb.setHibernateProperties(hibernateProperties());
        lsfb.setPackagesToScan("lk.ijse.dep.app.entity");
        return lsfb;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl(env.getRequiredProperty("hibernate.connection.url"));
        ds.setUsername(env.getRequiredProperty("hibernate.connection.username"));
        ds.setPassword(env.getRequiredProperty("hibernate.connection.password"));
        return ds;
    }

    private Properties hibernateProperties(){
        Properties prop = new Properties();
        prop.put("hibernate.dialect",env.getRequiredProperty("hibernate.dialect"));
        prop.put("hibernate.show_sql",env.getRequiredProperty("hibernate.show_sql"));
        prop.put("hibernate.hbm2ddl.auto",env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return prop;
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sf){
        return new HibernateTransactionManager(sf);
    }

}

