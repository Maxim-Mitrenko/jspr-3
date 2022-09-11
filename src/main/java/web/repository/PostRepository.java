package web.repository;

import org.springframework.stereotype.Repository;
import web.exception.NotFoundException;
import web.model.Post;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class PostRepository implements RepositoryInterface {

    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicInteger size = new AtomicInteger(0);

    @Override
    public List<Post> all() {
        return posts.values()
                .stream()
                .filter(x -> !x.isRemoved())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Post> getById(long id) {
        var post = posts.get(id);
        return post == null || post.isRemoved() ? Optional.empty() : Optional.of(post);
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(size.addAndGet(1));
        } else {
            var found = getById(post.getId()).orElseThrow(NotFoundException::new);
            if (found.isRemoved()) throw new NotFoundException();
        }
        posts.put(post.getId(), post);
        return post;
    }

    @Override
    public void removeById(long id) {
        Post post = getById(id).orElseThrow(NotFoundException::new);
        post.setRemoved(true);

    }
}
