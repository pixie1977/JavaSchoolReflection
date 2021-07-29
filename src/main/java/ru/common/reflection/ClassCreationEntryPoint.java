package ru.common.reflection;

import ru.common.reflection.support.ByteArrayClassLoader;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.Arrays;
import javax.tools.*;

public class ClassCreationEntryPoint
{
	public static void main(String[] args) throws Exception
	{
		String dClassName = "DynamicGeneratedClass_"+System.currentTimeMillis();
		byte[] compileResult = compileIt(dClassName, createSources(dClassName).getBytes());
		ByteArrayClassLoader byteArrayClassLoader = new ByteArrayClassLoader();
		Class dClass = byteArrayClassLoader.loadClassFromBytes(dClassName, compileResult);
		Object dClassInstance = dClass.newInstance();
		Method method = dClass.getMethod("sayHello", null);
		method.invoke(dClassInstance,null);
	}

	public static String createSources(String dClassName) {
		try (StringWriter aWriter = new StringWriter()){
			aWriter.write("public class "+ dClassName + "{");
			aWriter.write(" public void sayHello() {");
			aWriter.write("     System.out.println(\"Hello world!\");");
			aWriter.write(" }}\n");
			aWriter.flush();
			return aWriter.toString();
		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public static byte[] compileIt(String fNamePrefix, byte[] bytes) throws IOException
	{
		final String srcFname = fNamePrefix+".java";
		try(FileOutputStream fos = new FileOutputStream(srcFname)) {
			fos.write(bytes);
			fos.flush();
		}

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
		Iterable<? extends JavaFileObject> compilationUnits = fileManager
			.getJavaFileObjectsFromStrings(Arrays.asList(srcFname));
		JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
		                                                     null, compilationUnits);
		boolean success = task.call();
		fileManager.close();
		System.out.println("Success: " + success);

		File dstFile = new File(fNamePrefix+".class");
		byte[] dstBytes = Files.readAllBytes(dstFile.toPath());

		return dstBytes;
	}
}
