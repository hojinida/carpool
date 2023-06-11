// 비밀번호 형식 확인
$('#confirm-password').on('blur', function() {
    const password = $('#password').val();
    const confirmPassword = $('#confirm-password').val();

    if (password !== confirmPassword) {
        $('#submitBtn').prop('disabled', true);
        $('#submitBtn').text('비밀번호가 일치하지 않습니다');
        $('#password-check-msg').text('비밀번호가 일치하지 않습니다.');
            } else {
        $('#submitBtn').prop('disabled', false);
        $('#submitBtn').text('회원가입');
        $('#password-check-msg').text('');
    }
})

// 전화번호 형식 확인
$('#phone').on('blur', function() {
    var phone = $('#phone').val();
    var regex = /^010[-\s]?\d{4}[-\s]?\d{4}$/;
    if (!regex.test(phone)) {
        $('#phone-check-msg').text('올바른 전화번호 형식이 아닙니다.');
    } else {
        $('#phone-check-msg').text('');
    }
});   

// 이메일 인증 요청
var isSended = false;
$('#send-email').on("click", function() {
const email = $('#email').val();
if (!email.match(/^[^@]+@mju\.ac\.kr$/)) {
        $('#email-check-msg').text('이메일 형식이 올바르지 않습니다.');
        return false;
    }
else if(!isSended) {
        isSended = true;
        $('#email-check-msg').text('');
        $.ajax({
        url: "/email",
        type: "POST",
        headers: {'Content-Type': 'application/json'},
        data: JSON.stringify({"email": email}),
        success: function(data) {
            alert("인증번호가 전송되었습니다.");
            $("#auth-input").show();
            console.log(data);
        },
        error: function(e) {
            console.log(e);
        
        setTimeout(function() {
            isSended = false;
        }, 3000); // 3초간 같은 이벤트 무시
    }
})
}  
})

// 이메일 인증 확인
$('#verify-auth-code').on("click", function() {
const authCode = $('#auth-code').val();
$.ajax({
    url: "/email/auth",
    type: "POST",
    headers: {'Content-Type': 'application/json'},
    data: JSON.stringify({"token": authCode}),
    success: function(data) {
        alert("이메일 인증 성공");
        $("#auth-input").hide();
        console.log(data);
    },
    error: function(e) {
        console.log(e);
    }
})
})

// 폼 전송
$('#register-form').submit(function(e) {
e.preventDefault();
$.ajax({
    url: "/api/v1/user",
    type: "POST",
    headers: {'Content-Type': 'application/json'},
    data: JSON.stringify({"email": $('#email').val(), "password": $('#password').val(), "name": $('#name').val(), "phoneNumber": $('#phone').val()}),
    success: function(data) {
    alert("회원가입 성공");
        window.location.href = "index.html";
    },
    error: function(e) {
        console.log(e);
    }
})
})