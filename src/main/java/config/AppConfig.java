package config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//Annotations for config needs a config class
@Configuration
// where to look for Spring Beans (components)
@ComponentScan("com.revature")
public class AppConfig {

}
