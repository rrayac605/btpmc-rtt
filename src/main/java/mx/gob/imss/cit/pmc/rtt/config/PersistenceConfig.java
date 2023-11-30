package mx.gob.imss.cit.pmc.rtt.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.Properties;
import javax.sql.DataSource;
import mx.gob.imss.cit.pmc.rtt.dto.PropertiesConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "mx.gob.imss.cit.pmc.rtt.repository" })
@EntityScan(basePackages = { "mx.gob.imss.cit.pmc.rtt.dto" })
public class PersistenceConfig {

    @Autowired
    PropertiesConfigurator propertiesConfigurator;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        vendorAdapter.setGenerateDdl( false );
        vendorAdapter.setShowSql( Boolean.TRUE );

        factory.setDataSource( dataSource() );
        factory.setJpaVendorAdapter( vendorAdapter );
        factory.setPackagesToScan( "mx.gob.imss.cit.pmc.rtt.dto" );

        Properties jpaProperties = new Properties();
        jpaProperties.put( "hibernate.hbm2ddl.auto", "none" );
        jpaProperties.put( "hibernate.dialect",  "org.hibernate.dialect.Oracle12cDialect");
        jpaProperties.put( "hibernate.format_sql", Boolean.TRUE );
        jpaProperties.put( "hibernate.show_sql", Boolean.TRUE);

        factory.setJpaProperties( jpaProperties );

        factory.afterPropertiesSet();
        factory.setLoadTimeWeaver( new InstrumentationLoadTimeWeaver() );

        return factory;
    }

    @Bean(name = "transactionManagerSchedule")
    public PlatformTransactionManager transactionManagerSchedule() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }

    @Bean()
    public DataSource dataSource() {
        try {
            ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(propertiesConfigurator.getDataSource().getDriverClassName());
            comboPooledDataSource.setJdbcUrl(propertiesConfigurator.getDataSource().getUrl());
            comboPooledDataSource.setUser(propertiesConfigurator.getDataSource().getUsername());
            comboPooledDataSource.setPassword(propertiesConfigurator.getDataSource().getPassword());
            comboPooledDataSource.setInitialPoolSize(10);
            comboPooledDataSource.setAcquireIncrement(10);
            comboPooledDataSource.setMinPoolSize(10);
            comboPooledDataSource.setMaxPoolSize(100);
            comboPooledDataSource.setMaxIdleTime(6000);
            comboPooledDataSource.setUnreturnedConnectionTimeout(10000);
            comboPooledDataSource.setAcquireRetryAttempts(10);
            return comboPooledDataSource;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
