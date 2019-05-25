import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Home from './Home';
import Trainer from './Trainer';
import './App.css';
import { CookiesProvider } from 'react-cookie';


class App extends Component {
    render() {
        return (
            <CookiesProvider>
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/trainer' exact={true} component={Trainer}/>
                </Switch>
            </Router>
            </CookiesProvider>
        )
    }
}

export default App;