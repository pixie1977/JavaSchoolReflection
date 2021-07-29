package ru.common.reflection.support;

public class ByteArrayClassLoader extends ClassLoader
{
	public Class loadClassFromBytes(String name, byte[] bytes) {
		return defineClass(name,bytes,0,bytes.length);
	}
}
