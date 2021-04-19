let rtime;
let timeout = false;
let delta = 200;

window.addEventListener('resize', () => {
    rtime = new Date();
    if (timeout === false) {
        timeout = true;
        setTimeout(resizeend, delta);
    }
});

function resizeend() {
    if (new Date() - rtime < delta) {
        setTimeout(resizeend, delta);
    } else {
        timeout = false;
        window.location.reload();
    }
}
