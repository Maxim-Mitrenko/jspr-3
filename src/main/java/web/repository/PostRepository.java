package web.repository;

import org.springframework.stereotype.Repository;
import web.exception.NotFoundException;
import web.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository implements RepositoryInterface {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger size = new AtomicInteger();

    @Override
    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    @Override
    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(size.addAndGet(1));
        } else {
            getById(post.getId()).orElseThrow(NotFoundException::new);
        }
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public void removeById(long id) {
        var result = posts.remove(id);
        if (result == null) throw new NotFoundException();
    }
}
