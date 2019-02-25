package lk.ijse.dep.app.main;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Configurable
@ComponentScan("lk.ijse.dep.app")
@Import(HibernateConfig.class)
public class AppConfig {


}
