const apiUrlAuthentication = '/authenticate';

export function authenticate(username, password) {
    console.log("auth " + username + " " +password)
    const requestOptions = {
        method: 'GET',
        headers: {'Authorization': generateBasicAuthToken(username, password)}
    };
    return fetch(apiUrlAuthentication, requestOptions)
        .catch(reason => console.log(reason));
}

function generateBasicAuthToken(username, password) {
    return 'Basic ' + window.btoa(username + ":" + password)
}