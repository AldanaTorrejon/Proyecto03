package ar.edu.unju.edm.service.imp;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unju.edm.controller.CursosController;
import ar.edu.unju.edm.model.Curso;
import ar.edu.unju.edm.until.ListaCurso;
import ar.edu.unju.edm.service.ICursoService;
import ar.edu.unju.edm.repository.CursoRepository;

@Service
public class ICursoServiceImp implements ICursoService {

	 private static final Log GRUPO04 = LogFactory.getLog(CursosController.class);
		
		@Autowired
		ListaCurso list;
		@Autowired
		CursoRepository cursoRepository;

		@Override
		public void guardarCurso(Curso curso){
			cursoRepository.save(curso);
		}

		@Override
		public void eliminarCurso(Long id) throws Exception{
			Curso auxiliar =new Curso();
			auxiliar=buscarCurso(id);
			cursoRepository.delete(auxiliar);	
		}

		@Override
		public void modificarCurso(Curso curso) {
			cursoRepository.save(curso);
		}

		@Override
		public List<Curso> listarCursos() {
			GRUPO04.info("ingresando al metodo mostrar");
			List<Curso> auxiliar = new ArrayList<>();
			auxiliar=(List<Curso>) cursoRepository.findAll();	
			return auxiliar;
		}

		

		@Override
		public Curso buscarCurso(Long id) throws Exception{
			// TODO Auto-generated method stub
			Curso cursoEncontrado = new Curso();
			cursoEncontrado=cursoRepository.findById(id).orElseThrow(()->new Exception("curso no encontrado"));
			return cursoEncontrado;
		}
}
