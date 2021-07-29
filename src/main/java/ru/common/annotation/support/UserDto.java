package ru.common.annotation.support;

import lombok.NonNull;

@SimpleUserAnnotation
public class UserDto
{
	@SimpleUserAnnotation
	private final String name;
	private final String lastName;
	private String nickName;

	@SimpleUserAnnotation
	public UserDto(@SimpleUserAnnotation String name, @NonNull String lastName, String nickName) {
		this.name = name;
		this.lastName = lastName;
		this.nickName = nickName;
	}

	@SimpleUserAnnotation
	public String getName()
	{
		return name;
	}

	public String getLastName()
	{
		return lastName;
	}

	public String getNickName()
	{
		return nickName;
	}

	@SimpleUserAnnotation
	public void setNickName(@SimpleUserAnnotation  String nickName)
	{
		this.nickName = nickName;
	}
}
