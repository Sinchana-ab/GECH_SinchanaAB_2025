//package com.construction.companyManagement.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.construction.companyManagement.service.ContactMessageService;
//
//@Controller
//@RequestMapping("/admin/messages")
//public class AdminContactController {
//
//    @Autowired
//    private ContactMessageService contactMessageService;
//
//    @GetMapping
//    public String viewMessages(Model model) {
//        model.addAttribute("messages", contactMessageService.getAllMessages());
//        return "admin/all-messages";
//    }
//}
