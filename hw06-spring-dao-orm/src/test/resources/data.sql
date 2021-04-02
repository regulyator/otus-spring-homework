INSERT INTO AUTHOR (ID, FIO)
VALUES (1, 'Peter Watts test author');
INSERT INTO AUTHOR (ID, FIO)
VALUES (2, 'Robert Hainline test author');
INSERT INTO AUTHOR (ID, FIO)
VALUES (3, 'Arkady and Boris Strugatsky test author');
INSERT INTO AUTHOR (ID, FIO)
VALUES (4, 'Vernor Vinge test author');

INSERT INTO GENRE (ID, CAPTION)
VALUES (1, 'Horror test genre');
INSERT INTO GENRE (ID, CAPTION)
VALUES (2, 'Fantasy test genre');
INSERT INTO GENRE (ID, CAPTION)
VALUES (3, 'Sci-Fi test genre');

INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE, ID_AUTHOR)
VALUES (1, 'Blindsight book test', 3, 1);
INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE, ID_AUTHOR)
VALUES (2, 'The Moon Is a Harsh Mistress book test', 3, 2);
INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE, ID_AUTHOR)
VALUES (3, 'Prisoners of Power book test', 2, 3);