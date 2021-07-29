package ru.common.annotation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.common.annotation.support.CheckPasswordAnnotation;

@SpringBootApplication
@RestController
public class AnnotationEntryPoint
{
	static final String regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\\w\\s]).{6,}";

	public static void main(String[] args)
	{
		SpringApplication.run(AnnotationEntryPoint.class, args);
	}

	@GetMapping("/checkPassword")
	@CheckPasswordAnnotation(regexp)
	public String hello(@RequestParam(value = "password", defaultValue = "") String password) {
		return "saved";
	}
}
