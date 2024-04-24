document.getElementById('check_signup_password').addEventListener('click', function() {
    var inp = document.getElementById('signup_password');
    var eye = document.getElementById('signup_eye');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});

document.getElementById('check_signup_password2').addEventListener('click', function() {
    var inp = document.getElementById('signup_password2');
    var eye = document.getElementById('signup_eye2');

    if (inp.type === 'password') {
        inp.type = 'text';
        eye.src = 'assets/buttons/show.png';
    } else {
        inp.type = 'password';
        eye.src = 'assets/buttons/hide.png';
    }
});