<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="properties.pagecontent"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="home.title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<body>
<div>
    <c:import url="/jsp/header.jsp" charEncoding="utf-8"/>
    <div class="d-flex flex-row justify-content-around">
        <div class="list-group" style="width: 35%">
            <h4><fmt:message key="home.best.cocktails"/></h4>
            <c:forEach var="cocktail" items="${requestScope.cocktails}">
                <a href="controller?command=show_cocktail&id=${cocktail.id}"
                   class="list-group-item list-group-item-action flex-column align-items-start">
                    <div class="d-flex container-fluid justify-content-between">
                        <div class="d-flex flex-row">
                            <img src="${cocktail.imageSource}" class="img-fluid" style="width: 25%; height:auto;" alt="">
                            <div class="d-flex flex-column m-2">
                                <h5 class="mb-1">${cocktail.name}</h5>
                                <p class="mb-1" style="white-space: pre-line">${fn:substring(cocktail.instructions, 0, 100)}</p>
                            </div>
                        </div>
                        <small><fmt:formatNumber type="number" maxFractionDigits="2" value="${cocktail.averageMark}"/></small>
                    </div>
                </a>
            </c:forEach>
        </div>
        <div class="list-group" style="width: 35%">
            <h4><fmt:message key="home.best.bartenders"/></h4>
            <c:forEach var="bartender" items="${requestScope.bartenders}">
                <a href="controller?command=profile&id=${bartender.userId}" class="list-group-item list-group-item-action flex-column align-items-start">
                    <div class="d-flex container-fluid justify-content-between">
                        <div class="d-flex flex-row">
                            <img src="${bartender.avatarSource}" class="img-fluid w-25" alt="">
                            <div class="d-flex flex-column m-2">
                                <strong>${bartender.firstName} ${bartender.lastName}</strong>
                                <span><fmt:message key="bartenders.created"/>${bartender.cocktailsCreated}</span>
                                <span><fmt:message key="bartenders.average"/><fmt:formatNumber type="number" maxFractionDigits="2" value="${bartender.averageCocktailsRate}"/></span>
                            </div>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</div>
<c:import url="footer.jsp" charEncoding="utf-8"/>
</body>
</html>