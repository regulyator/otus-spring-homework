import React from "react";
import {FormControl, InputGroup, Modal} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {addCommentToBook, removeCommentFromBook} from "../../common/ApiServiceBooks";

export default class BookComments extends React.Component {
    static getDerivedStateFromProps(props, state) {
        if (props.book.id !== state.bookId) {
            return {bookId: props.book.id}
        } else {
            return null;
        }
    }

    constructor(props) {
        super(props);
        this.state = {
            book: props.book,
            bookId: props.book.id,
            newCommentText: ''
        };
    }

    newCommentChange = (event) => {
        this.setState(state => {
            state.newCommentText = event.target.value
            return {newCommentText: state.newCommentText}
        });
    }

    handleCommentDelete = (deletedCommentId) => {
        let idBook = this.state.book.id;

        removeCommentFromBook(idBook, deletedCommentId).then(value => this.setState({
            book: value
        }))
    }

    handleAddNewComment = () => {
        addCommentToBook(this.state.bookId, this.state.newCommentText).then(value => this.setState({
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
                    <Modal.Title>{this.props.book.bookName}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {this.state.book.comments?.map((comment, index) => (
                            <InputGroup className="mb-1 p-1" key={index}>
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