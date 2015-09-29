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
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pid2.tata.R;
import com.pid2.tata.db.ReporteModel;
import com.pid2.tata.db.ReporteModel.Reports;
import com.pid2.tata.ui.DetallesReporteActivity;
import com.pid2.tata.ui.ElTataMainActivity;

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
		 */
		Bundle extras = intent.getExtras();


		// Inserting to the DB
		saveReport(extras);



		// Esto es solo para conseguir el Tipo de Mensaje. No tiene utilidad para
		// nosotros mas que fines visuales
		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		// El parámetro "intent" de getMessageType() debe ser el mismo del
		// BroadcastReceiver.
		String messageType = gcm.getMessageType(intent);


		int id_rep = Integer.valueOf(extras.getString("id"));

		Log.i("*** GCM ***", String.valueOf(id_rep));

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
		if (!ElTataMainActivity.isOpen)
			mostrarNotificacion(extras);
		else if (ElTataMainActivity.mMainInstance != null)
			if (ElTataMainActivity.mMainInstance.actualFragment != null)
				handler.post(
						new Runnable() {
							@Override
							public void run() {
								Log.d("** GcmMessageHandler **", "Aplicación Abierta -> Insertar en ListView");
								Log.d("** GcmMessageHandler **", "" +
										ReporteModel
												.getReportsFromType(ElTataMainActivity.t_psicologicos)
												.toString());
								Log.d("** GcmMessageHandler **", "" +
										Reports.Psychological
												.getReportsFromType(ElTataMainActivity.t_psicologicos)
												.toString());
								;
//								ElTataMainActivity
//										.mMainInstance
//										.actualFragment
//										.mListView.insert(0,null);
							} // TODO: Checar cuidadosamente toda esta logica
						}
				);

		//TODO: Enviar acose de recibo al servidor
		new Thread(new Runnable() {
			@Override
			public void run() {
				// Envío al servidor
			}
		}).start();

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
								.setPriority(NotificationCompat.PRIORITY_HIGH)
								.setTicker("Nuevo Reporte Médico.");



				Intent intent = new Intent(getApplicationContext(), DetallesReporteActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

				// Como no empleamos (por ahora) los extras, los mandamos a la otra actividad
				// para su manipulación
				intent.putExtras(extras);
																										// Con esto, hacemos que el intent sea el MISMO
				PendingIntent contIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				// Intent.FLAG_ACTIVITY_NEW_TASK
				// PendingIntent.FLAG_ONE_SHOT

				// Todo: Talvez necesitemos esto
				// mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				// mNotificationManager.cancel(SIMPLE_NOTFICATION_ID_A);
				mBuilder.setContentIntent(contIntent);

				NotificationManager mNotificationManager =
						(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				//final int NOTIF_ALERTA_ID = 123;  // Identificador único de nuestra notificación
				mNotificationManager.notify(Integer.valueOf(extras.getString("id")), mBuilder.build());
			}
		});
	}


	private void saveReport(Bundle extras) {
		String t = extras.getString("tipo");
		// TODO: Validar que se haya enviado tipo y el id
		// TODO: Buscar si es paciente o paciente_str
		switch (t) {
			case ElTataMainActivity.t_fisioterapicos:
				//new Reports.Physiotherapy(
				ReporteModel.newPhysiotherapyReport(
						Long.parseLong(extras.getString("id")),
						extras.getString("fecha"),
						extras.getString("tipo"),
						extras.getString("doctor"),
						extras.getString("paciente"),
						extras.getString("terapia_ocupacional"),
						extras.getString("terapia_manual"),
						extras.getString("masajes"),
						extras.getString("evolucion"),
						extras.getString("recomendaciones"),
						false, false
				).save();
				break;
			case ElTataMainActivity.t_nutriologicos:
				//new Reports.Nutritional(
				ReporteModel.newNutritionalReport(
						Long.parseLong(extras.getString("id")),
						extras.getString("fecha"),
						extras.getString("tipo"),
						extras.getString("doctor"),
						extras.getString("paciente"),
						extras.getString("observaciones"),
						extras.getString("sugerencias"),
						extras.getString("peso"),
						extras.getString("talla"),
						extras.getString("glucosa"),
						extras.getString("presion"),
						extras.getString("imc"),
						extras.getString("est_apetito"),
						false, false
				).save();
				break;
			case ElTataMainActivity.t_geriatricos:
				//new Reports.Geriatric(
				ReporteModel.newGeriatricReport(
						Long.parseLong(extras.getString("id")),
						extras.getString("fecha"),
						extras.getString("tipo"),
						extras.getString("doctor"),
						extras.getString("paciente"),
						extras.getString("observaciones"),
						extras.getString("sugerencias"),
						extras.getString("tratamiento"),
						false, false
				).save();
				break;
			case ElTataMainActivity.t_psicologicos:
				Log.d("** GcmMessageHandler **", "Insertando psicológico");
				//new Reports.Psychological(
				ReporteModel.newPsychologicalReport(
						Long.parseLong(extras.getString("id")),
						extras.getString("fecha"),
						extras.getString("tipo"),
						extras.getString("doctor"),
						extras.getString("paciente"),
						extras.getString("observaciones"),
						extras.getString("sugerencias"),
						false, false
				).save();
				break;
		}
	}
}