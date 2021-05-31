import React from "react";
import {FormControl, InputGroup, Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {addCommentToBook, createOrUpdateBook} from "../../common/ApiServiceBooks";

export default class BookComments extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            book: props.book,
            newCommentText: ''
        };

        this.newCommentChange = this.newCommentChange.bind(this);
        this.handleCommentDelete = this.handleCommentDelete.bind(this);
        this.handleAddNewComment = this.handleAddNewComment.bind(this);
    }

    newCommentChange(event) {
        this.setState(state => {
            state.newCommentText = event.target.value
            return {newCommentText: state.newCommentText}
        });
    }

    handleCommentDelete(deletedCommentId) {
        let updatedBook = this.state.book;
        updatedBook.comments = this.state.book.comments.filter((comment) => {
            return comment.id !== deletedCommentId;
        })

        createOrUpdateBook(updatedBook).then(value => this.setState({
            book: value
        }))
    }

    handleAddNewComment() {
        addCommentToBook(this.state.book.id, this.state.newCommentText).then(value => this.setState({
            book: value,
            newCommentText: ''
        }))
    }

    render() {
        return (
            <Modal {...this.props}
                   size="lg"
                   aria-labelledby="contained-modal-title-vcenter"
                   centered>
                <Modal.Header>
                    <Modal.Title>{this.state.book.bookName}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.book.comments?.map((comment) => (
                            <InputGroup className="mb-1 p-1">
                                <FormControl type="text"
                                             placeholder="Comment"
                                             aria-label="Comment"
                                             value={comment.caption}
                                             readOnly={true}
                                />
                                <InputGroup.Append>
                                    <Button variant="danger"
                                            onClick={() => this.handleCommentDelete(comment.id)}>Delete</Button>
                                </InputGroup.Append>
                            </InputGroup>
                        )
                    )}
                </Modal.Body>
                <Modal.Footer>
                    <InputGroup className="mb-1 p-1">
                        <FormControl type="text"
                                     placeholder="Comment"
                                     aria-label="Comment"
                                     value={this.state.newCommentText}
                                     onChange={this.newCommentChange}
                        />
                        <InputGroup.Append>
                            <Button variant="outline-success" onClick={this.handleAddNewComment}>Add comment</Button>
                        </InputGroup.Append>
                    </InputGroup>
                </Modal.Footer>
            </Modal>
        );
    }
}