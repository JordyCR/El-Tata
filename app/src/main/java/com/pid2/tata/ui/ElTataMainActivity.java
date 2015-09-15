package com.pid2.tata.ui;

import android.annotation.TargetApi;
import android.graphics.Color;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.pid2.tata.R;
import com.pid2.tata.db.PacienteModel;
import com.pid2.tata.db.ReporteModel;
import com.pid2.tata.fragments.ListaReportesFragment;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ElTataMainActivity extends AppCompatActivity {
	/**
	 * Instancia del drawer
	 */
	DrawerLayout drawerLayout;

	/**
	 * Titulo inicial del drawer
	 */
	private String drawerTitle;

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


	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_el_tata_main);
		ButterKnife.bind(this);

		setToolbar(); // Setear Toolbar como action bar

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		if (navigationView != null) {
			setupDrawerContent(navigationView);
		}

		drawerTitle = getResources().getString(R.string.str_todos_reportes);
		if (savedInstanceState == null) {
			selectItem(drawerTitle);
		}

		View headerFondo = findViewById(R.id.header_view);
		Random rn = new Random();
		final int sdk = android.os.Build.VERSION.SDK_INT;
		int ran = rn.nextInt(2);
		int imgFondo = R.drawable.material_background2;
		if (ran == 1) imgFondo = R.drawable.material_background3;
		headerFondo.setBackground(ContextCompat.getDrawable(this, imgFondo)); // Esta linea hace lo anterior

		this.todos_los_reportes = getString(R.string.str_todos_reportes);
		this.psicologicos = getString(R.string.str_psicologicos);
		this.nutriologicos = getString(R.string.str_nutriologicos);
		this.geriatricos = getString(R.string.str_geriatricos);
		this.fisioterapicos = getString(R.string.str_fisioterapeuticos);

		// TODO: Borrar esto despues
		// Dummy insertion
		creaModelos();
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

			// TODO: Quitar
			ab.setBackgroundDrawable(new ColorDrawable(
					Color.parseColor("#2196f3")));
		}
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(
				new NavigationView.OnNavigationItemSelectedListener() {

					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						// Marcar item presionado
						menuItem.setChecked(true);
						// TODO: Crear nuevo fragmento
						String title = menuItem.getTitle().toString();
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
		// Enviar título como arguemento del fragmento
		Bundle args = new Bundle();
		args.putString(ListaReportesFragment.ARG_TIPO_REPORTES_FRAGMENT, title);

		Fragment fragment = ListaReportesFragment.newInstance(title);
		fragment.setArguments(args);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.main_content, fragment) // TODO: Aqui se infla el layout del fragment
				.commit();

		drawerLayout.closeDrawers(); // Cerrar drawer

		// TODO: Setear título actual del TOOLBAR
		super.setTitle(title);

		cambiarTheme(title);
		// super.setTheme();
	}


	// @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	// TODO: No sirve
	public void cambiarTheme(String title) {
		if (title.equals(this.todos_los_reportes)) {
			// setTheme(R.style.Theme_Fisiatricos);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(
					Color.parseColor("#2196f3")));
		} else if (title.equals(this.psicologicos)) {
			// setTheme(R.style.Theme_Psicologicos);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
					"#9c27b0")));
		} else if (title.equals(this.nutriologicos)) {
			// setTheme(R.style.Theme_Nutriologicos);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
					"#4caf50")));
		} else if (title.equals(this.geriatricos)) {
			// setTheme(R.style.Theme_Geriatricos);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
					"#795548")));
		} else if (title.equals(this.fisioterapicos)) {
			// setTheme(R.style.Theme_Fisiatricos);
			getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor(
					"#2196f3")));
		}
		// recreate(); // Todo: Añadir comparación de la versión actual para dar compatibilidad
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


	/**
	 * Modelos DUMMY
	 */
	private void creaModelos() {
		// 6 reportes, 2 pacientes
		PacienteModel jordy = new PacienteModel(4, "Jordy Joaquin", "Cuan", "Robledo", 22);
		jordy.save();


		new ReporteModel(1, "Hoy", "Nutrición", "El paciente se encuentra bastante bien", jordy).save();
		new ReporteModel(2, "16 de Marzo", "Fisiátrico", "Se han presentado mejoras en su caso", jordy).save();
		new ReporteModel(3, "En 1993", "Nutrición", "Al parecer esta decayendo, se sugiere se le visite", jordy).save();

		new ReporteModel(4, "Mañana", "Psicologico", "El paciente intentó suicidarse", jordy).save();
		new ReporteModel(5, "31", "Geriátrico", "Se ha presentado una mejora en su estado", jordy).save();
		new ReporteModel(6, "19 de Dic", "Psicologico", "Se ha dado de alta", jordy).save();
	}
}
