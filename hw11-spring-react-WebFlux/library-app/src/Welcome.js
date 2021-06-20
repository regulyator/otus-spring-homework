import 'bootstrap/dist/css/bootstrap.min.css';
import React from "react";
import Accordion from 'react-bootstrap/Accordion';

import AuthorsList from "./components/author/AuthorList";
import GenreList from "./components/genre/GenreList";
import BookList from "./components/book/BookList";

export default class Welcome extends React.Component {

    render() {
        return (
            <Accordion>
                <h2>Welcome to library!</h2>
                <BookList title='Books' eventKeyAccording="0"/>
                <GenreList title='Genres' eventKeyAccording="1"/>
                <AuthorsList title='Authors' eventKeyAccording="2"/>
            </Accordion>
        );
    }


}
