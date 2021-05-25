import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Author from "./Author";

const AuthorList = (props) => {
    return (
        <Card>
            <Card.Header>
                <Accordion.Toggle as={Button} variant="link" eventKey={props.eventKeyAccording}>
                    View {props.title}
                </Accordion.Toggle>
            </Card.Header>
            <Accordion.Collapse eventKey={props.eventKeyAccording}>
                <>
                    <Card.Body>{props.description}</Card.Body>
                    <div>
                        {props.authors.map((author) => (
                            <Author key={author.id} author={author}/>
                        ))}
                    </div>
                </>
            </Accordion.Collapse>
        </Card>
    )
};

export default AuthorList
