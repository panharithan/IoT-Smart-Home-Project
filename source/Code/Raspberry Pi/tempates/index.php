<?php
if (isset($_POST['LED_on'])){
    system("gpio -g mode 27 out");
    system("gpio -g mode 17 out");
    system("gpio -g write 27 0");
    system("gpio -g write 17 0");
}
else if (isset($_POST['LED_off']))
{system("gpio -g mode 27 out");
    system("gpio -g mode 17 out");
    system("gpio -g write 27 1");
    system("gpio -g write 17 1");
}
?>

<!DOCTYPE html>
<html>
<head>
    <title>Home Automation</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <style>

        body {
            background-image: url('https://s-media-cache-ak0.pinimg.com/originals/8c/0c/51/8c0c51b6ff1704439301d1c57989862f.jpg');
            background-attachment: fixed;
            background-repeat: no-repeat;
            background-size: cover;

        }

        @media screen and (max-width: 700px) {
            body {
                padding: 60px 0 0 0;
                width: 100%
            }
        }

        a{
            color: inherit;
        }
        .main_item{
            margin-top:40px;
        }
        .menu-item
        {
            display: inline-block;
            text-align: center;
            line-height: 80px;
            border-radius: 100%;
            color: white;
            width: 80px;
            height: 80px;
            margin-bottom: 10px;
            background: white;
            -webkit-transform: translate3d(0, 0, 0);
            transform: translate3d(0, 0, 0);
            -webkit-transition: -webkit-transform ease-out 200ms;
            transition: -webkit-transform ease-out 200ms;
            transition: transform ease-out 200ms;
            transition: transform ease-out 200ms, -webkit-transform ease-out 200ms;
        }

        .menu {
            margin:0px 40px -40px 40px;
            width: 120px;
            height: 100px;
            text-align: left;
            box-sizing: border-box;
            font-size: 26px;
        }



        .menu-item:hover {
            background: #EEEEEE;
            color: white;
        }

        .menu-item:nth-child(3) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }

        .menu-item:nth-child(4) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }

        .menu-item:nth-child(5) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }

        .menu-item:nth-child(6) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }

        .menu-item:nth-child(7) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }

        .menu-item:nth-child(8) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }

        .menu-item:nth-child(9) {
            -webkit-transition-duration: 180ms;
            transition-duration: 180ms;
        }
        .menu-open:checked + .menu-open-button {
            -webkit-transition-timing-function: linear;
            transition-timing-function: linear;
            -webkit-transition-duration: 200ms;
            transition-duration: 200ms;
            -webkit-transform: scale(0.8, 0.8) translate3d(0, 0, 0);
            transform: scale(0.8, 0.8) translate3d(0, 0, 0);
        }

        .menu-open:checked ~ .menu-item {
            -webkit-transition-timing-function: cubic-bezier(0.935, 0, 0.34, 1.33);
            transition-timing-function: cubic-bezier(0.935, 0, 0.34, 1.33);
        }

        .menu-open:checked ~ .menu-item:nth-child(3) {
            transition-duration: 180ms;
            -webkit-transition-duration: 180ms;
            -webkit-transform: translate3d(0.08361px, -104.99997px, 0);
            transform: translate3d(0.08361px, -104.99997px, 0);
        }

        .menu-open:checked ~ .menu-item:nth-child(4) {
            transition-duration: 280ms;
            -webkit-transition-duration: 280ms;
            -webkit-transform: translate3d(90.9466px, -52.47586px, 0);
            transform: translate3d(90.9466px, -52.47586px, 0);
        }

        .menu-open:checked ~ .menu-item:nth-child(5) {
            transition-duration: 380ms;
            -webkit-transition-duration: 380ms;
            -webkit-transform: translate3d(90.9466px, 52.47586px, 0);
            transform: translate3d(90.9466px, 52.47586px, 0);
        }

        .menu-open:checked ~ .menu-item:nth-child(6) {
            transition-duration: 480ms;
            -webkit-transition-duration: 480ms;
            -webkit-transform: translate3d(0.08361px, 104.99997px, 0);
            transform: translate3d(0.08361px, 104.99997px, 0);
        }

        .menu-open:checked ~ .menu-item:nth-child(7) {
            /*transition-duration: 580ms;*/
            -webkit-transition-duration: 580ms;
            -webkit-transform: translate3d(-90.86291px, 52.62064px, 0);
            transform: translate3d(-90.86291px, 52.62064px, 0);
        }

        .menu-open:checked ~ .menu-item:nth-child(8) {
            transition-duration: 680ms;
            -webkit-transition-duration: 680ms;
            -webkit-transform: translate3d(-91.03006px, -52.33095px, 0);
            transform: translate3d(-91.03006px, -52.33095px, 0);
        }

        .menu-open:checked ~ .menu-item:nth-child(9) {
            transition-duration: 780ms;
            -webkit-transition-duration: 780ms;
            -webkit-transform: translate3d(-0.25084px, -104.9997px, 0);
            transform: translate3d(-0.25084px, -104.9997px, 0);
        }

        .blue {
            background-color:white;
            color: blue;
            box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.14);
            text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.12);
        }

        .blue:hover {
            color: #2962FF;
            text-shadow: none;
        }

        .green {
            background-color: white;
            color: red;
            box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.14);
            text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.12);
        }

        .green:hover {
            color: #B71C1C;
            text-shadow: none;
        }

        .red {
            background-color:#651FFF;
            box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.14);
            text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.12);
        }

        .red:hover {
            color: #651FFF;
            text-shadow: none;
        }

        .purple {
            background-color: #C49CDE;
            box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.14);
            text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.12);
        }

        .purple:hover {
            color: #C49CDE;
            text-shadow: none;
        }

        .orange {
            background-color: #FC913A;
            box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.14);
            text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.12);
        }

        .orange:hover {
            color: #FC913A;
            text-shadow: none;
        }

        .lightblue {
            background-color:#651FFF;
            box-shadow: 3px 3px 0 0 rgba(0, 0, 0, 0.14);
            text-shadow: 1px 1px 0 rgba(0, 0, 0, 0.12);
        }

        .lightblue:hover {
            color: #651FFF;
            text-shadow: none;
        }

        .credit {
            margin: 24px 20px 120px 0;
            text-align: right;
            color: #EEEEEE;
        }

        .credit a {
            padding: 8px 0;
            color: #C49CDE;
            text-decoration: none;
            transition: all 0.3s ease 0s;
        }

        .credit a:hover {
            text-decoration: underline;
        }
        /*pop us camera*/

        div#popupContact {
            width: 35%;
            height: 30%;
            display:none;
            position:fixed;
            left:35%;
            top:17%;
            margin-left:-202px;
        }
        .camera_page {
            max-width:800px;
            height: 500px;
            padding:10px 10px;
            background-color: white;
            border:2px solid gray;
            border-radius:10px;
            font-family:raleway;
            opacity: 1;
            display: block;
        }
        .icon_span_close{
            color: red;
            position: absolute;
            font-size: 35px;
            display: block;
            right:-8%;
            margin-top: -20px;
        }

    </style>

