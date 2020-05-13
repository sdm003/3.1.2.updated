package web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web.service.UserService;

@SpringBootApplication
public class Main {

    @Autowired
    UserService userService;


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
