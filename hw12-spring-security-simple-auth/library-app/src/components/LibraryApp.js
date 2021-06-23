import React from "react";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import Welcome from "./Welcome";
import Login from "./authentication/Login";
import {PrivateRoute} from "./PrivateRoute";

export default function LibraryApp() {
    return (
        <BrowserRouter>
            <Switch>
                <Route exact={true} path="/" component={Login}/>
                <PrivateRoute path="/library" component={Welcome}/>
            </Switch>
        </BrowserRouter>
    );


}