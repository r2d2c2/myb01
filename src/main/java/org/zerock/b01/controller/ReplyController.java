package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.ReplyDTO;

import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    public ResponseEntity<Map<String, Long>> register(@RequestBody ReplyDTO replyDTO) {
        Map<String, Long> resultMap = Map.of("rno", 10L);
        return ResponseEntity.ok(resultMap);
    }


}
