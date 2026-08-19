package br.com.ferrogestao.controller;

import br.com.ferrogestao.service.BusinessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public String business(BusinessException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "error/business";
    }
}