import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Author from "./Author";

const apiUrlAllAuthors = '/library/api/authors/';

export default class AuthorList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eventKeyAccording: props.eventKeyAccording,
            title: props.title,
            description: props.description,
            authors: [],
            open: false
        };

        this.authorsListToggle = this.authorsListToggle.bind(this);
        this.loadAuthors = this.loadAuthors.bind(this);
        this.addAuthor = this.addAuthor.bind(this);
    }

    authorsListToggle() {
        if (this.state.authors.length === 0) {
            this.loadAuthors();
        }
    }

    loadAuthors() {
        fetch(apiUrlAllAuthors)
            .then(res => res.json())
            .then((data) => {
                this.setState({authors: data})
            })
            .catch(console.log)
    }

    addAuthor() {
        this.setState({
            authors: this.state.authors.concat({id: null, fio: ''})
        })
    }

    handleDelete = author => {
        const requestOptions = {
            method: 'DELETE',
            headers: {'Content-Type': 'application/json'}
        };
        fetch(apiUrlAllAuthors + author.id, requestOptions)
            .then(response => {
                    if (response.ok) {
                        return this.loadAuthors();
                    } else {
                        throw new Error(response.json())
                    }
                }
            )
            .catch(reason => console.log(reason));
    };

    render() {
        return (
            <>
                <Card>
                    <Card.Header>
                        <Accordion.Toggle className="m-2" as={Button} eventKey={this.state.eventKeyAccording}
                                          onClick={this.authorsListToggle}>
                            View {this.state.title}
                        </Accordion.Toggle>
                        {this.state.authors.length !== 0 &&
                        <Button className="m-2 btn btn-success" onClick={this.loadAuthors}>Refresh author list</Button>
                        }

                    </Card.Header>
                    <Accordion.Collapse eventKey={this.state.eventKeyAccording}>
                        <>
                            <Card.Body>{this.state.description}</Card.Body>
                            <div>
                                {this.state.authors.map((author) => (
                                    <Author key={author.id} author={author} onDelete={this.handleDelete}/>
                                ))}
                            </div>
                            <Button className="m-2 btn btn-success" onClick={this.addAuthor}>Add author</Button>
                        </>
                    </Accordion.Collapse>
                </Card>
            </>
        )
    }
};
