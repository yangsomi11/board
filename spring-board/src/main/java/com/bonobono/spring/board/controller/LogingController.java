package com.bonobono.spring.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogingController {
	
	@GetMapping(value="/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("login")
	public String login(HttpSession session, @RequestParam("id") String id, @RequestParam("pw") String pw) {

		final String DBID = "admin";
		final String DBPW = "1234";
		
		if(id.equals(DBID) && pw.equals(DBPW)) {
			// 로그인 성공 -> 로그인 정보 세션 등록
			session.setAttribute("id", id);
			return "redirect:/index";
		} else {
			// 로그인 실패 -> 로그인 호면으로 리다이렉트
			return "redirect:/login";
		}
	}
}
