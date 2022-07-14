package web.repository;

import java.util.List;
import java.util.Optional;

import web.model.Post;

public interface RepositoryInterface {

    List<Post> all();

    Optional<Post> getById(long id);

    Post save(Post post);

    void removeById(long id);
}
