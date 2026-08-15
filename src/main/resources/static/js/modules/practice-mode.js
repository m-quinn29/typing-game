import {TypingEngine} from "./typing-engine.js";


const DOM = {
    promptElement: document.getElementById('prompt-text'),
    inputElement: document.getElementById('input-field'),
    timerElement: document.getElementById('timer'),
    timeSelectors: document.getElementById('time-selectors'),
    nextButton: document.getElementById('next-button')
};

const typingEngine = new TypingEngine(DOM);

function addListeners(){
    const modeButtons = document.querySelectorAll('.mode-selector-container button');
    const timeButtons = document.querySelectorAll('.time-selector-container button');

    modeButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            const selectedMode = event.currentTarget.getAttribute('data-mode');
            setGameMode(selectedMode);
        });
    });

    timeButtons.forEach(button => {
        button.addEventListener('click', (event) => {
            const selectedTime = event.currentTarget.getAttribute('data-time');
            setTimeMode(selectedTime);
        });
    });

    DOM.nextButton.addEventListener('click', () => typingEngine.newText());
}

function setTimeMode(selectedTime){
    if (!typingEngine.state.timerStarted){
        typingEngine.state.startTime = selectedTime;
        typingEngine.state.selectedStartTime = selectedTime;
        typingEngine.state.time = selectedTime;
        DOM.timerElement.innerText = typingEngine.state.time + "s";
    }
}

function setGameMode(mode){
    if (mode === typingEngine.state.gameMode){
        return;
    }

    if (mode === "quote"){
        DOM.timeSelectors.style.display = 'none';
        DOM.timerElement.style.display = 'none';
        typingEngine.state.startTime = 0;
    }
    else if (mode === "word"){
        DOM.timeSelectors.style.display = 'block';
        DOM.timerElement.style.display = 'block';
        typingEngine.state.startTime = typingEngine.state.selectedStartTime;
    }
    typingEngine.state.gameMode = mode;
    typingEngine.newText();
}

addListeners();
typingEngine.newText();

