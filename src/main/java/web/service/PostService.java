package web.service;

import org.springframework.stereotype.Service;
import web.exception.NotFoundException;
import web.model.Post;
import web.repository.RepositoryInterface;

import java.util.List;

@Service
public class PostService {

    private final RepositoryInterface repository;

    public PostService(RepositoryInterface repository) {
        this.repository = repository;
    }

    public List<Post> all() {
        return repository.all();
    }

    public Post getById(long id) {
        return repository.getById(id).orElseThrow(NotFoundException::new);
    }

    public Post save(Post post) {
        return repository.save(post);
    }

    public void removeById(long id) {
        repository.removeById(id);
    }
}
