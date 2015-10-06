package com.pid2.tata.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.pid2.tata.R;
import com.pid2.tata.TataUtils;
import com.pid2.tata.db.ReporteModel;
import com.pid2.tata.ui.DetallesReporteActivity;
import com.pid2.tata.ui.ElTataMainActivity;
import com.pid2.tata.ui.TataAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by JordyCuan on 09/09/15.
 * Fragmento para el contenido principal
 */
public class ListaReportesFragment extends Fragment implements OnItemClickListener {
	/**
	 * Este argumento del fragmento representa el título de cada
	 * sección. Nosotros lo setteamos (similar a Bundle)
	 */
	public static final String ARG_TIPO_REPORTES_FRAGMENT = "tipo_frag";


	@Bind(R.id.dynamiclistview)
	public DynamicListView mListView;

	public String reportTypeOpened;

	List<ReporteModel> reports;

	private BaseAdapter mAdapter;


	/**
	 * Crea una instancia prefabricada de {@link ListaReportesFragment}
	 *
	 * @param sectionTitle Título usado en el contenido
	 * @return Instancia del fragmento
	 */
	public static ListaReportesFragment newInstance(String sectionTitle) {
		ListaReportesFragment fragment = new ListaReportesFragment();
		Bundle args = new Bundle();
		args.putString(ARG_TIPO_REPORTES_FRAGMENT, sectionTitle);
		fragment.setArguments(args);
		return fragment;
	}
	/***************
	 * CONSTRUCTOR *
	 ***************/
	public ListaReportesFragment() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// TODO: Intentar settear el Theme antes de este inflate
		View view = inflater.inflate(R.layout.section_fragment, container, false);
		ButterKnife.bind(this, view);

		reportTypeOpened = getArguments().getString(ARG_TIPO_REPORTES_FRAGMENT);

		//mToolbar.setBackgroundColor(R.color.barra_fisiologico);

		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		// TODO: Conseguir reportes del cursor de ActiveAndroid???

		// ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);

		// BIEN! Se deben conseguir los reportes segun el tipo
		reports = selectReports();
		BaseAdapter adapter = new TataAdapter(getActivity(), reports);

		SwingRightInAnimationAdapter swingRightInAnimationAdapter = new SwingRightInAnimationAdapter(adapter);

		this.mAdapter = swingRightInAnimationAdapter;

		swingRightInAnimationAdapter.setAbsListView(mListView);
		mListView.setAdapter(swingRightInAnimationAdapter);
		mListView.setOnItemClickListener(this);
	}


	private List<ReporteModel> selectReports() {
		if(reportTypeOpened.equals(ElTataMainActivity.fisioterapicos))
			return ReporteModel.getReportsFromType(TataUtils.t_fisioterapicos);
		if (reportTypeOpened.equals(ElTataMainActivity.nutriologicos))
			return ReporteModel.getReportsFromType(TataUtils.t_nutriologicos);
		if (reportTypeOpened.equals(ElTataMainActivity.geriatricos))
			return ReporteModel.getReportsFromType(TataUtils.t_geriatricos);
		if (reportTypeOpened.equals(ElTataMainActivity.psicologicos))
			return ReporteModel.getReportsFromType(TataUtils.t_psicologicos);
		// If is nothing from above then is "todos_los_reportes"
		return ReporteModel.getAll();
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

		Intent intent = new Intent(getActivity(), DetallesReporteActivity.class);
		Bundle b = new Bundle();
		b.putSerializable("reporte", reports.get(position));
		intent.putExtras(b);
		setSeenReport(reports.get(position), view);
		startActivity(intent);
	}


	private void setSeenReport(ReporteModel report, View view) {
		// Update DB
		if ( ! report.leido ) { // Optimiza
			report.leido = true;
			report.save();

			((TextView) view.findViewById(R.id.tv_fecha)).setTypeface(null, Typeface.BOLD);
			((TextView) view.findViewById(R.id.tv_contenido)).setTypeface(null, Typeface.BOLD);

			mListView.setAdapter(mAdapter); // TODO: Carroñero
			mAdapter.notifyDataSetChanged();
		}
	}
}