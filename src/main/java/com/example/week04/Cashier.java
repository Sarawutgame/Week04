package com.example.week04;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cashier {
    @RequestMapping(value = "/getChange/{money_change}", method = RequestMethod.GET)
    public Change getChange(@PathVariable("money_change") int money){
        Change money_change = new Change();
        if(money >= 1000){
            int ans = money / 1000;
            money = money - (1000 * ans);
            money_change.setB1000(ans);
        }
        if(money >= 500){
            int ans = money / 500;
            money = money - (500 * ans);
            money_change.setB500(ans);
        }
        if(money >= 100){
            int ans = money / 100;
            money = money - (100 * ans);
            money_change.setB100(ans);
        }
        if(money >= 20){
            int ans = money / 20;
            money = money - (20 * ans);
            money_change.setB20(ans);
        }
        if(money >= 10){
            int ans = money / 10;
            money = money - (10 * ans);
            money_change.setB10(ans);
        }
        if(money >= 5){
            int ans = money / 5;
            money = money - (5 * ans);
            money_change.setB5(ans);
        }
        if(money >= 5){
            int ans = money / 1;
            money = money - (1 * ans);
            money_change.setB1(ans);
        }

        return money_change;
    }
}
