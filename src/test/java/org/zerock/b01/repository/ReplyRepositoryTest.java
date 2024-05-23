package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;


@SpringBootTest
@Log4j2
class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void testSave() {
        Long bno=67L;
        Board board=Board.builder().bno(bno).build();

        Reply reply = Reply.builder().board(board).replyText("이거 댓글임3").build();
        replyRepository.save(reply);
    }

    @Test
    @Transactional//reply 테이블 검색에서 보드 테이블 까지 검색 할때 사용
    public void testFindByBno() {
        Long bno=67L;
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("rno").descending());
        Page<Reply> replies = replyRepository.listOfBoard(bno, pageRequest);
        replies.getContent().forEach(System.out::println);
    }

}