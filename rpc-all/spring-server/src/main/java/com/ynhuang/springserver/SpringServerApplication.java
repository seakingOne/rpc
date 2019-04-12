package com.ynhuang.springserver;

import com.ynhuang.server.RPCServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringServerApplication implements CommandLineRunner {

    @Autowired
    private RPCServer server;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringServerApplication.class);
        //app.setWebEnvironment(false);
        app.run(args);
    }

    @Override
    public void run(String... strings) throws Exception {
        server.run("127.0.0.1:9000");
    }
}
