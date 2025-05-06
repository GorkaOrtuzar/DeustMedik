package com.mycompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReadmeController {
  @GetMapping("/Readme")
  public String readmeView() {
    return "forward:/readme.html";
  }
}