</head>
<body>
<div class="main_item">
    <nav class="menu">
        <form method="post">
            <button name="LED_on" class="menu-item blue" id="turn_NO-LED" onclick="openLED()" data-toggle="tooltip" data-placement="right" title="Lamp-ON"><i class="glyphicon glyphicon-off"></i></button>
            <button name="LED_off" class="menu-item green" onclick="offLED()" id="turn_off-LED"data-toggle="tooltip" data-placement="right" title="Lamp-OFF"> <i class="glyphicon glyphicon-off"></i> </button>
        </form>
        <button name="camera" class="menu-item red"  onclick="div_show()" data-toggle="tooltip" data-placement="right" title="Camera"><i class="glyphicon glyphicon-camera"></i></button>
<!--        <button name="microphone" class="menu-item purple"> <i class="fa fa-microphone"></i> </button>-->
        <button name="lamp" class="menu-item orange" data-toggle="tooltip" data-placement="right" title="Lamp"><i class="glyphicon glyphicon-lamp"></i> </button>
        <a class="menu-item lightblue" href="Squadfree/index.html" data-toggle="tooltip" data-placement="right" title="About"> <i class="glyphicon glyphicon-exclamation-sign"></i> </a>
    </nav>
</div>
<div id="popupContact">
    <div class="icon_span_close"onclick="div_hide()">
        <span class="glyphicon glyphicon-remove-sign"></span>
    </div>
    <div id="icon_camera">
        <div class="camera_page">
            <img id="bg" src="{{ url_for('video_feed') }}"style="width: 100%; height: 100%">
        </div>
        <!-- Popup Div Ends Here -->
    </div>
</div>

<div class="btn_logout" style="text-align: right;margin-top: -150px;color: blueviolet">
    <h2><a href="logout.php" class="btn">Logout</a></h2>
</div>

<div class="group-name" style="width: 50%;height: auto;margin-left: 60%;margin-top: 20px; color: blue;">
    <div class="logo-school">
        <img src="https://upload.wikimedia.org/wikipedia/km/e/ee/Rupp_logo.png" style="width: 60px">
        <img src="fe.png" style="width: 60px">
    </div>
    <div class="Adviser-name" >
        <h3>Department : <strong style="color: yellow !important;font-size: 16px;">Telecommunication and <h3 style="margin-top: 10px;margin-left: 150px;font-size: 16px;"><strong> Electronic Engineering</strong></h3></strong></h3>
    </div>
    <div class="Adviser-name">
        <h3>Adviser : <strong style="color: yellow !important;font-size: 18px;margin-left: 50px">Prof. Thap Tharoeun</strong></h3>
    </div>
    <div class="team-name">
        <h3>Members:</h3>
        <div class="member" style="color: yellow;margin-top: -30px;margin-left: 150px;font-weight: 700">

            <h4> <strong>An Panharith</strong></h4>
            <h4><strong>Yeu Rotha</strong></h4>
            <h4><strong>Siet Sophort</strong></h4>

        </div>

    </div>

</div>

<script type="text/javascript">
    function openLED(){
//        document.getElementById("turn_NO-LED").style.backgroundColor ="blue";
        var x = document.getElementById("turn_NO-LED").style.backgroundColor ="blue";;
        document.getElementById("demo").innerHTML = x;

    }
    function offLED() {
        document.getElementById('turn_off-LED').style.backgroundColor = 'red';
    }
    function div_show() {
        document.getElementById('popupContact').style.display = "block";
    }
    function div_hide(){
        document.getElementById('popupContact').style.display = "none";
    }
</script>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>


</body>
</html>
