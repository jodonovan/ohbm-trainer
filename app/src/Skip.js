import React from "react";
import Button from 'react-bootstrap/Button';

function Skip(props) {
    const disabled = props.selectLevelState;
    return(
        <div className="fetch">
            <Button className="rounded p-1 m-1" variant="outline-warning" onClick={() => props.onClick()} disabled={disabled}>
                Skip
            </Button>
        </div>
    );
}

export default Skip