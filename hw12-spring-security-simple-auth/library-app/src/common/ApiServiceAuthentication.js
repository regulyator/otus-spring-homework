const apiUrlAuthentication = '/authenticate';

export function authenticate(username, password) {
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    };
    return fetch(`${apiUrlAuthentication}?username=${username}&password=${password}`, requestOptions)
        .catch(reason => console.log(reason));
}

function generateBasicAuthToken(username, password) {
    return 'Basic ' + window.btoa(username + ":" + password)
}