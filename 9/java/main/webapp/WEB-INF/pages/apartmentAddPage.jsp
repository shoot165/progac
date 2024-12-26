<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New Apartment</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

</head>


<body>
<div class="container">
    <form role ="form" class="form-horizontal" action="/apartment/add" method="post">
        <h3>Add Apartment</h3>
      <input class = "form-control form-group" type = "text" name = "district" placeholder="District">
      <input class = "form-control form-group" type = "text" name = "address" placeholder="Address">
      <input class = "form-control form-group" type = "number" name = "area" placeholder="Area">
      <input class = "form-control form-group" type = "number" name = "countOfRooms" placeholder="Count Of Rooms">
      <input class = "form-control form-group" type = "number" name = "price" placeholder="Price">
        <input type = "submit" class="btn btn-primary" value="Add">
    </form>
</div>
</body>
</html>