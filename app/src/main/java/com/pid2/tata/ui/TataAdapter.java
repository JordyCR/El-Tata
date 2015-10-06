package com.pid2.tata.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.util.Insertable;
import com.pid2.tata.R;
import com.pid2.tata.TataUtils;
import com.pid2.tata.db.ReporteModel;

import java.sql.Timestamp;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JordyCuan on 12/09/15.
 */
public class TataAdapter extends BaseAdapter implements Insertable<ReporteModel>{
	private List<ReporteModel> reportes;
	private LayoutInflater inflater = null;
	private Context mContext = null;

	/**
	 * Constructor de TataAdapter para la ListView
	 *
	 * @param context Contexto de la aplicación a tomar los parametros
	 * @param reportes Lista de los reportes a ser mostrados
	 */
	public TataAdapter(Context context, List<ReporteModel> reportes) {
		this.reportes = reportes;
		inflater = LayoutInflater.from(context);
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return reportes.size();
	}

	@Override
	public ReporteModel getItem(int position) {
		return reportes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		ViewHolder holder;

		if(convertView == null) {
			view = inflater.inflate(R.layout.list_item, parent, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder)view.getTag();
		}

		ReporteModel reporte = reportes.get(position);
		try {
			holder.fecha.setText(TataUtils.fecha(reporte.fecha));
		} catch (Exception e) {
			holder.fecha.setText("-"); // TODO: Quitar/Validar lógica
		}
		holder.contenido.setText(reporte.contenido);
		holder.listItem.setBackgroundColor(view.getResources().getColor(reporte.tipo_color));

		chooseColor(holder, reporte.tipo);

		isSeen(holder, reporte.leido);

		return view;
	}

	static class ViewHolder {
		@Bind(R.id.tv_fecha) TextView fecha;
		@Bind(R.id.tv_contenido) TextView contenido;
		@Bind(R.id.fondo_list_view) RelativeLayout listItem;
		@Bind(R.id.gradient_derecha) ImageView rightGradient;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}

	private void chooseColor(ViewHolder vh, String type) {
		switch (type) {
			case TataUtils.t_fisioterapicos:
				vh.rightGradient.setImageResource(R.drawable.gradient_fisioterapico_derecha);
				break;
			case TataUtils.t_nutriologicos:
				vh.rightGradient.setImageResource(R.drawable.gradient_nutriologico_derecha);
				break;
			case TataUtils.t_geriatricos:
				vh.rightGradient.setImageResource(R.drawable.gradient_geriatrico_derecha);
				break;
			case TataUtils.t_psicologicos:
				vh.rightGradient.setImageResource(R.drawable.gradient_psicologico_derecha);
				break;
		}
	}

	private void isSeen(ViewHolder holder, boolean leido) {
		if ( ! leido) {
			holder.fecha.setTypeface(null, Typeface.BOLD);
			holder.contenido.setTypeface(null, Typeface.BOLD);
		}
	}

	@Override
	public void add(int i, ReporteModel report) {
		// TODO: Debemos prevenir el mostrar un doble reporte si es que se repite el envio desde el server
		// TODO: Hay problemas al añadir en "Todos Los Reportes" No se respeta el color

		// Debemos saber que reporte está abierto
		if (ElTataMainActivity.mMainInstance == null) return;
		if (ElTataMainActivity.mMainInstance.actualFragment == null) return;


		// Tenemos Abierto "Todos los Reportes" => Se añade
		if (ElTataMainActivity.mMainInstance.actualFragment.reportTypeOpened
				.equals(ElTataMainActivity.todos_los_reportes)) {
			reportes.add(i, report);
			notifyDataSetChanged();
			return;
		}


		/** Si está abierta la pestaña y el reporte es del mismo tipo */

		// Se añade "Fisioterapéuticos"
		if (ElTataMainActivity.mMainInstance.actualFragment.reportTypeOpened
				.equals(ElTataMainActivity.fisioterapicos)
				&& report.tipo.equals(TataUtils.t_fisioterapicos)) {
			reportes.add(i, report);
			notifyDataSetChanged();
			return;
		}

		// Se añade "Nutriologos"
		if (ElTataMainActivity.mMainInstance.actualFragment.reportTypeOpened
				.equals(ElTataMainActivity.nutriologicos)
				&& report.tipo.equals(TataUtils.t_nutriologicos)) {
			reportes.add(i, report);
			notifyDataSetChanged();
			return;
		}

		// Se añade "Geriatricos"
		if (ElTataMainActivity.mMainInstance.actualFragment.reportTypeOpened
				.equals(ElTataMainActivity.geriatricos)
				&& report.tipo.equals(TataUtils.t_geriatricos)) {
			reportes.add(i, report);
			notifyDataSetChanged();
			return;
		}

		// Se añade "Psicológicos"
		if (ElTataMainActivity.mMainInstance.actualFragment.reportTypeOpened
				.equals(ElTataMainActivity.psicologicos)
				&& report.tipo.equals(TataUtils.t_psicologicos)) {
			reportes.add(i, report);
			notifyDataSetChanged();
			return;
		}
	}
}
