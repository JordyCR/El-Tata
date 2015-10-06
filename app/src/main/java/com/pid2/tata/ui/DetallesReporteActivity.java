package com.pid2.tata.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pid2.tata.R;
import com.pid2.tata.TataUtils;
import com.pid2.tata.db.ReporteModel;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetallesReporteActivity extends AppCompatActivity {

	@Bind(R.id.fondo_view) View fondo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle extras = getIntent().getExtras();
		ReporteModel report = (ReporteModel) extras.get("reporte");

		if (report == null) {
			Toast.makeText(this, "Reporte Nulo", Toast.LENGTH_SHORT).show();
			return;
		}


		setContentView(R.layout.activity_detalles_reporte);
		ButterKnife.bind(this);
		//getActionBar().setIcon(getResources().getDrawable(R.mipmap.ic_launcher));


		// TODO: cambiar por setTheme
		// Fondo de color
		String tipo_rep = extras.getString("tipo");
		tipo_rep = ((ReporteModel) extras.get("reporte")).tipo;
		switch (tipo_rep) {
			case TataUtils.t_geriatricos:
				fondo.setBackgroundColor(getResources().getColor(R.color.background_geriatrico));
				break;
			case TataUtils.t_fisioterapicos:
				fondo.setBackgroundColor(getResources().getColor(R.color.background_fisiologico));
				break;
			case TataUtils.t_psicologicos:
				fondo.setBackgroundColor(getResources().getColor(R.color.background_psicologico));
				break;
			case TataUtils.t_nutriologicos:
				fondo.setBackgroundColor(getResources().getColor(R.color.background_nutricional));
				break;
		}



		// TODO: Debemos saber que mostrar conforme al tipo de reporte
		try {
			((TextView) findViewById(R.id.lblFecha)).setText(TataUtils.fecha(report.fecha));
		} catch (Exception e) {
			((TextView) findViewById(R.id.lblFecha)).setText("-");
		}
		((TextView) findViewById(R.id.lblNombrePaciente)).setText(report.paciente_str);
		((TextView) findViewById(R.id.lblDetalleObservacion)).setText(report.observaciones);
		((TextView) findViewById(R.id.lblDetalleSugerencia)).setText(report.sugerencias);
		((TextView) findViewById(R.id.lblNombreDoctor)).setText(report.doctor);
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
