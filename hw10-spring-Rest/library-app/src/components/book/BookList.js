import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Book from "./Book";
import {deleteBook, loadAllBooks} from "../../common/ApiServiceBooks";


export default class BookList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eventKeyAccording: props.eventKeyAccording,
            title: props.title,
            books: []
        };

        this.booksListToggle = this.booksListToggle.bind(this);
        this.addBook = this.addBook.bind(this);
    }


    booksListToggle() {
        if (this.state.books.length === 0) {
            loadAllBooks().then(value => this.setState({books: value}));
        }
    }


    addBook() {
        this.setState({
            books: this.state.books.concat({id: null, bookName: ''})
        })
    }

    handleDelete = (book) => {
        deleteBook(book).then(() => loadAllBooks().then((data) => {
            this.setState({books: data})
        }));

    };

    render() {
        return (
            <>
                <Card>
                    <Card.Header>
                        <Accordion.Toggle className="m-2" as={Button} eventKey={this.state.eventKeyAccording}
                                          onClick={this.booksListToggle}>
                            View {this.state.title}
                        </Accordion.Toggle>

                    </Card.Header>
                    <Accordion.Collapse eventKey={this.state.eventKeyAccording}>
                        <>
                            <Card.Body>
                                <div>
                                    {this.state.books.map((book) => (
                                        <Book key={book.id} book={book} onDelete={this.handleDelete}/>
                                    ))}
                                </div>
                            </Card.Body>

                            <Button className="m-2 btn btn-success"
                                    onClick={this.addBook}>Add book</Button>
                            <Button className="btn btn-secondary"
                                    onClick={() => loadAllBooks().then(value => this.setState({books: value}))}>Refresh book list</Button>
                        </>
                    </Accordion.Collapse>
                </Card>
            </>
        )
    }
};
