package com.how2j.copy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.how2j.copy.mapper")
public class CopyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CopyApplication.class, args);
	}

}
