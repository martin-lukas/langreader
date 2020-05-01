package net.langreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class LangReaderApplication {
	public static void main(String[] args) {
		SpringApplication.run(LangReaderApplication.class, args);
	}
}
