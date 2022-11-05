package net.codejava;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppController {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		List<Book> listBooks=bookRepo.findAll();
		model.addAttribute("listBooks",listBooks);
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "signup_form";
	}
	
	@GetMapping("/login")
	public String showLoginForm(Model model) {
		model.addAttribute("login", new Login());
		return "loginform";
	}
	
	@GetMapping("/logout")
	public String logout(Model model, HttpSession session) {
		session.removeAttribute("email");
		List<Book> listBooks=bookRepo.findAll();
		model.addAttribute("listBooks",listBooks);
		return "index";
	}
	
	@GetMapping("/addform")
	public String showAddForm(Model model) {
		Book b = new Book();
		model.addAttribute("book", b);
		model.addAttribute("mode", "Add book");
		model.addAttribute("btn", "Add");
		return "addform";
	}
	
	@PostMapping("/process_register")
	public String processRegister(Model model, @ModelAttribute("user") User user) {
		User u = userRepo.findByEmail(user.getEmail());
		if(u!=null) {
			model.addAttribute("mes","Email already exists!!!");
			model.addAttribute("user", new User());
			return "signup_form";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepo.save(user);
		model.addAttribute("login", new Login());
		return "loginform";
	}
	
	
	@PostMapping("/process_login")
	public String processlogin(Model model, @ModelAttribute("login") Login login, HttpSession session) {
		User u = userRepo.findByEmail(login.getEmail());
		if( u==null ) {
			model.addAttribute("mes", "Login failed!!!");
			return "loginform";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if( !passwordEncoder.matches(login.getPassword(), u.getPassword())) {
			model.addAttribute("mes", "Login failed!!!");
			return "loginform";
		}
		session.setAttribute("email", u.getEmail());
		List<Book> listBooks=bookRepo.findAll();
		model.addAttribute("listBooks",listBooks);
		return "books";
	}

	
	@GetMapping("/books")
	public String listBooks(Model model) {
		List<Book> listBooks = bookRepo.findAll();
		model.addAttribute("listBooks", listBooks);
		return "books";
	}
	
	
	@GetMapping("/books/view/{id}")
	public String viewBook(Model model, @PathVariable("id") Long id) {
		Book book = bookRepo.getOne(id);
		model.addAttribute("book", book);
		model.addAttribute("mode", "View book");
		model.addAttribute("btn", "Edit");
		return "addform";
	}
	
	
	@PostMapping("/books/edit/{id}")
	public String editBook(Model model, @PathVariable("id") Long id) {
		Book book = bookRepo.getOne(id);
		model.addAttribute("book", book);
		model.addAttribute("mode", "Edit book");
		model.addAttribute("btn", "Save");
		return "addform";
	}
	
	
	@PostMapping("/books/save")
	public ModelAndView addBook(Model model, @ModelAttribute("book") Book book, @RequestParam("image") MultipartFile image) {
		Path path = Paths.get("uploads/");
		try {
			InputStream inputStream = image.getInputStream();
			Files.copy(inputStream, path.resolve(image.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			book.setAnh(image.getOriginalFilename().toLowerCase());
		}
		catch (Exception e) {
			System.out.print(e);
		}
        bookRepo.save(book);
		return new ModelAndView("redirect:" + "/books");
	}
	
	
	@GetMapping("/books/delete/{id}")
	public ModelAndView deleteBook(Model model, @PathVariable("id") Long id) {
		bookRepo.deleteById(id);
		return new ModelAndView("redirect:" + "/books");
	}
}
