import React from "react";
import Button from 'react-bootstrap/Button';

function Fetch(props) {
    const disabled = !props.selectLevelState;
    return(
        <div className="fetch">
            <Button className="rounded p-1 m-1" variant="outline-success" onClick={() => props.onClick()} disabled={disabled}>
                Fetch
            </Button>
        </div>
    );
}

export default Fetch