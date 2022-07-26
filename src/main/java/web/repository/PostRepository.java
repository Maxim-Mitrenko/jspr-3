package web.repository;

import org.springframework.stereotype.Repository;
import web.exception.NotFoundException;
import web.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class PostRepository implements RepositoryInterface {

    private final List<Post> posts = new CopyOnWriteArrayList<>();
    private final AtomicInteger size = new AtomicInteger(0);

    @Override
    public List<Post> all() {
        return posts;
    }

    @Override
    public Optional<Post> getById(long id) {
        return posts.stream()
                .filter(x -> x.getId() == id)
                .findAny();
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(size.addAndGet(1));
            posts.add(post);
        } else {
            final var found = getById(post.getId()).orElseThrow(NotFoundException::new);
            final var index = posts.indexOf(post);
            posts.remove(found);
            posts.add(index, post);
        }
        return post;
    }

    @Override
    public void removeById(long id) {
        Post post = getById(id).orElseThrow(NotFoundException::new);
        post.setRemoved(true);

    }
}
