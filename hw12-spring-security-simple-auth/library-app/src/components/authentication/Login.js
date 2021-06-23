import React, {useState} from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import {Container} from "react-bootstrap";
import {authenticate} from "../../common/ApiServiceAuthentication";
import {useHistory} from "react-router-dom";

export default function Login() {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    let history = useHistory();

    const handleSubmit = (event) => {
        event.preventDefault();
        authenticate(username, password)
            .then(response => {
                    if (response.ok) {
                        localStorage.setItem('authToken', window.btoa(username + ':' + password));
                        history.push("/library")
                    } else if (response.status === 401) {
                        alert("error")

                    }
                }
            )
    }

    function validateForm() {
        return username.length > 0 && password.length > 0;
    }

    return (
        <Container fluid="sm">
            <Form onSubmit={handleSubmit}>
                <Form.Group size="lg" controlId="username">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        autoFocus
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </Form.Group>
                <Form.Group size="lg" controlId="password">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </Form.Group>
                <Button block size="lg" type="submit" disabled={!validateForm()}>
                    Login
                </Button>
            </Form>
        </Container>
    );
}