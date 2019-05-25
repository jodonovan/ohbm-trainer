import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './Home';
import Trainer from './Trainer';
import './App.css';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/trainer' exact={true} component={Trainer}/>
                </Switch>
            </Router>
        )
    }
}

export default App;