<html>
<header>
    <script
            src="https://code.jquery.com/jquery-1.12.4.min.js"
            integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
            crossorigin="anonymous"></script>
</header>
<body>
<h2>Hello World!</h2>
<script>
    $.ajax({
        url: 'http://10.10.12.38:9889/bigDataPlayer/clickStat',
        type: 'get',
        data: {'type': 'day-7'},
        dataType: 'text',//后台做允许跨域情况下，后台返回的不是json数组用text方式请求，否则用json请求
        success: function (res) {
            console.log(res)
        },
        error: function () {
            alert("error")
        }
    });
</script>
</body>
</html>
