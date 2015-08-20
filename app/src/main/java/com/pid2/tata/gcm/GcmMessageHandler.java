package com.pid2.tata.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Set;

/**
 * Created by JordyCuan on 19/08/15.
 */
public class GcmMessageHandler extends IntentService {

	String mes;
	private Handler handler;

	public GcmMessageHandler() {
		super("GcmMessageHandler");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		handler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		/**
		 * El mensaje enviado del servidor es insertado en este intent, viene a modo de
		 * tabla hash y se recuperan los parametros mediante las llaves definidas por nosotros
		 *
		 * Este es el cuerpo del mensaje enviado al servidor de GCM

		 {
		    "data": {
		        "fecha": "19/Ago/15",
		        "doctor": "Max Cantorán Aguirre",
		        "paciente": "Ismael Castillo",
		        "comentarios": "Desafortunadamente el paciente se encuentra en estado terminal"
		    },
		    "registration_ids":
		        ["APA91bHDXfzISMoo6OfqhhODnU7TeNzqCxeE7lYZHhdI801wazh1e7vbITZVTqwKU5avNW0myInlZW3Aat3S8gzqawN9G5fQEkMVNvqkDHRRYt1IDsFrPuc"]
		 }

		intent.getExtras().get("mLlave");  // Devuelve 'Object'
		*/

		Bundle extras = intent.getExtras();




		// TODO: De aquí en adelante se procesan los datos enviados. Es recomentable usar una DB



		// Esto es solo para conseguir el Tipo de Mensaje. No tiene utilidad para
		// nosotros mas que fines visuales
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// El parámetro "intent" de getMessageType() debe ser el mismo del
		// BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);


		mes = extras.getString("fecha");


		Set<String> llaves = extras.keySet();
		String which = "";
		for (String llave : llaves) {
			which += llave + ", ";
		}

		Log.i("*** GCM ***", which);

		showToast();
		Log.i("*** GCM ***", "Received : (" + messageType + ")  " + extras.getString("title"));

		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	public void showToast() {

		// Intervenimos en el UI
		handler.post(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_LONG).show();
			}
		});

	}
}