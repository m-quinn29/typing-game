export class TypingEngine {

    constructor(DOM) {
        this.DOM = DOM;

        this.state = {
            gameMode: "quote",
            timerStarted: false,
            startTime: 0,
            time: 0,
            selectedStartTime: 15,
            quoteFinished: false,
            wpm: null,
            timer: null,

            highestCharIndex: 0,
            prevWordCharIndex: 0,
            prevOffsetTop: 0,

            scrollTotal: 0,
            firstLine: true
        };

        this.prompt = {
            textWords: null,
            charSpans: null
        };

        this.handleInput = this.handleInput.bind(this);

    }

    newText(){
        fetch(`/next-prompt?gameMode=${this.state.gameMode}`).then(response => response.text()).then(text => {
            this.DOM.promptElement.innerText = text;
            this.state.prevWordCharIndex = 0;
            this.state.highestCharIndex = 0;
            this.state.scrollTotal = 0;
            this.state.firstLine = true;

            this.#resetTimeAndWPM();
            this.DOM.inputElement.disabled = false;

            this.prompt.textWords = this.DOM.promptElement.innerHTML.split(" ");
            this.DOM.promptElement.innerHTML = "<span>" + this.DOM.promptElement.innerHTML.split("").join("</span><span>") + "</span>";
            this.prompt.charSpans = this.DOM.promptElement.querySelectorAll('span');

            this.DOM.promptElement.scrollTop = 0;
            this.DOM.inputElement.addEventListener("input", this.handleInput);
        })
    }

    handleInput() {
        if (!this.state.timerStarted) {
            this.#startTimer();
            this.state.timerStarted = true;
        }

        if (this.state.time <= 0 && this.state.gameMode === "word") {
            this.#gameOver();
            return;
        }

        if (this.state.quoteFinished){
            this.#gameOver();
            return;
        }

        this.DOM.timerElement.style.display = "block";
        this.DOM.inputElement.placeholder = "";

        this.#checkInputAndGiveFeedback();
    }

    #checkInputAndGiveFeedback(){
        const typedText = this.DOM.inputElement.value;

        let correctSoFar = true;
        let lastInputCharIndex = this.state.prevWordCharIndex + typedText.length;

        if (this.state.prevOffsetTop === 0){
            this.state.prevOffsetTop = this.prompt.charSpans[this.state.prevWordCharIndex].offsetTop;
        }

        if (lastInputCharIndex > this.prompt.charSpans.length){
            lastInputCharIndex = this.prompt.charSpans.length;
        }


        if (this.state.highestCharIndex > lastInputCharIndex){
            for (let i = lastInputCharIndex; i <= this.state.highestCharIndex; i++){
                this.prompt.charSpans[i].style.color = "#333";
            }
        }

        this.state.highestCharIndex = Math.min(lastInputCharIndex, this.prompt.charSpans.length-1);

        for (let i = 0; i < typedText.length; i++){
            let targetIndex = this.state.prevWordCharIndex + i

            if (targetIndex === (this.prompt.charSpans.length-1) && correctSoFar){
                this.prompt.charSpans[targetIndex].style.color = "green";
                this.#gameOver();
                return;
            }

            if (targetIndex >= this.prompt.charSpans.length){
                break;
            }

            else if (correctSoFar === false){
                this.prompt.charSpans[targetIndex].style.color = "red";
            }
            else if (this.prompt.charSpans[targetIndex].innerText === typedText.charAt(i) && correctSoFar){
                this.prompt.charSpans[targetIndex].style.color = "green";

                if (typedText.charAt(i) === " "){
                    this.state.prevWordCharIndex = targetIndex + 1;
                    this.DOM.inputElement.value = "";

                    this.#checkNewLine();
                }
            }
            else {
                correctSoFar = false;
                this.prompt.charSpans[targetIndex].style.color = "red";
            }
        }
    }

    #resetTimeAndWPM(){
        this.DOM.inputElement.value = "";
        clearInterval(this.state.timer);
        this.state.timerStarted = false;
        this.state.time = this.state.startTime;
        this.DOM.timerElement.innerText = this.state.startTime + "s";
    }

    #startTimer(){
        this.state.timer = setInterval(() => this.#wordTimer(), 1000);
    }

    #wordTimer(){
        if (this.state.gameMode === "word"){
            this.state.time--;
            if (this.state.time <= 0){
                this.#gameOver();
                return;
            }
        }
        else if (this.state.gameMode === "quote"){
            this.state.time++;
        }
        this.DOM.timerElement.innerText = this.state.time + "s";
    }

    #gameOver(){
        this.#calculateWPM();
        clearInterval(this.state.timer);
        this.DOM.inputElement.disabled = true;
        this.DOM.timerElement.innerText = "WPM: " + this.state.wpm;
    }

    #calculateWPM(){
        let correctCharCount = 0;
        this.prompt.charSpans.forEach(span => {
            if (span.style.color === "green") correctCharCount ++;
        });

        let textLength = this.state.startTime;
        if (this.state.gameMode === "quote"){
            textLength = this.state.time;
        }

        this.state.wpm = Math.round((correctCharCount / 5) * (60 / textLength));
    }

    //TODO
    #checkNewLine(){
        const currentSpan = this.prompt.charSpans[this.state.prevWordCharIndex];
        const prevSpan = this.prompt.charSpans[this.state.prevWordCharIndex-2];

        if (currentSpan.offsetTop > prevSpan.offsetTop) {
            if (this.state.firstLine) {
                this.state.firstLine = false;
                return;
            }

            this.state.scrollTotal += 40;
            this.DOM.promptElement.scrollTop = this.state.scrollTotal;

        }
    }

    //TODO
    #destroyPrevLineCharSpans() {

    }

}