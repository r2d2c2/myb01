package org.zerock.b01;


import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;


@SpringBootTest
@Log4j2
public class DataSourceTests {

    @Autowired
    private DataSource dataSource; // DataSource 클래스는 jakarta로 변경되지 않음(import javax)

    @Test
    public void testConnection() throws Exception {

        @Cleanup // 리소스 자동 닫아주는 코드생성. try-with-resources 문 대체
        Connection con = dataSource.getConnection();
        log.info(con);
        Assertions.assertNotNull(con);

    }



}
