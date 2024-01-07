package io.web.member.adapter.out.persistence;

import io.web.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class MemberPersistenceAdapter {

    private final JdbcTemplate jdbcTemplate;

    public int sendMail(Member member) {
        // Todo.. JDBC Template sql 문 직접 입력이 아니라 파일로 뺄 수 없을까?
        return 1;
    }

}
