package ru.common.reflection.support;

import java.util.Date;

public class MyClassExample
{
	public static final String DESC = "Description";

	public final String name;

	private final Date date;

	private String timestamp;

	public MyClassExample(String name, Date date) {
		this.name = name;
		this.date = date;
		this.timestamp = String.valueOf(System.currentTimeMillis());
	}

	private MyClassExample(String name) {
		this(name, new Date());
	}

	public String getName()
	{
		return name;
	}

	public Date getDate()
	{
		return date;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(String timestamp)
	{
		this.timestamp = timestamp;
	}

	private String setTimestampAndReturnValue(String timestamp)
	{
		this.timestamp = timestamp;
		return this.timestamp;
	}

	public String toString(){
		return String.join(" ",name, date.toString(), timestamp);
	}
}
