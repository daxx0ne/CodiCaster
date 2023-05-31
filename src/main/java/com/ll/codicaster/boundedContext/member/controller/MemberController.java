package com.ll.codicaster.boundedContext.member.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ll.codicaster.base.rq.Rq;
import com.ll.codicaster.base.rsData.RsData;
import com.ll.codicaster.boundedContext.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usr/member")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final Rq rq;

	@PreAuthorize("isAnonymous()")
	@GetMapping("/login")
	public String showLogin() {
		return "usr/member/login";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/newInfo")
	public String showNewInfo() {
		return "usr/member/newInfo";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/newInfo")
	public String updateInfo(@RequestParam String nickname, @RequestParam(required = false) String bodytype) {
		memberService.updateMemberInfo(rq.getLoginedMemberId(), nickname, bodytype);
		return "redirect:/usr/main";
	}

	// 중복 확인 요청을 처리하는 핸들러
	@ResponseBody
	@GetMapping("/checkNickname")
	public RsData<String> checkNickname(@RequestParam String nickname) {
		return memberService.checkNickname(nickname);
	}
}