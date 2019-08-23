<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/5/17
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>淡白影视</title>
    <link rel="shortcut icon" href="img/logo.ico" />
    <script src="js/jquery-3.3.1.js" type="text/javascript" charset="utf-8"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="css/sy.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span12">
            <nav class="navbar navbar-default">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="#">淡白影视</a>
                    </div>
                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#">首页<span class="sr-only">(current)</span></a></li>
                            <li><a href="#">动作片</a></li>
                            <li><a href="#">喜剧片</a></li>
                            <li><a href="#">爱情片</a></li>
                            <li><a href="#">科幻片</a></li>
                            <li><a href="#">恐怖片</a></li>
                            <li><a href="#">剧情片</a></li>
                            <li><a href="#">战争片</a></li>
                            <li><a href="#">国产剧</a></li>
                            <li><a href="#">韩国剧</a></li>
                            <li><a href="#">欧美剧</a></li>
                            <li><a href="#">日本剧</a></li>
                            </li>
                        </ul>
                        <form class="navbar-form navbar-left">
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="搜索影视">
                            </div>
                            <button type="submit" class="btn btn-default">搜索</button>
                        </form>
                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#">Link</a></li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">更多<span class="caret"></span></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#">友链</a></li>
                                    <li><a href="#">友链</a></li>
                                    <li><a href="#">友链</a></li>
                                    <li role="separator" class="divider"></li>
                                    <li><a href="#">站长QQ2671641895</a></li>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <div id="dzp">
                <div class="yslb">
                    <h4>动作片</h4>
                    <div class="ys">
                        <img class="ysimg" src="https://tupian.tupianzy.com/pic/upload/vod/2019-05-15/201905151557855866.jpg" pid="1">
                        <p class="text-center">name</p>
                    </div>
                </div>
                <div class="yslb">
                    <h4>喜剧片</h4>
                    <div class="ys">
                        <img class="ysimg" src="https://tupian.tupianzy.com/pic/upload/vod/2019-05-15/201905151557855866.jpg">
                        <p class="text-center">name</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script>
    $(function(){
        $('.ysimg').click(function(){
            var $v=$(this); //jQuery对象
            window.open("ys?pid="+$v.attr("pid"));
        });
    });
</script>
</body>
</html>