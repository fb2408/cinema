//same function for deleting movie as delete genre of movie
function edit(id) {
    let currentUrl = window.location.href.split('?')[0] + "edit/" + id;
    window.open(currentUrl ,"_self");
}

function del(id){
    let currentUrl = window.location.href.split('?')[0] + "delete/" + id;
    if (confirm('Jeste li sigurni da zelite obrisati?')) {
        window.open(currentUrl ,"_self")
        console.log('deleted');
    } else {
        console.log('not deleted');
    }
}

function openAddForm() {
    let elementSelect = document.getElementById("GenreMovieSelect");
    let elementButton = document.getElementById("GenreMovieButton");
    elementSelect.style.display = "inline";
    elementButton.style.display = "inline";
    let noElement = document.getElementById("openButton");
    noElement.style.display = "none";
}

function openAddFormGenres() {
    let elementInput = document.getElementById("GenreInput");
    let elementButton = document.getElementById("GenreButton");
    elementInput.style.display = "inline";
    elementButton.style.display = "inline";
    let noElement = document.getElementById("openButtonGenre");
    noElement.style.display = "none";
}

function viewGenres(id) {
    let currentUrl = window.location.href.split('?')[0] + id + "/genres/";
    window.open(currentUrl, "_self");
}

function showForDay(day) {
    let currentUrl = window.location.href.split('?')[0] + "?numOfDay="+day;
    window.open(currentUrl, "_self");
}

function changeActive() {
    let element1 = document.getElementById('firstLink');
    let element2 = document.getElementById('secondLink');
    let element1Active = document.getElementById('firstLinkActive');
    let element2Active = document.getElementById('secondLinkActive');
    if(element1Active.style.display == "none") {
        localStorage.setItem('active', 'active')
        element2.style.display = "inline";
        element2Active.style.display = "none"
        element1.style.display = "none";
        element1Active.style.display = "inline";
    } else {
        localStorage.setItem('active', 'previous')
        element1.style.display = "inline";
        element1Active.style.display = "none"
        element2.style.display = "none";
        element2Active.style.display = "inline";
    }
}


function reserveScreening(screeningId) {
    let currentUrl = window.location.href.split('cinema')[0] + "movieSeatReservation/" + screeningId +"/";
    window.open(currentUrl, "_self");
}

function showMoviesForCinema(cinemaId) {
    let currentUrl = window.location.href.split('?')[0] + "cinema/"  + cinemaId + "/";
    window.open(currentUrl, "_self");
}

function unHideLogin() {
    let currentUrl = window.location.href.split('#')[0] + "?NotLoggedIn=True";
    window.open(currentUrl, "_self");
}

function delReservation(id) {
    let currentUrl = window.location.href.split('#')[0] + "/delete/" + id;
    window.open(currentUrl, "_self");
}

