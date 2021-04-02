package ru.otus.library.service.data.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.library.dao.AuthorDao;
import ru.otus.library.domain.Author;
import ru.otus.library.service.data.AuthorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AuthorServiceImpl.class)
@DisplayName(value = "AuthorServiceImpl should ")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthorServiceImplTest {
    public static final long NEW_ID = 11L;
    private static final String NEW_AUTHOR = "NEW AUTHOR";
    private static final long EMPTY_ID = 0L;
    @MockBean
    private AuthorDao authorDao;
    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("insert new AUTHOR by fio and set id")
    void shouldInsertNewAuthorByFIOAndSetId() {
        when(authorDao.insert(any())).thenReturn(NEW_ID);

        Author newAuthor = authorService.create(NEW_AUTHOR);

        assertThat(NEW_ID).isEqualTo(newAuthor.getId());
    }

}