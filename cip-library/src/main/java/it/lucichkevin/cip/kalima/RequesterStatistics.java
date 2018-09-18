package it.lucichkevin.cip.kalima;

import it.lucichkevin.cip.preferences.PreferencesManager;


public class RequesterStatistics extends PreferencesManager {

	protected static final String CIP_REQUESTER_NUMBER_OF_REQUESTS_SENT = "CIP_REQUESTER_NUMBER_OF_REQUESTS_SENT";

	protected static final String CIP_REQUESTER_SIZE_OF_REQUESTS_SENT = "CIP_REQUESTER_SIZE_OF_REQUESTS_SENT";
	protected static final String CIP_REQUESTER_SIZE_OF_RESPONSES_RECEIVED = "CIP_REQUESTER_SIZE_OF_RESPONSES_RECEIVED";


	public static int getNumberOfRequestsSent(){
		return getPreferences().getInt( CIP_REQUESTER_NUMBER_OF_REQUESTS_SENT, 0 );
	}
	public static void setNumberOfRequestsSent( int number ){
		setPreferences( CIP_REQUESTER_NUMBER_OF_REQUESTS_SENT, number );
	}
	public synchronized static void increaseNumberOfRequestsSent(){
		setNumberOfRequestsSent((getNumberOfRequestsSent() + 1));
	}

	public static long getSizeOfRequestsSent(){
		return getPreferences().getLong( CIP_REQUESTER_SIZE_OF_REQUESTS_SENT, 0 );
	}
	public static void setSizeOfRequestsSent( long number ){
		setPreferences( CIP_REQUESTER_SIZE_OF_REQUESTS_SENT, number );
	}
	public synchronized static void increaseSizeOfRequestsSent( long number_to_increase ){
		setSizeOfRequestsSent((getSizeOfRequestsSent() + number_to_increase));
	}

	public static long getSizeOfResponsesReceived(){
		return getPreferences().getLong( CIP_REQUESTER_SIZE_OF_RESPONSES_RECEIVED, 0 );
	}
	public static void setSizeOfResponsesReceived( long number ){
		setPreferences( CIP_REQUESTER_SIZE_OF_RESPONSES_RECEIVED, number );
	}
	public synchronized static void increaseSizeOfResponsesReceived( long number_to_increase ){
		setSizeOfResponsesReceived((getSizeOfResponsesReceived() + number_to_increase));
	}

	public static int getNumberOfResponsesByStatus( int status ){
		return getPreferences().getInt( "NUMBER_OF_RESPONSES_WITH_CODE_"+ status, 0 );
	}
	public static void setNumberOfResponsesByStatus( int status, int number ){
		setPreferences( "NUMBER_OF_RESPONSES_WITH_CODE_"+ status, number );
	}
	public synchronized static void increaseNumberOfResponsesByStatus( int status ){
		setNumberOfResponsesByStatus( status, (getNumberOfResponsesByStatus(status) + 1) );
	}

}
