DROP TABLE IF EXISTS AUTHOR;
CREATE TABLE AUTHOR
(
    ID  BIGINT AUTO_INCREMENT PRIMARY KEY,
    FIO VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS GENRE;
CREATE TABLE GENRE
(
    ID      BIGINT AUTO_INCREMENT PRIMARY KEY,
    CAPTION VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS COMMENT;
CREATE TABLE COMMENT
(
    ID      BIGINT AUTO_INCREMENT PRIMARY KEY,
    CAPTION VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS BOOK;
CREATE TABLE BOOK
(
    ID        BIGINT AUTO_INCREMENT PRIMARY KEY,
    BOOK_NAME VARCHAR(255) NOT NULL,
    ID_GENRE  BIGINT(255)  NOT NULL,
    foreign key (ID_GENRE) references GENRE (ID),
);

DROP TABLE IF EXISTS BOOK_AUTHORS;
CREATE TABLE BOOK_AUTHORS
(
    ID_BOOK    BIGINT(255) NOT NULL,
    ID_AUTHOR BIGINT(255) NOT NULL,
    PRIMARY KEY (ID_BOOK, ID_AUTHOR),
    foreign key (ID_BOOK) references BOOK (ID),
    foreign key (ID_AUTHOR) references AUTHOR (ID)
);

DROP TABLE IF EXISTS BOOK_COMMENTS;
CREATE TABLE BOOK_COMMENTS
(
    ID_BOOK    BIGINT(255) NOT NULL,
    ID_COMMENT BIGINT(255) NOT NULL,
    PRIMARY KEY (ID_BOOK, ID_COMMENT),
    foreign key (ID_BOOK) references BOOK (ID),
    foreign key (ID_COMMENT) references COMMENT (ID)
);