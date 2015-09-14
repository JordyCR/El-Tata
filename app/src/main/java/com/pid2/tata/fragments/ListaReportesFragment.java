package com.pid2.tata.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.pid2.tata.R;

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


	@Bind(R.id.dynamiclistview)
	DynamicListView mListView;


	/***************
	 * CONSTRUCTOR *
	 ***************/
	public ListaReportesFragment() {
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.section_fragment, container, false);
		ButterKnife.bind(this, view);

		// Ubicar argumento en el text view de section_fragment.xml
		String tipoReporte = getArguments().getString(ARG_TIPO_REPORTES_FRAGMENT);

		return view;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Planets, android.R.layout.simple_list_item_1);
		AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
		animationAdapter.setAbsListView(mListView);
		mListView.setAdapter(animationAdapter);
		mListView.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
	}
}