let volume: number = 0.5;

function getAudio(name: string): HTMLAudioElement {
    const audio = new Audio(`./public/audio/${name}.mp3`);
    audio.volume = volume;
    return audio;
}

const pointAudio = async () => getAudio('point').play();
const dieAudio = async () => getAudio('die').play();
const wingAudio = async () => getAudio('wing').play();

const tickrate = 20;
const speed = 3;

function newElement(tag: string, clazz: string | null): HTMLElement {
    const elem = document.createElement(tag);
    if (clazz) {
        elem.classList.add(clazz);
    }
    return elem;
}

class Pipe {
    element: HTMLElement;
    body: HTMLElement;

    constructor(reverse: boolean = false) {
        this.element = newElement('div', 'barreira');
        const border = newElement('div', 'borda');
        this.body = newElement('div', 'corpo');
        this.element.appendChild(reverse ? this.body : border);
        this.element.appendChild(reverse ? border : this.body);
    }

    setHeight(height: number): void {
        this.body.style.height = `${height}px`;
    }
}

class DoublePipe {
    upper: Pipe;
    downer: Pipe;
    element: HTMLElement;
    height: number;
    gap: number;

    constructor(height: number, gap: number, x: number) {
        this.element = newElement('div', 'par-de-barreiras');
        this.upper = new Pipe(true);
        this.downer = new Pipe(false);
        this.element.appendChild(this.upper.element);
        this.element.appendChild(this.downer.element);
        this.height = height;
        this.gap = gap;
        this.drawGapHeight();
        this.setX(x);
    }

    drawGapHeight(): void {
        const upperHeight = Math.random() * (this.height - this.gap);
        const downerHeight = this.height - this.gap - upperHeight;

        this.upper.setHeight(upperHeight);
        this.downer.setHeight(downerHeight);
    }

    getX(): number {
        return parseInt(this.element.style.left.split('px')[0]);
    }

    setX(x: number): void {
        this.element.style.left = `${x}px`;
    }

    getWidth(): number {
        return this.element.clientWidth;
    }
}

function calculatePipes(gameHeight: number, gameWidth: number, pipeGap: number, pipeSpace: number): DoublePipe[] {
    const pipes: DoublePipe[] = [];
    for (let i = 0; i < Math.ceil(gameWidth / pipeSpace) + 1; i++) {
        pipes.push(new DoublePipe(gameHeight, pipeGap, 0.75 * gameWidth + pipeSpace * i));
    }
    return pipes;
}

class PipesLoader {
    pairs: DoublePipe[];
    pipeGap: number;
    gameWidth: number;
    madePoint: () => void;
    pipeSpace: number;

    constructor(gameHeight: number, gameWidth: number, pipeGap: number, pipeSpace: number, madePoint: () => void) {
        this.pairs = [];
        for (let i = 0; i < Math.ceil(gameWidth / pipeSpace) + 1; i++) {
            this.pairs.push(new DoublePipe(gameHeight, pipeGap, 0.75 * gameWidth + pipeSpace * i));
        }
        this.pipeGap = pipeGap;
        this.pipeSpace = pipeSpace;
        this.gameWidth = gameWidth;
        this.madePoint = madePoint;
    }

    animate(): void {
        const mid = this.gameWidth / 2;
        for (let pair of this.pairs) {
            pair.setX(pair.getX() - speed);

            if (pair.getX() < -pair.getWidth()) {
                pair.setX(pair.getX() + this.pipeSpace * this.pairs.length);
                pair.drawGapHeight();
            }

            if (pair.getX() + speed >= mid && pair.getX() < mid) {
                this.madePoint();
            }
        }
    }
}

class Flappy {
    flying: boolean;
    element: HTMLElement;
    gameHeight: number;

    constructor(gameHeight: number, game: FlappyGame) {
        this.flying = false;
        this.gameHeight = gameHeight;
        this.element = newElement('img', 'passaro');
        this.element.setAttribute('src', './public/images/flappy.png');
        this.setY(gameHeight / 2);

        game.element.addEventListener('mousedown', (e: MouseEvent) => {
            if (game.playing) {
                e.preventDefault();
                this.flying = true;
                wingAudio();
            }
        });

        game.element.addEventListener('mouseup', (e: MouseEvent) => {
            if (game.playing) {
                e.preventDefault();
                this.flying = false;
            }
        });

        game.element.addEventListener('touchstart', (e: TouchEvent) => {
            e.preventDefault();
            if (game.playing) {
                this.flying = true;
                wingAudio();
            }
        });

        game.element.addEventListener('touchend', (e: TouchEvent) => {
            e.preventDefault();
            if (game.playing) {
                this.flying = false;
            }
        });
    }

    animate(): void {
        const newY = this.getY() + (this.flying ? tickrate / 2.5 : tickrate / -4);
        const maxY = this.gameHeight - this.element.clientHeight;

        if (newY <= 0) {
            this.setY(0);
        } else if (newY >= maxY) {
            this.setY(maxY);
        } else {
            this.setY(newY);
        }
    }

