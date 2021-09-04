package ru.zhuk.maks.test_project.repo;

import org.springframework.data.repository.CrudRepository;
import ru.zhuk.maks.test_project.models.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

}
