import React from "react";
import {Redirect, Route} from "react-router-dom";

export const PrivateRoute = ({component: Component, authenticated, ...other}) => (
    <Route {...other} render={props => authenticated ?
        (<Component {...props} />) :
        (<Redirect to={{pathname: "/", state: {from: props.location}}}/>)
    }
    />
)