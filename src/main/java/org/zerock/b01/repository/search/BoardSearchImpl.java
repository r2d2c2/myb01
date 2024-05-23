package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.expression.spel.ast.Projection;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;
import org.zerock.b01.domain.QReply;
import org.zerock.b01.dto.BoardListReplyCountDTO;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {

        QBoard board = QBoard.board;
        JPQLQuery<Board> query = from(board);
        query.where(board.title.contains("3"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        // 페이징을 위한 page객체
        return new PageImpl<>(list, pageable, count);

    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard board = QBoard.board; // QBoard 객체 생성
        JPQLQuery<Board> query = from(board); // 쿼리 객체 생성

        if((types != null) && (types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder(); // 조건을 동적으로 추가하기 위한 객체
            for(String type : types) {
                switch(type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword)); // title에 키워드 포함 조건
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword)); // content에 키워드 포함 조건
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword)); // writer에 키워드 포함 조건
                        break;
                }
            }
            query.where(booleanBuilder);
        }
        query.where(board.bno.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword,Pageable pageable) {
        QReply reply = QReply.reply;
        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));//리플라이 보드랑 보드 비교
        query.groupBy(board);//left 조인후 그룹바이

        if((types != null) && (types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder(); // 조건을 동적으로 추가하기 위한 객체
            for(String type : types) {
                switch(type) {
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword)); // title에 키워드 포함 조건
                        break;
                    case "c":
                        booleanBuilder.or(board.content.contains(keyword)); // content에 키워드 포함 조건
                        break;
                    case "w":
                        booleanBuilder.or(board.writer.contains(keyword)); // writer에 키워드 포함 조건
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        JPQLQuery<BoardListReplyCountDTO> dtoQuery=query.select(Projections.
                bean(BoardListReplyCountDTO.class,
                        board.bno,board.title,board.writer,board.regDate,
                        reply.count().as("replyCount")));

        this.getQuerydsl().applyPagination(pageable, dtoQuery);
        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();
        long count = dtoQuery.fetchCount();
        return new PageImpl<>(dtoList,pageable,count);
    }

}
