package web.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.board.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}