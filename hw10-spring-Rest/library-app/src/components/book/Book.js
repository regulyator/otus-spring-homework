import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Button from "react-bootstrap/Button";
import {FormControl, InputGroup} from "react-bootstrap";
import BookModal from "./BookModal";
import {createOrUpdateBook} from "../../common/ApiServiceBooks";

export default class Book extends React.Component {
    constructor(props) {
        super(props);
        this.state = {book: props.book, modalShow: false};

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        createOrUpdateBook.call(this, this.state.book.id === null);
        event.preventDefault();

    }

    setModalShow(show) {
        this.setState({modalShow: show})
    }

    render() {


        return (
            <InputGroup className="mb-1 p-1">
                <FormControl type="text"
                             placeholder="Book caption"
                             aria-label="Book caption"
                             aria-describedby="basic-addon2"
                             value={this.state.book.bookName}
                />
                <InputGroup.Append>
                    <Button variant="danger" onClick={() => this.props.onDelete(this.state.book)}>Delete</Button>
                    <Button variant="primary" onClick={() => this.setModalShow(true)}>Edit book</Button>
                    <BookModal
                        book={this.state.book}
                        show={this.state.modalShow}
                        onHide={() => this.setModalShow(false)}
                    />
                </InputGroup.Append>
            </InputGroup>
        )
    }


};
