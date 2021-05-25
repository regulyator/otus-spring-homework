import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Author from "./Author";

class AuthorList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eventKeyAccording: props.eventKeyAccording,
            title: props.title,
            description: props.description,
            authors: props.authors,
            open: true
        };
    }

    handleDelete = () => {
        alert("Button Clicked!");
    };

    render() {
        return (
            <Card>
                <Card.Header>
                    <Accordion.Toggle as={Button}  eventKey={this.state.eventKeyAccording} onClick={this.handleTooggle(this.setState({ open: !this.state.open}))}>
                        View {this.state.title}
                    </Accordion.Toggle>
                </Card.Header>
                <Accordion.Collapse eventKey={this.state.eventKeyAccording}>
                    <>
                        <Card.Body>{this.state.description}</Card.Body>
                        <div>
                            {this.state.authors.map((author) => (
                                <Author key={author.id} author={author} onDelete={this.handleDelete}/>
                            ))}
                        </div>
                    </>
                </Accordion.Collapse>
            </Card>
        )
    }

    handleTooggle(event) {
        this.state.console.log(this.state.open)
    }
};

export default AuthorList
