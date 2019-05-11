import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
    state = {
        isLoading: true,
        refId : null
    };

    async componentDidMount() {
        const response = await fetch('/api/image/a6ee1b19-b742-471a-ba2a-8fd8cc008fa4');
        const body = await response.json();

        // console.log(response.text());
        this.setState({ refId: body, isLoading: false });
    }

    render() {
        const {refId, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo" />
                    <div className="App-intro">
                        <h2>Image refId</h2>
                            <div>
                                {refId.refId}
                            </div>
                        </div>
                </header>
            </div>
        );
    }}

export default App;
