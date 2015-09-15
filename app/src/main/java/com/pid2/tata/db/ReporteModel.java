package com.pid2.tata.db;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by JordyCuan on 06/09/15.
 */
@Table(name = "Reportes")
public class ReporteModel extends Model {
	@Column(name = "_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public long _id;

	@Column(name = "fecha")
	public String fecha;

	@Column(name = "tipo")
	public String tipo;

	@Column(name = "contenido")
	public String contenido;

	@Column(name = "paciente",
			onUpdate = Column.ForeignKeyAction.CASCADE,
			onDelete = Column.ForeignKeyAction.CASCADE)
	public PacienteModel paciente;

	public ReporteModel() {
	}

	public ReporteModel(long _id, String fecha, String tipo, String contenido, PacienteModel paciente) {
		this._id = _id;
		this.fecha = fecha;
		this.tipo = tipo;
		this.contenido = contenido;
		this.paciente = paciente;
	}

	public static List<ReporteModel> getAll() {
		return new Select().from(ReporteModel.class).orderBy("_id ASC").execute(); // TODO: definir orden
	}

	@Override
	public String toString() {
		return "ReporteModel{" +
				"_id=" + _id +
				", fecha='" + fecha + '\'' +
				", tipo='" + tipo + '\'' +
				", contenido='" + contenido + '\'' +
				", paciente=" + paciente +
				'}';
	}
}
