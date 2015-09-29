package com.pid2.tata.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nhaarman.listviewanimations.util.Insertable;
import com.pid2.tata.R;
import com.pid2.tata.db.ReporteModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JordyCuan on 12/09/15.
 */
public class TataAdapter extends BaseAdapter implements Insertable<ReporteModel>{
	private List<ReporteModel> reportes;
	private LayoutInflater inflater = null;

	/**
	 * Constructor de TataAdapter para la ListView
	 *
	 * @param context Contexto de la aplicación a tomar los parametros
	 * @param reportes Lista de los reportes a ser mostrados
	 */
	public TataAdapter(Context context, List<ReporteModel> reportes) {
		this.reportes = reportes;
		inflater = LayoutInflater.from(context);
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
		holder.fecha.setText(reporte.fecha);
		holder.contenido.setText(reporte.contenido);
		view.getResources();
		view.getResources().getColor(reporte.tipo_color);
		holder.listItem.setBackgroundColor(view.getResources().getColor(reporte.tipo_color));

		chooseColor(holder, reporte.tipo);

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
			case ElTataMainActivity.t_fisioterapicos:
				vh.rightGradient.setImageResource(R.drawable.gradient_fisioterapico_derecha);
				break;
			case ElTataMainActivity.t_nutriologicos:
				vh.rightGradient.setImageResource(R.drawable.gradient_nutriologico_derecha);
				break;
			case ElTataMainActivity.t_geriatricos:
				vh.rightGradient.setImageResource(R.drawable.gradient_geriatrico_derecha);
				break;
			case ElTataMainActivity.t_psicologicos:
				vh.rightGradient.setImageResource(R.drawable.gradient_psicologico_derecha);
				break;
		}
	}

	@Override
	public void add(int i, ReporteModel reporteModel) {
		reportes.add(i, reporteModel);
	}
}
