import Accordion from "react-bootstrap/Accordion";
import BookList from "./book/BookList";
import GenreList from "./genre/GenreList";
import AuthorList from "./author/AuthorList";
import {Container} from "react-bootstrap";


export default function Main() {
    return (
        <Container fluid="sm">
            <Accordion>
                <h2>Welcome to library!</h2>
                <BookList title='Books' eventKeyAccording="0"/>
                <GenreList title='Genres' eventKeyAccording="1"/>
                <AuthorList title='Authors' eventKeyAccording="2"/>
            </Accordion>
        </Container>
    );


}
