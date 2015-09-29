package com.pid2.tata.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.android.gms.games.internal.GamesContract;
import com.pid2.tata.R;
import com.pid2.tata.ui.ElTataMainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JordyCuan on 06/09/15.
 */
@Table(name = "Reportes")
public class ReporteModel extends Model {
	/** ALL REPORTS */
	@Column(name = "_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public long _id;

	/** ALL REPORTS */@Column(name = "fecha")
	public String fecha;

	/** ALL REPORTS */@Column(name = "tipo")
	public String tipo;

	/** ALL REPORTS */@Column(name = "tipo_color")
	public int tipo_color;

	/** ALL REPORTS */@Column(name = "contenido")
	public String contenido;

	/** ALL REPORTS */@Column(name = "doctor")
	public String doctor;

	/** ALL REPORTS */@Column(name = "paciente_str")
	public String paciente_str;

	/** ALL REPORTS */@Column(name = "leido")
	public boolean leido;

	/** ALL REPORTS */@Column(name = "archivado")
	public boolean archivado;

//	@Column(name = "paciente",
//			onUpdate = Column.ForeignKeyAction.CASCADE,
//			onDelete = Column.ForeignKeyAction.CASCADE)
//	public PacienteModel paciente;



	/** Physiotherapy */@Column(name = "terapia_ocupacional")
	public String terapia_ocupacional;

	/** Physiotherapy */@Column(name = "terapia_manual")
	public String terapia_manual;

	/** Physiotherapy */@Column(name = "masajes")
	public String masajes;

	/** Physiotherapy */@Column(name = "evolucion")
	public String evolucion;

	/** Physiotherapy */@Column(name = "recomendaciones")
	public String recomendaciones;



	/*+ Psychological, Nutritional, Geriatric */
	@Column(name = "observaciones")
	public String observaciones;

	/*+ Psychological, Nutritional, Geriatric */
	@Column(name = "sugerencias")
	public String sugerencias;




	/** Geriatric */@Column(name = "tratamiento")
	public String tratamiento;




	/** Nutritional */@Column(name = "peso")
	public String peso;

	/** Nutritional */@Column(name = "talla")
	public String talla;

	/** Nutritional */@Column(name = "glucosa")
	public String glucosa;

	/** Nutritional */@Column(name = "presion")
	public String presion;

	/** Nutritional */@Column(name = "imc")
	public String imc;

	/** Nutritional */@Column(name = "est_apetito")
	public String est_apetito;



	public ReporteModel() {
	}


	// TODO: Este se utiliza para las clases descendientes
	public ReporteModel(long _id, String fecha, String tipo, String doctor,
	                    String paciente_str, boolean leido, boolean archivado) {
		this._id = _id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.doctor = doctor;
		this.paciente_str = paciente_str;
		this.leido = leido;
		this.archivado = archivado;

		selectType(tipo);
	}


	/**
	 * Psychological Constructor
	 */
	private ReporteModel(long _id, String fecha, String tipo, String doctor,
	                    String paciente_str, String observaciones,
	                    String sugerencias, boolean leido, boolean archivado) {
		this._id = _id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.doctor = doctor;
		this.paciente_str = paciente_str;
		this.leido = leido;
		this.archivado = archivado;
		this.observaciones = observaciones;
		this.sugerencias = sugerencias;

		selectType(tipo);
	}

	public static ReporteModel newPsychologicalReport(long _id, String fecha, String tipo,
	                      String doctor, String paciente_str, String observaciones,
                          String sugerencias, boolean leido, boolean archivado) {
		ReporteModel report = new ReporteModel(_id, fecha, tipo, doctor, paciente_str,
				observaciones, sugerencias, leido, archivado); //NO PUEDE IR VACIO
		return report;
	}


	/**
	 * Physiotherapy Constructor
	 */
	private ReporteModel(long _id, String fecha, String tipo,
	                    String doctor, String paciente_str,
	                    String terapia_ocupacional, String terapia_manual,
	                    String masajes, String evolucion, String recomendaciones,
	                    boolean leido, boolean archivado) {
		this._id = _id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.doctor = doctor;
		this.paciente_str = paciente_str;
		this.leido = leido;
		this.archivado = archivado;
		this.terapia_ocupacional = terapia_ocupacional;
		this.terapia_manual = terapia_manual;
		this.masajes = masajes;
		this.evolucion = evolucion;
		this.recomendaciones = recomendaciones;

		selectType(tipo);
	}

	public static ReporteModel newPhysiotherapyReport(long _id, String fecha, String tipo,
                      String doctor, String paciente_str,
                      String terapia_ocupacional, String terapia_manual,
                      String masajes, String evolucion, String recomendaciones,
                      boolean leido, boolean archivado) {
		ReporteModel report = new ReporteModel(
				_id, fecha, tipo, doctor, paciente_str, terapia_ocupacional, terapia_manual,
				masajes, evolucion, recomendaciones, leido, archivado
		); //NO PUEDE IR VACIO
		return report;
	}


	/**
	 * Geriatric Constructor
	 */
	private ReporteModel(long _id, String fecha, String tipo, String doctor,
	                    String paciente_str, String observaciones,
	                    String sugerencias, String tratamiento,
	                    boolean leido, boolean archivado) {
		this._id = _id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.doctor = doctor;
		this.paciente_str = paciente_str;
		this.leido = leido;
		this.archivado = archivado;
		this.observaciones = observaciones;
		this.sugerencias = sugerencias;
		this.tratamiento = tratamiento;

		selectType(tipo);
	}

	public static ReporteModel newGeriatricReport(long _id, String fecha, String tipo,
	                      String doctor, String paciente_str, String observaciones,
                          String sugerencias, String tratamiento,
                          boolean leido, boolean archivado) {
		ReporteModel report = new ReporteModel(
				_id, fecha, tipo, doctor, paciente_str, observaciones, sugerencias,
				tratamiento, leido, archivado
		); //NO PUEDE IR VACIO
		return report;
	}



	/**
	 * Nutritional Constructor
	 */
	private ReporteModel(long _id, String fecha, String tipo, String doctor,
	                    String paciente_str, String observaciones,
	                    String sugerencias, String peso, String talla,
	                    String glucosa, String presion, String imc,
	                    String est_apetito, boolean leido, boolean archivado) {
		this._id = _id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.doctor = doctor;
		this.paciente_str = paciente_str;
		this.leido = leido;
		this.archivado = archivado;
		this.observaciones = observaciones;
		this.sugerencias = sugerencias;
		this.peso = peso;
		this.talla = talla;
		this.glucosa = glucosa;
		this.presion = presion;
		this.imc = imc;
		this.est_apetito = est_apetito;

		selectType(tipo);
	}

	public static ReporteModel newNutritionalReport(long _id, String fecha, String tipo,
                        String doctor, String paciente_str, String observaciones,
                        String sugerencias, String peso, String talla,
                        String glucosa, String presion, String imc,
                        String est_apetito, boolean leido, boolean archivado) {
		ReporteModel report = new ReporteModel(
				_id, fecha, tipo, doctor, paciente_str, observaciones, sugerencias,
				peso, talla, glucosa, presion, imc, est_apetito, leido, archivado
		); //NO PUEDE IR VACIO
		return report;
	}





	private void selectType(String type) {
		switch (type) {
			case ElTataMainActivity.t_fisioterapicos:
				tipo_color = R.color.listitem_fisiologico;
				break;
			case ElTataMainActivity.t_geriatricos:
				tipo_color = R.color.listitem_geriatrico;
				break;
			case ElTataMainActivity.t_nutriologicos:
				tipo_color = R.color.listitem_nutricional;
				break;
			case ElTataMainActivity.t_psicologicos:
				tipo_color = R.color.listitem_psicologico;
				break;
		}
	}


	public static List<ReporteModel> getAll() {
		return new Select().from(ReporteModel.class).orderBy("fecha ASC").execute(); // TODO: definir orden FECHA?
	}


//	public static List<ReporteModel> getAll() {
//		return new Select().from(ReporteModel.class).orderBy("_id ASC").execute();
//	}

//	public static List<ReporteModel> getAll() {
//		//Reports.Physiotherapy
//		List<ReporteModel> l = new ArrayList<>();
//
//		l.addAll(Reports.Psychological.getAll());
//		l.addAll(Reports.Geriatric.getAll());
//		l.addAll(Reports.Physiotherapy.getAll());
//		l.addAll(Reports.Nutritional.getAll());
//
//		return l;
//	}


	public static List<ReporteModel> getReportsFromType(String type) {
//		Class reportClass = Reports.Physiotherapy.class;
//		switch (type) {
//			case ElTataMainActivity.t_fisioterapicos:
//				reportClass = Reports.Physiotherapy.class;
//				break;
//			case ElTataMainActivity.t_psicologicos:
//				reportClass = Reports.Psychological.class;
//				break;
//			case ElTataMainActivity.t_geriatricos:
//				reportClass = Reports.Geriatric.class;
//				break;
//			case ElTataMainActivity.t_nutriologicos:
//				reportClass = Reports.Nutritional.class;
//				break;
//		}

		Class reportClass = ReporteModel.class;
		return new Select()
				.from(reportClass)
				.where("tipo = ?", type)
				.orderBy("fecha ASC").execute();
	}


	@Override
	public String toString() {
		return "ReporteModel{" +
				"_id=" + _id +
				", fecha='" + fecha + '\'' +
				", tipo='" + tipo + '\'' +
				", paciente=" + paciente_str +
				'}';
	}




	/**
	 * Inner classes to handle all report kinds
	 */
	public static class Reports {
		/**
		 * Psychological
		 */
//		@Table(name = "PsychologicalReports")
		public static class Psychological extends ReporteModel {
//			@Column(name = "observaciones")
			public String observaciones;

//			@Column(name = "sugerencias")
			public String sugerencias;

//			public Psychological() {
//			}

			public Psychological(long _id, String fecha, String tipo, String doctor,
			                     String paciente_str, String observaciones,
			                     String sugerencias, boolean leido, boolean archivado) {
				super(_id, fecha, tipo, doctor, paciente_str, leido, archivado);
				this.observaciones = observaciones;
				this.sugerencias = sugerencias;
			}

			@Override
			public String toString() {
				return super.toString() + "Psychological{" +
						"observaciones='" + observaciones + '\'' +
						", sugerencias='" + sugerencias + '\'' +
						'}';
			}

//			public static List<ReporteModel> getAll() {
//				return ReporteModel.getReportsFromType(ElTataMainActivity.t_psicologicos);
//			}
		}


		/**
		 * Physiotherapy
		 */
//		@Table(name = "PhysiotherapyReports")
		public static class Physiotherapy extends ReporteModel {
//			@Column(name = "terapia_ocupacional")
			public String terapia_ocupacional;

//			@Column(name = "terapia_manual")
			public String terapia_manual;

//			@Column(name = "masajes")
			public String masajes;

//			@Column(name = "evolucion")
			public String evolucion;

//			@Column(name = "recomendaciones")
			public String recomendaciones;

//			public Physiotherapy() {
//			}

			public Physiotherapy(long _id, String fecha, String tipo,
			                     String doctor, String paciente_str,
			                     String terapia_ocupacional, String terapia_manual,
			                     String masajes, String evolucion, String recomendaciones,
			                     boolean leido, boolean archivado) {
				super(_id, fecha, tipo, doctor, paciente_str, leido, archivado);
				this.terapia_ocupacional = terapia_ocupacional;
				this.terapia_manual = terapia_manual;
				this.masajes = masajes;
				this.evolucion = evolucion;
				this.recomendaciones = recomendaciones;
			}

//			public static List<ReporteModel> getAll() {
//				return ReporteModel.getReportsFromType(ElTataMainActivity.t_fisioterapicos);
//			}
		}


		/**
		 * Geriatric
		 */
//		@Table(name = "GeriatricReports")
		public static class Geriatric extends ReporteModel {
//			@Column(name = "observaciones")
			public String observaciones;

//			@Column(name = "sugerencias")
			public String sugerencias;

//			@Column(name = "tratamiento")
			public String tratamiento;

//			public Geriatric() {
//			}

			public Geriatric(long _id, String fecha, String tipo, String doctor,
			                 String paciente_str, String observaciones,
			                 String sugerencias, String tratamiento,
			                 boolean leido, boolean archivado) {
				super(_id, fecha, tipo, doctor, paciente_str, leido, archivado);
				this.observaciones = observaciones;
				this.sugerencias = sugerencias;
				this.tratamiento = tratamiento;
			}

//			public static List<ReporteModel> getAll() {
//				return ReporteModel.getReportsFromType(ElTataMainActivity.t_geriatricos);
//			}
		}


		/**
		 * Nutritional
		 */
//		@Table(name = "NutritionalReports")
		public static class Nutritional extends ReporteModel {
//			@Column(name = "observaciones")
			public String observaciones;

//			@Column(name = "sugerencias")
			public String sugerencias;

//			@Column(name = "peso")
			public String peso;

//			@Column(name = "talla")
			public String talla;

//			@Column(name = "glucosa")
			public String glucosa;

//			@Column(name = "presion")
			public String presion;

//			@Column(name = "imc")
			public String imc;

//			@Column(name = "est_apetito")
			public String est_apetito;

//			public Nutritional() {
//			}

			public Nutritional(long _id, String fecha, String tipo, String doctor,
			                   String paciente_str, String observaciones,
			                   String sugerencias, String peso, String talla,
			                   String glucosa, String presion, String imc,
			                   String est_apetito, boolean leido, boolean archivado) {
				super(_id, fecha, tipo, doctor, paciente_str, leido, archivado);
				this.observaciones = observaciones;
				this.sugerencias = sugerencias;
				this.peso = peso;
				this.talla = talla;
				this.glucosa = glucosa;
				this.presion = presion;
				this.imc = imc;
				this.est_apetito = est_apetito;
			}

//			public static List<ReporteModel> getAll() {
//				return ReporteModel.getReportsFromType(ElTataMainActivity.t_nutriologicos);
//			}
		}
	}
}
