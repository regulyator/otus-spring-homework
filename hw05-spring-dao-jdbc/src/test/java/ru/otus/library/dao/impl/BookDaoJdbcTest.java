package ru.otus.library.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import ru.otus.library.dao.BookDao;

@DisplayName("BookDaoJdbcTest should ")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    @Autowired
    @SpyBean
    private BookDao bookDao;

    public static final long EXIST_ID_BOOK = 1;
    public static final long NON_EXIST_ID_BOOK = 8;
    public static final long EMPTY_ID_BOOK = 0L;

}