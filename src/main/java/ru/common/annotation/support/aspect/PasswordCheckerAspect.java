package ru.common.annotation.support.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.common.annotation.support.CheckPasswordAnnotation;

import java.util.regex.Pattern;

@Aspect
@Component
public class PasswordCheckerAspect {
	@Pointcut("@annotation(pAnnotation)")
	public void callAt(CheckPasswordAnnotation pAnnotation) {}

	@Around("callAt(pAnnotation)")
	public Object around(ProceedingJoinPoint pjp, CheckPasswordAnnotation pAnnotation) throws Throwable {
		Object[] args = pjp.getArgs();
		if(args==null || args.length==0){ throw new RuntimeException("arg array cannot be null or empty!"); }
		Pattern pattern = Pattern.compile(pAnnotation.value());
		boolean check = pattern.matcher(args[0].toString()).find();
		if (check){ return pjp.proceed();}
		return "Incorrect value";
	}
}
