<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Apartments</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <h3><a href = "/">Apartments</a>></h3>>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
          <ul id="groupList" class="nav navbar-nav">
          <li><button type="button" id="add_apartment" class="btn btn-default navbar-btn">Add Apartment</button></li>
          <li><button type="button" id="delete_apartment" class="btn btn-default navbar-btn">Delete Apartment</button></li>
          <li><button type="button" id="update_apartment" class="btn btn-default navbar-btn">Update Apartment</button></li>
          <li><button type="button" id="reset" class="btn btn-default navbar-btn">Reset</button></li>
          </ul>
          <form class="navbar-form navbar-left" role="search" action="/search" method="post">
              <div class="form-group">
                  <input type="number" class="form-control" id="priceFrom" name="priceFrom" placeholder="Min Price">
              </div>
              <div class="form-group">
                  <input type="number" class="form-control" id="priceTo" name="priceTo" placeholder="Max Price">
              </div>
              <button type="submit" class="btn btn-default">Search</button>
          </form>>
      </div>
      </div>
    </nav>

    <table class="table table-striped">
        <thead>
        <tr>
            <td></td>
            <td><b>District</b>></td>
            <td><b>Address</b>></td>
            <td><b>Area</b>></td>
            <td><b>Count of rooms</b>></td>
            <td><b>Price</b>></td>
        </tr>
        </thead>
        <c:forEach items="${apartments}" var="apartment">
        <tr>
            <td><input type="checkbox" name="toDelete[]" value="${apartment.id}" id="checkbox_${contact.id}"/></td>
            <td>${apartment.district}</td>
            <td>${apartment.address}</td>
            <td>${apartment.area}</td>
            <td>${apartment.countOfRooms}</td>
            <td>${apartment.price}</td>

        </tr>
        </c:forEach>
    </table>
 <nav aria-label="Page navigation">
     <ul class="pagination">
         <c:if test="${allPages ne null}">
             <c:forEach var="i" begin="1" end="${allPages}">
                 <li><a href="/?page=<c:out value="${i-1}"/>"><c:out value="${i}"/></a> </li>
             </c:forEach>
         </c:if>
     </ul>
 </nav>
</div>


<script>
    $('#add_apartment').click(function(){
        window.location.href = '/apartmentAddPage';
    });
    $('#reset').click(function(){
        window.location.href = '/reset';
    });
    $('#delete_apartment').click(function(){
        let data = {'toDelete[]' : []};
        $(":checked").each(function (){
            data['toDelete[]'].push($(this).val());
        });
        $.post("/aparment/delete", data, function(data, status){
            window.location.reload();
        });
    });

    $('#update_apartment').click(function(){
        let selected = $(":checked");

        if (selected.length !== 1){
            alert('Please select only 1 element to update');
            return;
        }

     let apartmentId = selected.val();

window.location.href = '/apartmentUpdatePage?id=' + apartmentId;
    });

</script>

</body>>
