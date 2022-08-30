package com.example.week04;

import org.springframework.web.bind.annotation.*;

@RestController
public class MathAPI {
    @RequestMapping(value = "/plus/{n1}/{n2}", method = RequestMethod.GET)
    public String myPlus(@PathVariable("n1") double num_1,
                         @PathVariable("n2") double num_2){
        return (num_1 + num_2) + "";

    }
    @RequestMapping(value = "/minus/{n1}/{n2}", method = RequestMethod.GET)
    public String myMinus(@PathVariable("n1") double num_1,
                         @PathVariable("n2") double num_2){
        return (num_1 - num_2) + "";

    }
    @RequestMapping(value = "/divide/{n1}/{n2}", method = RequestMethod.GET)
    public String myDivide(@PathVariable("n1") double num_1,
                         @PathVariable("n2") double num_2){
        return (num_1 / num_2) + "";

    }
    @RequestMapping(value = "/multi/{n1}/{n2}", method = RequestMethod.GET)
    public String myMulti(@PathVariable("n1") double num_1,
                         @PathVariable("n2") double num_2){
        return (num_1 * num_2) + "";

    }
    @RequestMapping(value = "/mod/{n1}/{n2}", method = RequestMethod.GET)
    public String myMod(@PathVariable("n1") double num_1,
                         @PathVariable("n2") double num_2){
        return (num_1 % num_2) + "";

    }
    @RequestMapping(value = "/max", method = RequestMethod.POST)
    public String myMax(@RequestParam("number1") double number1,
                        @RequestParam("number2") double number2){
        if (number1 >= number2){
            return  number1 + "";
        }
        else{
            return  number2 + "";
        }

    }

}
