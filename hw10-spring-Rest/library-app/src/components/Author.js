import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Button from "react-bootstrap/Button";
import {FormControl, InputGroup} from "react-bootstrap";

class Author extends React.Component {
    constructor(props) {
        super(props);
        this.state = {authorFio: props.author.fio};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState({authorFio: event.target.value});
        console.log(this.props.author)
    }

    handleSubmit(event) {
        this.props.author.fio = this.state.authorFio;
        console.log(this.props.author)
        console.log(JSON.stringify(this.props.author))
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(this.props.author)
        };
        fetch('/library/api/authors', requestOptions)
            .then(response => response.json())
            .then(data => this.setState({authorFio: data.fio}));
        event.preventDefault();
    }

    render() {
        return (
            <InputGroup className="mb-1 p-1">
                <FormControl type="text"
                             placeholder="Author FIO"
                             aria-label="Author FIO"
                             aria-describedby="basic-addon2"
                             value={this.state.authorFio}
                             onChange={this.handleChange}
                />
                <InputGroup.Append>
                    <Button variant="success" onClick={this.handleSubmit}>Save</Button>
                    <Button variant="danger">Delete</Button>
                </InputGroup.Append>
            </InputGroup>
        )
    }


};

export default Author
