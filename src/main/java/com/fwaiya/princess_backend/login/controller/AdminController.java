package com.fwaiya.princess_backend.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
/** * 관리자 컨트롤러 * 테스트용 ** @author yaaan7 * @since 2025-06-26 */
public class AdminController {

    @GetMapping("/admin")
    public String admin() {

        return "admin Controller";
    }
}