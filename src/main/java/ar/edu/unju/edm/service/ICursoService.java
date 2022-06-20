package ar.edu.unju.edm.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.unju.edm.model.Curso;
@Service
public interface ICursoService {
	public void guardarCurso( Curso curso);
	public void eliminarCurso(Long id) throws Exception;
	public void modificarCurso(Curso curso);
	public List<Curso> listarCursos(); 
	public Curso buscarCurso(Long id) throws Exception;
}
