import {TypingEngine} from "./typing-engine.js";

const DOM = {

};

const typingEngine = new TypingEngine(DOM);

function addListeners(){
    const joinButtons = document.querySelectorAll('.join-buttons button');

    joinButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            const raceType = event.currentTarget.getAttribute('data-type');
            startRace(raceType);
        });
    });
}

function startRace(raceType) {
    document.querySelector('.join-buttons').style.display = "none";
    TypingEngine.raceType = raceType;
    showState('waiting-for-race');
}

function showState(state) {
    document.querySelectorAll('.game-state').forEach(element => {
        element.classList.remove('active')
    });

    document.querySelector(`.${state}`).classList.add('active');

}

addListeners();