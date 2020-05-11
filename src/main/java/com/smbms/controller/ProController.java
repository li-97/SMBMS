package com.smbms.controller;

import com.smbms.pojo.Provider;
import com.smbms.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/pro")
public class ProController {
    @Autowired
   private ProviderService providerService;
    @RequestMapping("/list")
    @ResponseBody
    public List<Provider> list(){
        return providerService.findPros(null,null);
    }
    @RequestMapping("/prosDeatils")
    public String prosDeatils(Model model,String queryProCode,String queryProName){
        List<Provider> pros = providerService.findPros(queryProCode,queryProName);
        model.addAttribute("providerList",pros);
        return "providerlist";
    }
}
