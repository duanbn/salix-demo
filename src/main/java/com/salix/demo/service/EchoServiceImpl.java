package com.salix.demo.service;

import org.springframework.stereotype.Service;

@Service
public class EchoServiceImpl implements IEchoService {

	@Override
	public Object echo(Object value) throws Exception {
		// Thread.sleep(1000);
		return value;
	}

	@Override
	public void discard() {
		System.out.println("invoke discard");
	}

	@Override
	public void discard(String value) {
		System.out.println("invoke discard " + value);
	}

	@Override
	public void error() throws Exception {
		throw new Exception("pinus test error");
	}

	@Override
	public void rterror() {
		throw new RuntimeException("pinus test rterror");
	}

}
