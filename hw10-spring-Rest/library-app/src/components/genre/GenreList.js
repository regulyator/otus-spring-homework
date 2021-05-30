import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react'
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import Genre from "./Genre";
import {deleteGenre, loadAllGenres} from "../../common/ApiServiceGenres";

export default class GenreList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            eventKeyAccording: props.eventKeyAccording,
            title: props.title,
            genres: []
        };

        this.genresListToggle = this.genresListToggle.bind(this);
        this.addGenre = this.addGenre.bind(this);
    }

    genresListToggle() {
        if (this.state.genres.length === 0) {
            loadAllGenres().then(value => this.setState({genres: value}));
        }
    }

    addGenre() {
        this.setState({
            genres: this.state.genres.concat({id: null, caption: ''})
        })
    }

    handleDelete = (genre) => {
        deleteGenre(genre).then(() => loadAllGenres().then((data) => {
            this.setState({genres: data})
        }));
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
                                    {this.state.genres.map((genre) => (
                                        <Genre key={genre.id} genre={genre} onDelete={this.handleDelete}/>
                                    ))}
                                </div>
                            </Card.Body>

                            <Button className="m-2 btn btn-success"
                                    onClick={this.addGenre}>Add genre</Button>
                            <Button className="btn btn-secondary"
                                    onClick={() => loadAllGenres().then(value => this.setState({genres: value}))}>Refresh genre list</Button>
                        </>
                    </Accordion.Collapse>
                </Card>
            </>
        )
    }
};
