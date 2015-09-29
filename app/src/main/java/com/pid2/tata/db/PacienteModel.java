package com.pid2.tata.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


/**
 * Created by JordyCuan on 06/09/15.
 */
@Table(name = "Pacientes")
public class PacienteModel extends Model{
	@Column(name = "_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public long _id;

	@Column(name = "nombre")
	public String nombre;

	@Column(name = "apellidoPaterno")
	public String apPat;

	@Column(name = "apellidoMaterno")
	public String apMat;

	@Column(name = "edad")
	public int edad;

	public PacienteModel() { super(); }

	public PacienteModel(long id, String nombre, String apPat, String apMat, int edad) {
		this._id = id;
		this.nombre = nombre;
		this.apPat = apPat;
		this.apMat = apMat;
		this.edad = edad;
	}

	// Used to return items from another table based on the foreign key
	public List<ReporteModel> getAllReportes() {
		return getMany(ReporteModel.class, "paciente"); // IMPORTANTE: Es el nombre del
														// atributo/columna en la Tabla/Objeto
														// del que "tenemos muchos"
	}



	public static List<PacienteModel> getAll() {
		return new Select().from(PacienteModel.class).orderBy("apellidoPaterno ASC").execute();
	}

	@Override
	public String toString() {
		return "PacienteModel{" +
				"nombre='" + nombre + '\'' +
				", apPat='" + apPat + '\'' +
				", apMat='" + apMat + '\'' +
				", edad=" + edad +
				'}';
	}
}
// TODO: QUITAR PUBLIC, Aparte, necesitamos esta clase?