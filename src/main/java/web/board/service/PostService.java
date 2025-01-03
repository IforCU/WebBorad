package web.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.board.entity.Post;
import web.board.repository.PostRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(String title, String content) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post); // id는 자동 증가
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    public Post updatePost(Long id, Post post){
        post.setId(id);
        return postRepository.save(post);
    }

    public void deletePost(Long id ){
        postRepository.deleteById(id);
    }
}
