package SJUCapstone.BE.user.repository;

import SJUCapstone.BE.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);
}
