package com.example.lab6.view;

import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Route(value = "mainPage.it")
public class MainWizardView extends VerticalLayout {
    private TextField tf1, tf2;
    private Select<String> pos, sch, hou;
    private RadioButtonGroup rd1;
    private Button btn1, btn2, btn3, btn4, btn5;
    private Notification nf;
    private Wizards wis;
    private int wantIndex = 0;

    public MainWizardView(){
        wis = new Wizards();
        tf1 = new TextField();
        tf1.setPlaceholder("Fullname");

        rd1 = new RadioButtonGroup<>();
        rd1.setLabel("Gender :");
        rd1.setItems("Male", "Female");

        pos = new Select<>();
        pos.setPlaceholder("Position");
        pos.setItems("", "Student", "Teacher");

        tf2 = new TextField();
        tf2.setLabel("Dollars");
        tf2.setPrefixComponent(new Div(new Text("$")));

        sch = new Select<>();
        sch.setPlaceholder("School");
        sch.setItems("","Hogwarts", "Beauxbatons", "Durmstrang");

        hou = new Select<>();
        hou.setPlaceholder("House");
        hou.setItems("", "Gryffindor", "Ravenclaw", "Hufflepuff", "Slytherin");

        btn1 = new Button("<<");
        btn2 = new Button("Create");
        btn3 = new Button("Update");
        btn4 = new Button("Delete");
        btn5 = new Button(">>");

        nf = new Notification();
        HorizontalLayout h1 = new HorizontalLayout();
        h1.add(btn1, btn2, btn3, btn4, btn5);
        add(tf1, rd1, pos, tf2, sch, hou);
        this.add(h1);
        this.fetchData();

        btn1.addClickListener(event -> {
            //<<
            if (wantIndex == 0) {
                wantIndex = 0;
            } else {
                wantIndex -= 1;
            }
            this.onTimeData();
        });

        btn5.addClickListener(event -> {
            if (wantIndex == wis.getModel().size() - 1) {
                wantIndex = wis.getModel().size() - 1;
            } else {
                wantIndex += 1;
            }
            this.onTimeData();
        });
        btn2.addClickListener(event -> {
            //create
            String name = tf1.getValue();
            int money = Integer.parseInt(tf2.getValue());
            String sex = rd1.getValue().equals("Male") ? "m" : "f";
            String position = pos.getValue().equals("Teacher") ? "teacher" : "student";
            String school = sch.getValue();
            String house = hou.getValue();
            Wizard newbie = new Wizard(null, sex, name, school, house, position, money);
            String output = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/addWizard")
                    .body(Mono.just(newbie), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            nf.show(output);
            this.fetchData();
            this.onTimeData();
        });
        btn3.addClickListener(event -> {
            //update
            String name = tf1.getValue();
            int money = Integer.parseInt(tf2.getValue());
            String sex = rd1.getValue().equals("Male") ? "m" : "f";
            String position = pos.getValue().equals("Teacher") ? "teacher" : "student";
            String school = sch.getValue();
            String house = hou.getValue();
            Wizard updateWis = new Wizard(wis.getModel().get(wantIndex).get_id(), sex, name, school, house, position, money);
            String output = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/updateWizard")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(updateWis), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            nf.show(output);
            this.fetchData();
            this.onTimeData();
        });
        btn4.addClickListener(event -> {
            //delete
            String output = WebClient.create()
                    .post()
                    .uri("http://localhost:8080/deleteWizard")
                    .body(Mono.just(wis.getModel().get(wantIndex)), Wizard.class)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            nf.show(output);
            this.wantIndex = this.wantIndex != 0?this.wantIndex-1:this.wantIndex+1;
            this.fetchData();
            this.onTimeData();
        });
    }

    private void fetchData() {
        ArrayList<Wizard> wisAll = WebClient.create()
                .get()
                .uri("http://localhost:8080/wizards")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<ArrayList<Wizard>>() {})
                .block();
        wis.setModel(wisAll);
    }

    private void onTimeData() {
        if (wis.getModel().size() != 0) {
            this.tf1.setValue(wis.getModel().get(wantIndex).getName());
            this.rd1.setValue(wis.getModel().get(wantIndex).getSex().equals("m") ? "Male" : "Female");
            // if else shorthand เช็ค sex กับ position
            this.tf2.setValue(String.valueOf(wis.getModel().get(wantIndex).getMoney()));
            this.pos.setValue(wis.getModel().get(wantIndex).getPosition().equals("teacher") ? "Teacher" : "Student");
            this.sch.setValue(wis.getModel().get(wantIndex).getSchool());
            this.hou.setValue(wis.getModel().get(wantIndex).getHouse());
        }
        else{
            this.tf1.setValue("");
            this.rd1.setValue("");
            this.tf2.setValue("");
            this.pos.setValue("");
            this.sch.setValue("");
            this.hou.setValue("");
        }
    }
}