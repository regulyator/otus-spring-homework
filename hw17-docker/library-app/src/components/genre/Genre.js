import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Button from "react-bootstrap/Button";
import {FormControl, InputGroup} from "react-bootstrap";
import {createOrUpdateGenre} from "../../common/ApiServiceGenres";

export default class Genre extends React.Component {
    constructor(props) {
        super(props);
        this.state = {genre: props.genre};
    }

    handleChange = (event) => {
        this.setState(state => {
            state.genre.caption = event.target.value
            return {genre: state.genre}
        });
    }

    handleSubmit = (event) => {
        event.preventDefault();
        createOrUpdateGenre(this.state.genre)
            .then(response => response.json())
            .then(data => {
                this.setState({genre: data});
            })
            .then(() => alert('Genre saved!'))
            .catch(error =>
                alert(error)
            );


    }

    render() {
        return (
            <InputGroup className="mb-1 p-1">
                <FormControl type="text"
                             placeholder="Genre caption"
                             aria-label="Genre caption"
                             aria-describedby="basic-addon2"
                             value={this.state.genre.caption}
                             onChange={this.handleChange}
                />
                <InputGroup.Append>
                    <Button variant="success" onClick={this.handleSubmit}>Save</Button>
                    <Button variant="danger" onClick={() => this.props.onDelete(this.state.genre)}>Delete</Button>
                </InputGroup.Append>
            </InputGroup>
        )
    }


};