    getY(): number {
        return parseInt(this.element.style.bottom.split('px')[0]);
    }

    setY(y: number): void {
        this.element.style.bottom = `${y}px`;
    }
}

class ProgressInfo {
    element: HTMLElement;

    constructor() {
        this.element = newElement('span', 'progresso');
        this.setPoints(0);
    }

    setPoints(points: number): void {
        this.element.innerHTML = points + '';
    }

    getPoints(): number {
        return parseInt(this.element.innerHTML);
    }
}

function isOverlapped(elementA: HTMLElement, elementB: HTMLElement): boolean {
    const bcrA = elementA.getBoundingClientRect();
    const bcrB = elementB.getBoundingClientRect();

    const horizontal = bcrA.left + bcrA.width >= bcrB.left && bcrB.left + bcrB.width >= bcrA.left;
    const vertical = bcrA.top + bcrA.height >= bcrB.top && bcrB.top + bcrB.height >= bcrA.top;
    return horizontal && vertical;
}

function isOverlappedWithBorder(bird: Flappy, border: HTMLElement) {
    const bcr = bird.element.getBoundingClientRect();
    const gameBcr = border.getBoundingClientRect();
    return (bcr.top | 0) <= (gameBcr.top | 0) + 5 || (bcr.bottom | 0) >= (gameBcr.bottom | 0) - 5;
}

function isCollided(bird: Flappy, barriers: PipesLoader, border: HTMLElement): boolean {
    let collided = false;
    for (let pair of barriers.pairs) {
        if (!collided) {
            const upper = pair.upper.element;
            const downer = pair.downer.element;
            collided =
                isOverlapped(bird.element, upper) ||
                isOverlapped(bird.element, downer) ||
                isOverlappedWithBorder(bird, border);
        }
    }
    return collided;
}

function setStatistic(name: string, v: number): void {
    const element = document.querySelector(`#${name} .value`);
    if (element) {
        element.innerHTML = v + '';
        popUp(element);
    }
}

function incrementStatistic(name: string, v: number): void {
    const element = document.querySelector(`#${name} .value`);
    if (element) {
        const value = Number(element.innerHTML);
        element.innerHTML = value + v + '';
        popUp(element);
    }
}

function getStatistic(name: string): number {
    const element = document.querySelector(`#${name} .value`);
    if (element) {
        return Number(element.innerHTML);
    }
    return 0;
}

function popUp(element: Element): void {
    element.classList.add('pop');
    setTimeout(() => {
        element.classList.remove('pop');
    }, 75);
}

class FlappyGame {
    element: HTMLElement;
    barrierLoader: PipesLoader;
    flappy: Flappy;
    progress: ProgressInfo;
    gameHeight: number;
    gameWitdh: number;

    playing: boolean = false;
    private points: number = 0;

    constructor() {
        this.element = document.querySelector('[wm-flappy]') || newElement('div', '');
        this.element.innerHTML = '';
        this.gameHeight = this.element.clientHeight;
        this.gameWitdh = this.element.clientWidth;
        this.progress = new ProgressInfo();
        this.barrierLoader = new PipesLoader(this.gameHeight, this.gameWitdh, 300, 400, () => {
            this.progress.setPoints(++this.points);
            pointAudio();
        });
        this.flappy = new Flappy(this.gameHeight, this);
        this.element.appendChild(this.progress.element);
        this.element.appendChild(this.flappy.element);
        this.barrierLoader.pairs.forEach((pair) => this.element.appendChild(pair.element));
    }

    request(message: string | null): void {
        const button = document.getElementById('start-button');
        if (button) {
            button.classList.remove('button-blocked');

            if (message) {
                button.innerHTML = message;
            }

            button.onmousedown = (e) => {
                if (e.button == 2 || button.innerHTML != message) return;

                button.innerHTML = '3';
                setTimeout(() => {
                    button.innerHTML = '2';
                    setTimeout(() => {
                        button.innerHTML = '1';
                        setTimeout(() => {
                            button.innerHTML = 'Playing...';
                            button.classList.add('button-blocked');
                            this.start();
                        }, 1000);
                    }, 1000);
                }, 1000);
            };
        } else {
            alert('Erro!');
        }
    }

    start(): void {
        this.playing = true;
        incrementStatistic('plays', 1);
        const timer = setInterval(() => {
            this.barrierLoader.animate();
            this.flappy.animate();
            if (isCollided(this.flappy, this.barrierLoader, this.element)) {
                this.playing = false;
                clearInterval(timer);
                dieAudio();
                const points = this.progress.getPoints();
                setStatistic('last', points);
                if (getStatistic('best') < points) {
                    setStatistic('best', points);
                }
                new FlappyGame().request(`You lost! Points: ${this.progress.getPoints()}. Click here to restart!`);
            }
        }, tickrate);
    }
}

new FlappyGame().request('Click here to start!');
