package com.example.lab6.controller;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WizardController {

    @Autowired
    private WizardService wizardService;

    @RequestMapping(value = "/wizards", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List getWizards(){
        List<Wizard> wizardsList = wizardService.retrieveWizards();
        System.out.println(wizardsList);
        return wizardsList;
    }
    @RequestMapping(value = "/addWizard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createWizards(@RequestBody Wizard wizard){
        Wizard wizards = wizardService.createWizard(wizard);
        return "Wizard has been created";
    }

    @RequestMapping(value = "/deleteWizard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String deleteWizards(@RequestBody Wizard wizard){
        Wizard wizards = wizardService.retrieveByID(wizard.get_id());
        boolean Status = wizardService.deleteWizard(wizard);
        return Status?"Wizard has been deleted":"Noting for delete";
    }
    @RequestMapping(value="/updateWizard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String updateWizards(@RequestBody Wizard wizard){
        Wizard wizards = wizardService.retrieveByID(wizard.get_id());
        if(wizards != null){
            wizardService.updateWizard(wizard);
            return "Wizard has been updated";
        }
        else{
            return "Update fail";
        }
    }
}
