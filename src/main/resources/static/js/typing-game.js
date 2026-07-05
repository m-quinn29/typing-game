const DOM = {
    promptElement: document.getElementById('prompt-text'),
    inputElement: document.getElementById('input-field'),
    timerElement: document.getElementById('timer'),
    timeSelectors: document.getElementById('time-selectors'),
    nextButton: document.getElementById('next-button')
};


let state = {
    gameMode: "quote",
    timerStarted: false,
    startTime: 0,
    time: 0,
    selectedStartTime: 15,
    quoteFinished: false,
    highestCharIndex: 0,
    prevWordCharIndex: 0,
    wpm: null,
    timer: null
};

let prompt = {
    textWords: null,
    charSpans: null
};


setPromptWordsAndSpans();
addListeners();

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

    DOM.nextButton.addEventListener('click', newText);
    DOM.inputElement.addEventListener("input", handleInput);
}


function handleInput() {
    if (!state.timerStarted) {
        startTimer();
        state.timerStarted = true;
    }

    if (state.time <= 0 && state.gameMode === "word") {
        gameOver();
        return;
    }

    if (state.quoteFinished){
        gameOver();
        return;
    }

    DOM.timerElement.style.display = "block";
    DOM.inputElement.placeholder = "";

    const typedText = DOM.inputElement.value;
    let correctSoFar = true;

    let lastInputCharIndex = state.prevWordCharIndex + typedText.length;

    if (lastInputCharIndex > prompt.charSpans.length){
        lastInputCharIndex = prompt.charSpans.length;
    }


    if (state.highestCharIndex > lastInputCharIndex){
        for (let i = lastInputCharIndex; i <= state.highestCharIndex; i++){
            prompt.charSpans[i].style.color = "#333";
        }
    }

    state.highestCharIndex = Math.min(lastInputCharIndex, prompt.charSpans.length-1);

    for (let i = 0; i < typedText.length; i++){
        let targetIndex = state.prevWordCharIndex + i

        if (targetIndex === (prompt.charSpans.length-1) && correctSoFar){
            prompt.charSpans[targetIndex].style.color = "green";
            gameOver();
            return;
        }

        if (targetIndex >= prompt.charSpans.length){
            break;
        }

        else if (correctSoFar === false){
            prompt.charSpans[targetIndex].style.color = "red";
        }
        else if (prompt.charSpans[targetIndex].innerText === typedText.charAt(i) && correctSoFar){
            prompt.charSpans[targetIndex].style.color = "green";

            if (typedText.charAt(i) === " "){
                state.prevWordCharIndex = targetIndex + 1;
                DOM.inputElement.value = "";
            }
        }
        else {
            correctSoFar = false;
            prompt.charSpans[targetIndex].style.color = "red";
        }
    }
}

function gameOver(){
    calculateWPM();
    clearInterval(state.timer);
    DOM.inputElement.disabled = true;
    DOM.timerElement.innerText = "WPM: " + state.wpm;
}

function newText(){
    fetch(`/next-prompt?gameMode=${state.gameMode}`).then(response => response.text()).then(text => {
        DOM.promptElement.innerText = text;
        setPromptWordsAndSpans();
        state.prevWordCharIndex = 0;
        state.highestCharIndex = 0;

        resetTimeAndWPM();
        DOM.inputElement.disabled = false;
    })
}






function startTimer(){
    state.timer = setInterval(wordTimer, 1000);
}


function wordTimer(){
    if (state.gameMode === "word"){
        state.time--;
        if (state.time <= 0){
            gameOver();
            return;
        }
    }
    else if (state.gameMode === "quote"){
        state.time++;
    }
    DOM.timerElement.innerText = state.time + "s";
}

function calculateWPM(){
    let correctCharCount = 0;
    prompt.charSpans.forEach(span => {
        if (span.style.color === "green") correctCharCount ++;
    });

    let textLength = state.startTime;
    if (state.gameMode === "quote"){
        textLength = state.time;
    }

    state.wpm = Math.round((correctCharCount / 5) * (60 / textLength));
}

function setPromptWordsAndSpans(){
    prompt.textWords = DOM.promptElement.innerHTML.split(" ");
    DOM.promptElement.innerHTML = "<span>" + DOM.promptElement.innerHTML.split("").join("</span><span>") + "</span>";
    prompt.charSpans = DOM.promptElement.querySelectorAll('span');
}

function resetTimeAndWPM(){
    DOM.inputElement.value = "";
    clearInterval(state.timer);
    state.timerStarted = false;
    state.time = state.startTime;
    DOM.timerElement.innerText = state.startTime + "s";
}



function setTimeMode(selectedTime){
    if (!state.timerStarted){
        state.startTime = selectedTime;
        state.selectedStartTime = selectedTime;
        state.time = selectedTime;
        DOM.timerElement.innerText = state.time + "s";
    }
}

function setGameMode(mode){
    if (mode === state.gameMode){
        return;
    }

    if (mode === "quote"){
        DOM.timeSelectors.style.display = 'none';
        DOM.timerElement.style.display = 'none';
        state.startTime = 0;
    }
    else if (mode === "word"){
        DOM.timeSelectors.style.display = 'block';
        DOM.timerElement.style.display = 'block';
        state.startTime = state.selectedStartTime;
    }
    state.gameMode = mode;
    newText();
}
