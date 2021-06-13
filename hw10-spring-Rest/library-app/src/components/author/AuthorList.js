import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Author from "./Author";
import {deleteAuthor, loadAllAuthors} from "../../common/ApiServiceAuthors";

export default class AuthorList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eventKeyAccording: props.eventKeyAccording,
            title: props.title,
            authors: []
        };

    }

    authorsListToggle = () => {
        if (this.state.authors.length === 0) {
            loadAllAuthors().then(value => this.setState({authors: value}));
        }
    }

    addAuthor = () => {
        this.setState({
            authors: this.state.authors.concat({id: null, fio: ''})
        })
    }

    handleDelete = (author) => {
        deleteAuthor(author)
            .then(response => {
                    if (response.ok) {
                        alert("Author deleted!");
                    } else {
                        response.text().then(text => {
                            alert(text);
                        })
                    }
                }
            ).then(() => loadAllAuthors().then((data) => {
            this.setState({authors: data})
        }));
    }

    render() {
        return (
            <>
                <Card>
                    <Card.Header>
                        <Accordion.Toggle className="m-2" as={Button} eventKey={this.state.eventKeyAccording}
                                          onClick={this.authorsListToggle}>
                            View {this.state.title}
                        </Accordion.Toggle>

                    </Card.Header>
                    <Accordion.Collapse eventKey={this.state.eventKeyAccording}>
                        <>
                            <Card.Body>
                                <div>
                                    {this.state.authors.map((author) => (
                                        <Author key={author.id} author={author} onDelete={this.handleDelete}/>
                                    ))}
                                </div>
                            </Card.Body>

                            <Button className="m-2 btn btn-success"
                                    onClick={this.addAuthor}>Add author</Button>
                            <Button className="m-2 btn btn-secondary"
                                    onClick={() => loadAllAuthors().then(value => this.setState({authors: value}))}>Refresh
                                author list</Button>
                        </>
                    </Accordion.Collapse>
                </Card>
            </>
        )
    }
};
