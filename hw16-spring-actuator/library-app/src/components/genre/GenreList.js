import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Genre from "./Genre";
import {deleteGenre, loadAllGenres} from "../../common/ApiServiceGenres";
import {handleErrors} from "../../common/Util";

export default class GenreList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eventKeyAccording: props.eventKeyAccording,
            title: props.title,
            genres: []
        };

    }

    genresListToggle = () => {
        if (this.state.genres.length === 0) {
            loadAllGenres().then(value => this.setState({genres: value._embedded.genres}));
        }
    }

    addGenre = () => {
        this.setState({
            genres: this.state.genres.concat({id: null, caption: ''})
        })
    }

    handleDelete = (genre) => {
        deleteGenre(genre)
            .then(handleErrors)
            .then(response => {
                    if (response.ok) {
                        alert("Genre deleted!");
                    } else {
                        response.text().then(text => {
                            alert(text)
                        })
                    }
                }
            ).then(() => loadAllGenres().then((data) => {
            this.setState({genres: data._embedded.genres})
        })).catch(error =>
            console.log(error)
        );
    }

    render() {
        return (
            <>
                <Card>
                    <Card.Header>
                        <Accordion.Toggle className="m-2" as={Button} eventKey={this.state.eventKeyAccording}
                                          onClick={this.genresListToggle}>
                            View {this.state.title}
                        </Accordion.Toggle>

                    </Card.Header>
                    <Accordion.Collapse eventKey={this.state.eventKeyAccording}>
                        <>
                            <Card.Body>
                                <div>
                                    {this.state.genres.map((genre,idx) => (
                                        <Genre key={idx} genre={genre} onDelete={this.handleDelete}/>
                                    ))}
                                </div>
                            </Card.Body>

                            <Button className="m-2 btn btn-success"
                                    onClick={this.addGenre}>Add genre</Button>
                        </>
                    </Accordion.Collapse>
                </Card>
            </>
        )
    }
};
