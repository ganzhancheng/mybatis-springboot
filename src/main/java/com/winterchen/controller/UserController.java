package com.winterchen.controller;

import com.winterchen.service.user.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/8/16.
 */
@Controller
@RequestMapping(value = "/data")
public class UserController {

    @Autowired
    private DataService dataService;

    @GetMapping("/add")
    public int addUser(String corpid){

        dataService.addData(corpid);

        return 0;
    }

    @GetMapping("/check")
    public int check(String corpid){

        dataService.check(corpid);

        return 0;
    }


    @GetMapping("isuse")
    public int isuse(){
        dataService.isuse();
        return 0;

    }



}
