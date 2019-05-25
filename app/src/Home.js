import React, { Component } from 'react';
import { Button, Container, Row, NavLink } from 'reactstrap';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Row>
                    <Button color="link"><Link to="/trainer">Open Heart Bhumi Trainer</Link></Button>
                    </Row>
                    <Row>
                    <Button color="link"><NavLink href="http://openheartopenheart.blogspot.com/2016/02/stages-of-spiritual-attainment.html">Open Heart Bhumi Model</NavLink></Button>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default Home;