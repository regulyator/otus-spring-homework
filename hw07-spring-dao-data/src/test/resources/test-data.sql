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

INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE)
VALUES (1, 'Blindsight test', 3);
INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE)
VALUES (2, 'The Moon Is a Harsh Mistress', 3);
INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE)
VALUES (3, 'Prisoners of Power', 2);
INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE)
VALUES (4, 'Prisoners of Power, Blindsight', 2);
INSERT INTO BOOK (ID, BOOK_NAME, ID_GENRE)
VALUES (5, 'The Moon Is a Harsh Mistress, Prisoners of Power', 2);

INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (1, 1);
INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (2, 2);
INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (3, 3);
INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (4, 3);
INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (4, 1);
INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (5, 2);
INSERT INTO BOOK_AUTHORS (ID_BOOK, ID_AUTHOR)
VALUES (5, 3);

insert into comment (id, caption, id_book)
values (1, 'nice book!', 1);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (2, 'So complicated', 1);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (3, 'atata', 1);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (4, 'Mooooooon!', 2);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (5, 'What a brilliant book!', 2);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (6, 'ohhhhh', 3);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (7, 'Not so bad', 3);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (8, 'Another comment', 4);
INSERT INTO COMMENT (ID, CAPTION, ID_BOOK)
VALUES (9, 'And more comment', 5);