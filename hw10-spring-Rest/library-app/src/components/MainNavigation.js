import 'bootstrap/dist/css/bootstrap.min.css';
import './MainNavigation.css'

import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';

import React from "react"

function MainNavigation(props) {
    return (
        <Card>
            <Card.Header>
                <Accordion.Toggle as={Button} variant="link" eventKey={props.eventKeyAccording}>
                    View {props.title}
                </Accordion.Toggle>
            </Card.Header>
            <Accordion.Collapse eventKey={props.eventKeyAccording}>
                <Card.Body>{props.description}</Card.Body>
            </Accordion.Collapse>
        </Card>
    );
}



export default MainNavigation;
