package com.pid2.tata.ui;

import android.annotation.TargetApi;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pid2.tata.R;
import com.pid2.tata.TataUtils;
import com.pid2.tata.db.PacienteModel;
import com.pid2.tata.db.ReporteModel;
import com.pid2.tata.db.ReporteModel.Reports;
import com.pid2.tata.fragments.ListaReportesFragment;

import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ElTataMainActivity extends AppCompatActivity {
	/**
	 * Instancia del drawer
	 */
	@Bind(R.id.drawer_layout)
	DrawerLayout drawerLayout;

	/**
	 * Titulo inicial del drawer
	 */
	String drawerTitle;

	/**
	 * Bandera Global para conocer el estado de la aplicación
	 */
	public static boolean isOpen = false;

	/** Strings */
	public static String todos_los_reportes;
	public static String psicologicos;
	public static String nutriologicos;
	public static String geriatricos;
	public static String fisioterapicos;



	@Bind(R.id.toolbar)
	Toolbar mToolbar;

	@Bind((R.id.nav_view))
	public NavigationView navigationView;


	public ListaReportesFragment actualFragment = null;
	public static ElTataMainActivity mMainInstance = null;


	//@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_el_tata_main);
		mMainInstance = this;

		ButterKnife.bind(this);

		setToolbar(); // Setear Toolbar como action bar

//		if (navigationView != null) {
			setupDrawerContent(navigationView);
//		}

		drawerTitle = getResources().getString(R.string.str_todos_reportes);
		if (savedInstanceState == null) {
			selectItem(drawerTitle);
		}

		setMaterialBackgroundToHeaderView();
		setNavigationBarTranslucent();

		todos_los_reportes = getString(R.string.str_todos_reportes);
		psicologicos = getString(R.string.str_psicologicos);
		nutriologicos = getString(R.string.str_nutriologicos);
		geriatricos = getString(R.string.str_geriatricos);
		fisioterapicos = getString(R.string.str_fisioterapeuticos);

		// TODO: Borrar esto despues
		// Dummy insertion
		TataUtils.creaModelos();
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void setMaterialBackgroundToHeaderView() {
		View headerFondo = findViewById(R.id.header_view);
		Random rn = new Random();
		int ran = rn.nextInt(2);
		int imgFondo = R.drawable.material_background2;
		if (ran == 1) imgFondo = R.drawable.material_background3;
		headerFondo.setBackground(ContextCompat.getDrawable(this, imgFondo));
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setNavigationBarTranslucent() {
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
	}

	private void setToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		final ActionBar ab = getSupportActionBar();
		if (ab != null) {
			// Poner ícono del drawer toggle
			// "Home" es el icono principal, le decimos al sistema que lo muestre (para que lo usemos)
			ab.setHomeAsUpIndicator(R.drawable.ic_menu);
			ab.setDisplayHomeAsUpEnabled(true);

			// TODO: No me gusta que esté aqui
			ab.setBackgroundDrawable(new ColorDrawable(
					getResources().getColor(R.color.barra_fisiologico)));
		}
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {

					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						// Marcar item presionado
						menuItem.setChecked(true);
						// Crear nuevo fragmento
						String title = menuItem.getTitle().toString().replaceAll(" - Nuevo\\(s\\)", "");
						menuItem.setTitle(title);           //replaceAll usa RegEx y hay problemas con los ()
						selectItem(title);
						return true;
					}
				}
		);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!drawerLayout.isDrawerOpen(GravityCompat.START)) {
			getMenuInflater().inflate(R.menu.menu_el_tata_main, menu);
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void selectItem(String title) {
		// Setear título actual del TOOLBAR
		super.setTitle(title);

		changeTheme(title);

		// Enviar título como arguemento del fragmento
		Bundle args = new Bundle();
		args.putString(ListaReportesFragment.ARG_TIPO_REPORTES_FRAGMENT, title);

		actualFragment = ListaReportesFragment.newInstance(title);
		actualFragment.setArguments(args);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.main_content, actualFragment) // Aqui se infla el layout del fragment
				.commit();

		drawerLayout.closeDrawers(); // Cerrar drawer
	}


	/**
	 * Sets Theme depending on the report type that the user is visualizing
	 * (It depends on the device version)
	 * @param title The selected type of report
	 */
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void changeTheme(String title) {
		ActionBar ab = getSupportActionBar();

		// DEFAULT: Todos los Reportes (Inicialización)
		int mBarColor = R.color.barra_fisiologico;
		int mBarColorDark = R.color.barra_dark_fisiologico;
		int mBackgroundColor = R.color.background_fisiologico;
		int mListItemColor = R.color.listitem_fisiologico;

		if (title.equals(this.psicologicos)) {
			mBarColor = R.color.barra_psicologico;
			mBarColorDark = R.color.barra_dark_psicologico;
			mBackgroundColor = R.color.background_psicologico;
			mListItemColor = R.color.listitem_psicologico;
		} else if (title.equals(this.nutriologicos)) {
			mBarColor = R.color.barra_nutricional;
			mBarColorDark = R.color.barra_dark_nutricional;
			mBackgroundColor = R.color.background_nutricional;
			mListItemColor = R.color.listitem_nutricional;
		} else if (title.equals(this.geriatricos)) {
			mBarColor = R.color.barra_geriatrico;
			mBarColorDark = R.color.barra_dark_geriatrico;
			mBackgroundColor = R.color.background_geriatrico;
			mListItemColor = R.color.listitem_geriatrico;
		} else if (title.equals(this.fisioterapicos)) {
			// This condition is unnecessary but I gonna let it here just for clarity
			mBarColor = R.color.barra_fisiologico;
			mBarColorDark = R.color.barra_dark_fisiologico;
			mBackgroundColor = R.color.background_fisiologico;
			mListItemColor = R.color.listitem_fisiologico;
		}

		ab.setBackgroundDrawable(new ColorDrawable(getResources().getColor(mBarColor)));
		getWindow().setBackgroundDrawableResource(mBackgroundColor);
		navigationView.setBackgroundColor(getResources().getColor(mBackgroundColor));

		final int sdk = android.os.Build.VERSION.SDK_INT;
		if (sdk < Build.VERSION_CODES.LOLLIPOP)
			return;

		getWindow().setStatusBarColor(getResources().getColor(mBarColorDark));
	}


	/**
	 * Ciclo de vida
	 */
	@Override
	protected void onResume() {
		super.onResume();
		ElTataMainActivity.isOpen = true;
	}

	@Override
	protected void onPause() {
		ElTataMainActivity.isOpen = false;
		super.onPause();
	}
}
