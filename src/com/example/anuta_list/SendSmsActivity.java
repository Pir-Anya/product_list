package com.example.anuta_list;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class SendSmsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_sms);
	}

	
	public void smsSend(View v) {
        EditText number=(EditText)findViewById(R.id.number);
        EditText message=(EditText)findViewById(R.id.message);
        String toSms="smsto:"+number.getText().toString();
        String messageText= message.getText().toString();
        Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));
 
        sms.putExtra("sms_body", messageText);
        startActivity(sms);
    }
}
