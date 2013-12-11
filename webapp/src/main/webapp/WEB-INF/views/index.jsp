<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>TITLE</title>

    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

    <link href="resources/css/bootstrap-3.0.2.css" rel="stylesheet" media="screen">
    <link href="resources/css/bootstrap-theme-3.0.2.css" rel="stylesheet">

    <script type="text/javascript" src="resources/js/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.9.1.min.js"></script>
    <script type="text/javascript" src="resources/js/bootstrap-3.0.2.js"></script>
</head>
<body>

<div class="container">

    <div class="masthead">
        <ul class="nav nav-pills pull-right">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#">About</a></li>
            <li><a href="#">Contact</a></li>
            <li><a href="j_spring_cas_security_logout">Logout</a>
        </ul>
         <h2><img src="resources/img/logo.png"></h2>
        <h3 class="muted">Smoking Cessation Program</h3>
    </div>

    <hr>

    <form role="form">
        <div class="form-group">
            <label for="gender">Are you male or female?</label>
            <input type="text" class="form-control" id="gender" name="gender" placeholder="male/female">
        </div>
        <div class="form-group">
            <label for="year_of_birth">What year were you born?</label>
            <input type="text" class="form-control" id="year_of_birth" name="year_of_birth" placeholder="eg. 1984">
        </div>
        <div class="form-group">
            <label for="quit_date">What date would you like to quit?</label>
            <input type="text" class="form-control" id="quit_date" name="quit_date" placeholder="yyyy-mm-dd">
        </div>
        <div class="form-group">
            <label for="smoking_habit">When do you smoke?</label>
            <input type="text" class="form-control" id="smoking_habit" name="smoking_habit" placeholder="eg. every few hours">
        </div>
        <div>
        <p><label>Do you have any medical conditions?</label></p>
        <label class="checkbox-inline">
            <input type="checkbox" name="medical_conditions" id="medical_conditions_tb" value="tb"> TB
        </label>
        <label class="checkbox-inline">
            <input type="checkbox" name="medical_conditions" id="medical_conditions_hiv" value="hiv"> HIV
        </label>
        <label class="checkbox-inline">
            <input type="checkbox" name="medical_conditions" id="medical_conditions_diabetes" value="diabetes"> DIABETES
        </label>
        <p></p>
        </div>
        <div class="form-group">
            <label for="drinking_habit">How much do you drink?</label>
            <input type="text" class="form-control" id="drinking_habit" name="drinking_habit" placeholder="eg. up to two drinks at a time">
        </div>
        <div class="form-group">
            <label for="pregnancy_plans">Are you planning to fall pregnant?</label>
            <input type="text" class="form-control" id="pregnancy_plans" name="pregnancy_plans" placeholder="YES or NO">
        </div>
        <button id="button" type="button" class="btn btn-default">Submit</button>
    </form>

    <hr>

    <div class="footer">
        <p>&copy; Cell-Life (NPO) - 2013</p>
    </div>

</div>

<script>
    $(document).ready(function () {
        $("button").on("click", function (e) {
            var data = $("form").serialize();
            console.log("posting: " + data);
            $.post("service/iquit-form", data, function (data, status) {
                alert("Data: " + data + "\nStatus: " + status);
            });
        });
    });
</script>

</body>
</html>
