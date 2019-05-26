import React from "react";
import ButtonToolbar from "react-bootstrap/ButtonToolbar";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";

class LevelSelector extends React.Component {

    render() {
        return (
                <ButtonToolbar className="border border-light rounded m-1">
                    <ButtonGroup className="mr-1">
                        {this.renderLevel(0)}
                    </ButtonGroup>
                    <ButtonGroup className="mr-1">
                        {this.renderLevel(1)}
                        {this.renderLevel(2)}
                        {this.renderLevel(3)}
                        {this.renderLevel(4)}
                        {this.renderLevel(5)}
                        {this.renderLevel(6)}
                    </ButtonGroup>
                    <ButtonGroup className="mr-1">
                        {this.renderLevel(7)}
                        {this.renderLevel(8)}
                        {this.renderLevel(9)}
                        {this.renderLevel(10)}
                    </ButtonGroup>
                    <ButtonGroup>
                        {this.renderLevel(11)}
                        {this.renderLevel(12)}
                        {this.renderLevel(13)}
                    </ButtonGroup>
                </ButtonToolbar>
        );
    }

    renderLevel(i) {
        return <Level value={i}
                      selected={this.props.levelsSelected[i]}
                      selectLevelState={this.props.selectLevelState}
                      onClick={() => this.props.onClick(i)}
        />
    }
}

function Level(props) {
    const selected = props.selected;
    const variant = (selected === true) ? "info" : "light";
    const disabled = !props.selectLevelState;
    // const state =
    return (
        <Button variant={variant} onClick={() => props.onClick()} disabled={disabled}>
            {props.value}
        </Button>
    );
}

export default LevelSelector;