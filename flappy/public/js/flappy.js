"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var volume = 0.5;
function getAudio(name) {
    var audio = new Audio("./public/audio/" + name + ".mp3");
    audio.volume = volume;
    return audio;
}
var pointAudio = function () { return __awaiter(void 0, void 0, void 0, function () { return __generator(this, function (_a) {
    return [2 /*return*/, getAudio('point').play()];
}); }); };
var dieAudio = function () { return __awaiter(void 0, void 0, void 0, function () { return __generator(this, function (_a) {
    return [2 /*return*/, getAudio('die').play()];
}); }); };
var wingAudio = function () { return __awaiter(void 0, void 0, void 0, function () { return __generator(this, function (_a) {
    return [2 /*return*/, getAudio('wing').play()];
}); }); };
var tickrate = 20;
var speed = 3;
function newElement(tag, clazz) {
    var elem = document.createElement(tag);
    if (clazz) {
        elem.classList.add(clazz);
    }
    return elem;
}
var Pipe = /** @class */ (function () {
    function Pipe(reverse) {
        if (reverse === void 0) { reverse = false; }
        this.element = newElement('div', 'barreira');
        var border = newElement('div', 'borda');
        this.body = newElement('div', 'corpo');
        this.element.appendChild(reverse ? this.body : border);
        this.element.appendChild(reverse ? border : this.body);
    }
    Pipe.prototype.setHeight = function (height) {
        this.body.style.height = height + "px";
    };
    return Pipe;
}());
var DoublePipe = /** @class */ (function () {
    function DoublePipe(height, gap, x) {
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
    DoublePipe.prototype.drawGapHeight = function () {
        var upperHeight = Math.random() * (this.height - this.gap);
        var downerHeight = this.height - this.gap - upperHeight;
        this.upper.setHeight(upperHeight);
        this.downer.setHeight(downerHeight);
    };
    DoublePipe.prototype.getX = function () {
        return parseInt(this.element.style.left.split('px')[0]);
    };
    DoublePipe.prototype.setX = function (x) {
        this.element.style.left = x + "px";
    };
    DoublePipe.prototype.getWidth = function () {
        return this.element.clientWidth;
    };
    return DoublePipe;
}());
function calculatePipes(gameHeight, gameWidth, pipeGap, pipeSpace) {
    var pipes = [];
    for (var i = 0; i < Math.ceil(gameWidth / pipeSpace) + 1; i++) {
        pipes.push(new DoublePipe(gameHeight, pipeGap, 0.75 * gameWidth + pipeSpace * i));
    }
    return pipes;
}
var PipesLoader = /** @class */ (function () {
    function PipesLoader(gameHeight, gameWidth, pipeGap, pipeSpace, madePoint) {
        this.pairs = [];
        for (var i = 0; i < Math.ceil(gameWidth / pipeSpace) + 1; i++) {
            this.pairs.push(new DoublePipe(gameHeight, pipeGap, 0.75 * gameWidth + pipeSpace * i));
        }
        this.pipeGap = pipeGap;
        this.pipeSpace = pipeSpace;
        this.gameWidth = gameWidth;
        this.madePoint = madePoint;
    }
    PipesLoader.prototype.animate = function () {
        var mid = this.gameWidth / 2;
        for (var _i = 0, _a = this.pairs; _i < _a.length; _i++) {
            var pair = _a[_i];
            pair.setX(pair.getX() - speed);
            if (pair.getX() < -pair.getWidth()) {
                pair.setX(pair.getX() + this.pipeSpace * this.pairs.length);
                pair.drawGapHeight();
            }
            if (pair.getX() + speed >= mid && pair.getX() < mid) {
                this.madePoint();
            }
        }
    };
    return PipesLoader;
}());
var Flappy = /** @class */ (function () {
    function Flappy(gameHeight, game) {
        var _this = this;
        this.flying = false;
        this.gameHeight = gameHeight;
        this.element = newElement('img', 'passaro');
        this.element.setAttribute('src', './public/images/flappy.png');
        this.setY(gameHeight / 2);
        game.element.addEventListener('mousedown', function (e) {
            if (game.playing) {
                e.preventDefault();
                _this.flying = true;
                wingAudio();
            }
        });
        game.element.addEventListener('mouseup', function (e) {
            if (game.playing) {
                e.preventDefault();
                _this.flying = false;
            }
        });
        game.element.addEventListener('touchstart', function (e) {
            e.preventDefault();
            if (game.playing) {
                _this.flying = true;
                wingAudio();
            }
        });
        game.element.addEventListener('touchend', function (e) {
            e.preventDefault();
            if (game.playing) {
                _this.flying = false;
            }
        });
    }
    Flappy.prototype.animate = function () {
        var newY = this.getY() + (this.flying ? tickrate / 2.5 : tickrate / -4);
        var maxY = this.gameHeight - this.element.clientHeight;
        if (newY <= 0) {
            this.setY(0);
        }
        else if (newY >= maxY) {
            this.setY(maxY);
        }
        else {
            this.setY(newY);
        }
    };
    Flappy.prototype.getY = function () {
        return parseInt(this.element.style.bottom.split('px')[0]);
    };
    Flappy.prototype.setY = function (y) {
        this.element.style.bottom = y + "px";
    };
    return Flappy;
}());
var ProgressInfo = /** @class */ (function () {
    function ProgressInfo() {
        this.element = newElement('span', 'progresso');
        this.setPoints(0);
    }
    ProgressInfo.prototype.setPoints = function (points) {
        this.element.innerHTML = points + '';
    };
    ProgressInfo.prototype.getPoints = function () {
        return parseInt(this.element.innerHTML);
    };
    return ProgressInfo;
}());
function isOverlapped(elementA, elementB) {
    var bcrA = elementA.getBoundingClientRect();
    var bcrB = elementB.getBoundingClientRect();
    var horizontal = bcrA.left + bcrA.width >= bcrB.left && bcrB.left + bcrB.width >= bcrA.left;
    var vertical = bcrA.top + bcrA.height >= bcrB.top && bcrB.top + bcrB.height >= bcrA.top;
    return horizontal && vertical;
}
function isOverlappedWithBorder(bird, border) {
    var bcr = bird.element.getBoundingClientRect();
    var gameBcr = border.getBoundingClientRect();
    return (bcr.top | 0) <= (gameBcr.top | 0) + 5 || (bcr.bottom | 0) >= (gameBcr.bottom | 0) - 5;
}
function isCollided(bird, barriers, border) {
    var collided = false;
    for (var _i = 0, _a = barriers.pairs; _i < _a.length; _i++) {
        var pair = _a[_i];
        if (!collided) {
            var upper = pair.upper.element;
            var downer = pair.downer.element;
            collided =
                isOverlapped(bird.element, upper) ||
                    isOverlapped(bird.element, downer) ||
                    isOverlappedWithBorder(bird, border);
        }
    }
    return collided;
}
function setStatistic(name, v) {
    var element = document.querySelector("#" + name + " .value");
    if (element) {
        element.innerHTML = v + '';
        popUp(element);
    }
}
function incrementStatistic(name, v) {
    var element = document.querySelector("#" + name + " .value");
    if (element) {
        var value = Number(element.innerHTML);
        element.innerHTML = value + v + '';
        popUp(element);
    }
}
function getStatistic(name) {
    var element = document.querySelector("#" + name + " .value");
    if (element) {
        return Number(element.innerHTML);
    }
    return 0;
}
function popUp(element) {
    element.classList.add('pop');
    setTimeout(function () {
        element.classList.remove('pop');
    }, 75);
}
var FlappyGame = /** @class */ (function () {
    function FlappyGame() {
        var _this = this;
        this.playing = false;
        this.points = 0;
        this.element = document.querySelector('[wm-flappy]') || newElement('div', '');
        this.element.innerHTML = '';
        this.gameHeight = this.element.clientHeight;
        this.gameWitdh = this.element.clientWidth;
        this.progress = new ProgressInfo();
        this.barrierLoader = new PipesLoader(this.gameHeight, this.gameWitdh, 300, 400, function () {
            _this.progress.setPoints(++_this.points);
            pointAudio();
        });
        this.flappy = new Flappy(this.gameHeight, this);
        this.element.appendChild(this.progress.element);
        this.element.appendChild(this.flappy.element);
        this.barrierLoader.pairs.forEach(function (pair) { return _this.element.appendChild(pair.element); });
    }
    FlappyGame.prototype.request = function (message) {
        var _this = this;
        var button = document.getElementById('start-button');
        if (button) {
            button.classList.remove('button-blocked');
            if (message) {
                button.innerHTML = message;
            }
            button.onmousedown = function (e) {
                if (e.button == 2 || button.innerHTML != message)
                    return;
                button.innerHTML = '3';
                setTimeout(function () {
                    button.innerHTML = '2';
                    setTimeout(function () {
                        button.innerHTML = '1';
                        setTimeout(function () {
                            button.innerHTML = 'Playing...';
                            button.classList.add('button-blocked');
                            _this.start();
                        }, 1000);
                    }, 1000);
                }, 1000);
            };
        }
        else {
            alert('Erro!');
        }
    };
    FlappyGame.prototype.start = function () {
        var _this = this;
        this.playing = true;
        incrementStatistic('plays', 1);
        var timer = setInterval(function () {
            _this.barrierLoader.animate();
            _this.flappy.animate();
            if (isCollided(_this.flappy, _this.barrierLoader, _this.element)) {
                _this.playing = false;
                clearInterval(timer);
                dieAudio();
                var points = _this.progress.getPoints();
                setStatistic('last', points);
                if (getStatistic('best') < points) {
                    setStatistic('best', points);
                }
                new FlappyGame().request("You lost! Points: " + _this.progress.getPoints() + ". Click here to restart!");
            }
        }, tickrate);
    };
    return FlappyGame;
}());
new FlappyGame().request('Click here to start!');
