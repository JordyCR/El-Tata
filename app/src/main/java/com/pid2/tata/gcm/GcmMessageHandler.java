package com.pid2.tata.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pid2.tata.MainActivity;
import com.pid2.tata.R;

/**
 * Created by JordyCuan on 19/08/15.
 */
public class GcmMessageHandler extends IntentService {

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


		// TODO: Usadas?
		String fecha = extras.getString("fecha");
		String doctor = extras.getString("doctor");
		String paciente = extras.getString("paciente");
		String comentarios = extras.getString("comentarios");


//		Set<String> llaves = extras.keySet();
//		String which = "";
//		for (String llave : llaves) {
//			which += llave + ", ";
//		}
//
//		Log.i("*** GCM ***", which);

		// Como nosotros no lo procesamos aquí, lo mandamos a las notificaciones y a la
		// actividad principal (aunque lo ideal sería aprovechar este otro hilo para
		// meterlo dentro de una DB).
		mostrarNotificacion(extras);
//		Log.i("*** GCM ***", "Received : (" + messageType + ")  " + extras.getString("title"));

		GcmBroadcastReceiver.completeWakefulIntent(intent);
	}

	public void mostrarNotificacion(final Bundle extras) {

		// Intervenimos en el UI
		handler.post(new Runnable() {
			public void run() {

				String text = "El paciente " + extras.getString("paciente") +	", tiene un nuevo reporte del médico " + extras.getString("doctor");
				NotificationCompat.BigTextStyle textStyle = new NotificationCompat.BigTextStyle().bigText(text);

				NotificationCompat.Builder
						mBuilder = new NotificationCompat.Builder(getApplicationContext())
								.setSmallIcon(R.drawable.ic_mini_doctor)
								.setLargeIcon((((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_launcher)).getBitmap()))
								// TODO: Cambiar estos 2 despues
								.setContentTitle("Nuevo Reporte Médico.")
								.setContentText("El paciente " + extras.getString("paciente") +
										", tiene un nuevo reporte del médico " + extras.getString("doctor"))
								//.setContentInfo("4")
								.setStyle(textStyle)
								.setAutoCancel(true)
								.setDefaults(Notification.DEFAULT_ALL)
								//.setPriority(NotificationCompat.PRIORITY_HIGH)
								.setTicker("Nuevo Reporte Médico.");

				Intent intent = new Intent(getApplicationContext(), MainActivity.class);

				// Como no empleamos (por ahora) los extras, los mandamos a la otra actividad
				// para su manipulación
				intent.putExtras(extras);

				PendingIntent contIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

				mBuilder.setContentIntent(contIntent);

				NotificationManager mNotificationManager =
						(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				final int NOTIF_ALERTA_ID = 123;  // Identificador único de nuestra notificación
				mNotificationManager.notify(NOTIF_ALERTA_ID, mBuilder.build());
			}
		});
	}
}