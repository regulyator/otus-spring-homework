package ru.otus.questions.services.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import ru.otus.questions.domain.QuizResult;
import ru.otus.questions.services.QuizResultHolder;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Сервис хранения результатов пользователей должен")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class QuizResultHolderImplMapTest {
    @Autowired
    private QuizResultHolder quizResultHolder;

    @Test
    @DisplayName(" добавлять новый результат теста и сохранять его под именем пользователя")
    void shouldStore() {
        final String userName1 = "User1";
        final String userName2 = "User2";
        QuizResult quizResult1 = new QuizResult(userName1, 1, 2, true);
        QuizResult quizResult2 = new QuizResult(userName2, 1, 2, false);
        quizResultHolder.putQuizResult(quizResult1);
        quizResultHolder.putQuizResult(quizResult2);
        Collection<QuizResult> storedResult1 = quizResultHolder.getUserResult(userName1);
        Collection<QuizResult> storedResult2 = quizResultHolder.getUserResult(userName2);

        assertEquals(1, storedResult1.size());
        assertEquals(1, storedResult2.size());

        storedResult1.forEach(quizResult -> assertEquals(quizResult1, quizResult));
        storedResult2.forEach(quizResult -> assertEquals(quizResult2, quizResult));
    }

    @Test
    @DisplayName(" не давать добавлять результаты через коллекцию результатов")
    void shouldReturnUnmodifiableList() {
        final String userName1 = "User1";
        QuizResult quizResult1 = new QuizResult(userName1, 1, 2, true);
        quizResultHolder.putQuizResult(quizResult1);
        Collection<QuizResult> viewResultUser = quizResultHolder.getUserResult(userName1);
        Collection<QuizResult> viewAllResult = quizResultHolder.getAllResult();
        QuizResult quizResult2 = new QuizResult(userName1, 1, 2, false);

        assertThrows(UnsupportedOperationException.class, () -> viewResultUser.add(quizResult2));
        assertThrows(UnsupportedOperationException.class, () -> viewAllResult.add(quizResult2));

        assertEquals(1, viewResultUser.size());
        assertEquals(1, viewAllResult.size());
        viewResultUser.forEach(quizResult -> assertEquals(quizResult1, quizResult));
        viewAllResult.forEach(quizResult -> assertEquals(quizResult1, quizResult));


    }

    @Test
    @DisplayName(" отдавать результаты всех пользователей")
    void putQuizResult() {
        final String userName1 = "User1";
        final String userName2 = "User2";
        QuizResult quizResult1 = new QuizResult(userName1, 1, 2, true);
        QuizResult quizResult2 = new QuizResult(userName2, 1, 2, false);
        quizResultHolder.putQuizResult(quizResult1);
        quizResultHolder.putQuizResult(quizResult2);
        Collection<QuizResult> storedResult = quizResultHolder.getAllResult();

        assertEquals(2, storedResult.size());
        assertTrue(storedResult.contains(quizResult1));
        assertTrue(storedResult.contains(quizResult2));
    }

    /**
     * Поднимаем контекст только с интересующим нас бином
     */
    @Configuration
    static class TestConfiguration {
        @Bean
        QuizResultHolder quizResultHolder() {
            return new QuizResultHolderImplMap();
        }
    }
}