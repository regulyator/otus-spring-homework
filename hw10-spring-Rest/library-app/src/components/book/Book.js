import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Button from "react-bootstrap/Button";
import {FormControl, InputGroup} from "react-bootstrap";
import BookEditModal from "./BookEditModal";
import {createOrUpdateBook} from "../../common/ApiServiceBooks";
import BookComments from "./BookComments";

export default class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            book: props.book,
            modalEditShow: false,
            modalCommentsShow: false,
            bookName: props.book.bookName,
            bookGenre: props.book.genre,
            bookAuthors: props.book.authors
        };

        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChangeGenre = this.handleChangeGenre.bind(this);
        this.handleChangeBookName = this.handleChangeBookName.bind(this);
        this.handleChangeAuthors = this.handleChangeAuthors.bind(this);
        this.handleSave = this.handleSave.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }


    handleSubmit(event) {
        createOrUpdateBook(this.state.book).then(value => this.setState({book: value}));
        event.preventDefault();

    }

    handleCancel() {
        this.setState(state => {
            return {
                bookName: state.book.bookName,
                bookGenre: state.book.genre,
                bookAuthors: state.book.authors
            }
        })
        this.setEditBookShow(false);
    }

    handleSave() {
        let updatedBook = Object.assign({}, this.state.book);

        updatedBook.bookName = this.state.bookName;
        updatedBook.genre = this.state.bookGenre;
        updatedBook.authors = this.state.bookAuthors;

        createOrUpdateBook(updatedBook)
            .then(value => {
                this.setState({
                    book: value,
                    bookName: value.bookName,
                    bookGenre: value.genre,
                    bookAuthors: value.authors
                })
            })
            .then(() => this.setEditBookShow(false));
    }

    handleChangeGenre(event) {
        this.setState(state => {
            state.bookGenre = state.genres.find((genre) => {
                return genre.id === event.target.value;
            })
            return {bookGenre: state.bookGenre}
        });
    }

    handleChangeAuthors(event) {
        const selectedAuthorsIDs = [];
        let selectedOptions = event.target.selectedOptions

        for (let i = 0; i < selectedOptions.length; i++) {
            selectedAuthorsIDs.push(selectedOptions.item(i).value)
        }

        this.setState(state => {
            state.bookAuthors = state.authors.filter((author) => {
                return selectedAuthorsIDs.includes(author.id);
            })
            return {bookAuthors: state.bookAuthors}
        });
    }

    handleChangeBookName(event) {
        this.setState(state => {
            state.bookName = event.target.value
            return {bookName: state.bookName}
        });
    }

    setEditBookShow(show) {
        this.setState({modalEditShow: show})
    }

    setBookCommentsShow(show) {
        this.setState({modalCommentsShow: show})
    }

    render() {


        return (
            <InputGroup className="mb-1 p-1">
                <FormControl type="text"
                             placeholder="Book caption"
                             aria-label="Book caption"
                             aria-describedby="basic-addon2"
                             value={this.state.book.bookName}
                             readOnly={true}
                />
                <InputGroup.Append>
                    {this.state.book.id !== null &&
                    <Button variant="danger" onClick={() => this.props.onDelete(this.state.book)}>Delete</Button>}
                    <Button variant="primary" onClick={() => this.setEditBookShow(true)}>Edit book</Button>
                    {this.state.book.id !== null &&
                    <Button variant="secondary" onClick={() => this.setBookCommentsShow(true)}>Book comments</Button>}
                    <BookEditModal
                        genres={this.props.genres}
                        authors={this.props.authors}
                        book={this.state.book}
                        bookName={this.state.bookName}
                        bookGenre={this.state.bookGenre}
                        bookAuthors={this.state.bookAuthors}
                        show={this.state.modalEditShow}
                        onHide={() => this.setEditBookShow(false)}
                        handleBookNameChange={this.handleChangeBookName}
                        handleBookGenreChange={this.handleChangeGenre}
                        handleBookAuthorsChange={this.handleChangeAuthors}
                        handleSave={this.handleSave}
                        handleCancel={this.handleCancel}
                    />
                    <BookComments
                        book={this.state.book}
                        show={this.state.modalCommentsShow}
                        onHide={() => this.setBookCommentsShow(false)}
                    />
                </InputGroup.Append>
            </InputGroup>
        )
    }


};
