import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Button from "react-bootstrap/Button";
import {FormControl, InputGroup} from "react-bootstrap";
import {createOrUpdateAuthor} from "../../common/ApiServiceAuthors";

export default class Author extends React.Component {
    constructor(props) {
        super(props);
        this.state = {author: props.author};
    }

    handleChange = (event) => {
        this.setState(state => {
            state.author.fio = event.target.value
            return {author: state.author}
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const create = this.state.author.id === null;
        createOrUpdateAuthor.call(this, create)
            .then(response => response.json())
            .then(data => {
                this.setState({author: data});
            })
            .then(() => alert(create ? 'Author created!' : 'Author updated!'))
            .catch(error =>
                alert(error)
            );
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
