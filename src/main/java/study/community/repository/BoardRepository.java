package study.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.community.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

}
