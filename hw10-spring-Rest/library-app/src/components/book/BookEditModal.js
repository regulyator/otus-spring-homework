import 'bootstrap/dist/css/bootstrap.min.css';
import React from "react";
import Button from "react-bootstrap/Button";
import {Col, Form, Modal} from "react-bootstrap";


export default class BookEditModal extends React.Component {

    render() {
        return (
            <Modal {...this.props}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered
                   onExit={this.props.handleCancel}>
                <Modal.Header>
                    <Modal.Title>{this.props.book.bookName}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div>
                        <Form.Row>
                            <Form.Group as={Col} controlId="formGridBookName">
                                <Form.Label>Book name</Form.Label>
                                <Form.Control type="text" placeholder="Book name"
                                              value={this.props.bookName}
                                              onChange={this.props.handleBookNameChange}/>
                            </Form.Group>

                            <Form.Group as={Col} controlId="formGridBookGenre">
                                <Form.Label>Genre</Form.Label>
                                <Form.Control as="select" value={this.props.bookGenre?.id}
                                              onChange={this.props.handleBookGenreChange}>
                                    {this.props.bookGenre === null && <option/>}
                                    {this.props.genres.map((genre) =>
                                        <option key={genre.id}
                                                value={genre.id}>{genre.caption}</option>
                                    )}
                                </Form.Control>
                            </Form.Group>
                        </Form.Row>

                        <Form.Group controlId="formBookAuthors">
                            <Form.Label>Example multiple select</Form.Label>
                            <Form.Control as="select" multiple
                                          value={this.props.bookAuthors?.map(value => value.id)}
                                          onChange={this.props.handleBookAuthorsChange}>
                                {this.props.authors.map((author) =>
                                    <option key={author.id}
                                            value={author.id}>{author.fio}</option>
                                )}
                            </Form.Control>
                        </Form.Group>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.props.handleCancel}>
                        Cancel
                    </Button>
                    <Button variant="primary" onClick={this.props.handleSave}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}
