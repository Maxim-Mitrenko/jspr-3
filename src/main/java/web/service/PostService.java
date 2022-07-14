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
        return repository
                .all()
                .stream()
                .filter(x -> !x.isRemoved())
                .toList();
    }

    public Post getById(long id) {
        var post = repository.getById(id).orElseThrow(NotFoundException::new);
        if (post.isRemoved()) throw new NotFoundException();
        return post;
    }

    public Post save(Post post) {
        if (post.getId() != 0) {
            var found = repository.getById(post.getId()).orElseThrow(NotFoundException::new);
            if (found.isRemoved()) throw new NotFoundException();
        }
        return repository.save(post);
    }

    public void removeById(long id) {
        var post = repository.getById(id).orElseThrow(NotFoundException::new);
        if (post.isRemoved()) throw new NotFoundException();
        repository.removeById(id);
    }
}
