package study.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.community.domain.User;


public interface UserRepository extends JpaRepository<User,Long> {
}
