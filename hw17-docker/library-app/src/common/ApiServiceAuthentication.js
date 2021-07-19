import {API_URL} from "../constants/constant";

const apiUrlAuthentication = '/library/api/authenticate';

export function authenticate(username, password) {
    console.log(API_URL)
    const requestOptions = {
        method: 'GET',
        headers: {authorization: createBasicAuthToken(username, password)}
    };
    return fetch(`${apiUrlAuthentication}`, requestOptions)
        .catch(reason => console.log(reason));
}


function createBasicAuthToken(username, password) {
    return 'Basic ' + window.btoa(username + ":" + password)
}