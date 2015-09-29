package com.pid2.tata;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pid2.tata.db.PacienteModel;
import com.pid2.tata.db.ReporteModel;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JordyCuan on 19/08/15.
 */


/**
 * Clase que se encarga de registrar la aplicación en el gcm server para conseguir un
 * RegID devuelto al final de esta ejecución en el EditText
 */
public class MainActivity extends Activity implements OnClickListener {

	@Bind(R.id.btnGetRegId) Button btnRegId;
	@Bind(R.id.etRegId) EditText etRegId;
	GoogleCloudMessaging gcm;
	String regid;
	String PROJECT_NUMBER = "279673819378";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_aplicacion);

		ButterKnife.bind(this);
		ActiveAndroid.initialize(this);

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


	@OnClick(R.id.showModels)
	public void mostrarTestModels() {
		// Mostrar todos los pacientes
		// Mostrar todos los reportes
		// Mostrar todos MIS reportes
		// Mostrar un determinado paciente
		// Mostrar un determinado reporte desde el paciente TODO: Aun no funcionan los Join's???
		etRegId.setText("");
		String which = "";

		which += "*PACIENTES* >>>"; {
		List<PacienteModel> pms = PacienteModel.getAll();
		for (PacienteModel pac : pms) {
			which += pac.toString() + "\n";
		}}

		which += "\n\n*REPORTES* >>>"; {
		List<ReporteModel> rms = ReporteModel.getAll();
		for (ReporteModel rep : rms) {
			which += rep.toString() + "\n";
		}}

		which += "\n\n*repos de JORDY* >>>"; {
			PacienteModel jordy = new Select().from(PacienteModel.class).executeSingle();
			List<ReporteModel> jar = jordy.getAllReportes();
			for (ReporteModel rep : jar) {
				which += rep.toString() + "\n";
		}}

		which += "\n\n*Determinado Paciente* >>>";
		PacienteModel dam = new Select().from(PacienteModel.class).where("nombre = ?", "Damaris").executeSingle();
		which += dam.toString();


		which += "\n\n*Paciente - Repo* >>>";
		which += new Select().from(PacienteModel.class)
				.innerJoin(ReporteModel.class)
				.on("nombre = ? and tipo = ?", "Damaris", "Nutricion")
				.executeSingle().toString();

		etRegId.setText(which);
	}
}