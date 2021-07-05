package ru.otus.library.configuration.security.acl;

import org.springframework.context.annotation.DependsOn;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import ru.otus.library.domain.Author;
import ru.otus.library.domain.Genre;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DemoInitAclImpl implements DemoInitAcl {
    private final MongoOperations mongoOperations;
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    public DemoInitAclImpl(MongoOperations mongoOperations, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.mongoOperations = mongoOperations;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }
    public void initAcl() {
        final List<Genre> genres = mongoOperations.findAll(Genre.class);
        final List<Author> authors = mongoOperations.findAll(Author.class);
        genres.forEach(this::initAclIdentityGenre);
        authors.forEach(this::initAclIdentityAuthor);

        initAclEntryGenre(genres);
        initAclEntryAuthor(authors);
    }


    private void initAclIdentityGenre(Genre genre) {
        namedParameterJdbcOperations.update("INSERT INTO acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) " +
                        "VALUES (:object_id_class, :object_id_identity, :parent_object, :owner_sid, :entries_inheriting)",
                new MapSqlParameterSource()
                        .addValue("object_id_class", 1)
                        .addValue("object_id_identity", genre.getId())
                        .addValue("parent_object", null)
                        .addValue("owner_sid", 1)
                        .addValue("entries_inheriting", false));
    }

    private void initAclEntryGenre(List<Genre> genres) {
        SqlParameterSource[] parentBatchSqlParameterSource = new SqlParameterSource[genres.size()];
        for (int i = 0; i < genres.size(); i++) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("acl_object_identity", 100 + i)
                    .addValue("ace_order", 1)
                    .addValue("sid", 1)
                    .addValue("mask", 1)
                    .addValue("granting", true)
                    .addValue("audit_success", true)
                    .addValue("audit_failure", true);
            parentBatchSqlParameterSource[i] = mapSqlParameterSource;
        }

        namedParameterJdbcOperations.batchUpdate("INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) " +
                        "VALUES (:acl_object_identity, :ace_order, :sid, :mask, :granting, :audit_success, :audit_failure)",
                parentBatchSqlParameterSource);
    }


    private void initAclIdentityAuthor(Author author) {
        namedParameterJdbcOperations.update("INSERT INTO acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) " +
                        "VALUES (:object_id_class, :object_id_identity, :parent_object, :owner_sid, :entries_inheriting)",
                new MapSqlParameterSource()
                        .addValue("object_id_class", 2)
                        .addValue("object_id_identity", author.getId())
                        .addValue("parent_object", null)
                        .addValue("owner_sid", 1)
                        .addValue("entries_inheriting", false));
    }

    private void initAclEntryAuthor(List<Author> authors) {
        SqlParameterSource[] parentBatchSqlParameterSource = new SqlParameterSource[authors.size()];
        for (int i = 0; i < authors.size(); i++) {
            MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                    .addValue("acl_object_identity", 103 + i)
                    .addValue("ace_order", 1)
                    .addValue("sid", 1)
                    .addValue("mask", 1)
                    .addValue("granting", true)
                    .addValue("audit_success", true)
                    .addValue("audit_failure", true);
            parentBatchSqlParameterSource[i] = mapSqlParameterSource;
        }

        namedParameterJdbcOperations.batchUpdate("INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask, granting, audit_success, audit_failure) " +
                        "VALUES (:acl_object_identity, :ace_order, :sid, :mask, :granting, :audit_success, :audit_failure)",
                parentBatchSqlParameterSource);
    }
}
