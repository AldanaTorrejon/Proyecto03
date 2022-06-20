package ar.edu.unju.edm.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unju.edm.model.Curso;
import ar.edu.unju.edm.service.ICursoService;

@Controller
public class CursosController {
	private static final Log GRUPO04=LogFactory.getLog(CursosController.class); //.getLog(UsuarioController.class);//constante con mayuscula 

	@Autowired
	Curso nuevoCurso;
	
	@Autowired
    ICursoService serviceCurso;
	
	@GetMapping("/otroCursos")//entra
	public ModelAndView addCurso() {
		ModelAndView vista = new ModelAndView("CargaCurso");//pasa nombre de la lista a pasar
		//vista.addObject("nuevoUsuario");
		vista.addObject("curso1", nuevoCurso);
		vista.addObject("band", "false");
		return vista;
	}

	@PostMapping("/guardarCursos")//se recibe
	public String saveCurso(@Valid  @ModelAttribute ("curso1") Curso cursoparaguardar, BindingResult resultado, Model model) { //del modelo viene 1 atributo llamado usuario y lo agarra le indica el tipo y un nombre 
		GRUPO04.info("Ingresando al metodo guardar. Curso: "+ cursoparaguardar.getFechaNacimiento() );
		GRUPO04.info(resultado.getAllErrors());
		if(resultado.hasErrors()) {
			GRUPO04.fatal("Error de validacion");
			model.addAttribute("curso1",cursoparaguardar);
			return "CargaCurso";
		}try {
			serviceCurso.guardarCurso(cursoparaguardar);
		}catch (Exception e){
			model.addAttribute("formCourseMessage", e.getMessage());
			model.addAttribute("curso1", cursoparaguardar);
			model.addAttribute("band", false);
			GRUPO04.error("saliendo del metodo: guardarcursos");
			return "CargaCurso";
		}
		model.addAttribute("formCourseMessage", "Curso guardado correcctamente");
        model.addAttribute("curso1", nuevoCurso);
        model.addAttribute("band", false);
        GRUPO04.error("saliendo del metodo: guardarcursos");
        return "CargaCurso";
	}

	@GetMapping("/ListadoCurso")
	public ModelAndView showCursos() {
		ModelAndView vista = new ModelAndView("ListadoCurso");
		vista.addObject("listaCursos", serviceCurso.listarCursos());
		return vista;
	}

	@GetMapping("/editarCursos/{id}")
	 public ModelAndView edituser(@PathVariable(name="id") Long id) throws Exception{
	    Curso cursoencontrado = new Curso();
	    cursoencontrado=serviceCurso.buscarCurso(id);  
			GRUPO04.info(cursoencontrado.getEmail());  
	    ModelAndView encontrado = new ModelAndView("CargaCurso");
	    encontrado.addObject("curso1", cursoencontrado);
	    GRUPO04.fatal("Saliendo del metodo encontrado curso");
	    encontrado.addObject("editMode",true);
	    return encontrado;
	    }
		

	@PostMapping("/modificarCurso")//se recibe
	public ModelAndView modCurso(@ModelAttribute ("curso1") Curso cursoparamodificar) {
		serviceCurso.modificarCurso(cursoparamodificar);
		ModelAndView vista = new ModelAndView("ListadoCurso");
		vista.addObject("listaCursos", serviceCurso.listarCursos());
		vista.addObject("formUsuarioErrorMessage", "Usuario Guardado Correctamente");
		return vista;
	}
	@GetMapping("/eliminarCurso/{id}")
	public String deleteCourse(@PathVariable(name="id") Long id, Model model) {
		try {
			serviceCurso.eliminarCurso(id);
		}catch(Exception e){
			GRUPO04.error("encontrando: eliminarcursos");
			model.addAttribute("formCourseMessage", e.getMessage());
			return "redirect:/otroCursos";
		}
	
	    return "redirect:/ListadoCurso";
	}

	/*@PostMapping("/sacarCurso")//se recibe
	public String sacarCurso(@Valid  @ModelAttribute ("curso") Curso cursoparasacar, BindingResult resultado, Model model) { //del modelo viene 1 atributo llamado usuario y lo agarra le indica el tipo y un nombre 
		GRUPO04.info("Ingresando al metodo sacar Curso: "+ cursoparasacar.getFechaNacimiento() );
		if(resultado.hasErrors()) {
			GRUPO04.fatal("Error de validacion");
			model.addAttribute("curso",cursoparasacar);
			return "CargaCurso";
		}
		for(int i=0;i<lista.getListado().size();i++) {
			if(lista.getListado().get(i).getId().equals(cursoparasacar.getId())) {
				lista.getListado().remove(i);
			}
		};

		GRUPO04.error("Tamaño del Listado: " + lista.getListado().size());
		return "redirect:/ListadoCurso";
	}*/

}
