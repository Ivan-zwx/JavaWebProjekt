package hr.algebra.javawebprojekt.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DetailedErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = (status != null) ? Integer.valueOf(status.toString()) : 0;

        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exceptionType = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object servletName = request.getAttribute(RequestDispatcher.ERROR_SERVLET_NAME);

        if (statusCode == 404 && (message == null || message.toString().isEmpty())) {
            message = "Not Found";
        }

        model.addAttribute("statusCode", statusCode != 0 ? statusCode : "None");
        model.addAttribute("message", message != null ? message : "None");
        model.addAttribute("exceptionType", exceptionType != null ? exceptionType : "None");
        model.addAttribute("exception", exception != null ? exception : "None");
        model.addAttribute("servletName", servletName != null ? servletName : "None");

        return "error";
    }
}

