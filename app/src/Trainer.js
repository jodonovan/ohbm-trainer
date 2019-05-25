// import React, {Component} from 'react';
// import logo from './logo.svg';
// import './App.css';
//
// class App extends Component {
//     state = {
//         isLoading: true,
//         refId : null
//     };
//
//     async componentDidMount() {
//         const response = await fetch('/api/image/a6ee1b19-b742-471a-ba2a-8fd8cc008fa4');
//         const body = await response.json();
//
//         // console.log(response.text());
//         this.setState({ refId: body, isLoading: false });
//     }
//
//     render() {
//         const {refId, isLoading} = this.state;
//
//         if (isLoading) {
//             return <p>Loading...</p>;
//         }
//
//         return (
//             <div className="App">
//                 <header className="App-header">
//                     <img src={logo} className="App-logo" alt="logo" />
//                     <div className="App-intro">
//                         <h2>Image refId</h2>
//                             <div>
//                                 {refId.refId}
//                             </div>
//                         </div>
//                 </header>
//             </div>
//         );
//     }}
//
// export default App;


import React from 'react'
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import ButtonToolbar from 'react-bootstrap/ButtonToolbar';
import Image from 'react-bootstrap/Image';
import Row from 'react-bootstrap/Row'
import AppNavbar from './AppNavbar';
import './index.css'


function Level(props) {
    const selected = props.selected;
    const variant = (selected === true) ? "info" : "light";
    const disabled = !props.selectLevelState;
    // const state =
    return(
        <Button variant={variant} onClick={() => props.onClick()} disabled={disabled} >
            {props.value}
        </Button>
    );
}

class LevelSelector extends React.Component {

    render() {
        return(
            <div className="buttons">
                <ButtonToolbar>
                {this.renderLevel(0)}
                {this.renderLevel(1)}
                {this.renderLevel(2)}
                {this.renderLevel(3)}
                {this.renderLevel(4)}
                {this.renderLevel(5)}
                {this.renderLevel(6)}
                {this.renderLevel(7)}
                {this.renderLevel(8)}
                {this.renderLevel(9)}
                {this.renderLevel(10)}
                {this.renderLevel(11)}
                {this.renderLevel(12)}
                {this.renderLevel(13)}
                </ButtonToolbar>
            </div>
        );
    }

