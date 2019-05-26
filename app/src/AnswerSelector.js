import React from "react";
import ButtonToolbar from "react-bootstrap/ButtonToolbar";
import Button from "react-bootstrap/Button";
import ButtonGroup from "react-bootstrap/ButtonGroup";


class AnswerSelector extends React.Component {
    render() {
        return (
                <ButtonToolbar className="border border-light rounded m-1">
                    <ButtonGroup className="mr-1">
                        {this.renderAnswer(0)}
                    </ButtonGroup>
                    <ButtonGroup className="mr-1">
                        {this.renderAnswer(1)}
                        {this.renderAnswer(2)}
                        {this.renderAnswer(3)}
                        {this.renderAnswer(4)}
                        {this.renderAnswer(5)}
                        {this.renderAnswer(6)}
                    </ButtonGroup>
                    <ButtonGroup className="mr-1">

                        {this.renderAnswer(7)}
                        {this.renderAnswer(8)}
                        {this.renderAnswer(9)}
                        {this.renderAnswer(10)}
                    </ButtonGroup>
                    <ButtonGroup>
                        {this.renderAnswer(11)}
                        {this.renderAnswer(12)}
                        {this.renderAnswer(13)}
                    </ButtonGroup>
                </ButtonToolbar>
        );
    }

    renderAnswer(i) {
        return <Answer value={i}
                       selected={this.props.answersSelected[i]}
                       selectLevelState={this.props.selectLevelState}
                       correctAnswer={this.props.correctAnswer}
                       onClick={() => this.props.onClick(i)}/>
    }
}

function Answer(props) {
    const selected = props.selected;
    const correctAnswer = props.correctAnswer;
    const value = props.value;

    var variant = (selected === true) ? "danger" : "primary";
    if ((value === correctAnswer) && (selected === true)) {
        variant = "success"
    }
    const disabled = props.selectLevelState;
    return (
        <Button variant={variant} onClick={() => props.onClick()} disabled={disabled}>
            {props.value}
        </Button>
    );
}

export default AnswerSelector;
