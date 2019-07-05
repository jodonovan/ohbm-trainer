import React from 'react'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row'
import AppNavbar from './AppNavbar';
import LevelSelector from './LevelSelector'
import AnswerSelector from './AnswerSelector'
import './index.css'
import Fetch from './Fetch'
import Skip from './Skip'
import InitialModal from './InitialModal'
import Picture from './Picture'
import Button from "react-bootstrap/Button";
import { withRouter } from 'react-router-dom';
import { instanceOf } from 'prop-types';
import { withCookies, Cookies } from 'react-cookie';

class Trainer extends React.Component {
    static propTypes = {
        cookies: instanceOf(Cookies).isRequired
    };

    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {
            csrfToken: cookies.get('XSRF-TOKEN'),
            isLoading: true,
            levelsSelected : Array(14).fill(false),
            answersSelected : Array(14).fill(false),
            correctAnswer : -1,
            selectLevelState : true,
            imageUrl : "",
            showModal : true
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
        for (let i = 0; i <= 13; i++) {
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
            const response = await fetch('/api/image?level=' + randomLevel, {credentials: 'include'});

            const body = await response.json();
            console.log("imageName:" + body.imageName);
            this.setState({imageUrl: '/api/image/resource?imageName=' + body.imageName});
            this.setState({correctAnswer : randomLevel});
            console.log("this.state.correctAnswer" + this.state.correctAnswer);

        }
    }

    handleSkipClick() {
        // if selectLevelState is true, skip is disabled
        // if selectLevelState is false, skip sets select level state to true
        this.setState({selectLevelState : true});
    }

    handleCloseModal() {
        this.setState({ showModal: false });
    }

    render() {
        return(
            <div>
            <AppNavbar/>
            <Container>
                <Row className="justify-content-md-center m-4">
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
                        onClick = {() => this.handleFetchClick()}
                    />
                    <Skip
                        selectLevelState = {this.state.selectLevelState}
                        onClick = {() => this.handleSkipClick()}
                    />
                </Row>
                <InitialModal show = {this.state.showModal} onHide={() => this.handleCloseModal()}/>
            </Container>
            </div>
        );
    }
}

export default withCookies(withRouter(Trainer));



