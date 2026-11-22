package org.goya.daw2.dwes.d2ud3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
public class Controlador {
	
	private final String [] USUARIOS = {"admin", "rachel", "usuario"};
    private final String [] PASSWORDS = {"admin123", "rachel123", "usuario123"};

    @GetMapping("/")
    public String inicio(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "inicio";
    }
    
    @GetMapping("/login")
    public String login(HttpSession session) {
        if (session.getAttribute("usuario") != null) {
            return "redirect:/";
        }
        return "login";
    }
    
    @PostMapping("/login")
    public String procesarLogin(@RequestParam("username") String username,
                                @RequestParam("password") String password,
                                HttpSession session,
                                Model model) {

        boolean valido = false;

        for (int i = 0; i < USUARIOS.length; i++) {
            if (USUARIOS[i].equals(username) && PASSWORDS[i].equals(password)) {
            	valido = true;
                break;
            }
        }

        if (valido) {
            session.setAttribute("usuario", username);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }
    
    @GetMapping("/pagina1")
    public String pagina1(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "pagina1";
    }

    @GetMapping("/pagina2")
    public String pagina2(HttpSession session) {
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "pagina2";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}

