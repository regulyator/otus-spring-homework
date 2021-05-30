import 'bootstrap/dist/css/bootstrap.min.css';
import React from "react";
import Button from "react-bootstrap/Button";
import {Col, Form, Modal} from "react-bootstrap";
import {loadAllAuthors} from "../../common/ApiServiceAuthors";
import {loadAllGenres} from "../../common/ApiServiceGenres";
import {createOrUpdateBook} from "../../common/ApiServiceBooks";


export default class BookModal extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            book: props.book,
            genres: [],
            authors: [],
            bookName: props.book.bookName,
            bookGenre: props.book.genre,
            bookAuthors: props.book.authors
        };

        this.handleChangeGenre = this.handleChangeGenre.bind(this);
        this.handleChangeBookName = this.handleChangeBookName.bind(this);
        this.handleChangeAuthors = this.handleChangeAuthors.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }

    componentDidMount() {
        loadAllAuthors().then(data => this.setState({authors: data}));
        loadAllGenres().then(data => this.setState({genres: data}));
    }

    handleChangeGenre(event) {
        this.setState(state => {
            state.bookGenre = state.genres.filter((genre) => {
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

    handleCancel() {
        console.log('hide')
        this.setState(state => {
            return {
                bookName: this.props.book.bookName,
                bookGenre: this.props.book.genre,
                bookAuthors: this.props.book.authors
            }
        })
        this.props.onHide.call();
    }

    handleSave = () => {
        this.setState(state => {
            state.book.bookName = this.state.bookName;
            state.book.genre = this.state.bookGenre;
            state.book.authors = this.state.bookAuthors;
            return {
                book: state.book
            }
        })

console.log(this.state.book)
        createOrUpdateBook.call(this, false);

        this.props.onHide.call();
    }

    render() {
        return (
            <Modal {...this.props}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered
                   onExit={() => console.log('exit')}>
                <Modal.Header>
                    <Modal.Title>{this.props.book.bookName}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div>
                        <Form.Row>
                            <Form.Group as={Col} controlId="formGridBookName">
                                <Form.Label>Book name</Form.Label>
                                <Form.Control type="text" placeholder="Book name"
                                              value={this.state.bookName}
                                              onChange={this.handleChangeBookName}/>
                            </Form.Group>

                            <Form.Group as={Col} controlId="formGridBookGenre">
                                <Form.Label>Genre</Form.Label>
                                <Form.Control as="select" value={this.state.bookGenre?.id}
                                              onChange={this.handleChangeGenre}>
                                    {this.state.bookGenre === null && <option/>}
                                    {this.state.genres.map((genre) =>
                                        <option key={genre.id}
                                                value={genre.id}>{genre.caption}</option>
                                    )}
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group controlId="formBookAuthors">
                            <Form.Label>Example multiple select</Form.Label>
                            <Form.Control as="select" multiple
                                          value={this.state.bookAuthors.map(value => value.id)}
                                          onChange={this.handleChangeAuthors}>
                                {this.state.authors.map((author) =>
                                    <option key={author.id}
                                            value={author.id}>{author.fio}</option>
                                )}
                            </Form.Control>
                        </Form.Group>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleCancel}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={this.handleSave}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }


}
