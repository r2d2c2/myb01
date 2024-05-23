package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,100).forEach(i-> {
        Board board = Board.builder()
                .title("title"+i)
                .content("content"+i)
                .writer("writer"+(i%10))
                .build();

        Board result = boardRepository.save(board);
        log.info("BNO: " + result.getBno());
        });

    }

    @Test
    public void testSelect() {
        Long bno = 10L;
        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();
        log.info(board);
    }

    @Test
    public void testUpdate() {
        Long bno = 10L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update title", "update content");
        boardRepository.save(board);
    }

    @Test
    public void testDelete() {
        Long bno = 10L;
        boardRepository.deleteById(bno);
    }

    @Test
    public void testPaging() { // 끌올
        Pageable pageable
                = PageRequest
                .of(0, 10, Sort.by("bno").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("total size: " + result.getSize());

        List<Board> list = result.getContent();
        list.forEach(board -> log.info(board));


    }

    @Test
    public void testSearch1() {

        Pageable pageable
                = PageRequest
                .of(1, 10, Sort.by("bno").descending());

        boardRepository.search1(pageable);
    }


    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};
        String keyword = "3";
        Pageable pageable = PageRequest.of(2, 10, Sort.by("bno").descending());
        Page<Board> result=  boardRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalPages());
        log.info(result.getSize());
        log.info(result.getNumber());
        log.info(result.hasPrevious() + "__" + result.hasNext());

        result.getContent().forEach(board -> log.info(board));
    }

    @Test
    public void searchWithReplyCount테스트(){
        String[] types = {"t", "c", "w"};
        String keyword = "3";
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());

        Page<BoardListReplyCountDTO> dto = boardRepository.searchWithReplyCount(types, keyword, pageable);
//        dto.getContent().forEach(board -> log.info("이거 테스트 임 @@@@ = {}",board));
        log.info(dto.getTotalPages()); //10
        log.info(dto.getSize()); //10
        log.info(dto.getNumber()); //1
        log.info(dto.hasPrevious() + "__" + dto.hasNext()); //ture__ture

        dto.getContent().forEach(board -> log.info("이거 테스트 임 @@@@ = {}",board));

    }


}
