import 'bootstrap/dist/css/bootstrap.min.css';
import React from "react";
import Accordion from 'react-bootstrap/Accordion';

import MainNavigation from './components/MainNavigation';
import AuthorsList from "./components/AuthorList";

class Welcome extends React.Component {
    state = {
        authors: [],
        genres: []
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

    render() {
        return (
            <Accordion >
                <h2>Welcome to library!</h2>
                <MainNavigation title='Books' description='List of all Books' eventKeyAccording="0"/>
                <MainNavigation title='Genres' description='List of all Genres' eventKeyAccording="1"/>
                <AuthorsList title='Authors' description='List of all Authors' eventKeyAccording="2" authors={this.state.authors}/>
            </Accordion>
        );
    }


}

export default Welcome;
