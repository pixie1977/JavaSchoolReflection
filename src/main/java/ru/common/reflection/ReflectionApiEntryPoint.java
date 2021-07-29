package ru.common.reflection;

import ru.common.reflection.support.MyClassExample;

import java.io.PrintStream;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Date;

public class ReflectionApiEntryPoint
{
	public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException
	{
		PrintStream sout = System.out;

		sout.println("Simple class MyClassExample");
		Class myClass = Class.forName("ru.common.reflection.support.MyClassExample");
		sout.println("getName -> " + myClass.getName());
		sout.println("getSimpleName -> " +myClass.getSimpleName());
		sout.println("getCanonicalName -> " +myClass.getCanonicalName());
		sout.println("getTypeName -> " + myClass.getTypeName());

		sout.println("============================================");

		sout.println("Array of classes MyClassExample");
		MyClassExample[] arrayOfMyClasses = new MyClassExample[1];
		sout.println("getName -> " +arrayOfMyClasses.getClass().getName());
		sout.println("getSimpleName -> " +arrayOfMyClasses.getClass().getSimpleName());
		sout.println("getCanonicalName -> " +arrayOfMyClasses.getClass().getCanonicalName());
		sout.println("getTypeName -> " +arrayOfMyClasses.getClass().getTypeName());
		sout.println("only for arrays print type name of the component: getComponentType.getName -> " + arrayOfMyClasses.getClass().getComponentType().getName());

		sout.println("============================================");
		sout.println("Package for class MyClassExample");
		Package myPackage = MyClassExample.class.getPackage();
		sout.println("getName -> " +myPackage.getName());
		sout.println("getImplementationVendor -> " +myPackage.getImplementationVendor());
		sout.println("getDeclaredAnnotations length -> " +myPackage.getDeclaredAnnotations().length);
		sout.println("getSpecificationTitle -> " +myPackage.getSpecificationTitle());
		sout.println("isSealed -> " +myPackage.isSealed());

		sout.println("============================================");
		sout.println("Constructor for class MyClassExample");
		Constructor[] constructors = MyClassExample.class.getConstructors();
		sout.println("constructors length -> " +constructors.length);
		sout.println("constructor 0 -> " +constructors[0].getName());
		sout.println("constructor 0 param count -> " +constructors[0].getParameterCount());
		Class[] paramTypes = constructors[0].getParameterTypes();
		String paramTypesAsString = Arrays.asList(paramTypes).stream()
			.map(Class::getName)
			.reduce((s1, s2) -> String.join(",", s1, s2))
			.get();
		sout.println("constructor 0 param types -> " + paramTypesAsString);
		Constructor constructor = MyClassExample.class.getConstructor(new Class[]{String.class, Date.class});
		sout.println("constructor with String & Date param types -> " + constructor.getName());

		MyClassExample myObject = (MyClassExample) constructor.newInstance("Ivan", new Date());
		sout.println("object has been created -> " + myObject);

		//Constructor privateConstructor = MyClassExample.class.getConstructor(new Class[]{String.class});
		Constructor privateConstructor = MyClassExample.class.getDeclaredConstructor(new Class[]{String.class});
		sout.println("private constructor with String param type -> " + privateConstructor.getName());
		privateConstructor.setAccessible(true);
		MyClassExample myObjectFromPrivateConstructor = (MyClassExample) privateConstructor.newInstance("Ivan");
		sout.println("object from private constructor has been created -> " + myObjectFromPrivateConstructor);

		sout.println("============================================");
		Field[] fields = MyClassExample.class.getFields();
		String fieldNamesStr = Arrays.asList(fields).stream()
			.map(Field::getName)
			.reduce((s1, s2) -> String.join(",", s1, s2))
			.get();
		sout.println("object field names -> " + fieldNamesStr);

		Field field = MyClassExample.class.getField("name");
		Object fieldValue = field.get(myObject);
		sout.println("object field \""+ field.getName() + "\" value -> " + fieldValue);

		field.setAccessible(true);
		field.set(myObject, "Петр");
		fieldValue = field.get(myObject);
		sout.println("object field \""+ field.getName() + "\" NEW value -> " + fieldValue);

		Field staticField = MyClassExample.class.getField("DESC");
		fieldValue = staticField.get(MyClassExample.class);
		sout.println("object final static field \""+ staticField.getName() + "\" value -> " + fieldValue);
		staticField.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(staticField, staticField.getModifiers() & ~Modifier.FINAL);
		staticField.set(null, "Desc2");
		fieldValue = staticField.get(MyClassExample.class);
		sout.println("object final static field \""+ staticField.getName() + "\" NEW value -> " + fieldValue);

		sout.println("============================================");
		Method[] methods = MyClassExample.class.getMethods();
		String methodNamesStr = Arrays.asList(methods).stream()
			.map(Method::getName)
			.reduce((s1, s2) -> String.join(",", s1, s2))
			.get();
		sout.println("object method names -> " + methodNamesStr);

		Method methodGet = MyClassExample.class.getMethod("getTimestamp", null);
		sout.println("object method getter -> " + methodGet.getName());
		Method methodSet = MyClassExample.class.getMethod("setTimestamp", String.class);
		sout.println("object method setter -> " + methodSet.getName());

		sout.println("object timestamp old value -> " + myObject.getTimestamp());
		Method method = myObject.getClass().getDeclaredMethod("setTimestamp", String.class);
		method.invoke(myObject, "11111");
		sout.println("object timestamp NEW value -> " + myObject.getTimestamp());

		Method[] declaredMethods = MyClassExample.class.getDeclaredMethods();
		methodSet = Arrays.stream(declaredMethods)
			.filter(methodItem -> methodItem.getName().equals("setTimestamp"))
			.findAny()
			.orElse(null);
		Class[] parameterTypes = methodSet.getParameterTypes();
		Class returnValueType = methodSet.getReturnType();
		methodSet = MyClassExample.class.getDeclaredMethod("setTimestamp", parameterTypes);
		methodSet.invoke(myObject, "11111");


		Method privateMethodSet = Arrays.stream(declaredMethods)
			.filter(methodItem -> methodItem.getName().equals("setTimestampAndReturnValue"))
			.findAny()
			.orElse(null);
		Class[] privateMethodParameterTypes = privateMethodSet.getParameterTypes();
		Class privateMethodReturnValueType = privateMethodSet.getReturnType();
		privateMethodSet = MyClassExample.class.getDeclaredMethod("setTimestamp", parameterTypes);
		privateMethodSet.setAccessible(true);
		Object result = privateMethodSet.invoke(myObject, "11111");
		Object castedResult = privateMethodReturnValueType.cast(result);
		String strResult = (String) castedResult;

	}
}
