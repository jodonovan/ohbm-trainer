import React from 'react';
import ReactDOM from 'react-dom';
import App from './Trainer';
import { BrowserRouter } from "react-router-dom";

it('renders without crashing', () => {
    const div = document.createElement('div');

    // see https://stackoverflow.com/questions/50025717/jest-enzyme-invariant-violation-you-should-not-use-route-or-withrouter-ou
    // ReactDOM.render(<App />, div);

    ReactDOM.render(
      <BrowserRouter>
        <App />
      </BrowserRouter>,
      div
  );
  ReactDOM.unmountComponentAtNode(div);
});
