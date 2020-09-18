package com.byteCode.core.controler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.byteCode.core.model.Post;
import com.byteCode.core.configuration.Paginas;

@Controller
@RequestMapping("/home")
public class controllerBasic {

	
	//@GetMapping(path= {"/saludar}","/holamundo"})
	
	@GetMapping(path = {"/posts","/"})
	public String saludar(Model model) {
		model.addAttribute("posts",this.getPosts());
		return "index";
		
	}
	
	
	//Normalmente esto se hace con un repositorio
	public List<Post> getPosts(){
		
		ArrayList<Post> Post = new ArrayList<>();
		Post.add(new Post(1,"Some quick example text to build - uno.","http://localhost:83/img/fotogaleria1.jpg", new Date(), "Post Uno"));
		Post.add(new Post(2,"Some quick example text to build - dos","http://localhost:83/img/fotogaleria2.jpg", new Date(), "Post Dos"));
		Post.add(new Post(3,"Some quick example text to build - tres","http://localhost:83/img/fotogaleria3.jpg", new Date(), "Post Tres"));
		Post.add(new Post(4,"Some quick example text to build - cuatro","http://localhost:83/img/fotogaleria4.jpg", new Date(), "Post Cuatro"));
		return Post;
	}
	
	@GetMapping(path="/public")
	public ModelAndView post() {
		ModelAndView modelAndView = new ModelAndView(Paginas.home);
		modelAndView.addObject("posts",this.getPosts());
		return modelAndView; 
	}
	
	@GetMapping(path = {"/postNew"})
	public ModelAndView getForm(){
		return new ModelAndView("form").addObject("post", new Post());
	}
	
	@GetMapping(path = {"/addNewPost"})
	public String getAddNewPost(Post post, Model model){
		List<Post> Post = this.getPosts();
		Post.add(post);
		model.addAttribute("posts",post);
		return "index";
	}
	
	
	//Parametros de petición
	/*
	@GetMapping(path= {"/post"})
	public ModelAndView getPostIndividual(@RequestParam(defaultValue="1", name="id",required=false) int id) {
		ModelAndView modelAndView = new ModelAndView(Paginas.post);
		
		List<Post> postFiltrado = this.getPosts().stream().filter( 
										(post) -> {
											return post.getId() == id;
										}).collect(Collectors.toList());
		
		
		modelAndView.addObject("posts",postFiltrado.get(0));
		return modelAndView; 
	}
	*/
	


	
	/**
	 * Parametros por path
	 * 
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(path= {"/post","/post/{id}"})
	public ModelAndView getPostIndividual(
			@RequestParam(defaultValue="1", name="id",required=false) 
			@PathVariable(required = true, name = "post") int id
			){
		
		ModelAndView modelAndView = new ModelAndView(Paginas.post);
		try { 
			
			
			List<Post> postFiltrado = this.getPosts().stream().filter( 
											(post) -> {
												return post.getId() == id;
											}).collect(Collectors.toList());
			modelAndView.addObject("posts",postFiltrado.get(0));
			
		 }
		catch (IndexOutOfBoundsException e) { 
            System.out.println("Exception: el elmemento o post no existe"); 
        }
	
	    finally { 
	        System.out.println("I am in final block"); 
	    } 
	    return modelAndView; 
	}	

}
