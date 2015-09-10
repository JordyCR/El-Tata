package com.pid2.tata.ui;

import android.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pid2.tata.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetallesReporteActivity extends ActionBarActivity {

	public static final String TIPO_REPORTE_GERIATRICO  = "geriatrico";
	public static final String TIPO_REPORTE_FISIOLOGICO = "fisiologico";
	public static final String TIPO_REPORTE_PSICOLOGICO = "psicologico";
	public static final String TIPO_REPORTE_NUTRICIONAL = "nutricional";

	@Bind(R.id.fondo_view) View fondo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalles_reporte);

		ButterKnife.bind(this);
		//getActionBar().setIcon(getResources().getDrawable(R.mipmap.ic_launcher));


		Bundle extras = getIntent().getExtras();

		String fecha = extras.getString("fecha");
		String nomPaciente = extras.getString("paciente");
		String nomDoctor = extras.getString("doctor");
		String observaciones = extras.getString("observaciones");
		String sugerencias = extras.getString("sugerencias");


		// Fondo de color
		String tipo_rep = extras.getString("tipo");
		switch (tipo_rep) {
			case TIPO_REPORTE_GERIATRICO:
				fondo.setBackgroundColor(getResources().getColor(R.color.color_geriatrico));
				break;
			case TIPO_REPORTE_FISIOLOGICO:
				fondo.setBackgroundColor(getResources().getColor(R.color.color_fisiologico));
				break;
			case TIPO_REPORTE_PSICOLOGICO:
				fondo.setBackgroundColor(getResources().getColor(R.color.color_psicologico));
				break;
			case TIPO_REPORTE_NUTRICIONAL:
				fondo.setBackgroundColor(getResources().getColor(R.color.color_nutricional));
				break;
		}




		((TextView) findViewById(R.id.lblFecha)).setText(fecha);
		((TextView) findViewById(R.id.lblNombrePaciente)).setText(nomPaciente);
		((TextView) findViewById(R.id.lblDetalleObservacion)).setText(observaciones);
		((TextView) findViewById(R.id.lblDetalleSugerencia)).setText(sugerencias);
		((TextView) findViewById(R.id.lblNombreDoctor)).setText(nomDoctor);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_detalles_reporte, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
