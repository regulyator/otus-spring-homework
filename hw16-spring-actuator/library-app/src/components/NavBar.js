import {Nav, Navbar} from "react-bootstrap";
import Button from "react-bootstrap/Button";
import {useEffect, useState} from "react";


export default function NavBar(props) {
    const {authenticated} = props;
    const [auth, setAuth] = useState(authenticated);
    useEffect(() => {
        setAuth(authenticated)
    }, [authenticated])

    const handleLogOut = () => {
        props.onLogOut(false);
    }

    return (
        <Navbar bg="light" variant="light">
            <Navbar.Brand>Library app</Navbar.Brand>
            <Nav className="mr-auto"/>
            <Nav>
                {auth && <Button variant="outline-dark" onClick={handleLogOut}>Logout</Button>}
            </Nav>
        </Navbar>
    );


}
