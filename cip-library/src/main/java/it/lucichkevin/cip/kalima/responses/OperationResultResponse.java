package it.lucichkevin.cip.kalima.responses;

import it.lucichkevin.cip.kalima.responses.AbstractResponse;


public class OperationResultResponse extends AbstractResponse {

	protected Data data;

//	public void handleData(){
//
//	}


	////////////////////////////////////////
	//	Getter and Setter

	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}



	public class Data {

		private boolean is_successful;


		////////////////////////////////////////
		//	Getter and Setter

		public boolean isSuccessful() {
			return is_successful;
		}
	}

}