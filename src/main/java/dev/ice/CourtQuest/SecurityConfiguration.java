package dev.ice.CourtQuest;


import com.vaadin.flow.spring.security.VaadinWebSecurity;
import dev.ice.CourtQuest.views.LoginView;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

//    @Bean
//    public UserService userService(){
//        return new UserService();
//    }
}