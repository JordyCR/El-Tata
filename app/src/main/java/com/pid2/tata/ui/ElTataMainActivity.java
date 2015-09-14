package com.pid2.tata.ui;

import android.annotation.TargetApi;
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
import com.pid2.tata.fragments.ListaReportesFragment;

import java.util.Random;

public class ElTataMainActivity extends AppCompatActivity {
	/**
	 * Instancia del drawer
	 */
	private DrawerLayout drawerLayout;

	/**
	 * Titulo inicial del drawer
	 */
	private String drawerTitle;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_el_tata_main);

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
//		if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//			headerFondo.setBackgroundDrawable( getResources().getDrawable(imgFondo) );
//		} else if(sdk < Build.VERSION_CODES.LOLLIPOP_MR1) {
//			headerFondo.setBackground( getResources().getDrawable(imgFondo));
//		} else {
		headerFondo.setBackground(ContextCompat.getDrawable(this, imgFondo)); // Esta linea hace lo anterior
//		}
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
		setTitle(title);
	}
}
