package com.mycompany.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {
  @GetMapping("/Chat")
  public String chatView() {
    return "forward:/chat.html";
  }
}