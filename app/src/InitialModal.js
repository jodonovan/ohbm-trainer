import React from "react";
import Modal from 'react-bootstrap/Modal';
import Button from "react-bootstrap/Button";


class InitialModal extends React.Component{
    render() {
        return (
            <Modal
                {...this.props}
                size="lg"
                aria-labelledby="contained-modal-title-vcenter"
                centered >
                <Modal.Header closeButton>
                    <Modal.Title id="contained-modal-title-vcenter">
                        Terms and Conditions
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <h4>Open Heart Bhumi Model Trainer</h4>
                    <p>
                        The use of this web application is permitted only on the basis that the downloading of all images presented in the application is expressly forbidden.
                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button onClick={this.props.onHide}>Agree and Proceed</Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default InitialModal