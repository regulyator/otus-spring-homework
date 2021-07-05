INSERT INTO acl_sid (id, principal, sid) VALUES
(1, true, 'admin'),
(2, true, 'user');

INSERT INTO acl_class (id, class, class_id_type) VALUES
(1, 'ru.otus.library.domain.Genre', 'java.lang.String'),
(2, 'ru.otus.library.domain.Author', 'java.lang.String'),
(3, 'ru.otus.library.domain.Book', 'java.lang.String');