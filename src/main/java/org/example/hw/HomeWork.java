package org.example.hw;

import org.example.hw.enity.Post;
import org.example.hw.enity.PostComment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Используя hibernate, создать таблицы:
 * 1. Post (публикация) (id, title)
 * 2. PostComment (комментарий к публикации) (id, text, post_id)
 *
 * Написать стандартные CRUD-методы: создание, загрузка, удаление.
 *
 * Доп. задания:
 * 1. * В сущностях post и postComment добавить поля timestamp с датами.
 * 2. * Создать таблицу users(id, name) и в сущностях post и postComment добавить ссылку на юзера.
 * 3. * Реализовать методы:
 * 3.1 Загрузить все комментарии публикации
 * 3.2 Загрузить все публикации по идентификатору юзера
 * 3.3 ** Загрузить все комментарии по идентификатору юзера
 * 3.4 **** По идентификатору юзера загрузить юзеров, под чьими публикациями он оставлял комменты.
 * // userId -> List<User>
 *
 *
 * Замечание:
 * 1. Можно использовать ЛЮБУЮ базу данных (например, h2)
 * 2. Если запутаетесь, приходите в группу в телеграме или пишите мне @inchestnov в личку.
 */

public class HomeWork {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure();
        try(SessionFactory sessionFactory = configuration.buildSessionFactory()) {

            insertTablePost(sessionFactory);
            insertTablePostComment(sessionFactory);


            updateTablePost(sessionFactory);
            updateTablePostComment(sessionFactory);


            Post post = new Post();
            PostComment comment = new PostComment();

//            deleteTable(sessionFactory, post);
//            deleteTable(sessionFactory, comment);

            printTable(sessionFactory, post);
            printTable(sessionFactory, comment);
        }
    }

    private static void updateTablePostComment(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()) {
            PostComment toUpdate = session.find(PostComment.class, 6);
            toUpdate.setText("Updated comment #6");
            Transaction tx = session.beginTransaction();
            session.merge(toUpdate);
            tx.commit();
        }
    }

    private static void insertTablePostComment(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            for (int i = 1; i <= 10; i++) {
                PostComment comment = new PostComment();
                comment.setId((long) i);
                comment.setText("Comment #" + i);
                comment.setPost(session.find(Post.class, ThreadLocalRandom.current().nextInt(1,11)));
                session.persist(comment);
            }
            tx.commit();
        }
    }

    private static void deleteTable(SessionFactory sessionFactory, Object toDelete) {
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();

            for (int i = 1; i <= 10; i++) {
                toDelete = session.find(toDelete.getClass(), i);
                session.remove(toDelete);
            }
            tx.commit();
        }
    }

    private static void printTable(SessionFactory sessionFactory, Object print) {
        try(Session session = sessionFactory.openSession()) {
            for (int i = 1; i <= 10; i++) {
                print = session.find(print.getClass(), i);
                System.out.println(print);
            }
        }
    }
    private static void insertTablePost(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            for (int i = 1; i <= 10; i++) {
                Post post = new Post();
                post.setId((long) i);
                post.setName("Post #" + i);
                session.persist(post);
            }
            tx.commit();
        }
    }
    private static void updateTablePost(SessionFactory sessionFactory){
        try(Session session = sessionFactory.openSession()) {
            Post toUpdate = session.find(Post.class, 6);
            toUpdate.setName("Updated post #6");
            Transaction tx = session.beginTransaction();
            session.merge(toUpdate);
            tx.commit();
        }
    }
}
