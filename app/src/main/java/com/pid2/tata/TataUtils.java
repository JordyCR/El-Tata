package com.pid2.tata;

import com.pid2.tata.db.PacienteModel;
import com.pid2.tata.db.ReporteModel;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by JordyCuan on 03/10/15.
 */
public final class TataUtils {

	/** Data - Tipo */
	public static final String t_psicologicos = "psicologico";
	public static final String t_nutriologicos = "nutricional";
	public static final String t_geriatricos = "geriatrico";
	public static final String t_fisioterapicos = "fisioterapia";


	/** TataServer Domain */
	public static final String BASE_URL = "https://tataserver.herokuapp.com";


	public static String fecha(String timeStamp) {
		return new Timestamp(Long.parseLong(timeStamp)).toString().substring(0,10);
	}


	/**
	 * Modelos DUMMY
	 */
	public static void creaModelos() {
		// 6 reportes, 2 pacientes
		PacienteModel jordy = new PacienteModel(4, "Jordy Joaquin", "Cuan", "Robledo", 22);
		jordy.save();

		Date date = new Date();

		ReporteModel.newPhysiotherapyReport
				(2, String.valueOf(1044094903346L), t_fisioterapicos,
						"Dr Enrique", "Pac. Jordy", "Terapia ocupacional", "Terapia manual", "masajes",
						"evolucion", "recomendaciones",
						false, false).save();
		ReporteModel.newPsychologicalReport
				(3, String.valueOf(1044094903346L), t_psicologicos, "Dr Enrique",
						"Pac. Jordy", "observaciones", "Sugerencias",
						false, false).save();



		ReporteModel.newNutritionalReport
				(4, String.valueOf(1044094903346L), t_nutriologicos, "Dr. Enrique", "Pac. Jordy",
						"observaciones", "sugerencias", "peso", "talla", "glucosa", "presion",
						"imc", "apetito",
						false, false).save();
		ReporteModel.newGeriatricReport
				(5, String.valueOf(1044094903346L), t_geriatricos, "Dr Enrique", "Pac. Jordy", "observaciones",
						"sugerencias", "tratamiento",
						false, false).save();



		ReporteModel.newPsychologicalReport
				(1, String.valueOf(1044094903346L), t_psicologicos, "Dr Enrique", "Pac. Jordy",
						"Observaciones", "Sugerencias",
						false, false).save();
//		ReporteModel.newPhysiotherapyReport
//				(6, String.valueOf(1044094903346L), t_fisioterapicos,
//						"Dr Enrique", "Pac. Jordy", "Terapia ocupacional", "Terapia manual", "masajes",
//						"evolucion", "recomendaciones",
//						false, false).save();
//
//
//
//		ReporteModel.newPsychologicalReport
//				(7, String.valueOf(1044094903346L), t_psicologicos, "Dr Enrique",
//						"Pac. Jordy", "observaciones", "Sugerencias",
//						false, false).save();
//		ReporteModel.newNutritionalReport
//				(8, String.valueOf(1044094903346L), t_nutriologicos, "Dr. Enrique", "Pac. Jordy",
//						"observaciones", "sugerencias", "peso", "talla", "glucosa", "presion",
//						"imc", "apetito",
//						false, false).save();
//
//
//
//		ReporteModel.newGeriatricReport
//				(9, String.valueOf(1044094903346L), t_geriatricos, "Dr Enrique", "Paciente", "observaciones",
//						"sugerencias", "tratamiento",
//						false, false).save();
//		ReporteModel.newPsychologicalReport
//				(10, String.valueOf(date.getTime()), t_psicologicos, "Dr Enrique", "Pac. Jordy",
//						"Observaciones", "Sugerencias",
//						false, false).save();
//
//		date.getTime();
	}
}
