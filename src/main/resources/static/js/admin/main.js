function boldIcon(element, iconId) {
    var icons = document.querySelectorAll('i');
    icons.forEach(function(icon) {
        icon.classList.remove('bold-icon');
    });

    element.classList.add('bold-icon');

    localStorage.setItem('selectedIcon', iconId);
}

function applySavedIcon() {
    var savedIconId = localStorage.getItem('selectedIcon');
    if (savedIconId) {
        var savedIcon = document.getElementById(savedIconId);
        if (savedIcon) {
            savedIcon.classList.add('bold-icon');
        }
    }
}

window.onload = applySavedIcon;


