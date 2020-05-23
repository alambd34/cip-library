package it.lucichkevin.cip.kalima;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import it.lucichkevin.cip.R;
import it.lucichkevin.cip.Utils;


public class RequesterStatisticsActivity extends AppCompatActivity {

	@Override
	protected void onCreate( Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_requester_statistics);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		setAppLogo();

		((Button) findViewById(R.id.btn_reset_requester_statistics)).setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick( View v ){
				RequesterStatistics.setNumberOfRequestsSent(0);
				RequesterStatistics.setSizeOfRequestsSent(0);
				RequesterStatistics.setSizeOfResponsesReceived(0);

				RequesterStatistics.setNumberOfResponsesByStatus(200, 0);
				RequesterStatistics.setNumberOfResponsesByStatus(301, 0);
				RequesterStatistics.setNumberOfResponsesByStatus(500, 0);

				finish();
				startActivity(getIntent());
			}
		});

		((TextView) findViewById(R.id.size_of_requests_sent)).setText( Utils.humanReadableByteCount(RequesterStatistics.getSizeOfRequestsSent()) );
		((TextView) findViewById(R.id.size_of_responses_received)).setText( Utils.humanReadableByteCount(RequesterStatistics.getSizeOfResponsesReceived()) );

		((TextView) findViewById(R.id.number_of_requests_sent)).setText( getString(R.string.requests,RequesterStatistics.getNumberOfRequestsSent()) );

		((TextView) findViewById(R.id.number_of_response_code_200)).setText( getString(R.string.responses,RequesterStatistics.getNumberOfResponsesByStatus(200)) );
		((TextView) findViewById(R.id.number_of_response_code_301)).setText( getString(R.string.responses,RequesterStatistics.getNumberOfResponsesByStatus(301)) );
		((TextView) findViewById(R.id.number_of_response_code_500)).setText( getString(R.string.responses,RequesterStatistics.getNumberOfResponsesByStatus(500)) );
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item ){
		switch( item.getItemId() ){
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void setAppLogo(){
		findViewById(R.id.app_logo).setVisibility(View.GONE);
	}

}
