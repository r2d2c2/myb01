package org.zerock.b01.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.zerock.b01.dto.MemberDTO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Log4j2
public class SampleController {

    // 내부 class
    class SampleDTO {
        private String p1, p2, p3;
        public String getP1() { return p1;}
        public String getP2() { return p2;}
        public String getP3() { return p3;}
    }


    @GetMapping("/ex/ex2")
    public void ex2(Model model) {

        List<String> strList = IntStream.range(1,10).mapToObj(i->"STR"+i)
                .collect(Collectors.toList());

        model.addAttribute("strList", strList);

        Map<String, String> map = new HashMap<>();
        map.put("p1", "p2");
        model.addAttribute("map", map);

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.p1 = "p1p1";
        sampleDTO.p2 = "p2p2";
        sampleDTO.p3 = "p3p3";
        model.addAttribute("sampleDTO", sampleDTO);
    }

    // Model : 스프링 MVC에 사용되는 데이터를 뷰에 전달하는데 사용.
    // addAttribute() 메서드 사용해서 전달.
    // 뷰 템플릿에서는 thymeleaf문법을 사용.
    @GetMapping("/hello")
    public void hello(Model model) {
        log.info("hello");
        model.addAttribute("msg", "Hello World");
    }

    @GetMapping("/ex/ex1")
    public void ex1(Model model) {
        List<String> list = Arrays.asList("111", "222", "CCC");
        model.addAttribute("list", list);
    }

    @GetMapping("/ex/ex3")
    public void ex3(Model model) {
        model.addAttribute("arr", new String[] {"AAA", "BBB", "CCC"});
    }

    @GetMapping("/memInsert")
    public void memInsert() {

    }

    @PostMapping("/memInsert")
    public String memInsert(@ModelAttribute MemberDTO memberDTO, Model model) {
        String info = memberDTO.getName() + ", " + memberDTO.getEmail() + ", " + memberDTO.getAge();
        model.addAttribute("info", info);
        return "result"; //  return "페이지이름";
    }


}
