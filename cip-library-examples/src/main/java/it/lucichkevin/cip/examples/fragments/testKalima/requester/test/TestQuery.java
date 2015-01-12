package it.lucichkevin.cip.examples.fragments.testKalima.requester.test;

import it.lucichkevin.cip.kalima.Request;


public class TestQuery extends Request.Query{

	private String name;

	public TestQuery( String name ){
		setName(name);
	}


	////////////////////////////////////////
	//	Getter and Setter

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
