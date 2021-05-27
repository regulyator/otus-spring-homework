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
            authors: [],
            open: true
        };
    }

    componentDidMount() {
        const apiUrlAllAuthors = '/library/api/authors';
        fetch(apiUrlAllAuthors)
            .then(res => res.json())
            .then((data) => {
                this.setState({ authors: data })
            })
            .catch(console.log)
    }

    handleDelete = () => {
        alert("Button Clicked!");
    };

    render() {
        return (
            <Card>
                <Card.Header>
                    <Accordion.Toggle as={Button}  eventKey={this.state.eventKeyAccording} >
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
};

export default AuthorList
