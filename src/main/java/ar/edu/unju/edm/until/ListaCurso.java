package ar.edu.unju.edm.until;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.unju.edm.model.Curso;

@Component
public class ListaCurso {
	private List<Curso> cursos = new ArrayList<>(); //List es una interfaz , implementacion de List es ArrayList
	public ListaCurso() {
		// TODO Auto-generated constructor stub
	}
	public List<Curso> getListado() {
		return cursos;
	}
	public void setListado(List<Curso> cursos) {
		this.cursos = cursos;
	}

}
