package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    @Query("select r from Reply  r where r.board.bno=:bno")
    Page<Reply> listOfBoard(Long bno, Pageable pageable);
    //스프링3 부터 @Param 없이 이름만 같으면 알아서 :변수가 변경 된다
}
