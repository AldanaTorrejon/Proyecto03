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
import ar.edu.unju.edm.model.Usuario;
import ar.edu.unju.edm.service.IUsuarioService;

@Controller
public class UsuarioController {
	private static final Log 	GRUPO04=LogFactory.getLog(UsuarioController.class);//constante con mayuscula

	@Autowired
	Usuario nuevoUsuario;
	
	@Autowired
	IUsuarioService serviceUsuario;
	
	@GetMapping("/otroUsuario")//entra
	public ModelAndView addUser() {
		ModelAndView vista = new ModelAndView("CargaUsuario");//pasa nombre de la lista a pasar
		//vista.addObject("nuevoUsuario");
		vista.addObject("usuario", nuevoUsuario);
		vista.addObject("editMode", false);
		return vista;
	}
	
	@PostMapping("/guardarusuario")
	public String saveUser(@Valid @ModelAttribute("usuario") Usuario usuarioparaguardar, BindingResult resultado, Model model) { //del modelo viene 1 atributo llamado usuario y lo agarra le indica el tipo y un nombre 
	
		GRUPO04.info("Ingresando al metodo guardar. Usuario: "+usuarioparaguardar.getFechaNacimiento());
		
		if(resultado.hasErrors()) {
			GRUPO04.fatal("Error de Validacion");
			model.addAttribute("usuario",usuarioparaguardar);
			return "CargaUsuario";
		}
		try { //controla si algo se ejecuta bien
			serviceUsuario.guardarUsuario(usuarioparaguardar);
		}catch(Exception error){ //si no sale por aqui
			model.addAttribute("formUsuarioErrorMessage", error.getMessage());
			model.addAttribute("usuario",usuarioparaguardar);
			GRUPO04.error("No se pudo guardar el usuario");
			return "CargaUsuario";
		}
		model.addAttribute("formUsuarioErrorMessage", "Usuario Guardado Correctamente");
		model.addAttribute("usuario", nuevoUsuario);
		return "CargaUsuario";
	}
	
	@GetMapping("/ListadoUsuario")
	public ModelAndView showUser() {
		ModelAndView vista = new ModelAndView("ListadoUsuario");
		vista.addObject("listaUsuario", serviceUsuario.mostrarUsuarios());
		return vista;
	}
	
	@GetMapping("/eliminarUsuario/{dni}")
	public String deleteUser(@PathVariable(name="dni")Long dni, Model model) {
		try {
			serviceUsuario.eliminarUsuario(dni);
		}catch(Exception error){
			GRUPO04.error("No se pudo eliminar el usuario");
			model.addAttribute("formUsuarioErrorMessage", error.getMessage());
			return "redirect:/otroUsuario";
		}
		return "redirect:/ListadoUsuario";
	}
	
	@GetMapping("/editarUsuario/{dni}")
	public ModelAndView ObtenerFormularioEditarUsuario(Model model, @PathVariable(name="dni")Long dni) throws Exception {
		Usuario usuarioEncontrado = new Usuario();
		usuarioEncontrado = serviceUsuario.buscarUsuario(dni);
		ModelAndView modelView = new ModelAndView("CargaUsuario");
		modelView.addObject("usuario", usuarioEncontrado);
		GRUPO04.error("usuario: "+ usuarioEncontrado.getDni());
		modelView.addObject("editMode", true);
		return modelView;
	}
	
	@PostMapping("/modificarUsuario")
	public ModelAndView postEditarUsuario(@ModelAttribute ("usuario") Usuario usuarioparamodificar) {  
		serviceUsuario.modificarUsuario(usuarioparamodificar);
		ModelAndView vista = new ModelAndView("ListadoUsuario");
		vista.addObject("ListaUsuario", serviceUsuario.mostrarUsuarios());
		vista.addObject("formUsuarioErrorMessage", "Usuario Guardado Correctamente");
		return vista;
	}

}
