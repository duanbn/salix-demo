package com.salix.demo.service;

public interface IEchoService {

	public Object echo(Object value);

	public void discard();

	public void discard(String value);

	public void error() throws Exception;

	public void rterror();

}
