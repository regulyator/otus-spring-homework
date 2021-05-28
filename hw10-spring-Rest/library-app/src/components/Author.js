import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Button from "react-bootstrap/Button";
import {FormControl, InputGroup} from "react-bootstrap";



export default class Author extends React.Component {
    constructor(props) {
        super(props);
        this.state = {author: props.author};

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        this.setState(state => {
            state.author.fio = event.target.value
            return {author: state.author}
        });
    }

    handleSubmit(event) {
        if(this.state.author.id === null){
            console.log("create");
            createAuthor.apply(this);
        } else {
            console.log("update");
            updateAuthor.apply(this);
        }
        event.preventDefault();

    }

    render() {
        return (
            <InputGroup className="mb-1 p-1">
                <FormControl type="text"
                             placeholder="Author FIO"
                             aria-label="Author FIO"
                             aria-describedby="basic-addon2"
                             value={this.state.author.fio}
                             onChange={this.handleChange}
                />
                <InputGroup.Append>
                    <Button variant="success" onClick={this.handleSubmit}>Save</Button>
                    <Button variant="danger" onClick={() => this.props.onDelete(this.state.author)}>Delete</Button>
                </InputGroup.Append>
            </InputGroup>
        )
    }


};

function createAuthor() {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.author)
    };
    fetch('/library/api/authors', requestOptions)
        .then(response => response.json())
        .then(data => this.setState({author: data}));
}

function updateAuthor() {
    const requestOptions = {
        method: 'PUT',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(this.state.author)
    };
    fetch('/library/api/authors', requestOptions)
        .then(response => response.json())
        .then(data => this.setState({author: data}));
}