    renderLevel(i) {
        return <Level value = {i}
                      selected = {this.props.levelsSelected[i]}
                      selectLevelState = {this.props.selectLevelState}
                      onClick = {() => this.props.onClick(i)}
        />
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

class AnswerSelector extends React.Component {
    render() {
        return(
            <div className="buttons">
                <ButtonToolbar>
                    {this.renderAnswer(0)}
                    {this.renderAnswer(1)}
                    {this.renderAnswer(2)}
                    {this.renderAnswer(3)}
                    {this.renderAnswer(4)}
                    {this.renderAnswer(5)}
                    {this.renderAnswer(6)}
                    {this.renderAnswer(7)}
                    {this.renderAnswer(8)}
                    {this.renderAnswer(9)}
                    {this.renderAnswer(10)}
                    {this.renderAnswer(11)}
                    {this.renderAnswer(12)}
                    {this.renderAnswer(13)}
                </ButtonToolbar>
            </div>
        );
    }

    renderAnswer(i) {
        return <Answer value = {i}
                       selected = {this.props.answersSelected[i]}
                       selectLevelState = {this.props.selectLevelState}
                       correctAnswer = {this.props.correctAnswer}
                       onClick = {() => this.props.onClick(i) }/>
    }
}

function Fetch(props) {
    const disabled = !props.selectLevelState;
    return(
        <div className="fetch">
            <Button variant="outline-success" onClick={() => props.onClick()} disabled={disabled}>
                Fetch
            </Button>
        </div>
    );
}

class Picture extends React.Component {
    render() {
        return (
            <div>
                <Image src = {this.props.url} fluid />
            </div>
        )
    }
}

class Trainer extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            levelsSelected : Array(14).fill(false),
            answersSelected : Array(14).fill(false),
            correctAnswer : -1,
            selectLevelState : true,
            imageUrl : ""
            //  2 global states :
            //  1) selectLevels (selectLevelState === true) - enable levels, disable answers, enable fetch, on 'fetch' clear any previous answers
            //    then load image and transition to
            //  2) selectAnswer (selectLevelState === false) - disable levels, enable answers, disable fetch, allow user to select answer, on 'correct answer' transition to
            //  selectLevels - same as above,
        };
    }

    handleLevelClick(i) {
        const levelsSelected = this.state.levelsSelected.slice();
        const levelSelected = levelsSelected[i];
        console.log("levelSelected[" + i + "] : " + levelSelected);
        levelsSelected[i] = !levelSelected;
        this.setState({levelsSelected : levelsSelected})
    }

    handleAnswerClick(i) {
        const levelsSelected = this.state.levelsSelected.slice();
        const answersSelected = this.state.answersSelected.slice();

        // only allow the user select the answer if the level has been selected
        if (levelsSelected[i]) {
            answersSelected[i] = true;
            this.setState({answersSelected : answersSelected});
            const correctAnswer = this.state.correctAnswer;
            if (i === correctAnswer) {
                this.setState({selectLevelState : true});
            }
        }

    }

    async handleFetchClick() {
        // iterate through levels, build a list of values, pick one and set the state of the app to this, update the dialogue
        this.setState({answersSelected : Array(14).fill(false)});
        const levelsSelected = this.state.levelsSelected.slice();
        const levelsSet = new Set();
        for (var i = 0; i <= 13; i++) {
            if (levelsSelected[i] === true) {
                console.log("Selected:" + i);
                levelsSet.add(i);
            }
        }

        // only proceed if at least one selected
        console.log("Number of levels selected:" + levelsSet.size)
        console.log("Levels selected:" + levelsSet)
        if (levelsSet.size > 0) {
            this.setState({selectLevelState : false});

            let levelsArray = Array.from(levelsSet);
            console.log("LevelsArray" + levelsArray);
            let randomLevel = levelsArray[Math.floor(Math.random() * levelsArray.length)];
            console.log("Random level" + randomLevel);
            const response = await fetch('/api/image?level=' + randomLevel);

            const body = await response.json();
            console.log("imageName:" + body.imageName);
            this.setState({ imageUrl: '/api/image/resource/?imageName=' + body.imageName});
            this.setState({correctAnswer : randomLevel});
            console.log("this.state.correctAnswer" + this.state.correctAnswer);

        }

    }

    render() {
        return(
            <div>
            <AppNavbar/>
            <Container >
                <Row className="justify-content-md-center">
                    <Picture url = {this.state.imageUrl}/>
                </Row>
                <Row className="justify-content-md-center">
                    <LevelSelector
                        levelsSelected={this.state.levelsSelected}
                        selectLevelState={this.state.selectLevelState}
                        onClick={(i) => this.handleLevelClick(i)}
                    />
                </Row>
                <Row className="justify-content-md-center">
                    <AnswerSelector
                        answersSelected = {this.state.answersSelected}
                        selectLevelState = {this.state.selectLevelState}
                        correctAnswer = {this.state.correctAnswer}
                        onClick = {(i) => this.handleAnswerClick(i)}
                    />
                </Row>
                <Row className="justify-content-md-center">
                    <Fetch
                        selectLevelState = {this.state.selectLevelState}
                        onClick = {(i) => this.handleFetchClick(i)}
                    />

                </Row>
            </Container>
            </div>
        );
    }
}

export default Trainer;


// Trainer
//    LevelSelector
//    AnswerSelector
//    Dialogue
//    Fetch Image

// States :
// Start : set levels (disable answers), then fetch image(number), then disable levels, then enable answers - onclick - test for correct
// answer, if incorrect continue, if correct green (disable answers but leave colours) enable levels, onclick clear answers (or clear on fetch)
// ========================================

// ReactDOM.render(<Trainer />, document.getElementById('root'));




