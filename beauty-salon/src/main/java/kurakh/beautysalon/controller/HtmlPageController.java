package kurakh.beautysalon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlPageController {

    @RequestMapping("/")
    public String mainPage(){
        return "main.html";
    }

    @RequestMapping("/all-products")
    public String allProductsPage(){
        return "allProducts.html";
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN')")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/admin")
    public String adminPage(){
        return "adminPage.html";
    }

    @RequestMapping("/user/login")
    public String logInPage(){
        return "logIn.html";
    }

    @RequestMapping("/create-product")
    public String productPage(){
        return "adminProductPage.html";
    }

    @RequestMapping("/create-category")
    public String categoryPage(){
        return "adminCategoryPage.html";
    }

}
