import React from "react";
import Button from 'react-bootstrap/Button';
import Popover from "react-bootstrap/Popover";
import OverlayTrigger from "react-bootstrap/OverlayTrigger";

function Help() {
    const popover = (
        <Popover id="popover-basic" title="Instructions">
            Click the buttons on the <strong>top row</strong> to choose the range of bhumis you want to test against.
            Then click the <strong>Fetch</strong> button to fetch an image chosen randomly from this range.
            Use the numbers on the <strong>bottom row</strong> to then choose your answer.
            You can skip a picture by clicking the <strong>Skip</strong> button, adjusting your bhumi range if you wish,
            and then clicking the <strong>Fetch</strong> button to load another image.
        </Popover>
    );

    return(
        <div className="help">
            <OverlayTrigger trigger="click" placement="right" overlay={popover}>
            <Button className="rounded p-1 m-1" variant="info" >
                Help
            </Button>
            </OverlayTrigger>
        </div>
    );
}

export default Help