import React from "react";
import Image from 'react-bootstrap/Image';

class Picture extends React.Component {
    render() {
        return (
            <div>
                <Image src = {this.props.url} fluid />
            </div>
        )
    }
}

export default Picture