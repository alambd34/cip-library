package it.lucichkevin.cip.examples.fragments.testKalima.response.test;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.kalima.responses.AbstractResponse;


public class TestResponse extends AbstractResponse {

	private TestResponse.Data data;

    public void handleData(){
        super.handleData();

        Utils.loggerDebug("["+ request.getRequestId() +"] hello => "+ data.getHello());

        //  Do something...
    }


    ////////////////////////////////////////
    //	Getter and Setter

	public TestResponse.Data getData() {
		return data;
	}
	public void setData(TestResponse.Data data) {
		this.data = data;
	}



    public class Data{

        private String hello;


        ////////////////////////////////////////
        //	Getter and Setter

        public String getHello() {
            return hello;
        }

        public void setHello(String hello) {
            this.hello = hello;
        }

    }
}
