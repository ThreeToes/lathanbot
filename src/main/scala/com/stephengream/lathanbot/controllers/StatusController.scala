/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stephengream.lathanbot.controllers
import com.stephengream.lathanbot.models.StatusObject
import org.springframework.web.bind.annotation.{RestController, RequestMapping, ResponseBody};

/**
 * 
 * @author Stephen Gream
 */
@RestController
class StatusController {
  
  @RequestMapping(Array("/"))
  @ResponseBody
  def index() : StatusObject = {
    val s = new StatusObject();
    s.setMessage("Greetings")
    s
  }
}
