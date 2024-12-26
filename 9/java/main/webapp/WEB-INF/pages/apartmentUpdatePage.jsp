<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Apartment</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
</head>

<body>
<div class="container">
    <form role ="form" class="form-horizontal" action="/apartment/update" method="post">
        <h3>Update Apartment</h3>
         <input type="hidden" name="id" value="${param.id}">

        <input class = "form-control form-group" type = "text" name = "district" placeholder="District" value="${apartment.district}" required>
        <input class = "form-control form-group" type = "text" name = "address" placeholder="Address" value="${apartment.address}">
        <input class = "form-control form-group" type = "number" name = "area" placeholder="Area" value="${apartment.area}" min="1" required>
        <input class = "form-control form-group" type = "number" name = "countOfRooms" placeholder="Count Of Rooms" value="${apartment.countOfRooms}">
        <input class = "form-control form-group" type = "number" name = "price" placeholder="Price" value="${apartment.price}">
        <input type = "submit" class="btn btn-primary" value="Update">
    </form>
</div>
</body>
</html>