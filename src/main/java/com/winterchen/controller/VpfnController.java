package com.winterchen.controller;

import com.winterchen.service.user.VpfnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/vpfn")
public class VpfnController {

    @Autowired
    VpfnService vpfnService;

    @GetMapping("getMapper")
    public int getMapper(String type) throws IOException {
        vpfnService.getMapper(type);
        return 0;
    }

    @GetMapping("getFromVpfn")
    public int getFromVpfn(String type) throws IOException {
        vpfnService.getFromVpfn(type);
        return 0;
    }

    @GetMapping("updateFile")
    public int updateFile(String type) throws IOException {
        vpfnService.updateFile(type);
        return 0;
    }
}
