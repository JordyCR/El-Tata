package com.pid2.tata;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by JordyCuan on 19/08/15.
 */


/**
 * Clase que se encarga de registrar la aplicación en el gcm server para conseguir un
 * RegID devuelto al final de esta ejecución en el EditText
 */
public class MainActivity extends Activity implements OnClickListener {

	Button btnRegId;
	EditText etRegId;
	GoogleCloudMessaging gcm;
	String regid;
	String PROJECT_NUMBER = "279673819378";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnRegId = (Button) findViewById(R.id.btnGetRegId);
		etRegId = (EditText) findViewById(R.id.etRegId);

		btnRegId.setOnClickListener(this);
	}

	public void getRegId() {
		new AsyncTask<Void, Void, String>() {
			@Override
			protected String doInBackground(Void... params) {
				String msg = "";
				try {
					if (gcm == null) {
						gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
					}
					regid = gcm.register(PROJECT_NUMBER);
					msg = "Registrando dispositivo\nregistration ID=" + regid;
					Log.i("*** GCM ***", msg);

				} catch (IOException ex) {
					msg = "Error :" + ex.getMessage();
				}
				return msg;
			}

			@Override
			protected void onPostExecute(String msg) {
				etRegId.setText(msg + "\n");
			}
		}.execute(null, null, null);
	}

	@Override
	public void onClick(View v) {
		getRegId();
	}
}