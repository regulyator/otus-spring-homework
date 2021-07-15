import React from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Main from "./Main";
import Login from "./authentication/Login";
import {PrivateRoute} from "./PrivateRoute";
import NavBar from "./NavBar";

export default class LibraryApp extends React.Component {
    constructor(props) {
        super(props);
        this.state = {authenticated: false};
    }

    handleUpdateAuthStatus = (authStatus) => {
        this.setState({authenticated: authStatus})
    }

    render() {
        return (
            <>
                <NavBar authenticated={this.state.authenticated} onLogOut={this.handleUpdateAuthStatus}/>
                <BrowserRouter>
                    <Switch>
                        <Route exact={true} path="/">
                            <Login onLogin={this.handleUpdateAuthStatus}/>
                        </Route>
                        <PrivateRoute exact={true} path="/library"
                                      component={Main} authenticated={this.state.authenticated}/>
                    </Switch>
                </BrowserRouter>
            </>

        );
    }


}
