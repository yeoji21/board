package com.practice.board;

import com.practice.board.domain.member.Member;
import com.practice.board.domain.member.SessionConst;
import com.practice.board.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@WebMvcTest
@Slf4j
class BoardApplicationTests {

	@Autowired
	MemberMapper memberMapper;
	protected MockHttpSession session;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

//	@Test
//	@Order(1)
//	void setSession() {
//		session = new MockHttpSession();
//		Member member = memberMapper.findById(6L);
//		log.warn("member = {}", member.toString());
//		session.setAttribute(SessionConst.LOGIN_MEMBER,member);
//		Member findMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
//		log.warn("findMember = {}", findMember.toString());
//		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//	}

	void init() {
		session = new MockHttpSession();
		Member member = memberMapper.findById(6L);
		session.setAttribute(SessionConst.LOGIN_MEMBER, member);
	}

	@Test
	void initTest() {
		init();
		Member findMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
		log.warn("findMember = {}", findMember.toString());
	}


}
