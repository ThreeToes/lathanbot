/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stephengream.lathanbot.controllers
import com.stephengream.lathanbot.models.StatusObject
import org.springframework.web.bind.annotation.{RestController, RequestMapping};

/**
 * 
 * @author Stephen Gream
 */
@RestController
class StatusController {
  
  @RequestMapping(Array("/"))
  def index() : StatusObject = {
    new StatusObject("OK");
  }
}
